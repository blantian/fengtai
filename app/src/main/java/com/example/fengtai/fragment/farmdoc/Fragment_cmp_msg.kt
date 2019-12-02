package com.example.fengtai.fragment.farmdoc

import android.content.Intent
import android.view.View
import android.widget.AdapterView
import com.bumptech.glide.Glide
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.activity.farmdoc.FarmdocMainActivity
import com.example.fengtai.adapter.SpinnerAdapter
import com.example.fengtai.entity.ImgResult
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.HuKou
import com.example.fengtai.entity.Item
import com.example.fengtai.entity.Region
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.util.APIService
import com.example.fengtai.util.Util
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.ui.ImageGridActivity
import kotlinx.android.synthetic.main.fragment_msg.btn_save
import kotlinx.android.synthetic.main.fragment_msg_comp.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File


class Fragment_cmp_msg : BaseFragment(R.layout.fragment_msg_comp) {

    private val IMAGE_PICKER_1 = 2100
    private val IMAGE_PICKER_2 = 2200
    private var img1 = ""
    private var img2 = ""


    override fun onStart() {
        super.onStart()

        initSpinner()

        val type = "".toMediaTypeOrNull()

        val arg = arguments?.getSerializable("hukou")




        arg?.let {
            /**
             *  当Intent传递的参数不为空时调用

             */
            val huKou = it as HuKou
            //避免从图像选择返回后引起的重加载问题
            if (img1.isEmpty() && img2.isEmpty()) {
                img1 = huKou.idcard_front ?: ""
                img2 = huKou.idcard_side ?: ""
                Glide.with(this).load(huKou.idcard_front).into(idcard1)
                Glide.with(this).load(huKou.idcard_side).into(idcard2)
            }


            et_idCard.setText(huKou.idcard ?: "")
            et_idName.setText(huKou.idcard_name ?: "")
            huKou.idcard_gender?.toInt()?.let { it1 -> s_sex.setSelection(it1 - 1) }
            et_idBirth.setText(huKou.birth_date ?: "")
            et_idAge.setText(huKou.age ?: "")
            et_idDetails.setText(huKou.xiangxi ?: "")

            btn_save.setOnClickListener {
                //绑定提交接口
                submit { it1 ->
                    it1.editHuKou(
                        huKou.id.toRequestBody(type),
                        //todo 图片上传
                        img1.toRequestBody(type),

                        et_idName.text.toString().toRequestBody(type),

                        img2.toRequestBody(type),

                        et_idBirth.text.toString().toRequestBody(type),
                        et_idCard.text.toString().toRequestBody(type),

                        ((s_idProvinces.selectedItem ?: Item(
                            "",
                            ""
                        )) as Item).value.toString().toRequestBody(type),
                        ((s_idCity.selectedItem ?: Item(
                            "", ""
                        )) as Item).value.toString().toRequestBody(type),
                        ((s_idSeat.selectedItem ?: Item(
                            "",
                            ""
                        )) as Item).value.toString().toRequestBody(type),

                        et_idDetails.text.toString().toRequestBody(type),

                        et_idAge.text.toString().toRequestBody(type),
                        (s_sex.selectedItemPosition + 1).toString().toRequestBody(type)
                    )
                }
            }


            //根据户主名判断是否为新
            huKou.idcard_name?.let {
                //已有数据提交请求改为编辑

                btn_save.setOnClickListener {
                    submit { apiService ->
                        apiService.editsHuKou(
                            mapOf<String, RequestBody>(
                                "id" to huKou.id.toRequestBody(type),
                                "idcard_name" to et_idName.text.toString().toRequestBody(type),
                                "birth_date" to et_idBirth.text.toString().toRequestBody(type),
                                "idcard" to et_idCard.text.toString().toRequestBody(type),
                                "age" to et_idAge.text.toString().toRequestBody(type),
                                "idcard_gender" to (s_sex.selectedItemPosition + 1).toString().toRequestBody(
                                    type
                                ),
                                "xiangxi" to et_idDetails.text.toString().toRequestBody(type),

                                "sheng" to (s_idProvinces.selectedItem as Item).value.toString().toRequestBody(
                                    type
                                ),
                                "shi" to (s_idCity.selectedItem as Item).value.toString().toRequestBody(
                                    type
                                ),
                                "xian" to (s_idSeat.selectedItem as Item?)?.value.toString().toRequestBody(
                                    type
                                ),
                                //todo  照片
                                "idcard_front" to img1.toRequestBody(type),
                                "idcard_side" to img2.toRequestBody(type)
                            )
                        )
                    }
                }

            }


        }


        val intent = Intent(context, ImageGridActivity::class.java)

        idcard1.setOnClickListener {
            startActivityForResult(intent, IMAGE_PICKER_1)
        }

        idcard2.setOnClickListener {
            startActivityForResult(intent, IMAGE_PICKER_2)
        }
    }

