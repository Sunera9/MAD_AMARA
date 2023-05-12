package com.example.articles
import android.view.LayoutInflater
import android.view.TextureView
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

class articleAdapter (private val articleList:ArrayList<ArticlesModel>):
    RecyclerView.Adapter<articleAdapter.ViewHolder>(){

    private lateinit var mListner: onItemClickListner

      interface onItemClickListner{
          fun onItemClick(postion:Int)
      }

    fun setOnItemClickListner(clickListner: onItemClickListner){
        mListner= clickListner
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): articleAdapter.ViewHolder {
      val itemView=LayoutInflater.from(parent.context).inflate(R.layout.article_card_view,parent,false)
        return ViewHolder(itemView,mListner)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val currentArticle= articleList[position]
        holder.tvArticleTitle.text = currentArticle.title
        holder.tvCardContent.text= currentArticle.content
        Glide.with(holder.itemView.context).load(currentArticle.imageUrl).into(holder.titleImage)
    }


    override fun getItemCount(): Int {
        return articleList.size
    }

    class ViewHolder(itemView:android.view.View,clickListner: onItemClickListner): RecyclerView.ViewHolder(itemView) {
        val tvArticleTitle: TextView = itemView.findViewById(R.id.tvArticleTitle)

        val tvCardContent: TextView = itemView.findViewById(R.id.tvCardContent)
        val titleImage: ImageView = itemView.findViewById(R.id.titleImage)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
            }

    }
}