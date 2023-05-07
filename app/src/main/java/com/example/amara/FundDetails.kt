package com.example.amara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatSpinner
import com.google.firebase.database.FirebaseDatabase

class FundDetails : AppCompatActivity() {

    private lateinit var tvFundId: TextView
    private lateinit var tvFundName: TextView
    private lateinit var tvFundDesc: TextView
    private lateinit var tvCat: TextView
    private lateinit var tvFundDate: TextView
    private lateinit var tvTarget: TextView
    private lateinit var tvcNo: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fund_details)

        initView()
        setValuesToViews()

        btnEdit.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("fundId").toString(),
                intent.getStringExtra("fundName").toString()
            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("fundId").toString()
            )
        }
    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("AmaraFunds").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Data successfully deleted",Toast.LENGTH_LONG).show()
            val intent = Intent(this, AdminPage::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{
            error ->
            Toast.makeText(this,"Error ${error.message}",Toast.LENGTH_LONG).show()

        }
    }

    private fun initView(){
        tvFundId = findViewById(R.id.tvId)
        tvFundName = findViewById(R.id.tvFundName)
        tvFundDesc = findViewById(R.id.tvFundDesc)
        tvCat = findViewById(R.id.tvCat)
        tvFundDate = findViewById(R.id.tvDate)
        tvTarget = findViewById(R.id.tvTarget)
        tvcNo = findViewById(R.id.tvcNo)
        tvEmail = findViewById(R.id.tvEmail)
        btnEdit = findViewById(R.id.btnEdit)
        btnDelete = findViewById(R.id.btnDelete)

    }

    private fun setValuesToViews(){

        tvFundId.text = intent.getStringExtra("fundId")
        tvFundName.text = intent.getStringExtra("fundName")
        tvFundDesc.text = intent.getStringExtra("fundDesc")
        tvCat.text = intent.getStringExtra("FundCat")
        tvFundDate.text = intent.getStringExtra("endDate")
        tvTarget.text = intent.getStringExtra("target")
        tvcNo.text = intent.getStringExtra("cNo")
        tvEmail.text = intent.getStringExtra("email")
    }

    private fun openUpdateDialog(
        fundId: String,
        fundName: String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.activity_update_form,null)

        mDialog.setView(mDialogView)

        val etFundName = mDialogView.findViewById<EditText>(R.id.etFundName)
        val etFundDesc = mDialogView.findViewById<EditText>(R.id.etFundDesc)
        val spinCat = mDialogView.findViewById<AppCompatSpinner>(R.id.spinCat)
        val etDate = mDialogView.findViewById<EditText>(R.id.etDate)
        val etTarget = mDialogView.findViewById<EditText>(R.id.etTarget)
        val etNumber = mDialogView.findViewById<EditText>(R.id.etNumber)
        val etEmail = mDialogView.findViewById<EditText>(R.id.etEmail)

        val btnUpdate = mDialogView.findViewById<Button>(R.id.btnConfirm)

        etFundName.setText(intent.getStringExtra("fundName").toString())
        etFundDesc.setText(intent.getStringExtra("fundDesc").toString())
        val category = intent.getStringExtra("FundCat").toString()
        val categoryPosition = getCategoryPosition(category)
        spinCat.setSelection(categoryPosition)
        etDate.setText(intent.getStringExtra("endDate").toString())
        etTarget.setText(intent.getStringExtra("target").toString())
        etNumber.setText(intent.getStringExtra("cNo").toString())
        etEmail.setText(intent.getStringExtra("email").toString())


        //mDialog.setTitle("Updating $fundName Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdate.setOnClickListener{
            updateFundData(
                fundId,
                etFundName.text.toString(),
                etFundName.text.toString(),
                spinCat.selectedItem.toString(),
                etDate.text.toString(),
                etTarget.text.toString(),
                etNumber.text.toString(),
                etEmail.text.toString(),

                )
            Toast.makeText(applicationContext, "Form Data Updated", Toast.LENGTH_LONG)


            //Setting updated data to text views
            tvFundName.text = etFundName.text.toString()
            tvFundDesc.text = etFundName.text.toString()
            tvCat.text = intent.getStringExtra("FundCat")
            tvFundDate.text = etDate.text.toString()
            tvTarget.text = etTarget.text.toString()
            tvcNo.text = etNumber.text.toString()
            tvEmail.text = etEmail.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun getCategoryPosition(category: String): Int {
        val categories = resources.getStringArray(R.array.ff_catFeilds)
        return categories.indexOf(category)
    }
    private fun updateFundData(
        id:String,
        name: String,
        desc: String,
        cat:String,
        date: String,
        target: String,
        conNo: String,
        mail: String,
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("AmaraFunds").child(id)
        val updatedFund = FundModel(id,name,desc,cat,date,target,conNo,mail)

        dbRef.setValue(updatedFund)
            .addOnSuccessListener {
                Toast.makeText(this, "Data successfully updated", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Error ${error.message}", Toast.LENGTH_LONG).show()
            }
    }
}