    private fun submit(request: (o: APIService) -> Call<Result<HuKou>>) {

        request(MyApplication.API)
            .enqueue(object : retrofit2.Callback<Result<HuKou>> {
                override fun onResponse(
                    call: Call<Result<HuKou>>,
                    response: Response<Result<HuKou>>
                ) {
                    if (response.body()?.code == 200) {

                        Util.makeToast(context, response.body()!!.message)
                        startActivity(Intent(context, FarmdocMainActivity::class.java))

                    } else
                        Util.makeToast(context, response.body()!!.message)
                }

                override fun onFailure(call: Call<Result<HuKou>>, t: Throwable) {
                    Util.makeToast(context, t.message)
                }
            })

    }

    /**
     * 三级地址 请求封装
     */
    private fun query(
        request: (o: APIService) -> Call<Result<ArrayList<Region>>>,
        handle: (body: ArrayList<Region>) -> Unit
    ) {
        request(MyApplication.API)
            .enqueue(object : retrofit2.Callback<Result<ArrayList<Region>>> {
                override fun onResponse(
                    call: Call<Result<ArrayList<Region>>>,
                    response: Response<Result<ArrayList<Region>>>
                ) {

                    if (response.body()?.code == 200) {
                        handle(response.body()!!.data)
                    } else
                        Util.makeToast(context, response.body()!!.message)
                }

                override fun onFailure(call: Call<Result<ArrayList<Region>>>, t: Throwable) {
                    Util.makeToast(context, t.message)
                }

            })

    }

    /**
     * 三级地址查询
     */
    private fun initSpinner() {

        //获取省份信息
        query(
            request = {
                it.provinces
            },
            handle = {
                val list = ArrayList<Item>()

                for (region in it)
                    list.add(
                        Item(
                            region.title,
                            region.id
                        )
                    )

                s_idProvinces.adapter = SpinnerAdapter(
                    context!!,
                    android.R.layout.simple_list_item_1,
                    list
                )

                (s_idProvinces.adapter as SpinnerAdapter).notifyDataSetChanged()
            })

        //当省份选定时 加载下一级数据
        s_idProvinces.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {


                query(request = {
                    //
                    it.getCities((s_idProvinces.adapter.getItem(position) as Item).value.toString())

                },
                    handle = {
                        val list = ArrayList<Item>()

                        for (region in it)
                            list.add(
                                Item(
                                    region.title,
                                    region.id
                                )
                            )

                        s_idCity.adapter = SpinnerAdapter(
                            context!!,
                            android.R.layout.simple_list_item_1,
                            list
                        )
                        (s_idCity.adapter as SpinnerAdapter).notifyDataSetChanged()
                    }

                )
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        //加载下一级数据
        s_idCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                query(request = {

                    it.getCounty("${(s_idCity.adapter.getItem(p2) as Item).value}fid=${(s_idProvinces.selectedItem as Item).value}")

                },
                    handle = {
                        val list = ArrayList<Item>()

                        for (region in it)
                            list.add(
                                Item(
                                    region.title,
                                    region.id
                                )
                            )

                        s_idSeat.adapter = SpinnerAdapter(
                            context!!,
                            android.R.layout.simple_list_item_1,
                            list
                        )

                        (s_idSeat.adapter as SpinnerAdapter).notifyDataSetChanged()
                    })


            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

    }

    /**
     * @param img 图片本地地址
     */
    private fun uploadCard(img: String) {
        Util.makeToast(context, "正在上传${img},请稍后")

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
                        val img_ = response.body()!!.data.img
                        if (!img1.contains("http")) {
                            img1 = img_
                        } else {
                            img2 = img_
                        }
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


    //接收相册返回预览相片
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                val images =
                    data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<*>
                val img_ = (images[0] as ImageItem).path
                //相当于Java switch,判断当前点击的选项    渲染并上传
                when (requestCode) {
                    IMAGE_PICKER_1 -> {
                        img1 = img_

                        Glide.with(this@Fragment_cmp_msg).load(img1).into(idcard1)

//                        uploadCard(img1)
                    }
                    IMAGE_PICKER_2 -> {
                        img2 = img_
//                        idcard2.setImageBitmap( BitmapFactory.decodeFile(img2))
                        Glide.with(this@Fragment_cmp_msg).load(img2).into(idcard2)
//                        uploadCard(img2, false)
                    }
                }
                uploadCard(img_)
            } else {
                Util.makeToast(context, "无数据")
            }
        }

    }

}

