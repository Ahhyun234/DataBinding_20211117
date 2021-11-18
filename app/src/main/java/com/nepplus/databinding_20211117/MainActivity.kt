package com.nepplus.databinding_20211117

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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

        binding.btnSignUp.setOnClickListener {
            val myIntent = Intent(mContext,SignUp::class.java)
            startActivity(myIntent)
        }

        binding.btnLogIn.setOnClickListener {
            val inputEmail = binding.edtID.text.toString()
            val inputPW = binding.edtPW.text.toString()

//            서버에 이메일/비번이 맞는 계정인지 로그인 요청
                ServerUtil.postRequestLogIN(inputEmail,inputPW, object:ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObject: JSONObject) {

//                        이 response가 main입장에서는 로그인 API를 호출하고 JsonObj를 받아서 돌아온 상황이기 때문에
                        Log.d("화면에서의 obj",jsonObject.toString())

                        val code = jsonObject.getInt("code")
                        val reason = jsonObject.getString("message")

//                       code:200 -> 로그인 성공 토스트

                        runOnUiThread{
                            if (code==200){
                                Toast.makeText(mContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                Toast.makeText(mContext, reason.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }

//                        message String으로 실패 사유를 알려준다.
//                        파싱으로 추출해서 로그인 실패 대신 사유를 띄우자
//                        Log.d("로그인 코드값",code.toString())
                    }

                })


             }

    }

    override fun setValues() {

    }



}