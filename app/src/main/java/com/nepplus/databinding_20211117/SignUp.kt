package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityMainBinding
import com.nepplus.databinding_20211117.databinding.ActivitySignUpBinding
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class SignUp : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        setValues()
        setupEvent()

    }

    override fun setupEvent() {

        binding.btnOk.setOnClickListener {
//            입력한 이메일 비밀번호 닉네임 변수로 담아두자
            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPW.text.toString()
            val inputNickname = binding.edtNickName.text.toString()


//            서버의 회원가입 기능에 전달(request)/ 돌아온 응답 대응(response)

            ServerUtil.putRequestSignUp(
                inputEmail,
                inputPw,
                inputNickname,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObj: JSONObject) {

                        val code = jsonObj.getInt("code")
                        val message = jsonObj.getString("message")
                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nick_name = userObj.getString("nick_name")

                        if (code == 200) {
//                        가입한 사람의 닉네임을 추출해서 메세지 띄우기 + 회원가입 화면 종류
                            runOnUiThread {
                                Toast.makeText(
                                    mContext,
                                    "${nick_name}님 회원가입 축하합니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            finish()
                            }
                        } else {
                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
//                        실패-> 서버가 알려주는 실패 사유를 알려주자 
                        }


                    }

                })

        }

    }

    override fun setValues() {
    }
}