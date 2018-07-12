package com.marekulip.wallcrusher.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.SparseArray
import com.marekulip.wallcrusher.R
import android.support.v4.graphics.drawable.DrawableCompat
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources


class Pictures(context:Context) {
    private val pictures:SparseArray<Bitmap> = SparseArray()
    init {
        pictures.put(0,BitmapFactory.decodeResource(context.resources,R.drawable.ground))
        pictures.put(1,getBitmapFromVectorDrawable(context, R.drawable.cast))
        pictures.put(2,BitmapFactory.decodeResource(context.resources,R.drawable.wall))
        pictures.put(3,getBitmapFromVectorDrawable(context,R.drawable.b))
        pictures.put(4,getBitmapFromVectorDrawable(context,R.drawable.run_1))
        pictures.put(5,getBitmapFromVectorDrawable(context,R.drawable.run_2))
    }

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap {
        var drawable = AppCompatResources.getDrawable(context, drawableId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }

        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    fun getPicture(num:Int):Bitmap{
        return pictures.get(num)
    }

    fun getManPictures():List<Bitmap>{
        val list = ArrayList<Bitmap>()
        list.add(pictures.get(4))
        list.add(pictures.get(5))
        return list
    }
}