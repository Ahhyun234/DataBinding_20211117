package com.nepplus.databinding_20211117.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData :Serializable {

//    생성자는 기본 생성자를 유지시키고 멤버변수만 추가 해서 제이슨 파싱해서 변수에 채워넣자
//    멤버변수: 서버의 데이터를 보고 담아주는 용도의 변수들로 만들자

    var id =0 //int가 들어올 자리
    var title = "" //String이 들어올 자리
    var imageUrl = ""

    companion object{

//        json Object를 가지고 TopicData형태로 변환해주는 함수 제작
//        다른 화면들에서는 이 함수를 끌어다 사용

        fun getTopicDataFromJson(jsonObject: JSONObject):TopicData{
            val resultTopicData = TopicData
            resultTopicData.id=jsonObject.getInt("id")
            resultTopicData.title = jsonObject.getString("title")
            resultTopicData.imageUrl=jsonObject
                    return

        }

    }

}