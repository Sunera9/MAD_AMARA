package com.example.billcart.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.billcart.R
import com.example.billcart.adapters.BillAdapter
import com.example.billcart.models.BillModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var billRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var billList: ArrayList<BillModel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        billRecyclerView = findViewById(R.id.rvBill)
        billRecyclerView.layoutManager = LinearLayoutManager(this )
        billRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        billList = arrayListOf<BillModel>()

        getBillData()

    }

    private fun getBillData(){
        billRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Bills")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                billList.clear()
                if (snapshot.exists()) {
                    for (billSnap in snapshot.children) {
                        val billData = billSnap.getValue(BillModel::class.java)
                        billList.add(billData!!)
                    }
                    val mAdapter = BillAdapter(billList)
                    billRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object :BillAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity, BillDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("billId", billList[position].billID)
                            intent.putExtra("organization", billList[position].orgName)
                            intent.putExtra("billAmt", billList[position].billAmt)
                            intent.putExtra("date", billList[position].date)
                            startActivity(intent)
                        }

                    })

                    billRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}