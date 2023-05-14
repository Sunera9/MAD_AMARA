package com.example.amara

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FundDetailsUser : AppCompatActivity() {


    private lateinit var etDonate: EditText
    private lateinit var tvFundName: TextView
    private lateinit var tvFundDesc: TextView
    private lateinit var tvCat: TextView
    private lateinit var tvFundDate: TextView
    private lateinit var tvTarget: TextView
    private lateinit var btnDonate: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_fund_details_user)

        etDonate = findViewById(R.id.edt_txtDonate)
        btnDonate = findViewById(R.id.btnDonate)

        initView()
        setValuesToViews()

        //get donation amount

            btnDonate.setOnClickListener {

                val donAmout= etDonate.text.toString()

                if(donAmout.isEmpty()){
                    etDonate.error = "Please add a proper fund Amount"
                }else {
                    saveDonations()

                    val intent = Intent(this, PaymentActivity::class.java)
                        .putExtra("donatedAmount",etDonate.text.toString())
                    startActivity((intent))
                }
            }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun saveDonations(){
//        val donAmout= etDonate.text.toString()

//        if(donAmout.isEmpty()){
//            etDonate.error = "Please add a proper fund Amount"
//        }
    }


    private fun initView(){

        tvFundName = findViewById(R.id.tvFundName)
        tvFundDesc = findViewById(R.id.tvFundDesc)
        tvCat = findViewById(R.id.tvCat)
        tvFundDate = findViewById(R.id.tvDate)
        tvTarget = findViewById(R.id.tvTarget)

    }

    private fun setValuesToViews(){

        tvFundName.text = intent.getStringExtra("fundName")
        tvFundDesc.text = intent.getStringExtra("fundDesc")
        tvCat.text = intent.getStringExtra("FundCat")
        tvFundDate.text = intent.getStringExtra("endDate")
        tvTarget.text = intent.getStringExtra("target")

    }


}