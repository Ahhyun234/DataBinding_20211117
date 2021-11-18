package com.nepplus.databinding_20211117

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.adapters.TopicAdapter
import com.nepplus.databinding_20211117.databinding.ActivityMain2Binding
import com.nepplus.databinding_20211117.datas.TopicData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class MainActivity2 : BaseActivity() {

    lateinit var binding: ActivityMain2Binding

    val mTopicList = ArrayList<TopicData>()

    lateinit var mTopicAdapter: TopicAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)

        setupEvent()
        setValues()
    }

    override fun setupEvent() {

        binding.topicListView.setOnItemClickListener { adapterView, view, position, l ->

            val myIntent = Intent(mContext,ViewTopicDetailActivity::class.java)
            startActivity(myIntent)
        }

    }

    override fun setValues() {
        getTopicListFromServer()

//        어댑터를 객체화 해서 List 뷰 어댑터로 연결

        mTopicAdapter = TopicAdapter(mContext, R.layout.topic_list_item, mTopicList)

        binding.topicListView.adapter = mTopicAdapter


//        연습 -> 내정보 API호출 -> 닉네임 추출/ui반영
//        getMyInfoFromServer()
//        실제 메인 화면의 데이터 받아오기 -> 토론 주제 목록 -> 리스트뷰로 표시


    }

    //    /////////////리스트뷰//////////////////////////////////////////////////
    fun getTopicListFromServer() {
        ServerUtil.getMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObject: JSONObject) {
                val dataObj = jsonObject.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")
                for (i in 0 until topicsArr.length()) {
//                    하나의 토론 주제를 표현하는 중괄호 추출
                    val topicObj = topicsArr.getJSONObject(i)
//                    목록에 뿌려줄 Topic Data 형태로 변환
                    val topicData = TopicData()
                    topicData.id = topicObj.getInt("id")
                    topicData.title = topicObj.getString("title")
                    topicData.imageUrl = topicObj.getString("img_url")

//                    완성된 topicData => mtopicList에 추가
                    mTopicList.add(topicData)
                }
                runOnUiThread {
                    mTopicAdapter.notifyDataSetChanged()

                }

            }

        })
    }
//어댑터 세팅보다 서버의 데이터 수신이 늦을 수도 있다. 만약서버가 더 늦는다면, 어댑터 세팅 이후에 목록 추가 -> 이 뜻은 Arraylist에 변화가 있다는것
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