package com.example.billcart.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.billcart.R


class MainActivity : AppCompatActivity() {

    private lateinit var btnElectricity: Button
    private lateinit var btnfav: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val firebase :DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnElectricity = findViewById(R.id.btnElectricity)
        btnfav = findViewById(R.id.btnfav)

        btnElectricity.setOnClickListener {
            val intent = Intent(this, AddBillActivity::class.java)
            startActivity(intent)
        }

        btnfav.setOnClickListener{
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }



    }
}