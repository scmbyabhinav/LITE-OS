package com.liteos.launcher

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * LITEOS 0.1: deliberately small native launcher.
 * No third-party runtime/UI libraries, network access, analytics, database,
 * background worker, or persistent service.
 */
class MainActivity : Activity() {
    private val ink = 0xFFF3F5F7.toInt()
    private val muted = 0xFF9AA6B2.toInt()
    private val pageColor = 0xFF101419.toInt()
    private val tileColor = 0xFF1B232B.toInt()
    private lateinit var appGrid: GridView
    private lateinit var appAdapter: AppAdapter
    private val apps = mutableListOf<AppEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = pageColor
        window.navigationBarColor = pageColor
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(30), dp(22), dp(12))
            setBackgroundColor(pageColor)
        }

        root.addView(label("L I T E O S", 13f, ink, true), matchWrap())
        root.addView(label("A little lighter.", titleSize(), ink, false).apply {
            setPadding(0, dp(22), 0, dp(2))
        }, matchWrap())

        val date = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault()).format(Date())
        root.addView(label(date, 14f, muted, false), matchWrap())
        root.addView(label("YOUR APPS", 11f, muted, true).apply {
            letterSpacing = 0.12f
            setPadding(0, dp(28), 0, dp(10))
        }, matchWrap())

        appGrid = GridView(this).apply {
            numColumns = if (resources.configuration.smallestScreenWidthDp >= 600) 5 else 4
            verticalSpacing = dp(10)
            horizontalSpacing = dp(8)
            stretchMode = GridView.STRETCH_COLUMN_WIDTH
            selector = null
            isVerticalScrollBarEnabled = false
        }
        appAdapter = AppAdapter()
        appGrid.adapter = appAdapter
        root.addView(appGrid, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        root.addView(label("LIGHT  •  SIMPLE  •  YOURS", 10f, muted, true).apply {
            gravity = Gravity.CENTER
            setPadding(0, dp(10), 0, dp(8))
        }, matchWrap())

        setContentView(root)
        reloadApps()
    }

    override fun onResume() {
        super.onResume()
        if (::appAdapter.isInitialized) reloadApps()
    }

    private fun reloadApps() {
        val query = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        val pm = packageManager
        val results = if (android.os.Build.VERSION.SDK_INT >= 33) {
            pm.queryIntentActivities(query, PackageManager.ResolveInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            pm.queryIntentActivities(query, 0)
        }

        apps.clear()
        results.asSequence()
            .filter { it.activityInfo.packageName != packageName }
            .distinctBy { it.activityInfo.packageName + "/" + it.activityInfo.name }
            .sortedBy { it.loadLabel(pm).toString().lowercase(Locale.getDefault()) }
            .forEach { info ->
                apps.add(AppEntry(
                    label = info.loadLabel(pm).toString(),
                    packageName = info.activityInfo.packageName,
                    activityName = info.activityInfo.name,
                    icon = info.loadIcon(pm)
                ))
            }
        appAdapter.notifyDataSetChanged()
    }

    private fun label(value: String, size: Float, color: Int, bold: Boolean) =
        TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            typeface = if (bold) Typeface.create("sans-serif", Typeface.BOLD)
                else Typeface.create("sans-serif", Typeface.NORMAL)
        }

    private fun titleSize() =
        if (resources.configuration.smallestScreenWidthDp >= 600) 40f else 34f

    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    )

    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

    private data class AppEntry(
        val label: String,
        val packageName: String,
        val activityName: String,
        val icon: android.graphics.drawable.Drawable
    )

    private inner class AppAdapter : BaseAdapter() {
        override fun getCount() = apps.size
        override fun getItem(position: Int) = apps[position]
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val item = getItem(position)
            val cell = (convertView as? LinearLayout) ?: LinearLayout(this@MainActivity).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(dp(4), dp(10), dp(4), dp(8))
                background = android.graphics.drawable.GradientDrawable().apply {
                    setColor(tileColor)
                    cornerRadius = dp(16).toFloat()
                }
            }

            cell.removeAllViews()
            val iconView = android.widget.ImageView(this@MainActivity).apply {
                setImageDrawable(item.icon)
                scaleType = android.widget.ImageView.ScaleType.FIT_CENTER
                contentDescription = item.label
            }
            cell.addView(iconView, LinearLayout.LayoutParams(dp(38), dp(38)))

            cell.addView(label(item.label, 11f, ink, false).apply {
                gravity = Gravity.CENTER
                maxLines = 2
                ellipsize = android.text.TextUtils.TruncateAt.END
                setPadding(0, dp(7), 0, 0)
            }, matchWrap())

            cell.contentDescription = "Open ${item.label}"
            cell.isFocusable = true
            cell.setOnClickListener { openApp(item) }
            return cell
        }
    }

    private fun openApp(item: AppEntry) {
        val launchIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
            setClassName(item.packageName, item.activityName)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            startActivity(launchIntent)
        } catch (_: Exception) {
            reloadApps()
        }
    }
}
