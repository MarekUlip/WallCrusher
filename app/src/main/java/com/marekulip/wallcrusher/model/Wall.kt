package com.marekulip.wallcrusher.model

import com.marekulip.wallcrusher.controler.GameManager

class Wall(private var bombCount:Int, gameManager: GameManager): ModelObject(gameManager) {
    //override var scoreValue = bombCount
    override fun touched() {
        return
    }

    fun removeBomb(){
        if(--bombCount == 0)gameManager.remove(this)
    }

    init {
        scoreValue = bombCount
    }
}