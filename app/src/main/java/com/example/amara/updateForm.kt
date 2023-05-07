package com.example.amara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class updateForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_update_form)
    }
}