package com.daniel.android_freshsnap.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.daniel.android_freshsnap.R

class Email : AppCompatEditText {

    private lateinit var successBackground: Drawable
    private lateinit var errorBackground: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Email"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        successBackground = ContextCompat.getDrawable(context, R.drawable.corners_outline) as Drawable
        errorBackground = ContextCompat.getDrawable(context, R.drawable.corners_outline_error) as Drawable

        addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(Patterns.EMAIL_ADDRESS.matcher(s!!).matches() || s.isEmpty()){
                    background = successBackground
                } else {
                    background = errorBackground
                    error = "Enter a valid email"
                }
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }
}