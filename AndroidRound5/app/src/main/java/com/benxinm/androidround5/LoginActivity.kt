package com.benxinm.androidround5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.benxinm.androidround5.view.LoginView
import com.hjq.toast.ToastUtils
import kotlinx.android.synthetic.main.login_view.*
import kotlinx.android.synthetic.main.login_view.view.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val view= LoginView(this)
        /**
         * 这里只有登录的按钮实现了接口
         */
        login.setOnClickListener {
            view.changeActivity(this,MainActivity())
        }
        var count=0
        loginImg.setOnClickListener {
            if (count%2==0){
                view.setImage(this,R.drawable.number_seven,loginImg)
            }else{
                view.setImage(this,R.drawable.happy,loginImg)
            }
            count++
        }
    }
}