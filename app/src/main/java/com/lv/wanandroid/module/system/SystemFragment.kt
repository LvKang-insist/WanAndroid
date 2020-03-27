package com.lv.wanandroid.module.system

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.elvishew.xlog.XLog
import com.lv.wanandroid.R

class SystemFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        XLog.e("SystemFragment")
        return LayoutInflater.from(context).inflate(R.layout.frag_system, container, false)
    }
}