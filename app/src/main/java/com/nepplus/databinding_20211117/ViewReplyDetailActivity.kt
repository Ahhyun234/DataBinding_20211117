package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityEditReplyBinding
import com.nepplus.databinding_20211117.datas.ReplyData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {
    lateinit var binding: ViewReplyDetailActivity
    lateinit var mReplydata : ReplyData
    val mReplyLise = ArrayList<ReplyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_view_reply_detail)

        setupEvent()
        setValues()
    }

    override fun setupEvent() {

    }

    override fun setValues() {

        mReplydata = intent.getSerializableExtra("reply") as ReplyData

        binding.txtSelectedSide.text = "${mReplydata.selectedSide.title}"
        binding.txtWriterNickName.text = mReplydata.writer.nickname
        binding.txtContent.text = mReplydata.content

        getReplyDetailfromServer()
    }

    fun getReplyDetailfromServer(){

        ServerUtil.getRequestReplyDetail(mContext,mReplydata.id,object:ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObject: JSONObject) {

                val dataObj = jsonObject.getJSONObject("data")
                val replyObj = dataObj.getJSONObject("reply")
                val repliesArr = replyObj.getJSONArray("replies")

                for(i in 0 until repliesArr.length()){
//                    []->{} 추출 -> replaydata로 변환 -> 대댓글 목록에 추가하자
                    mReplyLise.add(ReplyData.getReplyDataFromJson(repliesArr.getJSONObject(i)))

                }
            }

        })

    }
}