package com.android.byc.togglesbutton

import android.util.Log

/**
 * @author      yu
 * @version     1.0
 * @date        2019/4/24 14:41
 * @description
 */
object MyLog {
    private var isDebug = true

    fun setDebug(debug: Boolean) {
        isDebug = debug
    }

    fun e(info: String) {
        if (isDebug) {
            Log.e("TAG", info)
        }
    }

    fun d(info: String) {
        if (isDebug) {
            Log.d("TAG", info)
        }
    }
}