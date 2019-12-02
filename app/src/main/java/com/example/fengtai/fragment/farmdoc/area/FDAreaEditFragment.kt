package com.example.fengtai.fragment.farmdoc.area

import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.Plant
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.util.APIService
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.fragment_fdarea_edit.*
import kotlinx.android.synthetic.main.include_fdmember.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response


class FDAreaEditFragment : BaseFragment(R.layout.fragment_fdarea_edit) {
    private val type = "".toMediaTypeOrNull()
    private fun initView(plant: Plant) {
        et_name.setText(plant.name)
        s_type.setSelection(plant.area_type - 1)
        s_status.setSelection(plant.ste_type - 1)
        et_area.setText(plant.area)
    }


    override fun onStart() {
        super.onStart()

        arguments?.let {
            //来自适配器
            val id = it.getString("id")

            request(request = { apiService ->
                apiService.getSte(id)
            }, handle = { plant ->
                initView(plant)
            })

            btn_save.setOnClickListener {

                request(request = { apiService ->
                    apiService.editSte(
                        MyApplication.userId.toRequestBody(type),
                        id.toRequestBody(type),
                        et_name.text.toString().toRequestBody(type),
                        et_area.text.toString().toRequestBody(type),
                        (s_type.selectedItemPosition + 1).toString().toRequestBody(type),
                        (s_status.selectedItemPosition + 1).toString().toRequestBody(type)
                    )
                }, handle = {
                    backPrev()
                })
            }
            btn_del.setOnClickListener {
                request(request = { apiService ->
                    apiService.delSte("[$id]")
                }, handle = {
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
}
