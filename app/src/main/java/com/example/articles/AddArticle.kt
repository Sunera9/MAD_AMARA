package com.example.articles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddArticle : AppCompatActivity() {
    private lateinit var type: Spinner
    private lateinit var title: EditText
    private lateinit var content: EditText
    private lateinit var btnSaveData: Button
    private lateinit var imageView: ImageView

    private lateinit var dbRef: DatabaseReference
    private lateinit var storageReference: StorageReference
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_articles)

        type = findViewById(R.id.spinner3)
        title = findViewById(R.id.edittxt_titleedit)
        content = findViewById(R.id.edittxt_articlecontent)
        btnSaveData = findViewById(R.id.buttonedit)
        imageView = findViewById(R.id.imageViewedit)

        dbRef = FirebaseDatabase.getInstance().getReference("Articles")
        storageReference = FirebaseStorage.getInstance().reference.child("article_images")

        imageView.setOnClickListener {
            selectImage()
        }

        btnSaveData.setOnClickListener {
            saveArticlesData()
        }
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data
            imageView.setImageURI(selectedImageUri)
        }
    }

    private fun saveArticlesData() {
        val type = type.selectedItem.toString()
        val title = title.text.toString()
        val content = content.text.toString()


        if (selectedImageUri == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_LONG).show()
            return
        }

        val articleId = dbRef.push().key!!

        val imageRef = storageReference.child("$articleId.jpg")
        imageRef.putFile(selectedImageUri!!)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val article = ArticlesModel(articleId, title, content, type, uri.toString())
                    dbRef.child(articleId).setValue(article)
                        .addOnCompleteListener {
                            Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        .addOnFailureListener { err ->
                            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                        }
                }
            }
            .addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}
