package com.marekulip.wallcrusher.view

import android.graphics.Canvas
import android.util.Log

class CanvasAdapter(var width:Int,var height:Int) {
    var canvas: Canvas? = null
        set(value) {
            if(value!= null){
                field = value
                width = value.width
                height = value.height
            }
        }

    public fun draw(item:GraphicsObject){
        if (canvas!=null) {
            canvas?.drawBitmap(item.picture, null, item.drawArea, null)
        }else {
            Log.d("tst","Canvas is null")
        }
    }
}