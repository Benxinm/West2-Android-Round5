package com.benxinm.androidround5.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect

import android.view.WindowManager





object SizeUtil {
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun px2dip(context: Context, pxValue: Float): Int {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toInt()
    }

    fun px2sp(context: Context, pxValue: Float): Int {
        val fontScale: Float = context.getResources().getDisplayMetrics().scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale: Float = context.getResources().getDisplayMetrics().scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 获取当前设备宽度，单位px
     */
    fun getDeviceWidth(context: Context): Int {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return manager.defaultDisplay.width
    }

    /**
     * 获取当前设备高度，单位px
     */
    fun getDeviceHeight(context: Context): Int {
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return manager.defaultDisplay.height
    }

    /**
     * 获取状态栏高度，要在onWindowFocusChanged中调用，在onCreate中获取高度为0
     */
    fun getStatusBarHeight(activity: Activity): Int {
        val frame = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(frame)
        return frame.top
    }
}