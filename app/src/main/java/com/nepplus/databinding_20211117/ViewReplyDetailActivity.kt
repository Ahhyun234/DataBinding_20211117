package com.nepplus.databinding_20211117

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import com.nepplus.databinding_20211117.adapters.Re_ReplyAdapter
import com.nepplus.databinding_20211117.databinding.ActivityViewReplyDetailBinding
import com.nepplus.databinding_20211117.datas.ReplyData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject


class ViewReplyDetailActivity : BaseActivity() {

    lateinit var binding: ActivityViewReplyDetailBinding

    lateinit var mReplyData: ReplyData
    val mReplyLise = ArrayList<ReplyData>()
    lateinit var mReReplyAdapter : Re_ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_view_reply_detail)
        setupEvent()
        setValues()
    }

    override fun setupEvent() {


        binding.btnOk.setOnClickListener{
//            대댓글 등록 API호출

        val inputContent = binding.edtContent.text.toString()
            ServerUtil.postRequestReReply(mContext,mReplyData.id,inputContent,object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {

//                    1. 새로고침
                    getReplyDetailfromServer()


                    runOnUiThread {
                        //  2. 입력칸 비워두기
                        binding.edtContent.setText("")

                    }

                }

            })

        }

    }

    override fun setValues() {

        mReplyData = intent.getSerializableExtra("reply") as ReplyData

        binding.txtSelectedSide.text = "${mReplyData.selectedSide.title}"
        binding.txtWriterNickName.text = mReplyData.writer.nickname
        binding.txtContent.text = mReplyData.content

        getReplyDetailfromServer()

        mReReplyAdapter = Re_ReplyAdapter(mContext,R.layout.re_reply_list_item,mReplyLise)
        binding.reReplyListView.adapter =  mReReplyAdapter

    }

    fun getReplyDetailfromServer(){

        ServerUtil.getRequestReplyDetail(mContext,mReplyData.id,object:ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObject: JSONObject) {

                val dataObj = jsonObject.getJSONObject("data")
                val replyObj = dataObj.getJSONObject("reply")
                val repliesArr = replyObj.getJSONArray("replies")

                mReReplyAdapter.clear()

                for(i in 0 until repliesArr.length()){
//                    []->{} 추출 -> replaydata로 변환 -> 대댓글 목록에 추가하자
                    mReplyLise.add(ReplyData.getReplyDataFromJson(repliesArr.getJSONObject(i)))

                }
                runOnUiThread {
                    mReReplyAdapter.notifyDataSetChanged()

                    // 3. 리스트 뷰 스크롤을 맨 아래로 내려보기(댓글 갯수 -1 번째 칸으로 스크롤)
                    binding.reReplyListView.smoothScrollToPosition(mReplyLise.size-1)
                }
            }

        })

    }
}