package com.example.articles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class ArticleEdit : AppCompatActivity() {


    private lateinit var etArticleTitle: EditText
    private lateinit var etArticleContent: EditText
    private lateinit var btnSaveChanges: Button
    private  lateinit var eArticleImage : ImageView



    private val dbRef: DatabaseReference by lazy {
        FirebaseDatabase.getInstance().reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.articleedit)

        etArticleTitle = findViewById(R.id.edittxt_titleedits)
        etArticleContent = findViewById(R.id.edittxt_articlecontentedit)
        btnSaveChanges = findViewById(R.id.btnUpdate)
       eArticleImage = findViewById(R.id.imageViewedits)


        // Retrieve the image URL from the intent
        val imageUrl = intent.getStringExtra("articleImageUrl")

        // Load the image into the ImageView using Glide
        Glide.with(this)
            .load(imageUrl)
            .into(eArticleImage)

        // Get the article ID from the intent extra
        val articleId = intent.getStringExtra("articleId")
        if (articleId == null) {
            // Handle the case where the articleId is null
            Toast.makeText(this, "Article ID is null", Toast.LENGTH_SHORT).show()
            finish() // End the current activity
        } else {
            // Get a reference to the article in the database
            val articleRef = dbRef.child("Articles").child(articleId)

            // Set a listener to retrieve the article data
            articleRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    // Get the article data from the snapshot
                    val articleData = snapshot.getValue(ArticlesModel::class.java)

                    // Populate the fields with the retrieved data
                    etArticleTitle.setText(articleData?.title)
                    etArticleContent.setText(articleData?.content)

                    // Set the image of the article
                    val imageUrl = articleData?.imageUrl
                    if (imageUrl != null) {
                        val imageUri = Uri.parse(imageUrl)
                        eArticleImage.setImageURI(imageUri)
                    }

                }
            }.addOnFailureListener { exception ->
                // Handle the exception appropriately
                Toast.makeText(
                    this,
                    "Failed to retrieve article: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Set a click listener on the Save Changes button
            btnSaveChanges.setOnClickListener {
                // Get the values from the fields
                val newTitle = etArticleTitle.text.toString()
                val newContent = etArticleContent.text.toString()





                // Start the activity to select an image
                startActivityForResult(intent, 1)


                // Update the article data in the database
                articleRef.child("title").setValue(newTitle)
                    .addOnSuccessListener {
                        // Show a toast to confirm the changes
                        Toast.makeText(this, "Article updated successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        // Show an error message if the update operation failed
                        Toast.makeText(
                            this,
                            "Failed to update article: ${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                articleRef.child("content").setValue(newContent)


            }


        }


    }
    fun selectImage(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get the selected image URI
            val imageUri = data.data

            // Load the image into the ImageView using Glide
            Glide.with(this)
                .load(imageUri)
                .into(eArticleImage)

            // Get the article ID from the intent extra
            val articleId = intent.getStringExtra("articleId")

            // Get a reference to the article in the database
            val articleRef = articleId?.let { dbRef.child("Articles").child(it) }

            // Upload the new image to Firebase Storage
            val imageRef = FirebaseStorage.getInstance().reference.child("images/${UUID.randomUUID()}")

            // Upload the file to Firebase Storage
            imageRef.putFile(imageUri!!)
                .addOnSuccessListener { taskSnapshot ->
                    // Get the download URL of the uploaded file
                    imageRef.downloadUrl.addOnSuccessListener { imageUrl ->
                        // Update the article data in the database with the new image URL
                        if (articleRef != null) {
                            articleRef.child("imageUrl").setValue(imageUrl.toString())
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle the exception appropriately
                    Toast.makeText(
                        this,
                        "Failed to upload image: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }








}
