package com.example.madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fund_reciever_dashboard)

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()

        //direct to eligible page
        var button1 = findViewById<Button>(R.id.btn_chk_elgible)
        button1.setOnClickListener{
            val intent1 = Intent(this, applicant_eligible_form::class.java)
            startActivity(intent1)
        }
        //direct to request page
        var button2 = findViewById<Button>(R.id.btn_request_fund)
        button2.setOnClickListener{
            val intent2 = Intent(this, request_fund_form::class.java)
            startActivity(intent2)
        }
        //direct to Manage fund page
        var button3 = findViewById<Button>(R.id.btn_mng_mony)
        button3.setOnClickListener{
            val intent3 = Intent(this, manage_fund_recieved_list::class.java)
            startActivity(intent3)
        }
        //direct to bill page
        var button4 = findViewById<Button>(R.id.btn_pay_bill)
        button4.setOnClickListener{
            val intent4 = Intent(this, manage_fund_recieved_list::class.java) //check this with ran
            startActivity(intent4)
        }
        //direct to login page
        var button5 = findViewById<Button>(R.id.btn_pay_bill)
        button5.setOnClickListener{
            val intent5 = Intent(this, manage_fund_recieved_list::class.java) //check this with ran
            startActivity(intent5)
        }
    }
}