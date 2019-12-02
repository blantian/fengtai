package com.example.fengtai.util

import com.example.fengtai.MyApplication
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.Region
import retrofit2.Call
import retrofit2.Response

class AddressUtil {
    val list: MutableList<Region> = mutableListOf()

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
//                        Util.makeToast(context, response.body()!!.message)
                        handle(response.body()!!.data)
                    }

                }

                override fun onFailure(call: Call<Result<ArrayList<Region>>>, t: Throwable) {
                    t.printStackTrace()
                }

            })

    }

    /**
     * 三级地址查询
     */
    fun initData(): AddressUtil {

        //获取省份信息
//        一级
        query(
            request = {
                it.provinces
            },
            handle = {
                list += it
                for (item in it) {
                    //二级
                    query(request = { apiService ->
                        apiService.getCities(item.id)
                    },
                        handle = { list1 ->
                            list += list1
                            for (city in list1)
                            //三级
                                query(request = { it1 ->
                                    it1.getCounty("${city.id}}")

                                },
                                    handle = { list2 ->

                                        list += list2

                                    })

                        }
                    )


                }
            }
        )
        return this
    }
}
