package com.marekulip.wallcrusher.model

import com.marekulip.wallcrusher.controler.GameManager

class Bomb(gameManager: GameManager, val wall:Wall):ModelObject(gameManager) {
    override fun touched() {
        wall.removeBomb()
        gameManager.remove(this)
    }
}