package com.nepplus.databinding_20211117.datas

import org.json.JSONObject

class ReplyData {

    var id = 0
    var content = ""

    var writer = UserData()     //사용자 데이터가 들어올 것을 명시

    var selectedSide = Sidedata()


    companion object{

        fun getReplyDataFromJson(jsonObject: JSONObject): ReplyData{

            val replyData = ReplyData()

            replyData.id = jsonObject.getInt("id")
            replyData.content=jsonObject.getString("content")

            replyData.writer = UserData.getUserDataFromServer(userObj)
            val sideObj = jsonObject.getJSONObject("selected_side")
            replyData.selectedSide = Sidedata.getSideDataFromJson(sideObj)

            return ReplyData()
        }

    }
}