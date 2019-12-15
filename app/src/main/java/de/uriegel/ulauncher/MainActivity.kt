package de.uriegel.ulauncher

import android.content.Context
import android.content.Intent
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        //statusBarHeight = getStatusBarHeight(this)

        apps_list.layoutManager = GridLayoutManager(this, 3)
        apps_list.adapter = appsAdapter
        loadApps()
    }

    private fun loadApps() {
        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val availableActivities = packageManager.queryIntentActivities(i, 0)
        val apps = availableActivities.map {
            AppDetail(
                it.loadLabel(packageManager).toString(),
                it.activityInfo.packageName,
                it.activityInfo.loadIcon(packageManager),
                View.OnClickListener { _ ->
                    val intent = packageManager.getLaunchIntentForPackage(it.activityInfo.packageName)
                    this@MainActivity.startActivity(intent)
                })
        }.sortedBy {
            it.label
        }
        for (app in apps) {
            //val bm = app!!.icon!!.toBitmap()
            //bm.compress(Bitmap.CompressFormat.PNG, 1, )
            appsList.add(app)
        }
    }

    /*private fun loadListView() {

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
                appLabel.text = apps!![position].label

                val appName = convertView!!.findViewById(R.id.item_app_name) as TextView
                appName.text = apps!![position].name


                return convertView!!
            }
        }

        list!!.adapter = adapter
    }*/

    private fun getStatusBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return when {
            resourceId > 0 -> resources.getDimensionPixelSize(resourceId)
            else -> ceil(24 * resources.displayMetrics.density).toInt()
        }

    }

    private val appsList = ArrayList<AppDetail>()
    private var statusBarHeight = 0
    private val appsAdapter = AppsRecyclerAdapter(appsList)
}

