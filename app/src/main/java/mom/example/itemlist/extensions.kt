package mom.example.scalablerecyclerviewitem

import android.graphics.Bitmap
import android.view.View
import android.view.ViewTreeObserver

class NamedBitmap (var name: String, var bitmap: Bitmap) {

}

inline fun <T: View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}


inline fun android.support.design.widget.AppBarLayout.onOffsetChanged(noinline l: (appBarLayout: android.support.design.widget.AppBarLayout?, verticalOffset: Int) -> Unit) {
    addOnOffsetChangedListener(l)
}