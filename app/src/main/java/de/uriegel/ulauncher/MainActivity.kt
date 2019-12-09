package de.uriegel.ulauncher

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION
import kotlin.math.ceil
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        statusBarHeight = getStatusBarHeight(this)

        loadApps()
        loadListView()
        list!!.setOnItemClickListener { _, _, position, _ ->
            val i = manager!!.getLaunchIntentForPackage(apps!!.get(position).name!!.toString())
            this@MainActivity.startActivity(i)
        }
    }

    private fun loadApps() {
        manager = packageManager
        apps = ArrayList()

        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val availableActivities = manager!!.queryIntentActivities(i, 0)
        for (ri in availableActivities) {
            val app = AppDetail()
            app.label = ri.loadLabel(manager)
            app.name = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(manager)
            //val bm = app!!.icon!!.toBitmap()
            //bm.compress(Bitmap.CompressFormat.PNG, 1, )
            apps!!.add(app)
        }
    }

    private fun loadListView() {
        list = apps_list

        val adapter = object : ArrayAdapter<AppDetail>(
            this,
            R.layout.list_item,
            apps!!
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                if (convertView == null) {
                    convertView = layoutInflater.inflate(R.layout.list_item, null)
                    if (position == 0)
                        convertView.setPadding(convertView.paddingLeft, convertView.paddingTop + statusBarHeight, convertView.paddingRight, convertView.paddingBottom)
                }

                val appIcon = convertView!!.findViewById(R.id.item_app_icon) as ImageView
                appIcon.setImageDrawable(apps!!.get(position).icon)

                val appLabel = convertView!!.findViewById(R.id.item_app_label) as TextView
                appLabel.text = apps!!.get(position).label

                val appName = convertView!!.findViewById(R.id.item_app_name) as TextView
                appName.text = apps!!.get(position).name


                return convertView!!
            }
        }

        list!!.adapter = adapter
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return when {
            resourceId > 0 -> resources.getDimensionPixelSize(resourceId)
            else -> ceil((if (VERSION.SDK_INT >= VERSION_CODES.M) 24 else 25) * resources.displayMetrics.density).toInt()
        }

    }

    private var manager: PackageManager? = null
    private var apps: ArrayList<AppDetail>? = null
    private var list: ListView? = null
    private var statusBarHeight = 0
}

