package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setupEvent()
        setValues()

    }

    override fun setupEvent() {
        binding.btnLogIn.setOnClickListener {
            val inputEmail = binding.edtID.text.toString()
            val inputPW = binding.edtPW.text.toString()

//            서버에 이메일/비번이 맞는 계정인지 로그인 요청

        }

    }

    override fun setValues() {

    }



}