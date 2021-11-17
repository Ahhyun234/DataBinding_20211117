package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun setupEvent()

    abstract fun setValues()

    val mContext = this


}