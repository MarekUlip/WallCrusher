package com.marekulip.wallcrusher.model

import com.marekulip.wallcrusher.controler.GameManager

abstract class ModelObject(val gameManager: GameManager) {
    var scoreValue = 1
    abstract fun touched()
}