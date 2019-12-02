package com.example.fengtai.activity.farmdoc

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.fengtai.MyApplication
import com.example.fengtai.MyApplication.address
import com.example.fengtai.R
import com.example.fengtai.entity.base.Result
import com.example.fengtai.entity.farmdoc.HuKou
import com.example.fengtai.util.Map.labourMap
import com.example.fengtai.util.Map.relationMap
import com.example.fengtai.util.Map.sexMap
import com.example.fengtai.util.Util
import kotlinx.android.synthetic.main.activity_farmerdoc_edit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class FarmdocPersonalActivty : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmerdoc_edit)


        MyApplication.API.getHuKouList(MyApplication.userId).enqueue(object :
            Callback<Result<ArrayList<HuKou>>> {
            override fun onResponse(
                call: Call<Result<ArrayList<HuKou>>>,
                response: Response<Result<ArrayList<HuKou>>>
            ) {
                if (response.body()!!.code == 200) {
                    //成功后解析数据
                    initViews(response.body()!!.data[0])

                } else {
                    Util.makeToast(this@FarmdocPersonalActivty, "请填写牧户档案")
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


    private fun initViews(huKou: HuKou) {

        Glide.with(this@FarmdocPersonalActivty).load(huKou.idcard_front).into(idcard1)
        Glide.with(this@FarmdocPersonalActivty).load(huKou.idcard_side).into(idcard2)

        et_idName.text = huKou.idcard_name
        s_relation.text = relationMap[huKou.relations?.toInt()]
        et_name.text = huKou.name

        s_force.text = labourMap[huKou.labour_type?.toInt()]
        et_idCard.text = huKou.idcard
        et_phone.text = huKou.tel
        et_sex.text = sexMap[huKou.idcard_gender?.toInt()]
        et_idBirth.text = huKou.birth_date
        et_idDetails.text = huKou.xiangxi
        et_idAge.text = huKou.age
        //三级
        s_idProvinces.text = address.find { it.id == huKou.sheng }?.title
        s_idCity.text = address.find { it.id == huKou.shi }?.title
        s_idSeat.text = address.find { it.id == huKou.xian }?.title

        edit.setOnClickListener {
            startActivity(
                Intent(this, Farmdoc_Activity::class.java).putExtra("type", 1)
            )
        }

        edit2.setOnClickListener {
            startActivity(
                Intent(this, Farmdoc_Activity::class.java).putExtra("type", 2)
            )
        }

    }
}
