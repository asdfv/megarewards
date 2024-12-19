package by.grodno.casads

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.widget.TextView

class RTNCasBanner(context: Context) : TextView(context) {
    init {
        setBackgroundColor(Color.RED)
        gravity = Gravity.CENTER_HORIZONTAL
    }
}
