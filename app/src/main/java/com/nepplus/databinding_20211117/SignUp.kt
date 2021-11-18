package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityMainBinding
import com.nepplus.databinding_20211117.databinding.ActivitySignUpBinding

class SignUp : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_sign_up)

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



        }

    }

    override fun setValues() {
    }
}