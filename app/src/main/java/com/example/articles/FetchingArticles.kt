package com.example.articles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.articles.ArticlesModel
import com.example.articles.articleAdapter
import com.example.articles.R
import com.google.firebase.database.*

class FetchingArticles : AppCompatActivity() {

    private lateinit var articleView: RecyclerView
    private lateinit var articleList: ArrayList<ArticlesModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        articleView = findViewById(R.id.card_view)
        articleView.layoutManager = LinearLayoutManager(this)
        articleView.setHasFixedSize(true)

        articleList = arrayListOf()

        getArticlesData()
    }

    private fun getArticlesData() {
        articleView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Articles")

        dbRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                articleList.clear()
                if (snapshot.exists()) {
                    for (articleSnap in snapshot.children) {
                        val articleData = articleSnap.getValue(ArticlesModel::class.java)
                        articleList.add(articleData!!)
                    }
                    val mAdapter = articleAdapter(articleList)
                    articleView.adapter = mAdapter

                    mAdapter.setOnItemClickListner(object : articleAdapter.onItemClickListner {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingArticles, ArticleContent::class.java)

                            // Put extras
                            intent.putExtra("articleId", articleList[position].articleId)
                            intent.putExtra("articleTitle", articleList[position].title)
                            intent.putExtra("articleContent", articleList[position].content)
                            intent.putExtra("articleImageUrl", articleList[position].imageUrl)
                            intent.putExtra("articleType", articleList[position].type)
                            startActivity(intent)
                        }
                    })

                    articleView.visibility = View.VISIBLE
                } else {
                    Toast.makeText(this@FetchingArticles, "No articles found", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FetchingArticles, "Error ${error.message}", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}