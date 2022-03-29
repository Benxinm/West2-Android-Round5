package com.benxinm.androidround5

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.ViewFlipper
import androidx.recyclerview.widget.GridLayoutManager
import com.benxinm.androidround5.view.adapter.ImageAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_second.*
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.math.sqrt

/**
 * 九宫格主要是改造adapter实现的,实现了三个三个的点击接口
 * 点击是显示详情，长按是删除，点击加号是添加图片
 * 展示图片的采用了dialog但是因为实现它需要context和activity
 * 为了整洁和减少未知bug放在了activity中
 * 以及这里的图片为了测试方便都用int类型进行存储可以根据需要进行更改
 */
class SecondActivity : AppCompatActivity() {
    val imageList= mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        //测试用途
        repeat(4){
            imageList.add(R.drawable.number_seven)
        }
        val imageAdapter=ImageAdapter(this,imageList)
        imageAdapter.setOnItemClickListener(object:ImageAdapter.OnItemClickListener{
            override fun onTakePhotoClick() {
                imageList.add(R.drawable.number_seven)
                imageAdapter.notifyDataSetChanged()
            }
            override fun onItemLongClick(view: View?, position: Int) {
                imageList.removeAt(recyclerView.getChildAdapterPosition(view!!))
                imageAdapter.notifyDataSetChanged()
            }

            override fun onShowPhotoClick(view: View?) {
                showMyDialog(imageList[recyclerView.getChildAdapterPosition(view!!)])
            }
        })
        recyclerView.layoutManager= GridLayoutManager(this, 3 )
        recyclerView.adapter=imageAdapter
        toLoginActivity.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
    fun showMyDialog(url:Int){
        try {
            val popView= LayoutInflater.from(this).inflate(R.layout.dialog_image,null)
            val dialogImageView=popView.findViewById<ImageView>(R.id.iv_dialog_image)
            Glide.with(this).load(url).into(dialogImageView)
            val popupDialog=PopupWindow(popView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            popupDialog.setOutsideTouchable(true)
            popupDialog.setFocusable(true)
            if (popupDialog!=null&& !popupDialog.isShowing){
                popupDialog.animationStyle=R.style.dialog_anim
                popupDialog.showAtLocation(window.decorView,
                    Gravity.CENTER, 0, 0)
                popupDialog.setFocusable(true)
            }
        }catch (e:Exception){
            throw RuntimeException(e)
        }
    }
}