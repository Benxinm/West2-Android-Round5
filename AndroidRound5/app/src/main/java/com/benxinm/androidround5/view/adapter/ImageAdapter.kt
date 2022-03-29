package com.benxinm.androidround5.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.benxinm.androidround5.R
import com.benxinm.androidround5.util.SizeUtil
import com.bumptech.glide.Glide
import android.widget.PopupWindow
import com.bumptech.glide.request.RequestOptions
import java.lang.Exception


class ImageAdapter(val mContext:Context,val data:MutableList<Int>):RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    companion object{
        const val countLimit=9
    }
    private var onItemClickListener:OnItemClickListener?=null
    inner class ViewHolder(val imageView: ImageView):RecyclerView.ViewHolder(imageView){
    }
    //这里是点击的接口
    interface OnItemClickListener{
        //点击图片接口
        fun onTakePhotoClick()
        //长按接口
        fun onItemLongClick(view: View?,position: Int)
        //展示接口
        fun onShowPhotoClick(view:View?)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView=ImageView(parent.context)
        val params=LinearLayout.LayoutParams(SizeUtil.dip2px(parent.context,95f),SizeUtil.dip2px(parent.context,95f))
        params.setMargins(10,10,10,10);
        params.gravity=Gravity.CENTER
        imageView.layoutParams=params
        return ViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position==getItemCount()-1&&data.size< countLimit){
            Glide.with(mContext).load(R.drawable.add).into(holder.imageView)
            holder.imageView.setOnClickListener(object :View.OnClickListener {
                override fun onClick(p0: View?) {
                    onItemClickListener!!.onTakePhotoClick()
                }
            })
        }else{
            Glide.with(mContext).load(data[position]).into(holder.imageView)
            holder.imageView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(v: View?) {
                   onItemClickListener!!.onShowPhotoClick(v)
                }
            })
            holder.imageView.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    onItemClickListener!!.onItemLongClick(v,holder.adapterPosition)
                    return true
                }
            })
        }
    }
    override fun getItemCount(): Int {
        if (data!=null&&data.size>= countLimit){
            return countLimit
        }else{
            return if (data==null) 1 else data.size+1
        }
    }
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener=onItemClickListener
    }
}