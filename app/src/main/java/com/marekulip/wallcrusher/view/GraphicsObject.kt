package com.marekulip.wallcrusher.view

import android.graphics.Bitmap
import android.graphics.Rect

abstract class GraphicsObject(var left:Int,var top:Int, var right:Int, var bottom:Int, var picture: Bitmap) {
    var drawArea : Rect = Rect(left,top,right,bottom)

    open fun contains(x: Int, y:Int):Boolean{
        return x in left..right && y in this.top..bottom
    }

    open fun moveDown(speed: Int, border:Int):Boolean{
        top+=speed
        bottom += speed
        drawArea.top = top
        drawArea.bottom = bottom
        return true
    }
}