package com.davidbugaev.myapplication1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.LinearLayout.VERTICAL
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout.HORIZONTAL


class MainActivity : AppCompatActivity() {

    private val weightFirstLayout = 1.0f
    private val weightSecondLayout = 1.618f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout = findViewById<LinearLayout>(R.id.line1)
        callToAddView(10, layout)
    }

    private fun callToAddView(countAdd: Int, rootLinearLayout: LinearLayout) {
        if (countAdd != 0)
            calcSizeRootView(rootLinearLayout, countAdd)
    }

    private fun calcSizeRootView(rootLayout: LinearLayout, countAdd: Int) {
        rootLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                addChildView(rootLayout, countAdd)
            }
        })
    }

    private fun addChildView(rootLayout: LinearLayout, countAdd: Int) {
        rootLayout.orientation = if (rootLayout.height > rootLayout.width) VERTICAL else HORIZONTAL

        val layoutForPicture = drawChildView(weightFirstLayout, rootLayout)
        addImageButton(layoutForPicture)

        val layoutForNextStep = drawChildView(weightSecondLayout, rootLayout)
        callToAddView(countAdd.dec(), layoutForNextStep)

    }

    private fun drawChildView(weightRatioLayout: Float, rootLayout: LinearLayout): LinearLayout {
        val childLinearLayout = LinearLayout(this)
        val childLayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, weightRatioLayout)
        rootLayout.addView(childLinearLayout, childLayoutParams)
        return childLinearLayout
    }

    private fun addImageButton(layoutForPicture: LinearLayout) {
        val imageButtonParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val imageButton = ImageButton(this)
        imageButton.layoutParams = imageButtonParams
        imageButton.scaleType = ImageView.ScaleType.CENTER_CROP
        imageButton.setImageResource(R.drawable.pattern)
        imageButton.setOnClickListener({
            imageButton.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageButton.setImageResource(R.drawable.cat)
        })
        layoutForPicture.addView(imageButton)
    }
}


