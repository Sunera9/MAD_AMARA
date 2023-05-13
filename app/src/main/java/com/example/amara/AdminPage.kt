 package com.example.amara

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amara.adapters.FundAdapter
import com.google.firebase.database.*

class AdminPage : AppCompatActivity() {

    private lateinit var fundRecyclerView: RecyclerView
    private lateinit var fundList: ArrayList<FundModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_admin_page)

        fundRecyclerView = findViewById(R.id.rv_funds)
        fundRecyclerView.layoutManager = LinearLayoutManager(this)
        fundRecyclerView.setHasFixedSize(true)

        fundList = arrayListOf<FundModel>()

        getFundData()

        val btnAdd = findViewById<ImageButton>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
        }
    }

    private fun getFundData(){
        fundRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("AmaraFunds")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                fundList.clear()
                if(snapshot.exists()){
                    for(fundSnap in snapshot.children){
                        val fundData = fundSnap.getValue(FundModel::class.java)
                        fundList.add(fundData!!)
                    }
                    val mAdapter = FundAdapter(fundList)
                    fundRecyclerView.adapter = mAdapter

                    mAdapter.setItemOnClickListener(object : FundAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@AdminPage, FundDetails:: class.java)

                            //put extras
                            intent.putExtra("fundId", fundList[position].fundId)
                            intent.putExtra("fundName", fundList[position].fundName)
                            intent.putExtra("fundDesc", fundList[position].desc)
                            intent.putExtra("FundCat", fundList[position].cat)
                            intent.putExtra("endDate", fundList[position].date)
                            intent.putExtra("target", fundList[position].target)
                            intent.putExtra("cNo", fundList[position].num)
                            intent.putExtra("email", fundList[position].email)
                            startActivity(intent)
                        }

                    })

                    fundRecyclerView.visibility = View.VISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}