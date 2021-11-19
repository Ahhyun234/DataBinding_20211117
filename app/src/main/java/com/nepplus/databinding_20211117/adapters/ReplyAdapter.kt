package com.nepplus.databinding_20211117.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nepplus.databinding_20211117.R
import com.nepplus.databinding_20211117.datas.ReplyData
import com.nepplus.databinding_20211117.datas.TopicData

class ReplyAdapter(val mContext: Context, val resId: Int, val mList: List<ReplyData>) :
    ArrayAdapter<TopicData>(mContext, resId, mList) {

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

        txtWriterNickName.text=data.writer.nickname
        txtReplycontent.text=data.content
        txtSelectedSide.text = "( ${data.selectedSide.title} )"


        return row

    }


}