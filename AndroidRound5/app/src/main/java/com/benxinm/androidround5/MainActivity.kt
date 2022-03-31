package com.benxinm.androidround5

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.benxinm.androidround5.bean.Post
import com.hjq.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 这个柱状图的显示有些不够完善，略显粗糙
 * 但是基本功能都是实现了的，如：颜色的设定，宽度的设定，高度的设定(高度的显示是按照比例显示的)，最小间隔只是实现了接口并没有进行展示(默认最小为10)
 * 跳转的按钮在虚拟机中可能会被挡住但是真机上是可以显示清楚的
 */
class MainActivity : AppCompatActivity() {
    var adapter:ArrayAdapter<CharSequence>?=null
    var postColor=Color.BLACK
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ToastUtils.init(this.application)
        myLayout.setWillNotDraw(false)
        adapter=ArrayAdapter.createFromResource(this,R.array.colors,android.R.layout.simple_spinner_item)
        adapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        colorSpinner.adapter=adapter
        colorSpinner.onItemSelectedListener=SpinnerXMLSelectedListener()
        colorSpinner.visibility=View.VISIBLE
        var count=0
        button1.setOnClickListener {
            count++
            val postWidth=postWidthView.text.toString()
            val postHeight=postHeightView.text.toString()
            myLayout.add(postHeight, postColor,postWidth,null)
        }
        button2.setOnClickListener {
            val deletePosition=deletePositionView.text.toString()
            myLayout.delete(deletePosition)
        }
        toNextActivity.setOnClickListener{
            val intent=Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * 这里的多选框是用来测试用的，所有没有封装
     */
    inner class SpinnerXMLSelectedListener(): AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            arg0: AdapterView<*>?, arg1: View?, arg2: Int,
            arg3: Long
        ) {
            postColor=getSelectedColor(adapter?.getItem(arg2).toString())
        }
        override fun onNothingSelected(arg0: AdapterView<*>?) {}
    }
    fun getSelectedColor(colorSelected:String):Int{
        var color=Color.BLACK
        when(colorSelected){
            "红"->color=Color.RED
            "黄"->color=Color.YELLOW
            "蓝"->color=Color.BLUE
            "绿"->color=Color.GREEN
        }
        return color
    }
}