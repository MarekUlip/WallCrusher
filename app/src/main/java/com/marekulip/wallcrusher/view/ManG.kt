package com.marekulip.wallcrusher.view

import android.graphics.Bitmap

class ManG(left:Int,top:Int, right:Int, bottom:Int, picture:Bitmap, private val movePictures: List<Bitmap>):GraphicsObject(left,top,right, bottom, picture) {
    var waitFrames = 0
    val castPicture = picture
    val frameCount = 2
    var lastFrame = -1

    fun castSpell(){
        waitFrames = 15
        picture = castPicture
    }

    override fun moveDown(speed: Int, border: Int): Boolean {
        if(waitFrames>0){
            waitFrames--
        }else {
            if (++lastFrame == frameCount) lastFrame = 0
            picture = movePictures[lastFrame]
            waitFrames = 5
        }
        return true
    }

    override fun contains(x: Int, y: Int): Boolean {
        return false
    }
}