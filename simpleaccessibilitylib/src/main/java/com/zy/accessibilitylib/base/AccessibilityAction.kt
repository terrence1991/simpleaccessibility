package com.zy.accessibilitylib.base

import android.text.TextUtils
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import com.zy.accessibilitylib.util.AssistUtil
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 *   created by zhangyong
 *   on 2019/7/29
 */
class AccessibilityAction(parentTask: BaseTask): BaseAction(parentTask) {

    companion object {
        const val CHECK_ACTION_NODE_EXIST = 1
        const val CHECK_ACTION_NODE_NOT_EXIST = 2

        const val ACTION_FIND_AND_CLICK = 101
        const val ACTION_FIND_AND_LONG_CLICK = 102
        const val ACTION_FIND_AND_WRITE = 103
        const val ACTION_FIND_AND_CHECK = 104
    }

    var keepupFront = false
    var delayTime = 0L
    var pageOpened = false

    var packageName = ""
    var activityName = ""
    var needCheckPage = false
    var checkAction = 0
    var checkSearchAction: SearchAction? = null

    var executeAction = 0
    var executeSearchAction: SearchAction? = null
    var text1 = ""
    var text2 = ""
    var text3 = ""

    var waitTime = 0L
    var maxRetryCount = 0
    private var retryCount = 0

    fun onPageOpen() {
        if (!pageOpened) {
            pageOpened = true
        } else {
            return
        }
        if (delayTime != 0L) {
            Observable.timer(delayTime, TimeUnit.MILLISECONDS)
                .subscribe {
                    onPreExecute()
                    if (needCheckPage()) {
                        checkPage()
                    } else {
                        if (execute()) {
                            onPostExecute()
                        }
                    }
                }
            return
        }
        onPreExecute()
        if (needCheckPage()) {
            checkPage()
        } else {
            if (execute()) {
                onPostExecute()
            }
        }
    }

    fun isInTaskPage(currentPackage: CharSequence?, currentActivity: String): Boolean {
        return this.activityName == currentActivity && packageName == currentPackage
    }

    fun needCheckPage(): Boolean {
        return needCheckPage
    }

    fun checkPage() {
        when (checkAction) {
            CHECK_ACTION_NODE_EXIST -> {
                val node = searchNode(checkSearchAction)
                if (node != null) {
                    retryCount = 0
                    if (execute()) {
                        onPostExecute()
                    }
                    return
                }
            }
            CHECK_ACTION_NODE_NOT_EXIST -> {
                val node = searchNode(checkSearchAction)
                if (node == null) {
                    retryCount = 0
                    if (execute()) {
                        onPostExecute()
                    }
                    return
                }
            }
        }
        Log.e("zytest", "动作执行失败" + toString())
        if (retryCount < maxRetryCount) {
            retryCount++
            if (AssistUtil.CURRENT_ACTIVITY.get() != activityName) {
                AssistUtil.performReturnBack()
            }
            Observable.timer(waitTime, TimeUnit.MILLISECONDS)
                    .subscribe {
                        checkPage()
                    }
        } else {
            parentTask.endTask()
            //执行失败
        }
    }

