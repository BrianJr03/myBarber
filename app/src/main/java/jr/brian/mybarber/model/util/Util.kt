package jr.brian.mybarber.model.util

import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import jr.brian.mybarber.view.activities.HomeActivity

fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.inTransaction { replace(containerId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun showSnackbar(str: String, view: View, id: Int) {
    Snackbar.make(
        view.context,
        view.findViewById(id),
        str,
        Snackbar.LENGTH_SHORT
    ).show()
}

fun startHomeActivity(context: Context, activity: AppCompatActivity) {
    ContextCompat.startActivity(
        context,
        Intent(context, HomeActivity::class.java),
        null
    )
    activity.finish()
}

fun startHomeActivity(context: Context, activity: FragmentActivity) {
    ContextCompat.startActivity(
        context,
        Intent(context, HomeActivity::class.java),
        null
    )
    activity.finish()
}

fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {
    val scrollDuration = 500f;
    val smoothScroller = object : LinearSmoothScroller(this.context) {
        override fun getVerticalSnapPreference(): Int = snapMode
        override fun getHorizontalSnapPreference(): Int = snapMode
        override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
            return scrollDuration / computeVerticalScrollRange();
        }
    }
    smoothScroller.targetPosition = position
    layoutManager?.startSmoothScroll(smoothScroller)
}