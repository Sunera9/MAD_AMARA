package com.example.articles


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class ArticleContent : AppCompatActivity() {
//
     private lateinit var tvArticleId:TextView
    private lateinit var tvArticleTitle:TextView
    private lateinit var tvArticleContent:TextView
    private lateinit var tvArticleType:TextView
    private lateinit var tvArticleImage:ImageView



    override fun onCreate(savedInstanceState: Bundle?){

        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_contenttab)
        initView()
        setValuestoView()
    }
    private fun initView() {


        tvArticleImage = findViewById(R.id.contentImage)
        tvArticleTitle = findViewById(R.id.articleTitle)
        tvArticleContent = findViewById(R.id.articleContent)
//        tvArticleType = findViewById(R.id.t)

        val floatingActionButton3 = findViewById<FloatingActionButton>(R.id.floatingActionButton3)
        val button4 = findViewById<Button>(R.id.floatingActionButton4)
        val button5 = findViewById<Button>(R.id.floatingActionButton5)

        floatingActionButton3.setOnClickListener {
            if (button4.visibility == View.GONE) {
                button4.visibility = View.VISIBLE
                button5.visibility = View.VISIBLE
            } else {
                button4.visibility = View.GONE
                button5.visibility = View.GONE
            }
        }

        val floatingActionButton5 = findViewById<Button>(R.id.floatingActionButton5)

        floatingActionButton5.setOnClickListener {
            val articleId = intent.getStringExtra("articleId")
            val imageUrl = intent.getStringExtra("articleImageUrl")
            val intent = Intent(this, ArticleEdit::class.java)
            intent.putExtra("articleId", articleId)
            intent.putExtra("articleImageUrl", imageUrl)
            startActivity(intent)
        }



    }




    private fun setValuestoView() {

        val articleId = intent.getStringExtra("articleId")
        Toast.makeText(this, "Article ID: $articleId", Toast.LENGTH_SHORT).show()

        tvArticleTitle.text=intent.getStringExtra("articleTitle")
        tvArticleContent.text=intent.getStringExtra("articleContent")

        val imageUrl = intent.getStringExtra("articleImageUrl")
        Glide.with(this)
            .load(imageUrl)
            .into(tvArticleImage)

//        tvArticleType.text=intent.getStringExtra("articletype")
    }


}

