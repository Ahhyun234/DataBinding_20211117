package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nepplus.databinding_20211117.adapters.TopicAdapter
import com.nepplus.databinding_20211117.databinding.ActivityViewTopicDetailBinding
import com.nepplus.databinding_20211117.datas.TopicData

class ViewTopicDetailActivity : BaseActivity() {
    lateinit var binding:ActivityViewTopicDetailBinding

    lateinit var mTopicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this,R.layout.activity_view_topic_detail)

        setupEvent()
        setValues()
    }


    override fun setupEvent() {

    }

    override fun setValues() {

        mTopicData = intent.getSerializableExtra("topic")as TopicData

        binding.txtTopicTitle.text=mTopicData.title
        Glide.with(mContext).load(mTopicData.imageUrl).into(binding.imgTopic)

    }
}