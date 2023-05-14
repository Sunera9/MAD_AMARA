package com.example.amara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ThankYouActivity : AppCompatActivity() {

    private lateinit var amaraAmount: TextView
    private lateinit var btnThankyou: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_thank_you)

        amaraAmount = findViewById(R.id.tvDonAmount)
        btnThankyou = findViewById(R.id.btnToHome)

        val amaraAmountValue = intent.getDoubleExtra("AmaraPlus", 100.00)

        amaraAmount.text = amaraAmountValue.toString()

        btnThankyou.setOnClickListener{
            val intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
        }
    }
}
