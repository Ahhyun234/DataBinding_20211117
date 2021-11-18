package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityMain2Binding
import com.nepplus.databinding_20211117.datas.TopicData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class MainActivity2 : BaseActivity() {

    lateinit var binding: ActivityMain2Binding

    val mTopicList = ArrayList<TopicData>()


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
//        getMyInfoFromServer()
//        실제 메인 화면의 데이터 받아오기 -> 토론 주제 목록 -> 리스트뷰로 표시


    }

//    /////////////리스트뷰//////////////////////////////////////////////////
    fun getTopicListFromServer(){
        ServerUtil.getMainInfo(mContext,object :ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObject: JSONObject) {
                val dataObj = jsonObject.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")
                for(i in 0 until topicsArr.length()){
//                    하나의 토론 주제를 표현하는 중괄호 추출
                    val topicObj = topicsArr.getJSONObject(i)
//                    목록에 뿌려줄 Topic Data 형태로 변환
                    val topicData = TopicData()
                    topicData.id = topicObj.getInt("id")
                    topicData.title = topicObj.getString("title")
                    topicData.imageUrl= topicObj.getString("img_url")

//                    완성된 topicData => mtopicList에 추가
                    mTopicList.add(topicData)
                }

            }

        })
    }

//    fun getMyInfoFromServer() {
//
//        ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler {
//            override fun onResponse(jsonObject: JSONObject) {
//
//                val dataObj = jsonObject.getJSONObject("data")
//                val userObj = dataObj.getJSONObject("user")
//                val nickname = userObj.getString("nick_name")
//
//                runOnUiThread {
//                    binding.txtUserNickName.text = nickname
//
//                }
//
//
//            }
//        })
//    }
}