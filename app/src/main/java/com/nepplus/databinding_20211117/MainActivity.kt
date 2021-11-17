package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityMainBinding
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

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
                ServerUtil.postRequestLogIN(inputEmail,inputPW, object:ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObject: JSONObject) {

//                        이 response가 main입장에서는 로그인 API를 호출하고 JsonObj를 받아서 돌아온 상황이기 때문에
                        Log.d("화면에서의 obj",jsonObject.toString())
                    }

                })


             }

    }

    override fun setValues() {

    }



}