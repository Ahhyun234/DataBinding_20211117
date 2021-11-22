package com.nepplus.databinding_20211117

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    lateinit var btnBack : ImageView

    abstract fun setupEvent()

    abstract fun setValues()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        액션바가 있는 화면에서만 실행되도록 함
        supportActionBar?.let {
//            엑션바가 null이 아닐때만 해달라는 코드
            setCustomActionBar()
        }

    }


    //    커스템 액션바를 달아주는 함수를 제작 -> 액션바는 무조건 있다는 전제 하에 작성
    fun setCustomActionBar() {
     val defActionBar = supportActionBar!!      ///기존 액션바 가져오기
      defActionBar.displayOptions= ActionBar.DISPLAY_SHOW_CUSTOM  // 이 엑션바를 커스텀 모드로 변경
      defActionBar.setCustomView(R.layout.my_custom_action_bar)  //커스텀뷰가 어떤 xml인지
      val toolBar = defActionBar.customView.parent as Toolbar   //좌우 여백 제거(Toolbar소환:여백값 세팅)
        toolBar.setContentInsetsAbsolute(0,0)


//    액션바의  커스템 뷰에 추가된 UI요소들을 멤버변수에 연결
        btnBack = defActionBar.customView.findViewById(R.id.btnBack)
        finish()   //뒤로가기

    }


}