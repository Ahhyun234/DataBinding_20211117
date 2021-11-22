package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.adapters.Re_ReplyAdapter
import com.nepplus.databinding_20211117.databinding.ActivityEditReplyBinding
import com.nepplus.databinding_20211117.datas.ReplyData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {
    lateinit var binding: ViewReplyDetailActivity
    lateinit var mReplydata : ReplyData
    val mReplyLise = ArrayList<ReplyData>()
    lateinit var mReReplyAdapter : mReplyData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_view_reply_detail)

        setupEvent()
        setValues()
    }

    override fun setupEvent() {


        binding.btnOk.setOnClickListener{
//            대댓글 등록 API호출

        val inputContent = binding.edtContent.text.toString()
            ServerUtil.postRequestReReply(mContext,mReplydata.id,inputContent,object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {

                }

            })

        }

    }

    override fun setValues() {

        mReplydata = intent.getSerializableExtra("reply") as ReplyData

        binding.txtSelectedSide.text = "${mReplydata.selectedSide.title}"
        binding.txtWriterNickName.text = mReplydata.writer.nickname
        binding.txtContent.text = mReplydata.content

        getReplyDetailfromServer()

        mReReplyAdapter = Re_ReplyAdapter(mContext,R.layout.re_reply_list_item,mReplyLise)
        dsf
    }

    fun getReplyDetailfromServer(){

        ServerUtil.getRequestReplyDetail(mContext,mReplydata.id,object:ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObject: JSONObject) {

                val dataObj = jsonObject.getJSONObject("data")
                val replyObj = dataObj.getJSONObject("reply")
                val repliesArr = replyObj.getJSONArray("replies")

                mReReplyAdapter.clear

                for(i in 0 until repliesArr.length()){
//                    []->{} 추출 -> replaydata로 변환 -> 대댓글 목록에 추가하자
                    mReplyLise.add(ReplyData.getReplyDataFromJson(repliesArr.getJSONObject(i)))

                }
                runOnUiThread {
                    mReReplyAdapter.notifyDataSetChanged()
                }
            }

        })

    }
}