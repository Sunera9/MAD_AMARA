package com.example.madproject

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class applicant_eligible_form : AppCompatActivity() {

    private lateinit var etUserFirstName: EditText
    private lateinit var etUserLastName: EditText
    private lateinit var etUserAddress: EditText
    private lateinit var etUserMobile: EditText
    private lateinit var spinnerUserFamily: Spinner
    private lateinit var uploadFamilyDetails: Button
    private lateinit var uploadMonthlyIncome: Button
    private lateinit var buttonSaveForm: Button
    private lateinit var buttonCancel: EditText

      //database reference
    private lateinit var dbRef: DatabaseReference
    private lateinit var storage: StorageReference


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.applicant_eligible_form)

        //initialize data
        etUserFirstName = findViewById(R.id.edit_FirstName)
        etUserLastName = findViewById(R.id.edit_LastName)
        etUserAddress = findViewById(R.id.edt_postal_address)
        etUserMobile = findViewById(R.id.edt_mobile_no)

        //spinner family details
        val familyOptions = arrayOf("2 Members", "3 Members", "4 Members", "5 Members", "More than 5 Members") //family option array
        spinnerUserFamily = findViewById(R.id.family_members) //initialize spinner
        // Create an ArrayAdapter and set it as the spinner's adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, familyOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerUserFamily.adapter = adapter

        uploadFamilyDetails = findViewById(R.id.butn5_upload)
        //callback to the upload button
        uploadFamilyDetails.setOnClickListener {
            //generate unique document Id
            val doc1Id = dbRef.child("Family Details Document").push().key
            //upload document to the firebase storage
            //val doc1Uri: Uri = /* Get the document URI from your app */
            val documentRef = storage.child("Family Details Document").child("$doc1Id.pdf")
            //val uploadTask = documentRef.putFile(doc1Uri)
        }



        //initialize Firebase database reference
        dbRef = FirebaseDatabase.getInstance().getReference("Eligible_Users")
        storage = FirebaseStorage.getInstance().reference

        buttonSaveForm = findViewById(R.id.btn_submit_user_eligible) //submit button
        //callback to the button
        buttonSaveForm.setOnClickListener{
            saveUserRequest()
        }



    }



    // create Save Request Function
        private fun saveUserRequest(){
            //getting values
            val eligibleUserFirstName = etUserFirstName.text.toString()
            val eligibleUserLastName = etUserLastName.text.toString()
            val eligibleUserAddress = etUserAddress.text.toString()
            val eligibleUserMobile = etUserMobile.text.toString()
            val selectedFamilyNo = spinnerUserFamily.selectedItem.toString()


            //validation check
            if (eligibleUserFirstName.isEmpty()){
                etUserFirstName.error = "Please Fill Required Details!"
            }
            //validation check
            if (eligibleUserLastName.isEmpty()){
                etUserLastName.error = "Please Fill Required Details!"
            }
            //validation check
            if (eligibleUserAddress.isEmpty()){
                etUserAddress.error = "Please Fill Required Details!"
            }
            //validation check
            if (eligibleUserMobile.isEmpty()){
                etUserMobile.error = "Please Fill Required Details!"
            }

            //generate unique id for users
            val eligibleUserId = dbRef.push().key !!

            //create variable
            val eligibleUser = eligibleUserModel(eligibleUserId,eligibleUserFirstName,eligibleUserLastName,eligibleUserAddress,eligibleUserMobile,selectedFamilyNo)

            //put data into firebase
            dbRef.child(eligibleUserId).setValue(eligibleUser)
                .addOnCompleteListener {
                    Toast.makeText(this, "Form Data Added Successfully", Toast.LENGTH_LONG).show()
                }.addOnFailureListener{err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }
    //direct to Fund Receiver Dashboard page

}