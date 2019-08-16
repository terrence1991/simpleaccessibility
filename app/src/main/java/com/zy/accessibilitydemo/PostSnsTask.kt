package com.sodao.jktapp.task

import android.content.Intent
import android.util.Log
import com.sodao.jktapp.JktApp
import com.sodao.jktapp.utils.FileUtil
import com.sodao.jktapp.utils.GalleryUtil
import com.zy.accessibilitylib.base.AccessibilityAction
import com.zy.accessibilitylib.base.BaseTask
import com.zy.accessibilitylib.base.EndAction
import com.zy.accessibilitylib.base.OpenActivityAction
import com.zy.accessibilitylib.task.TaskManager
import org.json.JSONArray
import java.io.File

/**
 *   created by zhangyong
 *   on 2019/7/25
 */
class PostSnsTask(): BaseTask() {

    constructor(type: Int, content: String, attachment: JSONArray): this() {

        if (type == 1) {
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
            action2.executeAction = AccessibilityAction.ACTION_FIND_AND_LONG_CLICK
            action2.executeSearchAction = AccessibilityAction.SearchAction()
            action2.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_TEXT
            action2.executeSearchAction!!.keyword = "拍照分享"
            actions.add(action2)

            //设置文案
            val action8 = AccessibilityAction(this)
            action8.packageName = "com.tencent.mm"
            action8.activityName = "com.tencent.mm.plugin.sns.ui.SnsUploadUI"
            action8.needCheckPage = false
            action8.executeAction = AccessibilityAction.ACTION_FIND_AND_WRITE
            action8.executeSearchAction = AccessibilityAction.SearchAction()
            action8.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_CLASS
            action8.executeSearchAction!!.keyword = "android.widget.EditText"
            action8.text1 = content
            actions.add(action8)

            //结束任务
            val action10 = EndAction(this)
            actions.add(action10)

            TaskManager.addTask(this)
        } else {
            //复制文件到其他路径 然后更新gallery
            for (i in attachment.length()-1 downTo 0) {
                val src = attachment[i].toString().substring(7)
                var dest = "/sdcard/Pictures/sodao" + System.currentTimeMillis()
                val index = src.lastIndexOf('.')
                val extName = if (index > 0) src.substring(index) else ""
                dest += extName
                FileUtil.copy(src, dest)
                GalleryUtil.updateGallery(JktApp.getContext(), File(dest))
            }

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

            //选择Pictures目录
            val action4 = AccessibilityAction(this)
            action4.packageName = "com.tencent.mm"
            action4.activityName = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI"
            action4.delayTime = 1000
            action4.needCheckPage = false
            action4.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
            action4.executeSearchAction = AccessibilityAction.SearchAction()
            action4.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FULL_TEXT
            action4.executeSearchAction!!.keyword = "图片和视频"
            action4.executeSearchAction!!.direction = "T1"
            actions.add(action4)

            val action5 = AccessibilityAction(this)
            action5.packageName = "com.tencent.mm"
            action5.activityName = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI"
            action5.delayTime = 1000
            action5.keepupFront = true
            action5.needCheckPage = false
            action5.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
            action5.executeSearchAction = AccessibilityAction.SearchAction()
            action5.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_TEXT
            action5.executeSearchAction!!.keyword = "Pictures"
            action5.executeSearchAction!!.direction = "T2"
            actions.add(action5)

//            val action6 = AccessibilityAction(this)
//            action6.packageName = "com.tencent.mm"
//            action6.activityName = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI"
//            action6.delayTime = 5000
//            action6.keepupFront = true
//            action6.needCheckPage = false
//            action6.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
//            action6.executeSearchAction = AccessibilityAction.SearchAction()
//            action6.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_TEXT
//            action6.executeSearchAction!!.keyword = "预览"
//            action6.executeSearchAction!!.direction = "L1"
//            actions.add(action6)

            for (i in 0 until attachment.length()) {
                //从相册选择图片
                val action61 = AccessibilityAction(this)
                action61.packageName = "com.tencent.mm"
                action61.activityName = "com.tencent.mm.plugin.gallery.ui.AlbumPreviewUI"
                if (i == 0) {
                    action61.delayTime = 1000
                }
                action61.keepupFront = true
                action61.needCheckPage = false
                action61.executeAction = AccessibilityAction.ACTION_FIND_AND_CLICK
                action61.executeSearchAction = AccessibilityAction.SearchAction()
                action61.executeSearchAction!!.type = AccessibilityAction.SearchAction.SEARCH_WITH_FIRST_CLASS
                action61.executeSearchAction!!.keyword = "android.widget.GridView"
                action61.executeSearchAction!!.direction = "B$i-Vandroid.view.View"
                actions.add(action61)
            }

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
            action8.text1 = content
            actions.add(action8)

            //结束任务
            val action10 = EndAction(this)
            actions.add(action10)

            TaskManager.addTask(this)
        }
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

    override fun endTask(success: Boolean) {
        Log.e("zytest", "发朋友圈任务结束")
        super.endTask(success)
    }
}