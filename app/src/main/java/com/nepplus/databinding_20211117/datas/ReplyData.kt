package com.nepplus.databinding_20211117.datas

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ReplyData {

    var id = 0
    var content = ""

    var writer = UserData()     //사용자 데이터가 들어올 것을 명시

    var selectedSide = Sidedata()

//        언제 적힌 댓글인가?
    var createAt = Calendar.getInstance() //기본 캘린더 변수로 대입 -> 기본값은 현재 일시

    companion object{

        fun getReplyDataFromJson(jsonObject: JSONObject): ReplyData{

            val replyData = ReplyData()

            replyData.id = jsonObject.getInt("id")
            replyData.content=jsonObject.getString("content")

            val userObj = jsonObject.getJSONObject("user")
            replyData.writer = UserData.getUserDataFromServer(userObj)

            val sideObj = jsonObject.getJSONObject("selected_side")
            replyData.selectedSide = Sidedata.getSideDataFromJson(sideObj)

//            작성일시 파싱 -> String 으로 서버가 내려줌

            val createdAtStr = jsonObject.getString("created_at")
//            임시로 추출한 String -> Calendar 형태로 변환 -> 댓글 데이터의 작성 일시로 반영

//            서버가 준 스트링을 분석하기 위한 SimpleDateFormat만들기
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

//            parse로 Date 형태의 일시 생성
            replyData.createAt.time = sdf.parse(createdAtStr)

            return replyData
        }

    }
}