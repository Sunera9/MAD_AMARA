package com.example.amara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.amara.R


class PaymentActivity : AppCompatActivity() {

    private lateinit var tvAmount: TextView
    private lateinit var btnPay: Button
    private lateinit var etcNoEditText: EditText
    private lateinit var etXPEditText: EditText
    private lateinit var etXP2EditText: EditText
    private lateinit var cvvetEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_payment)

        tvAmount = findViewById((R.id.tvAmount))
        btnPay = findViewById(R.id.btnPay)
        etcNoEditText = findViewById(R.id.etcNo)
        etXPEditText = findViewById(R.id.etXP)
        etXP2EditText = findViewById(R.id.etXP2)
        cvvetEditText = findViewById(R.id.cvvet)

        val donatedAmount = intent.getStringExtra("donatedAmount")

        tvAmount.text = donatedAmount

        btnPay.setOnClickListener{

            val etcNo = etcNoEditText.text.toString().trim()
            val etXP = etXPEditText.text.toString().trim()
            val etXP2 = etXP2EditText.text.toString().trim()
            val cvvet = cvvetEditText.text.toString().trim()

            // Validate etcNo field
            if (etcNo.isEmpty()) {
                etcNoEditText.error = "Please enter your Card Number"
                return@setOnClickListener
            } else if (etcNo.length != 12) {
                etcNoEditText.error = "Card Number should be exactly 12 Numbers"
                return@setOnClickListener
            }

            // Validate etXP field
            if (etXP.isEmpty()) {
                etXPEditText.error = "Please enter Year of expiration"
                return@setOnClickListener
            } else if (etXP.length != 2) {
                etXPEditText.error = "This should be exactly 2 Numbers"
                return@setOnClickListener
            }

            // Validate etXP2 field
            if (etXP2.isEmpty()) {
                etXP2EditText.error = "Please enter Month in expiration"
                return@setOnClickListener
            } else if (etXP2.length != 2) {
                etXP2EditText.error = "This should be exactly 2 Numbers"
                return@setOnClickListener
            }

            // Validate cvvet field
            if (cvvet.isEmpty()) {
                cvvetEditText.error = "Please enter your CVV"
                return@setOnClickListener
            } else if (cvvet.length != 3) {
                cvvetEditText.error = "CVV should be exactly 3 Numbers"
                return@setOnClickListener
            }

            val userD = tvAmount.text.toString().toDouble()
            val amaraD = userD * 0.05

            val intent = Intent(this, ThankYouActivity::class.java)
            intent.putExtra("AmaraPlus", amaraD)
            startActivity(intent)
        }
    }
}
