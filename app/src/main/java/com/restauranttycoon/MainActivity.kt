
package com.restauranttycoon

import android.app.Activity
import android.os.Bundle
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.rgb(30, 45, 38)
        window.navigationBarColor = Color.rgb(30, 45, 38)

        setContentView(RestaurantGameView(this))
    }
}

class RestaurantGameView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var cash = 150
    private var served = 0
    private var customers = 2
    private var tables = 2
    private var level = 1

    private val dark = Color.rgb(38, 43, 39)
    private val cream = Color.rgb(255, 239, 205)
    private val gold = Color.rgb(255, 190, 70)
    private val green = Color.rgb(42, 115, 75)

    private fun drawBox(
        canvas: Canvas,
        x: Float,
        y: Float,
        w: Float,
        h: Float,
        color: Int,
        radius: Float = 15f
    ) {
        paint.color = color
        paint.style = Paint.Style.FILL

        canvas.drawRoundRect(
            RectF(x, y, x + w, y + h),
            radius,
            radius,
            paint
        )
    }

    private fun drawText(
        canvas: Canvas,
        value: String,
        x: Float,
        y: Float,
        size: Float,
        color: Int,
        bold: Boolean = false
    ) {
        paint.color = color
        paint.textSize = size
        paint.textAlign = Paint.Align.LEFT

        paint.typeface = if (bold) {
            android.graphics.Typeface.DEFAULT_BOLD
        } else {
            android.graphics.Typeface.DEFAULT
        }

        canvas.drawText(value, x, y, paint)
    }

    private fun drawCenteredText(
        canvas: Canvas,
        value: String,
        x: Float,
        y: Float,
        size: Float,
        color: Int
    ) {
        paint.color = color
        paint.textSize = size
        paint.textAlign = Paint.Align.CENTER
        paint.typeface = android.graphics.Typeface.DEFAULT_BOLD

        canvas.drawText(value, x, y, paint)

        paint.textAlign = Paint.Align.LEFT
    }

    private fun drawCustomer(
        canvas: Canvas,
        x: Float,
        y: Float
    ) {
        // Customer head
        paint.color = Color.rgb(220, 170, 125)
        canvas.drawCircle(x, y, 11f, paint)

        // Customer body
        drawBox(
            canvas,
            x - 12f,
            y + 12f,
            24f,
            23f,
            Color.rgb(55, 110, 190),
            7f
        )
    }

    private fun drawTable(
        canvas: Canvas,
        x: Float,
        y: Float,
        customerPresent: Boolean
    ) {
        // Chairs
        drawBox(
            canvas,
            x - 8f,
            y + 15f,
            25f,
            30f,
            Color.rgb(125, 75, 45),
            7f
        )

        drawBox(
            canvas,
            x + 63f,
            y + 15f,
            25f,
            30f,
            Color.rgb(125, 75, 45),
            7f
        )

        // Table
        drawBox(
            canvas,
            x,
            y,
            80f,
            60f,
            Color.rgb(130, 76, 42),
            12f
        )

        drawBox(
            canvas,
            x + 8f,
            y + 8f,
            64f,
            44f,
            Color.rgb(184, 120, 72),
            8f
        )

        drawCenteredText(
            canvas,
            "FOOD",
            x + 40f,
            y + 36f,
            11f,
            Color.WHITE
        )

        if (customerPresent) {
            drawCustomer(
                canvas,
                x + 40f,
                y - 42f
            )

            drawCenteredText(
                canvas,
                "CUSTOMER",
                x + 40f,
                y - 55f,
                9f,
                dark
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val sx = width / 400f
        val sy = height / 800f

        canvas.save()
        canvas.scale(sx, sy)

        // Background
        canvas.drawColor(Color.rgb(242, 231, 207))

        // Header
        drawBox(
            canvas,
            0f, 0f,
            400f, 145f,
            dark, 0f
        )

        drawText(
            canvas,
            "RESTAURANT TYCOON",
            20f, 42f,
            23f, cream, true
        )

        drawText(
            canvas,
            "YOUR FIRST RESTAURANT",
            20f, 67f,
            12f, gold
        )

        // Money panel
        drawBox(
            canvas,
            20f, 85f,
            165f, 43f,
            Color.rgb(65, 75, 65),
            12f
        )

        drawText(
            canvas,
            "COINS: $cash",
            35f, 113f,
            18f, gold, true
        )

        // Served panel
        drawBox(
            canvas,
            205f, 85f,
            175f, 43f,
            Color.rgb(65, 75, 65),
            12f
        )

        drawText(
            canvas,
            "SERVED: $served",
            218f, 113f,
            17f, cream, true
        )

        // Dining floor
        drawBox(
            canvas,
            15f, 165f,
            370f, 420f,
            Color.rgb(225, 201, 158),
            20f
        )

        drawText(
            canvas,
            "DINING AREA",
            30f, 195f,
            17f, dark, true
        )

        // Floor tiles
        paint.color = Color.rgb(213, 187, 145)
        paint.strokeWidth = 1f

        for (i in 0..4) {
            canvas.drawLine(
                20f,
                220f + i * 65f,
                380f,
                220f + i * 65f,
                paint
            )
        }

        // Restaurant tables
        for (i in 0 until tables.coerceAtMost(4)) {

            val x = if (i % 2 == 0) 65f else 235f
            val y = if (i < 2) 270f else 440f

            drawTable(
                canvas,
                x,
                y,
                i < customers
            )
        }

        // Kitchen
        drawBox(
            canvas,
            15f, 605f,
            370f, 75f,
            Color.rgb(75, 85, 69),
            15f
        )

        drawText(
            canvas,
            "KITCHEN",
            30f, 637f,
            20f, cream, true
        )

        drawText(
            canvas,
            "Orders waiting: $customers",
            30f, 660f,
            14f, gold
        )

        // Serve button
        drawBox(
            canvas,
            15f, 700f,
            220f, 70f,
            green,
            15f
        )

        drawCenteredText(
            canvas,
            "SERVE FOOD",
            125f, 742f,
            20f, Color.WHITE
        )

        // Upgrade button
        drawBox(
            canvas,
            245f, 700f,
            140f, 70f,
            Color.rgb(177, 111, 40),
            15f
        )

        drawCenteredText(
            canvas,
            "UPGRADE",
            315f, 731f,
            17f, Color.WHITE
        )

        drawCenteredText(
            canvas,
            "Cost: 100",
            315f, 751f,
            13f, cream
        )

        // Restaurant level
        drawCenteredText(
            canvas,
            "RESTAURANT LEVEL $level",
            200f, 695f,
            12f, dark
        )

        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.action == MotionEvent.ACTION_UP) {

            val x = event.x * 400f / width
            val y = event.y * 800f / height

            // Serve food
            if (
                y in 700f..770f &&
                x in 15f..235f
            ) {
                if (customers > 0) {

                    cash += 25
                    served++

                    customers = (
                        customers - 1
                    ).coerceAtLeast(0)

                    invalidate()
                }
            }

            // Upgrade restaurant
            if (
                y in 700f..770f &&
                x in 245f..385f
            ) {
                if (cash >= 100 && tables < 4) {

                    cash -= 100
                    tables++
                    level++

                    customers = (
                        customers + 1
                    ).coerceAtMost(tables)

                    invalidate()
                }
            }

            return true
        }

        return true
    }
}
