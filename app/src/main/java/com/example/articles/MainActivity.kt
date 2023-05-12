package com.example.articles


import com.example.articles.AddArticle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsertData = findViewById(R.id.addButton)

        btnInsertData.setOnClickListener{
            val intent = Intent(this, AddArticle::class.java)
            startActivity(intent)
        }

        btnFetchData = findViewById(R.id.fetchData)

        btnFetchData.setOnClickListener{
            val intent = Intent(this, FetchingArticles::class.java)
            startActivity(intent)
        }
    }
}
