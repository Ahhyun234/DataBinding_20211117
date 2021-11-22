package com.nepplus.databinding_20211117.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nepplus.databinding_20211117.R
import com.nepplus.databinding_20211117.ViewReplyDetailActivity
import com.nepplus.databinding_20211117.ViewTopicDetailActivity
import com.nepplus.databinding_20211117.datas.ReplyData
import com.nepplus.databinding_20211117.datas.TopicData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject
import java.text.SimpleDateFormat

class ReplyAdapter(val mContext: Context, val resId: Int, val mList: List<ReplyData>) :
    ArrayAdapter<ReplyData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInflater.inflate(R.layout.reply_list_item, null)
        }
        val row = tempRow!!

        val data = mList[position]

        val txtReplycontent = row.findViewById<TextView>(R.id.txtReplyContent)
        val txtWriterNickName = row.findViewById<TextView>(R.id.txtWriterNickName)
        val txtSelectedSide = row.findViewById<TextView>(R.id.txtSelectedSide)
        val txtCreatedAt = row.findViewById<TextView>(R.id.txtCreatedAt)
        val txtReplyCount = row.findViewById<TextView>(R.id.txtReplyCount)
        val txtlikeCount = row.findViewById<TextView>(R.id.txtLikeCount)
        val txtDislikeCount = row.findViewById<TextView>(R.id.txtDislikeCount)




        txtWriterNickName.text=data.writer.nickname
        txtReplycontent.text=data.content
        txtSelectedSide.text = "( ${data.selectedSide.title} )"

        txtReplyCount.text="답글: ${data.replyCount} 개"
        txtlikeCount.text="좋아요: ${data.likeCount} 개"
        txtDislikeCount.text="싫어요: ${data.dislikeCount} 개"

//        내 좋아요 여부에 따른 테두리 색 / 글씨 색 변경
        if (data.mylike){
//            red border박스로 txt like count 의 배경을 변경
            txtlikeCount.setBackgroundResource(R.drawable.res_border_box)

            txtlikeCount.setTextColor(mContext.resources.getColor(R.color.red))

        }
        else{
            txtlikeCount.setBackgroundColor(R.drawable.gray_border_box)
            txtDislikeCount.setTextColor(mContext.resources.getColor(R.color.gray))
        }


        if (data.myDislike){
//            red border박스로 txt like count 의 배경을 변경
            txtlikeCount.setBackgroundResource(R.drawable.res_border_box)

            txtlikeCount.setTextColor(mContext.resources.getColor(R.color.red))

        }
        else{
            txtlikeCount.setBackgroundColor(R.drawable.gray_border_box)
            txtDislikeCount.setTextColor(mContext.resources.getColor(R.color.gray))
        }

        val sdf = SimpleDateFormat("yyyy/MM/dd a h시 m분")

        txtCreatedAt.text = data.getFormmatedCreatedAt()



        txtlikeCount.setOnClickListener{
//            이 댓글에 좋아요를 남겼다고 서버 api를 호출할것
            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id,true,object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {
//                    어댑터를 들고있는 상세현황 화면의 기능을 활용=> 토론 주제 상세 다시 가져오기 (댓글도 가져오게 됨)

                    (mContext as ViewTopicDetailActivity).getTopicDetailFromServer()

                }

            })
        }

        txtDislikeCount.setOnClickListener {
//             이 댓글에 싫어요를 남겼다고 API호출

            ServerUtil.postRequestReplyLikeOrDislike(mContext,data.id,false,object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {

                    (mContext as ViewTopicDetailActivity).getTopicDetailFromServer()

                }

            })

        }

        txtReplyCount.setOnClickListener {
            val myIntent = Intent(mContext,ViewReplyDetailActivity::class.java)
            mContext.startActivity(myIntent)
        }

        return row

    }


}