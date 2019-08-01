package com.sodao.jktapp.task

import android.content.Intent
import android.util.Log
import com.zy.accessibilitylib.base.AccessibilityAction
import com.zy.accessibilitylib.base.BaseTask
import com.zy.accessibilitylib.base.EndAction
import com.zy.accessibilitylib.base.OpenActivityAction
import com.zy.accessibilitylib.task.TaskManager

/**
 *   created by zhangyong
 *   on 2019/7/25
 */
class PostSnsTask: BaseTask() {

    init {
        val action = OpenActivityAction(this)
        action.type = 2
        action.packageName = "com.tencent.mm"
        action.activityName = "com.tencent.mm.ui.LauncherUI"
        action.action = Intent.ACTION_MAIN
        action.category = Intent.CATEGORY_DEFAULT
        action.extra["From_fail_notify"] = true
        action.extra["jump_sns_from_notify"] = true
        action.flag = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        actions.add(action)

        //朋友圈拍照分享
        val action2 = AccessibilityAction(this)
        action2.packageName = "com.tencent.mm"
        action2.activityName = "com.tencent.mm.plugin.sns.ui.SnsTimeLineUI"
        action2.needCheckPage = false
        action2.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
        action2.executeSearchAction = AccessibilityAction.SearchAction()
        action2.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_TEXT
        action2.executeSearchAction!!.keyword = "拍照分享"
        actions.add(action2)

        //从相册选择
        val action3 = AccessibilityAction(this)
        action3.packageName = "com.tencent.mm"
        action3.activityName = "com.tencent.mm.ui.base.k"
        action3.needCheckPage = false
        action3.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
        action3.executeSearchAction = AccessibilityAction.SearchAction()
        action3.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_TEXT
        action3.executeSearchAction!!.keyword = "从相册选择"
        action3.executeSearchAction!!.direction = "T2"
        actions.add(action3)

        //从相册选择第一个图片
        val action4 = AccessibilityAction(this)
        action4.packageName = "com.tencent.mm"
        action4.activityName = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI"
        action4.delayTime = 2000
        action4.needCheckPage = false
        action4.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
        action4.executeSearchAction = AccessibilityAction.SearchAction()
        action4.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_CLASS
        action4.executeSearchAction!!.keyword = "android.widget.GridView"
        action4.executeSearchAction!!.direction = "B0-Vandroid.view.View"
        actions.add(action4)

        //从相册选择第二个图片
        val action5 = AccessibilityAction(this)
        action5.keepupFront = true
        action5.packageName = "com.tencent.mm"
        action5.activityName = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI"
        action5.needCheckPage = false
        action5.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
        action5.executeSearchAction = AccessibilityAction.SearchAction()
        action5.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_CLASS
        action5.executeSearchAction!!.keyword = "android.widget.GridView"
        action5.executeSearchAction!!.direction = "B1-Vandroid.view.View"
        actions.add(action5)

        //从相册选择第三个图片
        val action6 = AccessibilityAction(this)
        action6.keepupFront = true
        action6.packageName = "com.tencent.mm"
        action6.activityName = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI"
        action6.needCheckPage = false
        action6.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
        action6.executeSearchAction = AccessibilityAction.SearchAction()
        action6.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_CLASS
        action6.executeSearchAction!!.keyword = "android.widget.GridView"
        action6.executeSearchAction!!.direction = "B2-Vandroid.view.View"
        actions.add(action6)

        //从相册选择完成
        val action7 = AccessibilityAction(this)
        action7.keepupFront = true
        action7.packageName = "com.tencent.mm"
        action7.activityName = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI"
        action7.needCheckPage = false
        action7.executeAction = AccessibilityAction.ACTION_FIND_AND_CHECK
        action7.executeSearchAction = AccessibilityAction.SearchAction()
        action7.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_TEXT
        action7.executeSearchAction!!.keyword = "完成"
        actions.add(action7)

        //设置文案
        val action8 = AccessibilityAction(this)
        action8.packageName = "com.tencent.mm"
        action8.activityName = "com.tencent.mm.plugin.sns.ui.SnsUploadUI"
        action8.needCheckPage = false
        action8.executeAction = AccessibilityAction.ACTION_FIND_AND_WRITE
        action8.executeSearchAction = AccessibilityAction.SearchAction()
        action8.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_CLASS
        action8.executeSearchAction!!.keyword = "android.widget.EditText"
        action8.text1 = "这是要分享的文案"
        actions.add(action8)

        //结束任务
        val action10 = EndAction(this)
        actions.add(action10)

        TaskManager.addTask(this)
    }

    override fun start() {
        Log.e("zytest", "发朋友圈任务开始")
        super.start()
    }

    override fun nextStep() {
        Log.e("zytest", "发朋友圈任务步骤${step+1}")
        super.nextStep()
    }

    override fun onPageOpen(currentPackage: CharSequence?, currentActivity: String) {
        Log.e("zytest", "跳转页面$currentPackage $currentActivity")
        super.onPageOpen(currentPackage, currentActivity)
    }

    override fun endTask() {
        Log.e("zytest", "发朋友圈任务结束")
        super.endTask()
    }
}