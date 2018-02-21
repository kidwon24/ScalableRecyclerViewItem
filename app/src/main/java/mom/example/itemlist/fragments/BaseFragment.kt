package mom.example.scalablerecyclerviewitem.fragments

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

open class BaseFragment : Fragment() {
    var fragmentContainerId: Int = 0
    open val fragmentLayoutReference: Int = 0

    fun load() {
        load(activity)
    }

    fun load(activity: FragmentActivity?) {
        if (activity == null)
            return
        activity.supportFragmentManager.beginTransaction()
                .replace(this.fragmentContainerId, this)
                .addToBackStack(this.javaClass.simpleName)
                .commit()
    }

    fun load(anActivity: FragmentActivity?, noBackStack:Boolean = true) {
        if (anActivity == null)
            return
        if (!noBackStack) {
            load(anActivity)
            return
        }
        anActivity.supportFragmentManager.beginTransaction()
                .replace(this.fragmentContainerId, this)
                .commit()
    }

    /*
    fun onBackPressed() {
        val fm = activity.supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        }
    }
    */
}