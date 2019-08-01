package com.zy.accessibilitylib.service

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.EditText
import com.zy.accessibilitylib.task.TaskManager
import com.zy.accessibilitylib.util.AssistUtil
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 *   created by zhangyong
 *   on 2019/7/29
 */
class AutoScriptService: AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        AssistUtil.initService(this)
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event!!.eventType) {
            val currentPackage = event.packageName
            val currentActivity = if (event.className != null) event.className.toString().intern() else ""
            Log.e("zytest", "currentPackage: $currentPackage - currentActivity: $currentActivity")
            AssistUtil.CURRENT_PACKAGE.set(currentPackage?.toString() ?: "")
            AssistUtil.CURRENT_ACTIVITY.set(currentActivity)
            TaskManager.onPageOpen(currentPackage, currentActivity)
        }
    }
}