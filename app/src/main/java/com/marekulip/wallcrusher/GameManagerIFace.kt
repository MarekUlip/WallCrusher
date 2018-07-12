package com.marekulip.wallcrusher

import android.content.Context

interface GameManagerIFace {
    fun endGame()
    fun setScore(score:Int)
    fun getContext():Context
}