    override fun execute(): Boolean {
        when (executeAction) {
            ACTION_FIND_AND_CLICK -> {
                val node = searchNode(executeSearchAction)
                if (node != null) {
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return true
                }
            }
            ACTION_FIND_AND_LONG_CLICK -> {
                val node = searchNode(executeSearchAction)
                if (node != null) {
                    node.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK)
                    return true
                }
            }
            ACTION_FIND_AND_WRITE -> {
                val node = searchNode(executeSearchAction)
                if (node != null) {
                    AssistUtil.setEditText(node, text1)
                    return true
                }
            }
            ACTION_FIND_AND_CHECK -> {
                val node = searchNode(executeSearchAction)
                if (node != null) {
//                    val method = node::class.java.getMethod("setSealed", Boolean::class.java)
//                    method.invoke(node, false)
//                    node.isChecked = true
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    return true
                }
            }
        }
        Log.e("zytest", "动作执行失败" + toString())
        if (retryCount < maxRetryCount) {
            retryCount++
            if (AssistUtil.CURRENT_ACTIVITY.get() != activityName) {
                AssistUtil.performReturnBack()
            }
            Observable.timer(waitTime, TimeUnit.MILLISECONDS)
                .subscribe {
                    if (execute()) {
                        onPostExecute()
                    }
                }
        } else {
            parentTask.endTask()
            //执行失败
        }
        return false
    }

    override fun onPostExecute() {
        parentTask.nextStep()
    }

    private fun searchNode(searchAction: SearchAction?): AccessibilityNodeInfo? {
        if (searchAction != null) {
            var node: AccessibilityNodeInfo? = null
            when (searchAction!!.type) {
                SearchAction.SEARCH_WITH_FIRST_ID -> {
                    node = AssistUtil.getFirstNodeInfoByViewId(searchAction!!.keyword)
                }
                SearchAction.SEARCH_WITH_FIRST_TEXT -> {
                    node = AssistUtil.getFirstNodeInfoByText(searchAction!!.keyword)
                }
                SearchAction.SEARCH_WITH_FIRST_CLASS -> {
                    node = AssistUtil.getFirstNodeInfoByClass(searchAction!!.keyword)
                }
            }
            if (node != null && !TextUtils.isEmpty(searchAction!!.direction)) {
                val dirs = searchAction!!.direction.split("-")
                for (d in dirs) {
                    when (d[0]) {
                        'T' -> {
                            val step = d.substring(1).toInt()
                            for (i in 0 until step) {
                                node = node?.parent
                            }
                        }
                        'L' -> {
                            val step = d.substring(1).toInt()
                            val parent = node?.parent
                            if (parent != null) {
                                for (i in 0 until parent.childCount) {
                                    if (parent.getChild(i) == node) {
                                        if (i-step < 0) return null
                                        node = parent.getChild(i-step)
                                        return node
                                    }
                                }
                            }
                        }
                        'B' -> {
                            val step = d.substring(1).toInt()
                            if (node!!.childCount > step) {
                                node = node.getChild(step)
                            }
                        }
                        'R' -> {
                            val step = d.substring(1).toInt()
                            val parent = node?.parent
                            if (parent != null) {
                                for (i in 0 until parent.childCount) {
                                    if (parent.getChild(i) == node) {
                                        if (i+step > parent.childCount) return null
                                        node = parent.getChild(i+step)
                                        return node
                                    }
                                }
                            }
                        }
                        'V' -> {
                            val className = d.substring(1)
                            for (i in 0 until node!!.childCount) {
                                if (node.getChild(i).className == className) {
                                    return node.getChild(i)
                                }
                            }
                            return null
                        }
                    }
                }
            }
            return node
        }
        return null
    }

    override fun toString(): String {
        return "AccessibilityAction(keepupFront=$keepupFront, delayTime=$delayTime, packageName='$packageName', activityName='$activityName', needCheckPage=$needCheckPage, checkAction=$checkAction, checkSearchAction=$checkSearchAction, executeAction=$executeAction, executeSearchAction=$executeSearchAction, text1='$text1', text2='$text2', text3='$text3', waitTime=$waitTime, maxRetryCount=$maxRetryCount, retryCount=$retryCount)"
    }


    class SearchAction {

        companion object {
            const val SEARCH_WITH_FIRST_ID = 1
            const val SEARCH_WITH_FIRST_TEXT = 2
            const val SEARCH_WITH_FIRST_CLASS = 3
        }

        var type = 0
        var keyword = ""
        var direction = ""

        override fun toString(): String {
            return "SearchAction(type=$type, keyword='$keyword', direction='$direction')"
        }

    }
}