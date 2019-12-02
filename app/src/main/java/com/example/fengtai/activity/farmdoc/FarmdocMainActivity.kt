package com.example.fengtai.activity.farmdoc

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.adapter.MyPagerAdapter
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.HuKouUser
import com.example.fengtai.entity.farmdoc.InfoCount
import com.example.fengtai.fragment.farmdoc.home.FDHomeItemFragment
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.activity_farmerdoc_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class FarmdocMainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmerdoc_main)


        MyApplication.API.getUser(MyApplication.userId)
            .enqueue(object : Callback<Result<HuKouUser>> {
                override fun onFailure(call: Call<Result<HuKouUser>>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<Result<HuKouUser>>,
                    response: Response<Result<HuKouUser>>
                ) {
                    if (response.body()!!.status == 200) {

                        val body = response.body()!!.data

                        MyApplication.API.getCount(MyApplication.userId).enqueue(object :
                            Callback<Result<InfoCount>> {
                            override fun onFailure(call: Call<Result<InfoCount>>, t: Throwable) {
                                t.printStackTrace()
                            }

                            override fun onResponse(
                                call: Call<Result<InfoCount>>,
                                response: Response<Result<InfoCount>>
                            ) {
                                if (response.body()!!.code == 200) {
                                    //成功后解析数据
                                    initViews(body, response.body()!!.data)
                                }
                            }
                        })


                    } else {
                        Util.makeToast(this@FarmdocMainActivity, "请填写牧户档案")
                        val intent = Intent(this@FarmdocMainActivity, Farmdoc_Activity::class.java)
                        this@FarmdocMainActivity.startActivity(intent)
                    }
                }


            })
    }

    fun initViews(huKou: HuKouUser, infoCount: InfoCount) {

        Glide.with(this).load(huKou.avatar).into(头像)

        用户名.text = huKou.username
        人口.text = infoCount.renkou.toString()
        补贴.text = infoCount.butie
        收支总汇.text = infoCount.zonghui
        草原面积.text = infoCount.mianji?.toString()

        R1.setOnClickListener {
            startActivity(Intent(this, FarmdocPersonalActivty::class.java))
        }

        btn_member.setOnClickListener {
            jumpTab(0)
        }
        btn_total.setOnClickListener {
            jumpTab(1)
        }
        btn_area.setOnClickListener {
            jumpTab(2)
        }

        btn_compensate.setOnClickListener {
            jumpTab(3)
        }

        initRae()
        initArea()
        initCompensate()
    }

    /**
     * 根据类型跳转
     */
    private fun jumpTab(type: Int) {
        startActivity(Intent(this, FDMemberActivity::class.java).putExtra("selected", type))
    }


    @SuppressLint("SimpleDateFormat")
    private val dateFormatter = SimpleDateFormat("YYYY-MM-dd")
    //月初
    @SuppressLint("SimpleDateFormat")
    private val firstDayFormatter = SimpleDateFormat("YYYY-MM-01")
    //年初
    @SuppressLint("SimpleDateFormat")
    private val dayFormatter = SimpleDateFormat("YYYY-01-01")

    private val date = Date()
    /**
     * 初始化收支
     */
    private fun initRae() {
        // todo 从对话框拿到时间后跳入
        addToView(
            new(0),
            viewPager = vp_rae,
            tabLayout = tab_rae
        )
    }

    /**
     * 初始化草场面积
     */
    private fun initArea() {
        addToView(
            new(1),
            viewPager = vp_area,
            tabLayout = tab_area
        )
    }

    /**
     * 初始化补贴
     */
    private fun initCompensate() {
        addToView(
            new(2),
            viewPager = vp_compensate,
            tabLayout = tab_compensate
        )

    }

    private fun new(layout: Int): List<Fragment> {
        return when (layout) {
            0 -> listOf(
                FDHomeItemFragment.newInstance(
                    0,
                    0,
                    firstDayFormatter.format(date),
                    dateFormatter.format(date)
                ),
                FDHomeItemFragment.newInstance(
                    0, 1,
                    dayFormatter.format(date),
                    dateFormatter.format(date)
                ),
                FDHomeItemFragment.newInstance(
                    0, 2,
                    firstDayFormatter.format(date),
                    dateFormatter.format(date)
                )
            )
            1 -> listOf(
                FDHomeItemFragment.newInstance(
                    1,
                    0,
                    firstDayFormatter.format(date),
                    dateFormatter.format(date)
                ),
                FDHomeItemFragment.newInstance(
                    1, 1,
                    dayFormatter.format(date),
                    dateFormatter.format(date)
                ),
                FDHomeItemFragment.newInstance(
                    1, 2,
                    firstDayFormatter.format(date),
                    dateFormatter.format(date)
                )
            )
            else -> listOf(
                FDHomeItemFragment.newInstance(
                    2,
                    0,
                    firstDayFormatter.format(date),
                    dateFormatter.format(date)
                ),
                FDHomeItemFragment.newInstance(
                    2, 1,
                    dayFormatter.format(date),
                    dateFormatter.format(date)
                ),
                FDHomeItemFragment.newInstance(
                    2, 2,
                    firstDayFormatter.format(date),
                    dateFormatter.format(date)
                )
            )
        }

    }

    private fun addToView(fragments: List<Fragment>, viewPager: ViewPager, tabLayout: TabLayout) {
        val adapter = MyPagerAdapter(supportFragmentManager)

        adapter.addFragment(fragments[0], "月")
        adapter.addFragment(fragments[1], "年")
        adapter.addFragment(fragments[2], "自定义")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}
            override fun onTabSelected(p0: TabLayout.Tab?) {
                //调用时间选择器
                if (p0 != null && p0.text == "自定义") {
                    timePicker(fragments[2])
                }
            }

        })

    }

    private fun timePicker(fragment: Fragment) {

        val startTime = Calendar.getInstance()
        val endTime = Calendar.getInstance()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val dialogStart = DatePickerDialog(this)
            val dialogEnd = DatePickerDialog(this)
            dialogStart.setOnDateSetListener { datePicker, year, month, dayOfMonth ->

                startTime.set(Calendar.YEAR, year)
                startTime.set(Calendar.MONTH, month)
                startTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                dialogStart.dismiss()
                dialogEnd.show()

            }
            dialogEnd.setOnDateSetListener { view, year, month, dayOfMonth ->
                dialogEnd.dismiss()
                endTime.set(Calendar.YEAR, year)
                endTime.set(Calendar.MONTH, month)
                endTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                Util.makeToast(
                    this@FarmdocMainActivity,
                    "已选择:${dateFormatter.format(startTime.time)},${dateFormatter.format(endTime.time)}"
                )

                (fragment as FDHomeItemFragment).sendDate(
                    dateFormatter.format(startTime.time),
                    dateFormatter.format(endTime.time)
                )
            }

            dialogStart.show()

        } else {
            Util.makeToast(this, "系统不支持")
        }


    }

}