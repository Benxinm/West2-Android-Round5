package com.benxinm.androidround5.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PostView:View {
    private var process=0f
    private var myLeft=0f
    private var myStart=0f
    private var myRight=0f
    private var fatherHeight=0
    private var color=0
    private var text:String=""
    constructor(context: Context,left:Float,start:Float,right:Float,fatherHeight:Int):super(context){
        myLeft=left
        myStart=start
        myRight=right
        this.fatherHeight=fatherHeight
    }
    constructor(context: Context, attrs: AttributeSet): super(context,attrs){

    }
    fun setProcess(process:Float){
        this.process=process
        invalidate()
    }
    fun setColor(color:Int?){
        if (color!=null){
            this.color=color
        }else{
            this.color=Color.BLACK
        }
    }
    fun setText(text: String?){
        if (text!=null){
            this.text=text
        }
    }
    fun getColor():Int{
        return color
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension((myRight-myLeft).toInt(),fatherHeight)
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)//TODO 3
        val paint=Paint()
        paint.textSize=25f
        if (this.text!=null){
            canvas?.drawText(text,(myRight-myLeft)*0.2.toFloat(),(fatherHeight).toFloat()-process,paint)
        }
        paint.color= color
        canvas?.drawRect(0f,fatherHeight-process,myRight-myLeft,fatherHeight.toFloat(), paint)

    }
    fun startAnimator(targetHeight:Float){
        val animator= ObjectAnimator.ofFloat(this,"process",0f,targetHeight)
        animator.setDuration(1500).start()
    }
}