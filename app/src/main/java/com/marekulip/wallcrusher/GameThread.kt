package com.marekulip.wallcrusher

import android.content.Context
import android.util.Log
import android.view.SurfaceHolder
import com.marekulip.wallcrusher.controler.GameManager
import com.marekulip.wallcrusher.view.CanvasAdapter

class GameThread(val context: Context,var surfaceHolder: SurfaceHolder) {
    var isRunning:Boolean = false
    val gameManager = GameManager(context as GameManagerIFace, CanvasAdapter(surfaceHolder.surfaceFrame.width(),surfaceHolder.surfaceFrame.height()))//TODO test it
    var gameThread = Thread()
    fun startGame(){
        isRunning = true
        gameManager.restart()
        gameThread = Thread(Runnable {
            while(isRunning){
                Log.d("r","r")
                gameManager.canvasAdapter.canvas = surfaceHolder.lockCanvas()
                gameManager.gameTick()
                surfaceHolder.unlockCanvasAndPost(gameManager.canvasAdapter.canvas)
            }
        })
        gameThread.start()
    }

    fun stopGame(){
        isRunning = false
    }
}