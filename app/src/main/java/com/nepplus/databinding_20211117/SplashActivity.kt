package com.nepplus.databinding_20211117

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivitySplashBinding
import com.nepplus.databinding_20211117.utils.ContextUtil

class SplashActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        setupEvent()
        setValues()
    }

    override fun setupEvent() {

    }

    override fun setValues() {

//        2.5초가 지나면 어느 화면으로 갈지 검사

        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({

            val myIntent : Intent

//            이전에 로그인에 성공한 기록이 있는가? -> 토큰이 있는가?
//            자동 로그인을 체크 했는가? -> 있으면 메인 없으면 로그인 화면으로 이동
//이 두가지가 보두 충족되면 이동 => && 사용
            if (ContextUtil.getAutoLogin(mContext)&& ContextUtil.getToken(mContext) !==""){
                myIntent = Intent(mContext,MainActivity2::class.java)
            }
            else{
                myIntent = Intent(mContext,LoginActivity::class.java)
                            }

            startActivity(myIntent)

                              },2500)

    }
}