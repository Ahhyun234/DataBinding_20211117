package com.nepplus.databinding_20211117

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.databinding_20211117.databinding.ActivityEditReplyBinding
import com.nepplus.databinding_20211117.datas.TopicData
import com.nepplus.databinding_20211117.utils.ServerUtil
import org.json.JSONObject

class EditReplyActivity : BaseActivity() {


    lateinit var binding: ActivityEditReplyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= DataBindingUtil.setContentView(mContext,R.layout.activity_edit_reply)
        setupEvent()
        setValues()
    }

    override fun setupEvent() {

        binding.btnContentOk.setOnClickListener {
            val inputCotent = binding.edtContent.text.toString()

            if (inputCotent.length<10){
                Toast.makeText(mContext, "최소 10글자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            ServerUtil.postRequestTopicReply(mContext, mTopicData.id,InputContent, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {

                    val code = jsonObject.getInt("code")
                    runOnUiThread {
                        if(code==200){
                            Toast.makeText(mContext, "의견 등록에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        }
                        else{ val message = jsonObject.getString("message")
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                        }
                    }

                }
            })

        }

    }

    override fun setValues() {

        mTopicData = Intent.getSerializableExtra("topic") as TopicData

        binding.txtTopicTitle.text = mTopicData.title
        binding.txtMySideTitle.text = mTopicData!!.title
    }
}