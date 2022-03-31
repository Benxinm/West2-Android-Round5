package com.benxinm.androidround5.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.benxinm.androidround5.view.adapter.ImageAdapter

class NineGridView:RecyclerView {
    constructor(context:Context):super(context)
    constructor(context: Context,attrs:AttributeSet):super(context,attrs)
    constructor(context: Context,attrs: AttributeSet,index:Int):super(context, attrs,index)
    fun setAdapter(imageAdapter: ImageAdapter){
        adapter=imageAdapter
    }
}