package com.benxinm.androidround5.view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.benxinm.androidround5.R
import com.benxinm.androidround5.util.SizeUtil
import com.bumptech.glide.Glide
import com.hjq.toast.ToastUtils
import java.lang.Exception
import java.lang.RuntimeException

class NineGridView:RecyclerView {
    companion object{
        const val countLimit=9
    }
    constructor(context:Context):super(context)
    constructor(context: Context,attrs:AttributeSet):super(context,attrs)
    constructor(context: Context,attrs: AttributeSet,index:Int):super(context, attrs,index)
    interface OnItemClickListener{
        //点击图片接口
        fun onTakePhotoClick()
        //长按接口
        fun onItemLongClick(view: View?, position: Int)
        //展示接口
        fun onShowPhotoClick(view: View?)
    }
    inner class ImageAdapter(val mContext:Context,val data:MutableList<Int>):RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
        private var onItemClickListener:OnItemClickListener?=null
        inner class ViewHolder(val imageView: ImageView):RecyclerView.ViewHolder(imageView){
        }
        //这里是点击的接口

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val imageView= ImageView(parent.context)
            val params= LinearLayout.LayoutParams(
                SizeUtil.dip2px(parent.context,95f),
                SizeUtil.dip2px(parent.context,95f))
            params.setMargins(10,10,10,10);
            params.gravity= Gravity.CENTER
            imageView.layoutParams=params

            return ViewHolder(imageView)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position==getItemCount()-1&&data.size< countLimit){
                Glide.with(mContext).load(R.drawable.add).into(holder.imageView)
                holder.imageView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        onItemClickListener!!.onTakePhotoClick()
                    }
                })
            }else{
                Glide.with(mContext).load(data[position]).into(holder.imageView)
                holder.imageView.setOnClickListener(object : View.OnClickListener{
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
        fun getGridLayoutManager(context: Context,size:Int): GridLayoutManager {
            if (size<=0){
                ToastUtils.show("显示出现错误")}
            if (size==1) return GridLayoutManager(context,1)
            if (size>=5){
                return GridLayoutManager(context,3)
            }else{
                return GridLayoutManager(context,2)
            }
        }
        fun showMyDialog(context: Context, parent: View, url: Int){
            try {
                val popView= LayoutInflater.from(context).inflate(R.layout.dialog_image,null)
                val dialogImageView=popView.findViewById<ImageView>(R.id.iv_dialog_image)
                Glide.with(context).load(url).into(dialogImageView)
                val popupDialog=
                    PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                popupDialog.setOutsideTouchable(true)
                popupDialog.setFocusable(true)
                if (popupDialog!=null&& !popupDialog.isShowing){
                    popupDialog.animationStyle= R.style.dialog_anim
                    popupDialog.showAtLocation(parent,
                        Gravity.CENTER, 0, 0)
                    popupDialog.setFocusable(true)
                }
            }catch (e: Exception){
                throw RuntimeException(e)
            }
        }
    }
    fun setAdapter(imageAdapter: ImageAdapter){
        adapter=imageAdapter
    }

}