package com.lv.core.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun <T> T.longForDate(long: Long): String {
    return SimpleDateFormat(("yyyy-MM-dd")).format(Date(long))
}

/**
 * 日期格式字符串转换成时间戳
 * @param date 字符串日期
 * @param format 如：yyyy-MM-dd HH:mm:ss
 * @return
 */
@SuppressLint("SimpleDateFormat")
fun <T> T.dateForLong(date_str: String, format: String): Long {
    return SimpleDateFormat(format).parse(date_str)!!.time
}