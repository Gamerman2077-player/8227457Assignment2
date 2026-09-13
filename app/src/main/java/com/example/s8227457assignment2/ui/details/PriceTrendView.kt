package com.example.s8227457assignment2.ui.details

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.example.s8227457assignment2.R
import kotlin.math.max
import kotlin.math.min

class PriceTrendView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var values: List<Double> = emptyList()

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.accent_magenta)
        style = Paint.Style.STROKE
        strokeWidth = dpToPx(3f)
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private val gridPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.border_subtle)
        style = Paint.Style.STROKE
        strokeWidth = dpToPx(1f)
    }

    private val pointPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.accent_magenta)
        style = Paint.Style.FILL
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    fun setData(newValues: List<Double>) {
        values = newValues
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (values.size < 2) {
            return
        }

        val chartLeft = paddingLeft.toFloat()
        val chartTop = paddingTop.toFloat()
        val chartRight = width.toFloat() - paddingRight
        val chartBottom = height.toFloat() - paddingBottom

        val chartWidth = chartRight - chartLeft
        val chartHeight = chartBottom - chartTop

        if (chartWidth <= 0f || chartHeight <= 0f) {
            return
        }

        drawGrid(
            canvas = canvas,
            left = chartLeft,
            top = chartTop,
            right = chartRight,
            bottom = chartBottom
        )

        val lowestValue = values.minOrNull() ?: return
        val highestValue = values.maxOrNull() ?: return

        val valueRange = max(
            highestValue - lowestValue,
            highestValue * 0.01
        )

        val lowerBound = lowestValue - valueRange * 0.15
        val upperBound = highestValue + valueRange * 0.15
        val adjustedRange = upperBound - lowerBound

        val linePath = Path()
        val fillPath = Path()

        values.forEachIndexed { index, value ->

            val x = chartLeft +
                    (index.toFloat() / (values.size - 1)) * chartWidth

            val normalizedValue =
                ((value - lowerBound) / adjustedRange)
                    .coerceIn(0.0, 1.0)

            val y = chartBottom -
                    normalizedValue.toFloat() * chartHeight

            if (index == 0) {
                linePath.moveTo(x, y)

                fillPath.moveTo(x, chartBottom)
                fillPath.lineTo(x, y)
            } else {
                linePath.lineTo(x, y)
                fillPath.lineTo(x, y)
            }

            if (index == values.lastIndex) {
                pointPaint.color =
                    context.getColor(R.color.accent_magenta)

                canvas.drawCircle(
                    x,
                    y,
                    dpToPx(4f),
                    pointPaint
                )
            }
        }

        fillPath.lineTo(chartRight, chartBottom)
        fillPath.close()

        fillPaint.shader = LinearGradient(
            0f,
            chartTop,
            0f,
            chartBottom,
            intArrayOf(
                context.getColor(R.color.accent_magenta),
                context.getColor(android.R.color.transparent)
            ),
            floatArrayOf(
                0f,
                1f
            ),
            Shader.TileMode.CLAMP
        )

        fillPaint.alpha = 70

        canvas.drawPath(
            fillPath,
            fillPaint
        )

        canvas.drawPath(
            linePath,
            linePaint
        )
    }

    private fun drawGrid(
        canvas: Canvas,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ) {
        val rows = 4

        for (row in 0..rows) {
            val y =
                top + ((bottom - top) / rows) * row

            canvas.drawLine(
                left,
                y,
                right,
                y,
                gridPaint
            )
        }
    }

    private fun dpToPx(dp: Float): Float {
        return dp * resources.displayMetrics.density
    }
}