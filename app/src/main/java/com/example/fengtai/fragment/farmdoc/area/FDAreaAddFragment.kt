package com.example.fengtai.fragment.farmdoc.area

import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.Plant
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.fragment_fdarea_add.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response


class FDAreaAddFragment : BaseFragment(R.layout.fragment_fdarea_add) {
    private val type = "".toMediaTypeOrNull()
    override fun onStart() {
        super.onStart()

        btn_save.setOnClickListener {
            MyApplication.API.addSte(
                MyApplication.userId.toRequestBody(type),
                et_name.text.toString().toRequestBody(type),
                et_area.text.toString().toRequestBody(type),
                (s_type.selectedItemPosition + 1).toString().toRequestBody(type),
                (s_status.selectedItemPosition + 1).toString().toRequestBody(type)
            ).enqueue(object : retrofit2.Callback<Result<Plant>> {
                override fun onFailure(call: Call<Result<Plant>>, t: Throwable) {
                    Util.makeToast(context, t.message)
                }

                override fun onResponse(
                    call: Call<Result<Plant>>,
                    response: Response<Result<Plant>>
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
    }


}
