package com.marekulip.wallcrusher.view

class ViewManager(val pictures:Pictures,var canvas: CanvasAdapter) {
    var man : ManG? = null
    var gObjects: MutableList<GraphicsObject> = ArrayList()
    fun draw(){
        for(g:GraphicsObject in gObjects){
            canvas.draw(g)
        }
    }

    fun gameTick(speed:Int,border:Int):Boolean{
        synchronized(gObjects) {
            for (g: GraphicsObject in gObjects) {
                if (!g.moveDown(speed, border)) {
                    return false
                }
                canvas.draw(g)
            }
            return true
        }
    }

    fun makeManCast(){
        man?.castSpell()
    }
}