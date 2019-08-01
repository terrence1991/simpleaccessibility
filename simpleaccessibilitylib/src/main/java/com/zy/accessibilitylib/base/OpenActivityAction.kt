package com.zy.accessibilitylib.base

import android.content.ComponentName
import android.content.Intent
import com.zy.accessibilitylib.util.AssistUtil

/**
 *   created by zhangyong
 *   on 2019/7/29
 */
class OpenActivityAction(parentTask: BaseTask): BaseAction(parentTask) {

    companion object {
        const val START_WITH_ACTION = 1
        const val START_WITH_ACTIVITY = 2
    }

    var type = 0
    var action = ""
    var category = ""
    var packageName = ""
    var activityName = ""
    var flag: Int = 0
    var extra: HashMap<String, Any> = HashMap<String, Any>()

    override fun execute(): Boolean {
        var intent: Intent? = null
        when (type) {
            START_WITH_ACTION -> {
                intent = Intent(action)
            }
            START_WITH_ACTIVITY -> {
                intent = Intent()
                intent.component = ComponentName(packageName, activityName)
                intent.action = action
                intent.addCategory(category)
            }
        }
        if (flag != 0) {
            intent!!.flags = flag
        }
        if (extra.isNotEmpty()) {
            for (key in extra.keys) {
                when {
                    extra[key] is String -> intent!!.putExtra(key, extra[key] as String)
                    extra[key] is Int -> intent!!.putExtra(key, extra[key] as Int)
                    extra[key] is Boolean -> intent!!.putExtra(key, extra[key] as Boolean)
                    extra[key] is Long -> intent!!.putExtra(key, extra[key] as Long)
                }
            }
        }
        AssistUtil.assistService?.startActivity(intent)
        return true
    }
}