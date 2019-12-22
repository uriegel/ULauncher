package de.uriegel.ulauncher

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EdgeEffect
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        apps_list.layoutManager = GridLayoutManager(this, 3)
        apps_list.adapter = appsAdapter

        apps_list.edgeEffectFactory = object : RecyclerView.EdgeEffectFactory() {
            override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
                return EdgeEffect(view.context).apply { color = Color.BLUE }
            }
        }

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

    private val appsList = ArrayList<AppDetail>()
    private val appsAdapter = AppsRecyclerAdapter(appsList)
}

