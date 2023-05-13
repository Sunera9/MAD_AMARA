package com.example.billcart.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.billcart.R

class BillDetailsActivity: AppCompatActivity() {
    private lateinit var tvBillId: TextView
    private lateinit var tvOrganization: TextView
    private lateinit var tvBillAmt: TextView
    private lateinit var tvDate: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_details)

        initView()
        setValuesToViews()
    }

    private fun initView() {
        tvBillId = findViewById(R.id.tvBillId)
        tvOrganization = findViewById(R.id.tvOrg)
        tvBillAmt = findViewById(R.id.tvBillAmt)
        tvDate = findViewById(R.id.tvDate)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)

    }

    private fun setValuesToViews() {

        tvBillId.text =intent.getStringExtra("billId")
        tvOrganization.text = intent.getStringExtra("organization")
        tvBillAmt.text = intent.getStringExtra("billAmt")
        tvDate.text = intent.getStringExtra("date")

    }

}