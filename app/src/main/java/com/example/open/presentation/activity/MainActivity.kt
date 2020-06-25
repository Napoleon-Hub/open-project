package com.example.open.presentation.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.open.R
import com.example.open.databinding.ActivityMainBinding
import com.example.open.databinding.DataDisplayFragmentBinding
import com.example.open.model.AnimalsViewModel
import com.example.open.presentation.adapter.RecyclerAdapter

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

}