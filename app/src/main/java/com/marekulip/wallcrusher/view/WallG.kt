package com.marekulip.wallcrusher.view

import android.graphics.Bitmap

class WallG(left:Int,top:Int, right:Int, bottom:Int, picture: Bitmap):GraphicsObject(left,top,right, bottom, picture) {

    override fun moveDown(speed: Int, border:Int):Boolean{
        super.moveDown(speed, border)
        if(bottom>border){
            return false
        }
        return true
    }

    override fun contains(x: Int, y: Int): Boolean {
        return false
    }
}