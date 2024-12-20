package by.grodno.casads

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView

class RTNCasBanner(context: Context) : LinearLayout(context) {
    private var titleTextView: AppCompatTextView
    private var subtitleTextView: AppCompatTextView

    init {
        LayoutInflater.from(context).inflate(R.layout.rtn_cas_banner, this, true)
        titleTextView = findViewById(R.id.text1)
        subtitleTextView = findViewById(R.id.text2)
        orientation = VERTICAL
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun setSubtitle(subtitle: String) {
        subtitleTextView.text = subtitle
    }
}
