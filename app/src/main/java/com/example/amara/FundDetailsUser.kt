package com.example.amara

import android.annotation.SuppressLint
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

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_fund_details_user)

        etDonate = findViewById(R.id.edt_txtDonate)
        btnDonate = findViewById(R.id.btnDonate)

        dbRef = FirebaseDatabase.getInstance().getReference("AmaraFunds")

        initView()
        setValuesToViews()

        btnDonate.setOnClickListener{
           saveDonations()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun saveDonations(){
       //get donation amount
        val donAmout= etDonate.text.toString()

            if(donAmout.isNotEmpty())
                try {
                    val donationAmount = donAmout.toDouble()
                    // do something with the donationAmount
                    dbRef.child("donations").setValue(donationAmount)
                } catch (e: NumberFormatException) {
                    // handle the case where the input is not a valid floating-point number
                    Toast.makeText(this, "Invalid input. Please enter a valid donation amount.", Toast.LENGTH_SHORT).show()
                }
            else {
                // handle the case where the input is empty
                Toast.makeText(this, "Please enter a donation amount.", Toast.LENGTH_SHORT).show()

            }



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
