package com.example.madproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class request_fund_form: AppCompatActivity() {

    private lateinit var etReceiverFName: EditText
    private lateinit var etReceiverLName: EditText
    private lateinit var etReceiverMobile: EditText
    private lateinit var spinnerReceiverReason: Spinner
    //private lateinit var uploadReason: EditText
    private lateinit var buttonSendForm: Button

    //database reference
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_fund_form)

        //initialize data
        etReceiverFName = findViewById(R.id.edt_firstName)
        etReceiverLName = findViewById(R.id.edt_lastName)
        etReceiverMobile = findViewById(R.id.edt_phone_no)
        //spinner family details
        val familyOptions = arrayOf("Education", "Health", "Home Construction", "Other") //family option array
       spinnerReceiverReason = findViewById(R.id.request_Reason_type) //initialize spinner
        // Create an ArrayAdapter and set it as the spinner's adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, familyOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerReceiverReason.adapter = adapter
        //etReceiverReason = findViewById(R.id.btn_upload) //upload document

        buttonSendForm = findViewById(R.id.btn_submit_request)

        dbRef = FirebaseDatabase.getInstance().getReference("Eligible_Users")

        //callback to the button
        buttonSendForm.setOnClickListener{
            sendUserRequest()
        }
    }

    // create Save Request Function
    private fun sendUserRequest(){
        //getting values
        val receiverFirstName = etReceiverFName.text.toString()
        val receiverLastName = etReceiverLName.text.toString()
        val receiverMobile = etReceiverMobile.text.toString()
        val selectedReasonType = spinnerReceiverReason.selectedItem.toString() //spinner
        //val receiverReason = et

        //validation check
        if (receiverFirstName.isEmpty()){
            etReceiverFName.error = "Please Fill Required Details!"
        }
        //validation check
        if (receiverLastName.isEmpty()){
            etReceiverLName.error = "Please Fill Required Details!"
        }
        //validation check
        if (receiverMobile.isEmpty()){
            etReceiverMobile.error = "Please Fill Required Details!"
        }

        //generate unique id for users
        val fundReceiverId = dbRef.push().key !!

        //create variable
        val fundReceiver = fundReceiverModel(fundReceiverId,receiverFirstName,receiverLastName,receiverMobile,selectedReasonType)

        //put data into firebase
        dbRef.child(fundReceiverId).setValue(fundReceiver)
            .addOnCompleteListener {
                Toast.makeText(this, "Form Data Added Successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }


    }
    //receiver
}