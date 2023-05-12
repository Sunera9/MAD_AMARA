package com.example.billcart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.billcart.R
import com.example.billcart.models.BillModel

class BillAdapter (private val billList: ArrayList<BillModel>) :
    RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillAdapter.ViewHolder {
       val itemView =  LayoutInflater.from(parent.context).inflate(R.layout.bill_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentBill = billList[position]
        holder.tvBillName.text = currentBill.orgName
    }

    override fun getItemCount(): Int {
       return billList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvBillName : TextView = itemView.findViewById(R.id.tvBillName)

    }

}