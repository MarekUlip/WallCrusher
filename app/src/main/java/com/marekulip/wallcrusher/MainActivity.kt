package com.marekulip.wallcrusher

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.TextView
import android.util.DisplayMetrics
import android.os.Build
import android.view.ViewTreeObserver



class MainActivity : AppCompatActivity(), SurfaceHolder.Callback, GameManagerIFace {
    var gameThread:GameThread? = null
    var offsetY: Int = 0
    private val handler = Handler(Looper.getMainLooper())
    override fun endGame() {
        gameThread?.stopGame()
        invalidateOptionsMenu()
    }

    override fun setScore(score: Int) {
        handler.post(Runnable {
            findViewById<TextView>(R.id.score).text = score.toString()
        })
    }

    override fun getContext(): Context {
        return this
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        if (p0 != null) {
            gameThread = GameThread(this,p0)
            gameThread?.gameManager?.offsetY = offsetY
        }
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
        gameThread?.stopGame()
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        if (p0 != null) gameThread?.surfaceHolder = p0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val surface = findViewById<SurfaceView>(R.id.surface)
        surface.holder.addCallback(this)
        gameThread = GameThread(this,surface.holder)
        getScales(surface)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.game_menu,menu)
        if(menu!=null){
            val isRunning = gameThread?.isRunning == true
            if(isRunning){
                menu.findItem(R.id.play).isVisible = false
                menu.findItem(R.id.pause).isVisible = true
            }else{
                menu.findItem(R.id.play).isVisible = true
                menu.findItem(R.id.pause).isVisible = false
            }

        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.play -> startGame()
            R.id.pause -> pauseGame()
        }
        invalidateOptionsMenu()
        return true
    }

    override fun onResume() {
        super.onResume()
        //startGame()
    }

    override fun onPause() {
        super.onPause()
        pauseGame()
    }

    private fun startGame(){
        gameThread?.startGame()
    }

    private fun pauseGame(){
        gameThread?.stopGame()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
            gameThread?.gameManager?.click(event.x.toInt(), event.y.toInt())
        }
        return true
    }

    /**
     * Sets listener that will tell us when the SurfaceView was placed so we can then measure it and send data to other classes.
     * This is only used when the user STARTS the application.
     */
    private fun getScales(mView: SurfaceView) {
        mView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
                    mView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                else
                    mView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                sendScales(mView)
            }
        })
    }

    /**
     * Sets scales for game and draw manager so that these classes know what they´re up against.
     * I mean what scales does the screen have.
     */
    private fun sendScales(mView: SurfaceView) {
        val locations = IntArray(2)
        mView.getLocationOnScreen(locations)
        offsetY = locations[1]
        /*val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)*/
        //Log.d("heightWidth","H"+mView.getHeight()+"W"+mView.getWidth());
    }
}
