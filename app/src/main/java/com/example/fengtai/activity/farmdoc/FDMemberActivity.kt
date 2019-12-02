package com.example.fengtai.activity.farmdoc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.fengtai.R
import com.example.fengtai.adapter.MyPagerAdapter
import com.example.fengtai.fragment.farmdoc.FDItemFragment
import kotlinx.android.synthetic.main.fragment_fd.*

class FDMemberActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fdmember)
        val adapter = MyPagerAdapter(supportFragmentManager)

        adapter.addFragment(
            FDItemFragment.newInstance(R.string.member_name),
            getString(R.string.member_name)
        )
        adapter.addFragment(
            FDItemFragment.newInstance(R.string.member_total),
            getString(R.string.member_total)
        )
        adapter.addFragment(
            FDItemFragment.newInstance(R.string.member_area),
            getString(R.string.member_area)
        )
        adapter.addFragment(
            FDItemFragment.newInstance(R.string.member_compensate),
            getString(R.string.member_compensate)
        )

        viewPager.adapter = adapter
        viewPager.currentItem = intent.getIntExtra("selected", 0)
        tab.setupWithViewPager(viewPager)
    }
}