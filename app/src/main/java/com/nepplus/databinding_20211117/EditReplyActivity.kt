package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityEditReplyBinding
import com.nepplus.databinding_20211117.datas.TopicData

class EditReplyActivity : BaseActivity() {


    lateinit var binding: ActivityEditReplyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= DataBindingUtil.setContentView(mContext.R.layout.activity_edit_reply)
        setupEvent()
        setValues()
    }

    override fun setupEvent() {

    }

    override fun setValues() {

        mTopicData = Intent.getSerializableExtra("topic") as TopicData

        binding.txtTopicTitle.text = mTopicData.title
        binding.txtMySideTitle.text = mTopicData!!.titlex
    }
}