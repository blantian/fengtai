package com.example.fengtai.fragment.farmdoc.compensate

import android.app.DatePickerDialog
import android.os.Build
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.Compensate
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.fragment_fdarea_add.btn_save
import kotlinx.android.synthetic.main.fragment_fdcompensate_add.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class FDCompensateAddFragment : BaseFragment(R.layout.fragment_fdcompensate_add) {
    private val type = "".toMediaTypeOrNull()
    override fun onStart() {
        super.onStart()

        btn_save.setOnClickListener {
            MyApplication.API.addCompensate(
                MyApplication.userId.toRequestBody(type),
                et_item.text.toString().toRequestBody(type),
                et_money.text.toString().toRequestBody(type),
                et_time.text.toString().toRequestBody(type)
            ).enqueue(object : retrofit2.Callback<Result<Compensate>> {
                override fun onFailure(call: Call<Result<Compensate>>, t: Throwable) {
                    Util.makeToast(context, t.message)
                }

                override fun onResponse(
                    call: Call<Result<Compensate>>,
                    response: Response<Result<Compensate>>
                ) {
                    if (response.body()?.code == 200) {

                        Util.makeToast(context, response.body()!!.message)
                        backPrev()

                    } else {
                        Util.makeToast(context, response.body()!!.message)
                    }

                }

            })

        }
        et_time.setOnClickListener {
            timePicker()
        }

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
