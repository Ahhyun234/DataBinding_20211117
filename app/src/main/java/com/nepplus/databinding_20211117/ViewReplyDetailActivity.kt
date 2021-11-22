package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityEditReplyBinding
import com.nepplus.databinding_20211117.datas.ReplyData

class ViewReplyDetailActivity : BaseActivity() {
    lateinit var binding: ViewReplyDetailActivity
    lateinit var mReplydata : ReplyData

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

    }
}