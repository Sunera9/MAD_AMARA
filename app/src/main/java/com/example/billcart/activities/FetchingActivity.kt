package com.example.billcart.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billcart.R
import com.example.billcart.models.BillModel

class FetchingActivity : AppCompatActivity() {

    private lateinit var billRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var empList: ArrayList<BillModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        billRecyclerView = findViewById(R.id.rvBill)
        billRecyclerView.layoutManager = LinearLayoutManager(this )
        billRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        empList = arrayListOf<BillModel>()

        getBillData()

    }

    private fun getBillData(){
        billRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.

    }

}