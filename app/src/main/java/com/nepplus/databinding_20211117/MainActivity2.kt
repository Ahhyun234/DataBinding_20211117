package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityMain2Binding
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class MainActivity2 : BaseActivity() {

    lateinit var binding: ActivityMain2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        setupEvent()
        setValues()
    }

    override fun setupEvent() {

    }

    override fun setValues() {

//        연습 -> 내정보 API호출 -> 닉네임 추출/ui반영
        getMyInfoFromServer()
    }

    fun getMyInfoFromServer() {

        ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObject: JSONObject) {

                val dataObj = jsonObject.getJSONObject("data")
                val userObj = dataObj.getJSONObject("user")
                val nickname = userObj.getString("nick_name")

                runOnUiThread {
                    binding.txtUserNickName.text = nickname

                }


            }
        })
    }
}