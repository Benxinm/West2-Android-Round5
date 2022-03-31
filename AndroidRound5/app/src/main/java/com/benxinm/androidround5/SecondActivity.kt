package com.benxinm.androidround5

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import com.benxinm.androidround5.view.NineGridView
import com.benxinm.androidround5.view.NineGridView.ImageAdapter
import kotlinx.android.synthetic.main.activity_second.*

/**
 * 九宫格主要是改造adapter实现的,实现了三个的点击接口
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
        val imageAdapter= NineGridView(this).ImageAdapter(this, imageList)
        imageAdapter.setOnItemClickListener(object: NineGridView.OnItemClickListener{
            override fun onTakePhotoClick() {
                imageList.add(R.drawable.number_seven)
                nineGridView.layoutManager= imageAdapter.getGridLayoutManager(this@SecondActivity,imageList.size)
                imageAdapter.notifyDataSetChanged()
            }
            override fun onItemLongClick(view: View?, position: Int) {
                imageList.removeAt(nineGridView.getChildAdapterPosition(view!!))
                nineGridView.layoutManager= imageAdapter.getGridLayoutManager(this@SecondActivity,imageList.size)
                imageAdapter.notifyDataSetChanged()
            }
            override fun onShowPhotoClick(view: View?) {
                imageAdapter.showMyDialog(this@SecondActivity,window.decorView,imageList[nineGridView.getChildAdapterPosition(view!!)])
            }
        })
        nineGridView.layoutManager= imageAdapter.getGridLayoutManager(this,imageList.size)
        nineGridView.setAdapter(imageAdapter)
        toLoginActivity.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
}