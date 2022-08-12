package jr.brian.mybarber.model.util

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import jr.brian.mybarber.R
import jr.brian.mybarber.view.activities.home.HomeActivity

fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment) {
    supportFragmentManager.inTransaction { replace(containerId, fragment) }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun startHomeActivity(context: Context, activity: AppCompatActivity) {
    ContextCompat.startActivity(
        context,
        Intent(context, HomeActivity::class.java),
        null
    )
    (context as AppCompatActivity).overridePendingTransition(
        R.anim.slide_in_right,
        R.anim.slide_out_right
    )
    activity.finish()
}

fun startHomeActivity(context: Context, activity: FragmentActivity) {
    ContextCompat.startActivity(
        context,
        Intent(context, HomeActivity::class.java),
        null
    )
    (context as AppCompatActivity).overridePendingTransition(
        R.anim.slide_in_right,
        R.anim.slide_out_right
    )
    activity.finish()
}

fun openDialog(context: Context, title: String, iconId: Int, msg: String) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setIcon(iconId)
        .setMessage(msg)
        .show()
}