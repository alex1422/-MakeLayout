package com.example.makelayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT)

        params.weight = 1.toFloat()
        val img = arrayOf(
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4, R.drawable.img_5, R.drawable.img_6, R.drawable.img_7, R.drawable.img_8)

        val num = mutableListOf<Int>()
        val catViews = ArrayList<ImageView>()
        img.shuffle()

        val colorListener = View.OnClickListener() {
            GlobalScope.launch(Dispatchers.Main) {
                open_close(it as ImageView, img[num[catViews.indexOf(it)]])
            }
        }

        for (i in 0..15) {
            catViews.add(ImageView(applicationContext).apply {
                    setImageResource(R.drawable.squarecat)
                    layoutParams = params
                    num.add(i,i / 2)
                    setOnClickListener(colorListener)
                }
            )
        }

        num.shuffle()
        catViews.shuffle()
        val rows = Array(4, { LinearLayout(applicationContext) })
        var count = 0
        for (view in catViews) {
            val row: Int = count / 4
            rows[row].addView(view)
            count++
        }
        for (row in rows) {
            row.apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = params
            }
            layout.addView(row)
        }
        setContentView(layout)
    }

    suspend fun open_close(v: ImageView, r: Int) {
        v.setImageResource(r)
        delay(1000)
        v.setImageResource(R.drawable.squarecat)
    }
}

