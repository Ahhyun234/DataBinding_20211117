package com.nepplus.databinding_20211117

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityLoginBinding
import com.nepplus.databinding_20211117.utils.ContextUtil
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setupEvent()
        setValues()

    }

    override fun setupEvent() {

        binding.autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->
//            Log.d("체크박스",isChecked.toString())
//              선택한 값을 ContextUtil 로 저장
            ContextUtil.setAutoLogin(mContext,isChecked)


        }

        binding.btnSignUp.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        binding.btnLogIn.setOnClickListener {
            val inputEmail = binding.edtID.text.toString()
            val inputPW = binding.edtPW.text.toString()

//            서버에 이메일/비번이 맞는 계정인지 로그인 요청
            ServerUtil.postRequestLogIN(inputEmail,inputPW,object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObject: JSONObject) {

//                        이 response가 main입장에서는 로그인 API를 호출하고 JsonObj를 받아서 돌아온 상황이기 때문에
                        Log.d("화면에서의 obj", jsonObject.toString())

                        val code = jsonObject.getInt("code")

//                       code:200 -> 로그인 성공 토스트

                        runOnUiThread {
                            if (code == 200) {
//                                로그인 성공 페이지 로그인 성공 시 그 사람의 닉네임 추출해서 표출

                                val dataObj = jsonObject.getJSONObject("data")
                                val userObj = dataObj.getJSONObject("user")
                                val nick_name = userObj.getString("nick_name")

                                Toast.makeText(mContext, "${nick_name}님 환영합니다.", Toast.LENGTH_SHORT)
                                    .show()

                                val token = jsonObject.getString("token")
//                                **따낸 토큰을 Shared preference라는 공간에 토큰 저장
                                ContextUtil.setToken(mContext,token)

//                                //아이디로 입력한 이메일 -> Sharepreference에 저장
                                ContextUtil.setLoginEmail(mContext,inputEmail)


//                              ////////////////////////////메인으로 이동
                                val myIntent = Intent(mContext,MainActivity2::class.java)
                                startActivity(myIntent)
                                finish()

                            } else {
                                val reason = jsonObject.getString("message")
                                Toast.makeText(mContext, reason.toString(), Toast.LENGTH_SHORT)
                                    .show()
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

    binding.edtID.setText(ContextUtil.getLoginEmail(mContext))
        binding.autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)

    }


}