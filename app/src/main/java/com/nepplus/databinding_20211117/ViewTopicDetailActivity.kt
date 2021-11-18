package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityViewTopicDetailBinding

class ViewTopicDetailActivity : BaseActivity() {
    lateinit var binding:ActivityViewTopicDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =DataBindingUtil.setContentView(this,R.layout.activity_view_topic_detail)

        setupEvent()
        setValues()
    }


    override fun setupEvent() {

    }

    override fun setValues() {

    }
}