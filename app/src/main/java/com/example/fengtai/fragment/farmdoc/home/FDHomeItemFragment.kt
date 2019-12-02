package com.example.fengtai.fragment.farmdoc.home


import android.os.Bundle
import android.view.View
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.entity.base.Result
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.util.APIService
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.fragment_fdhome_item.*
import retrofit2.Call
import retrofit2.Response

private const val ARG_PARAM0 = "param0"
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"


class FDHomeItemFragment : BaseFragment(R.layout.fragment_fdhome_item) {
    private var layoutType: Int? = 0
    private var type: Int? = 0
    private var startTime: String? = ""
    private var endTime: String? = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            startTime = it.getString(ARG_PARAM1)
            endTime = it.getString(ARG_PARAM2)
            layoutType = it.getInt(ARG_PARAM3)
            type = it.getInt(ARG_PARAM0)
        }
    }


    override fun onStart() {
        super.onStart()
        initViews()
    }

    private fun initViews() {
        when (layoutType) {
            //支出收入
            0 -> {
                when (type) {
                    //月
                    0 -> flatInOut("3")
                    //年
                    1 -> flatInOut("5")
                    //自定义
                    2 -> flatInOut("")
                }
            }
            //面积
            1 -> {

                when (type) {
                    //月
                    0 -> flatArea("3")
                    //年
                    1 -> flatArea("5")
                    //自定义
                    2 -> flatArea("")
                }

            }
            //补贴
            2 -> {
                when (type) {
                    0 -> flatCompensate("3")
                    1 -> flatCompensate("5")
                    2 -> flatCompensate("")
                }
            }
        }
    }

    public fun sendDate(startTime: String?, endTime: String?) {
        this.startTime = startTime
        this.endTime = endTime
        initViews()
    }

    private fun flatInOut(type: String) {
        query(request = {
            it.countInOut(MyApplication.userId, type, startTime, endTime, "")
        }, handle = {
            flatData("总收入", it.income, "总支出", it.expenditure)
        })
    }

    private fun flatArea(type: String) {
        flatData("总面积", "0", "总种类", "0")
    }


    private fun flatCompensate(type: String) {
        query(request = {
            it.countCompensate(MyApplication.userId, type, startTime, endTime, "")
        }, handle = {
            if (it.size!=0) {
                flatData("总补贴", it[0].datacountsum)
            }
        })
    }


    private fun <T> query(
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
//                        Util.makeToast(context, response.body()!!.message)
                        handle(response.body()!!.data)
                    }
                }

                override fun onFailure(call: Call<Result<T>>, t: Throwable) {
                    Util.makeToast(context, t.localizedMessage)
                }

            })
    }


    /**
     * 接收到数据后的数据填充
     */
    private fun flatData(t1: String, info1: String, t2: String? = null, info2: String? = null) {
        dateRange.text = "$startTime ~ $endTime"
        t_1.text = t1
        info_1.text = info1


        //根据差异调整显示
        t_2.visibility = View.INVISIBLE
        info_2.visibility = View.INVISIBLE

        t2?.let {
            t_2.visibility = View.VISIBLE
            t_2.text = it
        }

        info2?.let {
            info_2.visibility = View.VISIBLE
            info_2.text = it
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *@param type 3 月 5 年
         * @return A new instance of fragment FDCompensateAddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(layoutType: Int, type: Int, param1: String?, param2: String?) =
            FDHomeItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putInt(ARG_PARAM3, layoutType)
                    putInt(ARG_PARAM0, type)
                }
            }
    }
}
