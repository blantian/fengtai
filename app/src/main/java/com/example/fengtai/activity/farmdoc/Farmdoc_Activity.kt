package com.example.fengtai.activity.farmdoc

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.fengtai.MyApplication
import com.example.fengtai.R
import com.example.fengtai.adapter.MyPagerAdapter
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.HuKou
import com.example.fengtai.fragment.farmdoc.Fragment_cmp_msg
import com.example.fengtai.fragment.farmdoc.Fragment_msg
import com.example.fengtai.util.Util
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Farmdoc_Activity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_farmdoc)

        //根据是否有名判断是否为新注册用户
        MyApplication.API.getHuKouList(MyApplication.userId).enqueue(object :
            Callback<Result<ArrayList<HuKou>>> {
            override fun onResponse(
                call: Call<Result<ArrayList<HuKou>>>,
                response: Response<Result<ArrayList<HuKou>>>
            ) {
                if (response.body()!!.code == 200) {
                    //成功后解析数据
                    flatViews(response.body()!!.data[0])

                } else {
                    Util.makeToast(this@Farmdoc_Activity, "请填写牧户档案")
                    val intent = Intent(this@Farmdoc_Activity, Farmdoc_Activity::class.java)
                    this@Farmdoc_Activity.startActivity(intent)
                }
            }

            override fun onFailure(
                call: Call<Result<ArrayList<HuKou>>>,
                t: Throwable
            ) {
                t.printStackTrace()
            }
        })
    }

    private var bundle: Bundle = Bundle()
    private fun flatViews(huKou: HuKou) {
        //将获取到的数据放入bundle传递给跳转页面
        bundle.putSerializable("hukou", huKou)
        initViews()
    }

    private fun initViews() {

        viewPager = findViewById(R.id.viewPager_farm)
        tabLayout = findViewById(R.id.tablayoutdoc)

        val adapter = MyPagerAdapter(supportFragmentManager)

        val f1 = Fragment_msg()
        val f2 = Fragment_cmp_msg()

        f1.arguments = bundle
        f2.arguments = bundle

        adapter.addFragment(f1, "户主信息")
        adapter.addFragment(f2, "完善信息")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        val type = intent.getIntExtra("type", -1)
        if (type == 2)
            viewPager.currentItem = 1

    }

}
