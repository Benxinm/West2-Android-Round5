package com.benxinm.androidround5.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.benxinm.androidround5.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.login_view.view.*

class LoginView:LinearLayout {
    constructor(context: Context):super(context)
    constructor(context: Context, attrs: AttributeSet):super(context,attrs)
    init {
       LayoutInflater.from(context).inflate(R.layout.login_view,this)
    }
    /**
     *  实现了界面跳转的接口，可为登录成功和注册界面的跳转
     *  逻辑判断可以放在调用他的地方，测试这里不知道为什么无法获取editText里的内容
     */
    fun changeActivity(context: Context,activity: Activity){
        val intent=Intent(context,activity.javaClass)
        context.startActivity(intent)
    }
    /**
     * 图片设置
     */
    fun setImage(context: Context,url:Int,imageView: ImageView){
        Glide.with(context).load(url).into(imageView)
    }

    /**
     * 取不到值
    fun getAccount():String{
        val ac=findViewById<EditText>(R.id.loginAc)
        return ac.text.toString()
    }
    fun getPassword():String{
        val psd=findViewById<EditText>(R.id.loginPsd)
        return psd.text.toString()
    }
    */
}