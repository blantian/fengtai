package com.example.fengtai.fragment.farmdoc

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.adapter.FDItemAdapter
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.Item
import com.example.fengtai.fragment.base.BaseFragment
import com.example.fengtai.fragment.farmdoc.area.FDAreaAddFragment
import com.example.fengtai.fragment.farmdoc.area.FDAreaEditFragment
import com.example.fengtai.fragment.farmdoc.compensate.FDCompensateAddFragment
import com.example.fengtai.fragment.farmdoc.compensate.FDCompensateEditFragment
import com.example.fengtai.fragment.farmdoc.member.FDMemberADDFragment
import com.example.fengtai.fragment.farmdoc.member.FDMemberEditFragment
import com.example.fengtai.util.APIService
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.fragment_fd_item.*
import retrofit2.Call
import retrofit2.Response


private const val ARG_PARAM1 = "param1"


class FDItemFragment : BaseFragment(R.layout.fragment_fd_item) {
    private var tabName: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tabName = it.getInt(ARG_PARAM1)
        }
    }


    override fun onStart() {
        super.onStart()
        when (tabName) {
            R.string.member_name -> {

                query(request = {
                    it.getPersonnels(MyApplication.userId)
                }, handle = {

                    val list = ArrayList<Item>()

                    for (item in it)
                        list.add(
                            Item(
                                item.id,
                                item.name,
                                item.call
                            )
                        )

                    refreshLayout.finishRefresh()

                    initView(FDMemberADDFragment(), list, FDMemberEditFragment())
                })


            }
            R.string.member_total -> {

                initView(null, ArrayList(), null)

            }
            R.string.member_area -> {
                query(request = {
                    it.getStes(MyApplication.userId)
                }, handle = {

                    val list = ArrayList<Item>()

                    for (item in it)
                        list.add(
                            Item(
                                item.id,
                                item.name,
                                item.area + " m²"
                            )
                        )

                    refreshLayout.finishRefresh()

                    initView(FDAreaAddFragment(), list, FDAreaEditFragment())
                })


            }
            R.string.member_compensate -> {
                query(request = {
                    it.getCompensates(MyApplication.userId)
                }, handle = {

                    val list = ArrayList<Item>()

                    for (item in it)
                        list.add(
                            Item(
                                item.id,
                                item.title,
                                item.price + " 元"
                            )
                        )

                    refreshLayout.finishRefresh()

                    initView(FDCompensateAddFragment(), list, FDCompensateEditFragment())
                })


            }
        }

        refreshLayout.setOnRefreshListener {
          refresh()
        }

    }

    fun refresh() {
        onStart()
        refreshLayout.finishRefresh()
    }

    private fun <T> query(
        request: (o: APIService) -> Call<Result<ArrayList<T>>>,
        handle: (body: ArrayList<T>) -> Unit
    ) {
        request(MyApplication.API)
            .enqueue(object : retrofit2.Callback<Result<ArrayList<T>>> {
                override fun onResponse(
                    call: Call<Result<ArrayList<T>>>,
                    response: Response<Result<ArrayList<T>>>
                ) {
                    if (response.body()?.code == 200) {
//                        Util.makeToast(context, response.body()!!.message)
                        handle(response.body()!!.data)
                    }
                }

                override fun onFailure(call: Call<Result<ArrayList<T>>>, t: Throwable) {
                    Util.makeToast(context, "请注意数据填写正确,规范")
                }

            })
    }


    private fun initView(fragment: Fragment?, items: ArrayList<Item>, type: Fragment?) {
        //页面标题
        title.text = tabName?.let { getString(it) }

        btn_add.setOnClickListener {
            fragment?.let { it1 ->
                activity!!.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragment, it1)
                    .addToBackStack(null)
                    .commit()
            }
        }

        fdmember_list.layoutManager = LinearLayoutManager(context)

        fdmember_list.adapter = FDItemAdapter(items, this, type)

//        fdmember_list.adapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        //添加分割线
        fdmember_list.addItemDecoration(object : RecyclerView.ItemDecoration() {
            /**
             *
             * @param outRect 边界
             * @param view recyclerView ItemView
             * @param parent recyclerView
             * @param state recycler 内部数据管理
             */
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = 8
            }

        })

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FDItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            FDItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}
