package com.example.fengtai.fragment.farmdoc.compensate

import android.app.DatePickerDialog
import android.os.Build
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.Compensate
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.util.APIService
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.fragment_fdcompensate_edit.*
import kotlinx.android.synthetic.main.include_fdmember.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class FDCompensateEditFragment : BaseFragment(R.layout.fragment_fdcompensate_edit) {
    private val type = "".toMediaTypeOrNull()

    private fun initView(compensate: Compensate) {
        et_item.setText(compensate.title)
        et_money.setText(compensate.price)
        et_time.text = compensate.addtime
    }

    override fun onStart() {
        super.onStart()

        et_time.setOnClickListener {
            timePicker()
        }




        arguments?.let {
            //来自适配器
            val id = it.getString("id")
            request(request = { apiService ->
                apiService.getCompensate(id)
            },
                handle = { compensate ->
                    initView(compensate)
                }
            )


            btn_del.setOnClickListener {
                request(
                    request = { apiService -> apiService.delCompensate("[$id]") },
                    handle = {
                        backPrev()
                    })
            }



            btn_save.setOnClickListener {
                request(
                    request = { apiService ->
                        apiService.editCompensate(
                            MyApplication.userId.toRequestBody(type),
                            id.toRequestBody(type),
                            et_item.text.toString().toRequestBody(type),
                            et_money.text.toString().toRequestBody(type),
                            et_time.text.toString().toRequestBody(type)
                        )
                    },
                    handle = {
                        backPrev()
                    })

            }


        }


    }


    private fun <T> request(
        request: (o: APIService) -> Call<Result<T>>,
        handle: (body: T) -> Unit
    ) {
        request(MyApplication.API)
            .enqueue(object : retrofit2.Callback<Result<T>> {
                override fun onResponse(
                    call: Call<Result<T>>,
                    response: Response<Result<T>>
                ) {
                    if (response.body()?.code == 200) {
                        Util.makeToast(context, response.body()!!.message)
                        handle(response.body()!!.data)
                    }
                }

                override fun onFailure(call: Call<Result<T>>, t: Throwable) {
                    Util.makeToast(context, t.localizedMessage)
                }

            })
    }

    private fun timePicker() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val dialogStart = DatePickerDialog(context)
            dialogStart.setOnDateSetListener { datePicker, year, month, dayOfMonth ->
                val time = Calendar.getInstance()
                time.set(Calendar.YEAR, year)
                time.set(Calendar.MONTH, month)
                time.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                et_time.text = SimpleDateFormat("YYYY-MM-dd").format(time.time)
            }

            dialogStart.show()

        } else {
            Util.makeToast(context, "系统不支持")
        }


    }
}
