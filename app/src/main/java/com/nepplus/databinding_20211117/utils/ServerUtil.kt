package com.nepplus.databinding_20211117.utils

import android.provider.ContactsContract
import android.util.Log
import kotlinx.coroutines.flow.combine
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    interface JsonResponseHandler {
        fun onResponse(jsonObject: JSONObject)
    }

    companion object {
//        여기에 적는 변수/ㅎ마수는 Static에 대응됨
//        클래스 이름.기능() 활용 가능

//        모든 서버의 기능이 공유 할 Host 서버 컴퓨터 주소

        val HOST_URL = "http://54.180.52.26"

//        로그인 함수 - POST

        fun postRequestLogIN(email: String, pw: String, handler: JsonResponseHandler?) {
//            1. 어디로 가야 하는가 URL
            val urlString = "${HOST_URL}/user"

//            2. 어떤 데이터를 들고가는가? (파라미터)
            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .build()

//            3. 어떤 메소드 + 1/2 데이터 결합 => 어떤 요청인지 완성
//              어디로 어떤 방식으로 갈것인지

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()

//            4. 완성된 Requst를 실제로 호출 => 클라이언트 역할

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

//                    실패: 물리적으로 (연결 자체)접속 실패 -> 보통 토스트 띄움
                }

                override fun onResponse(call: Call, response: Response) {
//                    결과가 무엇이든 응답이 돌아온 상황

//                    응답의 본문에 어떤 내용? -> 본문만 스트링으로 변환
                    val bodyString = response.body!!.string()

//                    bodyString은 Json양식으로 가공됨-> 한글도 임시 변환 된 상태 (encoding)
//                    일반 String -> JsonObject로 변환 (한글도 원상복구)
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())

//                    나를 호출한 화면에게 jsonObj를 처리하는 일처리를 미루자
                    handler?.onResponse(jsonObj)

                }

            })


        }

        // fun putRequestSignUp(){} -> 도전 과제3
        fun putRequestSignUp(
            email: String,
            pw: String,
            nickname: String,
            handler: JsonResponseHandler?
        ) {
            val urlSting = "${HOST_URL}/user"

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickname)
                .build()

            val request = Request.Builder().url(urlSting).put(formData).build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버 응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }


        //        /////////////////GET 방식 (중복확인)///////////////////////////////
        fun getRequestDupCheck(type: String, value: String, handler: JsonResponseHandler?) {

//            1. 어디로 갈 것인지 +  2. 어떤 파라미터 => GET 방식은 이것을 같이 씀
//            url을 만드는 과정이 복잡하기 때문에 한단계씩 샇아나가는 방식으로 url작성
            val urlBuilder = "${HOST_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
            urlBuilder.addEncodedQueryParameter("type", type)
            urlBuilder.addEncodedQueryParameter("value", value)

//              최종 완성된 주소 string으로 저장
            val urlString = urlBuilder.toString()
            Log.d("완성주소", urlString)



//            3. 어떤 메소드 + 정보 종합생성
            val request = Request.Builder().url(urlString).get().build()

//            API 호출 client

            val client = OkHttpClient()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonobj = JSONObject(bodyString)
                    Log.d("서버응답", jsonobj.toString())
                    handler?.onResponse(jsonobj)

                }

            })

        }


    }
}


