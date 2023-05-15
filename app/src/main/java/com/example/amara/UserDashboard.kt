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

class UserDashboard : AppCompatActivity() {

    private lateinit var fundRecyclerView: RecyclerView
    private lateinit var fundList: ArrayList<FundModel>
    private lateinit var dbRef : DatabaseReference

    private lateinit var btnHome:ImageButton
    private lateinit var btnApply:ImageButton
    private lateinit var btnDonate:ImageButton
    private lateinit var btnFeed:ImageButton
    private lateinit var btnBills:ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_user_dashboard)

        fundRecyclerView = findViewById(R.id.rv_funds)
        fundRecyclerView.layoutManager = LinearLayoutManager(this)
        fundRecyclerView.setHasFixedSize(true)

        btnHome = findViewById(R.id.ibHome)
        btnApply= findViewById(R.id.ibDonate)
        btnDonate = findViewById(R.id.ibFund)
        btnFeed = findViewById(R.id.ibFeed)
        btnBills = findViewById(R.id.ibBills)

        fundList = arrayListOf<FundModel>()

        getFundData()


        btnHome.setOnClickListener {
            val intent = Intent(this, FundMainActivity::class.java)
            startActivity(intent)
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, FundMainActivity::class.java)
            startActivity(intent)
        }

        btnApply.setOnClickListener {
            val intent = Intent(this, FundMainActivity::class.java)
            startActivity(intent)
        }

        btnDonate.setOnClickListener {
            val intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
        }

        btnFeed.setOnClickListener {
            val intent = Intent(this, FundMainActivity::class.java)
            startActivity(intent)
        }

        btnBills.setOnClickListener {
            val intent = Intent(this, FundMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getFundData(){
        fundRecyclerView.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("AmaraFunds")

        dbRef.addValueEventListener(object : ValueEventListener {
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
                            val intent = Intent(this@UserDashboard, FundDetailsUser:: class.java)

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