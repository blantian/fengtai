package com.example.fengtai.fragment.farmdoc

import android.content.Intent
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.activity.farmdoc.FarmdocMainActivity
import com.example.fengtai.adapter.SpinnerAdapter
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.HuKou
import com.example.fengtai.entity.Item
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.util.APIService
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.fragment_msg.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response


class Fragment_msg : BaseFragment(R.layout.fragment_msg) {


    override fun onStart() {
        super.onStart()
        val arg = arguments?.getSerializable("hukou")
        val type = "".toMediaTypeOrNull()

        initSpinner()


        //当不是进行编辑操作时会被调用,否则会被覆盖掉
        submit {

            it.enrollHuKou(
                MyApplication.userId.toRequestBody(type),
                et_name.text.toString().toRequestBody(type),
                (s_relation.selectedItem as Item).value.toString().toRequestBody(type),
                et_phone.text.toString().toRequestBody(type),
                (s_force.selectedItem as Item).value.toString().toRequestBody(type)
            )

        }

        arg?.let {
            /**
             *  当Intent传递的参数不为空时调用

             */
            val huKou = it as HuKou
            et_name.setText((huKou.name ?: ""))
            huKou.relations?.toInt()?.let { it1 ->
                //设置当前选定项为接收到的数据项
                s_relation.setSelection(it1 - 1)
            }
            et_phone.setText(huKou.tel ?: "")
            huKou.name?.let {
                //确保未注册(以户主判断)
                //已有数据提交请求改为编辑
                submit { apiService ->
                    apiService.editsHuKou(
                        //创建MAP 结构数据
                        mapOf<String, RequestBody>(
                            "id" to huKou.id.toRequestBody(type),
                            "name" to et_name.text.toString().toRequestBody(type),
                            "relations" to (s_relation.selectedItem as Item).value.toString().toRequestBody(
                                type
                            ),
                            "tel" to et_phone.text.toString().toRequestBody(type),
                            "labour_type" to (s_force.selectedItem as Item).value.toString().toRequestBody(
                                type
                            )
                        )
                    )
                }
            }

            huKou.labour_type?.toInt()?.let { it1 ->
                //设置当前选定项为接收到的数据项
                s_force.setSelection(it1 - 1)
            }
        }
    }

    private fun initSpinner() {
        s_relation.adapter = SpinnerAdapter(
            context!!,
            android.R.layout.simple_list_item_1,
            listOf(
                Item("户主", "1"),
                Item("妻子", "2"),
                Item("儿子", "3"),
                Item("女儿", "4")
            )
        )
        s_force.adapter = SpinnerAdapter(
            context!!,
            android.R.layout.simple_list_item_1,
            listOf(
                Item("是", "1"),
                Item("否", "2")
            )
        )
    }

    /**
     * 利用函数委托简化复用代码
     */
    private fun submit(request: (o: APIService) -> Call<Result<HuKou>>) {
        btn_save.setOnClickListener {
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
                        Util.makeToast(context, "请注意数据填写正确,规范")
                    }
                })
        }

    }
}
