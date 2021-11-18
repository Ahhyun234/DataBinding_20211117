package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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

        binding.btnIdCheck.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()

//            서버에 중복 확인

            ServerUtil.getRequestDupCheck("EMAIL",inputEmail, object:ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {

                    val code = jsonObject.getInt("code")
                    runOnUiThread{

                        if (code==200 ){
                            binding.txtEmailDupCheck.text="사용해도 좋습니다."

                        }
                        else{
                            binding.txtEmailDupCheck.text= "중복된 이메일입니다."
                        }

                    }
                }

            } )

        }

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


                        if (code == 200) {
                            val dataObj = jsonObj.getJSONObject("data")
                            val userObj = dataObj.getJSONObject("user")
                            val nick_name = userObj.getString("nick_name")
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