package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityLogInBinding
import com.nepplus.databinding_20211117.databinding.ActivityMain2Binding

class MainActivity2 : BaseActivity() {

    lateinit var binding: ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView(this,R.layout.activity_main2)

        setupEvent()
        setValues()
    }

    override fun setupEvent() {

    }

    override fun setValues() {

    }
}