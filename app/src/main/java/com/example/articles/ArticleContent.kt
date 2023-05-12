package com.example.articles

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Text

class ArticleContent : AppCompatActivity() {

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
    }


          private fun setValuestoView() {
//              tvArticleId.text=intent.getStringExtra("articleID")
              tvArticleTitle.text=intent.getStringExtra("articleTitle")
              tvArticleContent.text=intent.getStringExtra("articleContent")
//        tvArticleType.text=intent.getStringExtra("articletype")



          }

    }

