package com.karan.paintapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Motion
import com.karan.paintapp.MainActivity.Companion.paintBrush
import com.karan.paintapp.MainActivity.Companion.path

class PaintView: View {

//    var params = ViewGroup.LayoutParams?

    companion object {
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()
        var currentBrush = Color.BLACK
    }

    constructor(context: Context): this(context, null) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        paintBrush.isAntiAlias = true
        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f

        var params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var x = event?.x
        var y = event?.y
        if (event != null) {
            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (y != null) {
                        if (x != null) {
                            path.moveTo(x,y)
                        }
                    }
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    if (x != null) {
                        if (y != null) {
                            path.lineTo(x,y)
                        }
                    }
                    pathList.add(path)
                    colorList.add(currentBrush)
                }
                else -> return false
            }
        }
        postInvalidate()
        return false
    }

    override fun onDraw(canvas: Canvas) {
        for (i in pathList.indices) {
            paintBrush.setColor(colorList[i])
            canvas.drawPath(pathList[i], paintBrush)
            invalidate()
        }
    }

}