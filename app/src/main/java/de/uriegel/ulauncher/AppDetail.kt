package de.uriegel.ulauncher

import android.graphics.drawable.Drawable
import android.view.View

class AppDetail(
    val label: String,
    val name: String,
    val icon: Drawable,
    val onClick: View.OnClickListener) {

    override fun equals(other: Any?): Boolean {
        return if (other is AppDetail) other.name == name else false
    }
}
