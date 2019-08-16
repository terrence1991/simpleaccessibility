package com.sodao.jktapp.task

import android.content.Intent
import android.util.Log
import android.widget.EditText
import com.zy.accessibilitylib.base.AccessibilityAction
import com.zy.accessibilitylib.base.BaseTask
import com.zy.accessibilitylib.base.EndAction
import com.zy.accessibilitylib.base.OpenActivityAction
import com.zy.accessibilitylib.task.TaskManager

/**
 *   created by zhangyong
 *   on 2019/7/25
 */
class AddFriendTask(): BaseTask() {

    constructor(type: Int, keyword: String) : this() {
        val action = OpenActivityAction(this)
        action.type = 2
        action.packageName = "com.tencent.mm"
        action.activityName = "com.tencent.mm.ui.LauncherUI"
        action.action = Intent.ACTION_MAIN
        action.category = Intent.CATEGORY_DEFAULT
        action.flag = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        actions.add(action)

        //LuncherUI搜索按钮
        val action2 = AccessibilityAction(this)
        action2.packageName = "com.tencent.mm"
        action2.activityName = "com.tencent.mm.ui.LauncherUI"
        action2.needCheckPage = false
        action2.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
        action2.executeSearchAction = AccessibilityAction.SearchAction()
        action2.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_TEXT
        action2.executeSearchAction!!.keyword = "更多功能按钮"
        action2.executeSearchAction!!.direction = "T1-B0"
        action2.waitTime = 2000
        action2.maxRetryCount = 3
        actions.add(action2)

        //ftsmainui输入框
        val action3 = AccessibilityAction(this)
        action3.packageName = "com.tencent.mm"
        action3.activityName = "com.tencent.mm.plugin.fts.ui.FTSMainUI"
        action3.keepupFront = true
        action3.delayTime = 2000
        action3.needCheckPage = false
        action3.executeAction = AccessibilityAction.ACTION_FIND_AND_WRITE
        action3.executeSearchAction = AccessibilityAction.SearchAction()
        action3.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_CLASS
        action3.executeSearchAction!!.keyword = EditText::class.java.name
        action3.text1 = keyword
        action3.waitTime = 2000
        action3.maxRetryCount = 3
        actions.add(action3)

        //需要等待
        val action4 = AccessibilityAction(this)
        action4.packageName = "com.tencent.mm"
        action4.activityName = "com.tencent.mm.plugin.fts.ui.FTSMainUI"
        action4.keepupFront = true
        action4.pageOpened = true
        action4.delayTime = 2000
        action4.needCheckPage = false
        action4.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
        action4.executeSearchAction = AccessibilityAction.SearchAction()
        action4.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_TEXT
        action4.executeSearchAction!!.keyword = "查找"
        action4.executeSearchAction!!.direction = "T2"
        action4.waitTime = 2000
        action4.maxRetryCount = 3
        actions.add(action4)

        //结束任务
        val action5 = EndAction(this)
        actions.add(action5)

        TaskManager.addTask(this)
    }

    override fun start() {
        Log.e("zytest", "加好友任务开始")
        super.start()
    }

    override fun nextStep() {
        Log.e("zytest", "加好友任务步骤${step+1}")
        super.nextStep()
    }

    override fun onPageOpen(currentPackage: CharSequence?, currentActivity: String) {
        Log.e("zytest", "跳转页面$currentPackage $currentActivity")
        super.onPageOpen(currentPackage, currentActivity)
    }

    override fun endTask(success: Boolean) {
        Log.e("zytest", "加好友任务结束")
        super.endTask(success)
    }
}