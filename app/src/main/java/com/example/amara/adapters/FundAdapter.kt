package com.example.amara.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amara.FundModel
import com.example.amara.R

class FundAdapter(private val fundList: ArrayList<FundModel>) : RecyclerView.Adapter<FundAdapter.ViewHolder>(){

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setItemOnClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fund_list_item, parent,false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val thisFund = fundList[position]
        holder.tvFundName.text = thisFund.fundName
        holder.tvFundDesc.text = thisFund.desc
    }

    override fun getItemCount(): Int {
        return fundList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvFundName : TextView = itemView.findViewById((R.id.tvFundTitle))
        val tvFundDesc : TextView = itemView.findViewById(R.id.tvCardDesc)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}

