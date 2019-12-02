package com.example.fengtai.fragment.farmdoc.member

import android.content.Intent
import com.bumptech.glide.Glide
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.entity.ImgResult
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.Personnel
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.util.Util
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import kotlinx.android.synthetic.main.fragment_fdmember_add.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


class FDMemberADDFragment : BaseFragment(R.layout.fragment_fdmember_add) {
    private val code = 100
    private var img = ""
    private val type = "".toMediaTypeOrNull()
    override fun onStart() {
        super.onStart()

        btn_save.setOnClickListener {

            val file = File(img)
            //先上传图片而后提交注册
            MyApplication.API
                .uploadImg(
                    MultipartBody.Part.createFormData(
                        "photo",
                        file.name,
                        file.asRequestBody("image/*".toMediaTypeOrNull())
                    )
                )
                .enqueue(object : retrofit2.Callback<Result<ImgResult>> {

                    override fun onResponse(
                        call: Call<Result<ImgResult>>,
                        response: Response<Result<ImgResult>>
                    ) {
                        if (response.body()?.code == 200) {

                            Util.makeToast(context, response.body()!!.message)


                            //确认图片上传后 提交信息
                            MyApplication.API.addPersonnel(
                                MyApplication.userId.toRequestBody(type),
                                response.body()!!.data.img.toRequestBody(type),
                                et_call.text.toString().toRequestBody(type),
                                et_name.text.toString().toRequestBody(type),
                                et_relations.text.toString().toRequestBody(type),
                                et_phone.text.toString().toRequestBody(type),
                                (s_labour.selectedItemPosition + 1).toString().toRequestBody(type)
                            ).enqueue(object : retrofit2.Callback<Result<Personnel>> {

                                override fun onResponse(
                                    call: Call<Result<Personnel>>,
                                    response: Response<Result<Personnel>>
                                ) {
                                    if (response.body()?.code == 200) {

                                        Util.makeToast(context, response.body()!!.message)
                                        backPrev()

                                    } else {
                                        Util.makeToast(context, response.body()!!.message)
                                    }
                                }

                                override fun onFailure(
                                    call: Call<Result<Personnel>>,
                                    t: Throwable
                                ) =
                                    Util.makeToast(context, t.message)
                            })


                        } else {
                            Util.makeToast(context, response.body()!!.message)
                        }
                    }

                    override fun onFailure(call: Call<Result<ImgResult>>, t: Throwable) {
                        Util.makeToast(context, t.message)
                    }
                }
                )
        }


        pic.setOnClickListener {
            startActivityForResult(
                Intent(context, ImageGridActivity::class.java),
                code
            )
        }
    }


    //接收相册返回预览相片
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                val images =
                    data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>

                if (requestCode == code) {
                    img = (images[0] as ImageItem).path

                    Glide.with(this@FDMemberADDFragment).load(img).into(pic)

                } else {
                    Util.makeToast(context, "无数据")
                }
            }

        }

    }
}
