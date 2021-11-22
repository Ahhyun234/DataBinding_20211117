package com.nepplus.databinding_20211117

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityMyProfileBinding
import com.nepplus.databinding_20211117.utils.ContextUtil

class MyProfileActivity : BaseActivity() {

    lateinit var binding : ActivityMyProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_profile)

        setupEvent()
        setValues()
    }

    override fun setupEvent() {

        binding.btnLogout.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃 확인")
            alert.setMessage("정말 로그아웃 하시겠습니까")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                //로그아웃 처리는 ? => 저장된 Token을 "" 으로 돌려주자 (내폰에 저장된 토큰 삭제)

                ContextUtil.setToken(mContext,"")
//            화면 종료 -> Splash로 이돌

            val myIntent = Intent(mContext,SplashActivity::class.java)
            myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(myIntent)


            })
            alert.setNegativeButton("취소",null)
                alert.show()
        }

    }

    override fun setValues() {

    }
}