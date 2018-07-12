package com.marekulip.wallcrusher.controler

import android.util.Log
import com.marekulip.wallcrusher.GameManagerIFace
import com.marekulip.wallcrusher.model.Bomb
import com.marekulip.wallcrusher.model.ModelObject
import com.marekulip.wallcrusher.model.Wall
import com.marekulip.wallcrusher.view.*
import kotlin.collections.Map.Entry
import kotlin.math.max

class GameManager(val parent:GameManagerIFace, var canvasAdapter: CanvasAdapter) {

    var score = 5
    var penalty = 1
    var speed:Int = 5
    var step = (canvasAdapter.height / 100.0) / 15
    var gameEnded:Boolean = true

    var maxRandom = 2
    private val viewManager = ViewManager(Pictures(parent.getContext()), canvasAdapter)

    var offsetY = 0
    var highestY = 0
    var gObjectSize = canvasAdapter.height / 6
    var endBorder = canvasAdapter.height - gObjectSize
    var gObjectDistance = gObjectSize * 2




    var gameObjects : HashMap<ModelObject,GraphicsObject> = HashMap()

    fun remove(modelObject: ModelObject){
        score+=modelObject.scoreValue
        synchronized(viewManager.gObjects){
            viewManager.gObjects.remove(gameObjects[modelObject])
        }
        gameObjects.remove(modelObject)
        parent.setScore(score)
        checkScore()
    }

    fun click(xPoint:Int,yPoint:Int){
        val x = xPoint
        val y = yPoint-offsetY
        for(item: Entry<ModelObject,GraphicsObject> in gameObjects){
            if(item.value.contains(x,y)){
                item.key.touched()
                viewManager.makeManCast()
                return
            }
        }
        score -= penalty
        parent.setScore(score)
        checkScore()
    }

    fun checkScore(){
        if(score<=-5)endGame()
        when {
            score<=10 -> {
                speed = 1
                maxRandom = 2
            }
            score<=50 -> {
                speed = 5
                maxRandom = 3
            }
            score<=100 -> {
                speed = 7
                maxRandom = 4
            }
            score<=200 -> {
                speed = 9
                maxRandom = 5
            }
            score<=300 -> {
                speed = 15
                maxRandom = 5
            }
        }
    }

    fun gameTick(){
        val dist = Math.ceil(step * speed).toInt()
        if(gameObjects.isEmpty())createWall(randomInt(1,maxRandom))
        if(!viewManager.gameTick(dist,endBorder))endGame()
        highestY += speed
        if(highestY>gObjectDistance){
            createWall(randomInt(1,maxRandom))
        }
    }

    private fun endGame(){
        parent.endGame()
        gameEnded = true
    }

    fun restart(){
        if(gameEnded) {
            score = 0
            checkScore()
            viewManager.gObjects.clear()
            viewManager.gObjects.add(UnmovableG(0, 0, canvasAdapter.width, canvasAdapter.height, viewManager.pictures.getPicture(0)))
            val man = ManG(canvasAdapter.width - gObjectSize / 2, endBorder, canvasAdapter.width, canvasAdapter.height, viewManager.pictures.getPicture(1), viewManager.pictures.getManPictures())
            viewManager.gObjects.add(man)
            viewManager.man = man
            gameObjects.clear()
            createWall(randomInt(1, maxRandom))
            gameEnded = false
            parent.setScore(score)
        }
    }

    fun randomInt(from:Int,to:Int):Int{
        return (Math.random()*to).toInt() + from
    }

    private fun createWall(bc:Int){
        highestY = 0
        var bombCount = bc
        if (bombCount<1)bombCount = 1
        val wall = Wall(bombCount,this)
        val wallG = WallG(0,0,canvasAdapter.width,gObjectSize,viewManager.pictures.getPicture(2))
        gameObjects[wall] = wallG
        viewManager.gObjects.add(wallG)
        val step:Int =  canvasAdapter.width/bombCount
        for(i in 0..(bombCount-1)){
            val bomb = Bomb(this,wall)
            val bombG = BombG(step*i,0,gObjectSize+step*i,gObjectSize,viewManager.pictures.getPicture(3))
            gameObjects[bomb] = bombG
            viewManager.gObjects.add(bombG)
        }
    }
}