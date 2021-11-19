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
    var tempCreateAt = Calendar.getInstance() //기본 캘린더 변수로 대입 -> 기본값은 현재 일시


//    //좋아요 싫어요 댓글 관련 숫자 파싱
    var likeCount = 0
    var dislikeCount = 0
    var mylike =0
    var myDislike =0
    var jf
    
//    내핸드폰의 시간을 고려해서 시차를 보정해서 시간을 보여주는 함수
    fun getFormmatedCreatedAt():String{
//        서버가 주는 시간 : Gmt +0
//        내 폰의 시간 : 설정된 GML +? 시차가 얼마나 나는가? (서울 +9시간)

        val timeZone = Calendar.getInstance().timeZone   ///내폰의 시간대를 받아 옴
        val timeOffset = timeZone.rawOffset /1000 /60 /60    /// time 존으로 부터 Gmt는 몇시간 차이나는지 계산
        this.tempCreateAt.add(Calendar.HOUR,timeOffset)     // 글의 작성시간을 시차만큼 더해주자(표준 시간에 시차를 더해서 내 폰의 시간 보정)
        val sdf = SimpleDateFormat("yyyy/MM/dd a h시 m분")

        return sdf.format(this.tempCreateAt.time)

    }

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
            replyData.tempCreateAt.time = sdf.parse(createdAtStr)

            replyData.likeCount = jsonObject.getInt("like_count")
            replyData.dislikeCount = fff

            return replyData
        }

    }
}