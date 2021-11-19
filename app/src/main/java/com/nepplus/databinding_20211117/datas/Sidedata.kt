package com.nepplus.databinding_20211117.datas

import android.view.WindowInsets
import org.json.JSONObject

class Sidedata {

    var id = 0
    var title = ""
    var voteCount = 0

    companion object{

        fun getSideDataFromJson(jsonObject: JSONObject):Sidedata{
            val sidedata = Sidedata()
            sidedata.id = jsonObject.getInt("id")
            sidedata.title=jsonObject.getString("title")
            sidedata.voteCount=jsonObject.getInt("vote_count")


            return sidedata
        }

    }


    }

