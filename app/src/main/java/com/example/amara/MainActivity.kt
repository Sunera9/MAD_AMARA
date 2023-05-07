package com.example.amara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    //Global variables created for input fields

    private lateinit var edtName : EditText
    private lateinit var edtDesc : EditText
    private lateinit var spinnerCat : Spinner
    private lateinit var edtDate : EditText
    private lateinit var edtEmail : EditText
    private lateinit var targetFill : EditText
    private lateinit var numFill : EditText
    private lateinit var btnSubmit : Button

    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        edtName = findViewById(R.id.edt_txt_Name)
        edtDesc = findViewById(R.id.edt_txt_desc)
        spinnerCat = findViewById(R.id.spinCat)
        edtDate = findViewById(R.id.etDate)
        edtEmail= findViewById(R.id.etEmail)
        targetFill = findViewById(R.id.etTarget)
        numFill = findViewById(R.id.etNumber)
        btnSubmit = findViewById(R.id.btn_submit)

        dbRef = FirebaseDatabase.getInstance().getReference("AmaraFunds")

        btnSubmit.setOnClickListener{



            val fundName = edtName.text.toString().trim()
            val desc = edtDesc.text.toString().trim()
            val email = edtEmail.text.toString().trim()


            //Email pattern
            val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+")


            if(fundName.isEmpty()){
                edtName.error = "Fund Name is Mandetory"
                return@setOnClickListener
            } else if(desc.isEmpty()){
                edtDesc.error = "Enter a fund Description"
                return@setOnClickListener
            } else if (desc.split(" ").size > 50){
                // Show an error message
                edtDesc.error = "Description should be less than 200 words"

            }else if (!emailPattern.matches(email)){
                edtEmail.error = "Please enter a valid email address"
                // Clear the EditText
                edtEmail.text = null
                return@setOnClickListener

            }else {
                saveFundData()
            }


            val intent = Intent(this, AdminPage::class.java)
            startActivity((intent))



        }

    }

    private fun saveFundData(){

        //getting Values
        val fundName = edtName.text.toString()
        val desc = edtDesc.text.toString()
        val cat = spinnerCat.selectedItem.toString()
        val date = edtDate.text.toString()
        val target =  targetFill.text.toString()
        val num = numFill.text.toString()
        val email = edtEmail.text.toString()


        //Generate custom ID for each fund
        val fundId = dbRef.push().key!! //null check
        val currentAmount = 0

        val fund = FundModel(fundId, fundName,desc,date,cat,target,num,email)

        dbRef.child(fundId).setValue(fund)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted Successfully.", Toast.LENGTH_SHORT).show()

                edtName.text.clear()
                edtDesc.text.clear()
                edtEmail.text.clear()
                spinnerCat.adapter = null
                edtDate.text.clear()
                targetFill.text.clear()
                numFill.text.clear()

            }.addOnFailureListener{err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()

            }
    }
}