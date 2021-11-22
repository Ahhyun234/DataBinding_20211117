package com.nepplus.databinding_20211117

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nepplus.databinding_20211117.adapters.ReplyAdapter
import com.nepplus.databinding_20211117.adapters.TopicAdapter
import com.nepplus.databinding_20211117.databinding.ActivityViewTopicDetailBinding
import com.nepplus.databinding_20211117.datas.ReplyData
import com.nepplus.databinding_20211117.datas.TopicData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class ViewTopicDetailActivity : BaseActivity() {
    lateinit var binding: ActivityViewTopicDetailBinding

    lateinit var mTopicData: TopicData

    val mReplyList = ArrayList<ReplyData>()
    lateinit var mReplyAdapter:ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_topic_detail)

        setupEvent()
        setValues()

    }

    override fun onResume() {
        super.onResume()

//         onResume은 이 화면이 나타날 때 마다 계속 실행 되는 함수-> 이홤면에 돌아올 때 마다 서버에서 다시 댓글목록도 물러오게 함
        getTopicDetailFromServer()
    }


    override fun setupEvent() {

        binding.btnWriteReply.setOnClickListener {

//            아직 사용자가 투표를 하지 않았다면 댓글 작성을 할 수 없게 한다.
//            //1. 투표여부 확인 ( 내가 투표 안했다면 강제종료
                if(mTopicData.mySide ==null){
                    Toast.makeText(mContext, "한 진영에 투표를 해야 의견을 작성할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }


//            //2. 어디에 투표 했는지? (mTopic에 하위정보 mySide로 모두 들어있다)
//                            mTopicData를 통째로 넘겨주면 내 선택 진영도 포함되어 넘어간다

            val myIntent = Intent(mContext,EditReplyActivity::class.java)
            myIntent.putExtra("myside")
            startActivity(myIntent)
        }

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

        binding.btnVote02.setOnClickListener {
            ServerUtil.postRequestLogVote(mContext,mTopicData.sideList[1].id, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {

                    getTopicDetailFromServer()
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

        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        binding.replyListView.adapter = mReplyAdapter

    }

    fun getTopicDetailFromServer() {


        ServerUtil.getRequestTopicDetail(
            mContext,
            mTopicData.id,"NEW",
            object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObject: JSONObject) {

                    val dataObj = jsonObject.getJSONObject("data")
                    val topicObj = dataObj.getJSONObject("topic")

//                topic 토론 주제가 담긴 json Object 토픽 데이타변환 함수의 재료로 사용
                    mTopicData = TopicData. getTopicDataFromJson (topicObj)

                    runOnUiThread {
                        refreshUi()
                    }

                    val repliesArr = topicObj.getJSONArray("replies")

                    mReplyList.clear()

                    for(i in 0 until repliesArr.length()){

                        val replyObj = repliesArr.getJSONObject(i)
                        val replyData = ReplyData.getReplyDataFromJson(replyObj)
                        mReplyList.add(replyData)

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