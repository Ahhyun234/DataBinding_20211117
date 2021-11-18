package com.nepplus.databinding_20211117.utils

class Context {

    companion object {
        private val prefName = "ColosseumPref"
        private val TOKEN = "TOKEN"

//        token값에 대한 setter

        fun setToken(context: Context, token: String) {
//                        1. prefName을 이용해서 shaedpreference열기
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

//                      2. 토큰값을 위에 저장
            pref.edit().putString(TOKEN, token).apply()


        }

//        token 값에 대한 getter

        fun getToken(context: Context): String {
//            1. 메모장 열기
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
//                2. 저장된 토큰 값을 리턴
            return pref.getString(TOKEN,"")!!



        }

    }
}