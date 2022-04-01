package com.benxinm.androidround5.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.benxinm.androidround5.R
import com.benxinm.androidround5.bean.Post
import com.hjq.toast.ToastUtils
import kotlinx.android.synthetic.main.activity_main.view.*
import java.lang.Exception
import kotlin.concurrent.thread

/**
 *这里我无法实现在一个view上实现柱形图的动画,所以继承了layout并在上面添加postView
 *文字设置了设置内容、大小和能否可见的接口
 */
class HistogramLayout:LinearLayout {
    constructor(context:Context):super(context){

    }
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){

    }
    companion object{
        var xLocation=50f
        var yLocation=200f
        var mHeight=0f
        var mWidth=0f
        var gap=20f
        var count=0
        var postWidth=50
        var mIntHeight=0
        var minimumGap=10
        var textSize=40f
        var bottomTextVisible=true
        var leftTextVisible=true
        var bottomText="测试用的表"
        var leftText="测试用的竖轴"
        private var postList = mutableListOf<Post>()
        private val postViewList= mutableListOf<PostView>()
        var flag=true
    }

    override fun addView(child: View?, index: Int) {
        super.addView(child, index)
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeight=measuredHeight*0.3.toFloat()
        mIntHeight= mHeight.toInt()
        yLocation=measuredHeight*0.5.toFloat()
        mWidth=measuredWidth*0.8.toFloat()
        xLocation=measuredWidth*0.1.toFloat()
        if (postViewList.size==1){
            gap= mWidth*0.5.toFloat()
        } else{
            gap=(mWidth- postWidth* postViewList.size)/ (postViewList.size+1)
        }
        /**
         * 最小间距判定
         */
        if (gap< minimumGap){
            ToastUtils.show("达到设定最小间隔")
            flag=false
        }
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val paint= Paint()
        /**这里是按照比例来算的*/
        if (bottomTextVisible){
            paint.textSize= textSize
            val rect= Rect()
            paint.getTextBounds(bottomText,0, bottomText.length,rect)
            val wordsLength= rect.width()
            Log.d("555",wordsLength.toString())
            val wordsHeight= rect.height()
            canvas?.drawText(bottomText, xLocation+ mWidth/2-wordsLength/2, yLocation+wordsHeight+wordsHeight/4,paint)
        }
        if (leftTextVisible){
            paint.textSize= textSize
            val rect= Rect()
            paint.getTextBounds(leftText,0, leftText.length,rect)
            val wordsLength= rect.width()*3
            val wordsHeight= rect.height()
            val path=Path()
            path.lineTo(0f,wordsLength.toFloat())
            canvas?.drawTextOnPath(leftText,path, yLocation- mHeight*0.75.toFloat(), -(xLocation-wordsHeight-wordsHeight/3),paint)
        }
        paint.style=Paint.Style.STROKE
        paint.strokeWidth=5f
        canvas?.drawLine(xLocation, yLocation, xLocation+ mWidth, yLocation,paint)
        canvas?.drawLine(xLocation, yLocation, xLocation, yLocation- mHeight,paint)
    }

    /**
     *
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {//TODO 2
        layoutChildren((xLocation+gap).toInt(), (yLocation- mIntHeight).toInt(),(xLocation+gap).toInt()+ postWidth,
            yLocation.toInt(),false)
    }
    fun start(color:Int?,postHeight:String?){
        val view= PostView(this.context, xLocation+gap+(gap+ postWidth)* count, yLocation, xLocation+(gap+ postWidth)* (count+1),
            mIntHeight)
        view.setText(postHeight)
        view.setColor(color)//Color
        addView(view, count)
        postViewList.add(count,view)
        count++
    }
    fun add(height:String,color: Int?,width:String?,text:String?){
        /**
         * 判断是否到达了最小间隔，以及对输入的错误数据的捕获
         */
        if (flag){
            var post:Post?
            try {
                post=Post(height.toFloat(),text)
            }catch (e:Exception){
                ToastUtils.show("输入的高度不合法")
                return
            }
            if (width!=null){
                when(width){
                    ""->ToastUtils.show("使用默认宽度")
                    else->{
                        try {
                            val trans2Int=width.toInt()
                            setPostWidth(trans2Int)
                        }catch (e:Exception){
                            ToastUtils.show("输入的宽度不合法请重新输入")
                            return
                        }
                    }
                }
            }
            postList.add(post)
            start(color,height)
        }
    }
    fun delete(index: String){
        var indexInt:Int?

        try {
            indexInt=index.toInt()-1
            if (indexInt> count-1){
                ToastUtils.show("输入的位置超出范围")
                return
            }
        }catch (e:Exception){
            ToastUtils.show("输入的位置不合法请重新输入")
            return
        }
        val view= postViewList[indexInt]
        postViewList.remove(view)
        count--
        removeView(view)
    }
    fun layoutChildren(left:Int,top:Int,right:Int,bottom:Int,forceLeftGravity:Boolean){
        val childCount=childCount
        val max=getMax(postList)
        var tmp=0f
        for (index in 0 until childCount){
            val child=getChildAt(index) as PostView
            child.layout(left+(gap.toInt()+ postWidth)*index,top,right+(gap.toInt()+ postWidth)*index,bottom)
            tmp=postList[index].height/max* mIntHeight-20//TODO 1
            child.startAnimator(tmp)
        }
    }
    fun setPostWidth(width: Int){
        postWidth=width
    }
    fun setTextSize(size:Float){
        textSize=size
    }
    fun setBottomText(text: String?){
        if (text!=null){
            bottomText=text
        }
    }
    fun setLeftText(text: String?){
        if (text!=null){
            leftText=text
        }
    }
    fun setBottomTextVisible(){
        bottomTextVisible=!bottomTextVisible
        thread {
            invalidate()
        }
    }
    fun setLeftTextVisible(){
        leftTextVisible=!leftTextVisible
        thread {
            invalidate()
        }
    }
    //设定最小间距
    fun setMinimumGap(gap:Int){
        minimumGap=gap
    }
    private fun getMax(list: MutableList<Post>):Float{
        var max=0f
        if (list.size==0){
            return max
        }else{
            for (tmp in list){
                if (tmp.height>max) max=tmp.height
            }
            return max
        }
    }
}