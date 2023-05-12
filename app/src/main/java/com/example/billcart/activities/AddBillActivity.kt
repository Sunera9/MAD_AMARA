package com.example.billcart.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.billcart.models.BillModel
import com.example.billcart.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBillActivity: AppCompatActivity() {

    //Global variables for input fields
    private lateinit var spinOrganization: Spinner
    private lateinit var etAccNo: EditText
    private lateinit var etMobile: EditText
    private lateinit var etBillAmt: EditText
    private lateinit var etEmail: EditText
    private lateinit var etDate: EditText
    private lateinit var btnAddBill: Button

    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_bill)

        spinOrganization = findViewById(R.id.spinCompany)
        etAccNo = findViewById(R.id.etAccNo)
        etMobile = findViewById(R.id.etMobile)
        etBillAmt = findViewById(R.id.etBill)
        etEmail = findViewById(R.id.etEmail)
        etDate = findViewById(R.id.etDate)
        btnAddBill = findViewById(R.id.btnConfirm)

        dbRef =  FirebaseDatabase.getInstance().getReference("Bills")

        btnAddBill.setOnClickListener {
            saveEBillData()
        }
    }

    private fun saveEBillData() {

        //getting values
        val orgName = spinOrganization.selectedItem.toString()
        val accNo = etAccNo.text.toString()
        val mobile = etMobile.text.toString()
        val billAmt = etBillAmt.text.toString()
        val email = etEmail.text.toString()
        val date = etDate.text.toString()

        //Email pattern
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9._-]+")


        if (accNo.isEmpty()){
            etAccNo.error = "Please enter account no"
        }

        if (mobile.isEmpty()){
            etMobile.error = "Please enter mobile no"
        }

        if (billAmt.isEmpty()){
            etBillAmt.error = "Please enter Bill amount"
        }

        if (!emailPattern.matches(email)){
           etEmail.error = "Please enter a valid email address"
            //clear the EditText
            etEmail.text = null
        }

        //Generate custom ID for each fund
   val billID = dbRef.push().key!! //null check

   val bill = BillModel(billID, orgName, accNo, mobile, billAmt, email, date)

        dbRef.child(billID).setValue(bill)
            .addOnCompleteListener{
                Toast.makeText(this, "Data inserted Successfully", Toast.LENGTH_LONG).show()

                etAccNo.text.clear()
                etMobile.text.clear()
                etBillAmt.text.clear()
                etEmail.text.clear()
                etDate.text.clear()


            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()


            }

    }
}