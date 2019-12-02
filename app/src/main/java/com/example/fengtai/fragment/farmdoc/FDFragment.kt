package com.example.fengtai.fragment.farmdoc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fengtai.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FDMemberFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FDMemberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FDMemberFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fd, container, false)
    }
    override fun onResume() {
        super.onResume()
        view!!.setOnKeyListener { _, code, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_UP && code == KeyEvent.KEYCODE_BACK) {
                // 监听到返回按钮点击事件
                activity!!.supportFragmentManager.popBackStack()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onStart() {
        super.onStart()



    }


}
