package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityMyProfileBinding

class MyProfileActivity : BaseActivity() {

    lateinit var binding : ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)

        setupEvent()
        setValues()
    }

    override fun setupEvent() {

    }

    override fun setValues() {

    }
}