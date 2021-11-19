package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nepplus.databinding_20211117.adapters.TopicAdapter
import com.nepplus.databinding_20211117.databinding.ActivityViewTopicDetailBinding
import com.nepplus.databinding_20211117.datas.TopicData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {
    lateinit var binding: ActivityViewTopicDetailBinding

    lateinit var mTopicData: TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)

        setupEvent()
        setValues()
    }


    override fun setupEvent() {

        binding.btnVote01.setOnClickListener {
//            첫진영에 투표 -> 서버 api 호출 -> 새로고침
            ServerUtil.postRequestLogVote(
                mContext,
                mTopicData.sideList[0].id,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObject: JSONObject) {

                        val code = jsonObject.getInt("code")
                        if (code == 200) {
//                            새로고침(토론 상세 조회 -> 선택진영목록-> 득표수)
                            getTopicDetailFromServer()


                        } else {
                            val message = jsonObject.getString("message")

                            runOnUiThread{
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                })

        }

    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic") as TopicData

        binding.txtTopicTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageUrl).into(binding.imgTopic)

//        현재 진행 상황을 조회하는 API를 호출 -> 토론 진영 목록/몇표 획득
        getTopicDetailFromServer()
    }

    fun getTopicDetailFromServer() {

        ServerUtil.getRequestTopicDetail(
            mContext,
            mTopicData.id,
            object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObject: JSONObject) {

                    val dataObj = jsonObject.getJSONObject("data")
                    val topicObj = dataObj.getJSONObject('topic')

//                topic 토론 주제가 담긴 json Object 토픽 데이타변환 함수의 재료로 사용
                    mTopicData TopicData . getTopicDataFromJson (topicObj)

                    runOnUiThread {
                        refreshUi()
                    }

                }

            })


    }

    fun refreshUi() {
//        mtopic 데이터 변경 시 리프레시

        binding.txtTopicTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageUrl).into(binding.imgTopic)
        binding.txtReplyTitle.text = "댓글 갯수 : ${mTopicData.replyCount} 개"

        binding.txtSideTitle01.text = mTopicData.sideList[0].title
        binding.txtSideTitle02.text = mTopicData.sideList[1].title

        binding.txtSideVoteCount01.text = "${mTopicData.sideList[0].voteCount} 표"
        binding.txtSideVoteCount02.text = "${mTopicData.sideList[1].voteCount} 표"
    }


}