package de.uriegel.ulauncher

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout


class SquareLayout(context: Context?, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode)
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode)

        super.onMeasure(newWidthMeasureSpec, newHeightMeasureSpec)
    }
}