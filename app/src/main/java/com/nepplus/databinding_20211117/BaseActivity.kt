package com.nepplus.databinding_20211117

import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun setupEvent()

    abstract fun setValues()

    val mContext = this

    //    커스템 액션바를 달아주는 함수를 제작 -> 액션바는 무조건 있다는 전제 하에 작성
    fun setCustomActionBar() {
     val defActionBar = supportActionBar!!      ///기존 액션바 가져오기
      defActionBar.displayOptions= ActionBar.DISPLAY_SHOW_CUSTOM  // 이 엑션바를 커스텀 모드로 변경
      defActionBar.setCustomView(R.layout.my_custom_action_bar)  //커스텀뷰가 어떤 xml인지
      val toolBar = defActionBar.customView.parent as Toolbar   //좌우 여백 제거(Toolbar소환:여백값 세팅)
        toolBar.setContentInsetsAbsolute(0,0)


    }


}