package com.zy.accessibilitylib.util

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.accessibility.AccessibilityNodeInfo
import java.util.concurrent.atomic.AtomicReference

/**
 *   created by zhangyong
 *   on 2019/7/29
 */
@SuppressLint("StaticFieldLeak")
object AssistUtil {

    var assistService: AccessibilityService? = null
    var CURRENT_ACTIVITY = AtomicReference("com.flowbox.fbox.MainActivity")
    var CURRENT_PACKAGE = AtomicReference("com.flowbox.fbox")

    fun initService(service: AccessibilityService) {
        assistService = service
    }

    /**
     * 获取根节点 Gets root node.
     *
     * @return the root node
     */
    fun getRootNode(): AccessibilityNodeInfo? {
        return if (assistService != null) {
            assistService?.rootInActiveWindow
        } else null
    }

    /**
     * Gets first node info by view id.
     *
     * @param viewId the view id
     * @return the first node info by view id
     */
    fun getFirstNodeInfoByViewId(viewId: String): AccessibilityNodeInfo? {
        val rootNode = getRootNode()
                ?: return null
        val nodeInfos = rootNode!!.findAccessibilityNodeInfosByViewId(viewId)
        val accessibilityNodeInfo = if (nodeInfos.size > 0) nodeInfos.get(0) else null
        if (accessibilityNodeInfo == null) {
//            LogUtils.d("getFirstNodeInfoByViewId :  nodeInfo is null ,viewId :$viewId")
        }
        return accessibilityNodeInfo
    }

    /**
     * Gets first node info by text.
     *
     * @param name the name
     * @return the first node info by text
     */
    fun getFirstNodeInfoByText(name: String): AccessibilityNodeInfo? {
        val rootNode = getRootNode()
                ?: return null
        val nodeInfos = rootNode.findAccessibilityNodeInfosByText(name)
        val accessibilityNodeInfo = if (nodeInfos.size > 0) nodeInfos[0] else null
        if (accessibilityNodeInfo == null) {
//            LogUtils.d("getFirstNodeInfoByText :  nodeInfo is null ,name :$name")
        }
        return accessibilityNodeInfo
    }

    /**
     * Gets first node info by class.
     *
     * @param className the class name
     * @return the first node info by class
     */
    fun getFirstNodeInfoByClass(className: String): AccessibilityNodeInfo? {
        val rootNode = getRootNode()
            ?: return null
        for (i in 0 until rootNode.childCount) {
            if (rootNode.getChild(i).className == className) {
                return rootNode.getChild(i)
            }
            if (rootNode.getChild(i).childCount > 0) {
                val nodes= getNodeInfoByClass(rootNode.getChild(i), className)
                if (nodes.isNotEmpty()) return nodes[0]
            }
        }
        return null
    }

    fun getNodeInfoByClass(rootNode: AccessibilityNodeInfo, className: String): List<AccessibilityNodeInfo> {
        val nodes = mutableListOf<AccessibilityNodeInfo>()
        for (i in 0 until rootNode.childCount) {
            if (rootNode.getChild(i).className == className) {
                nodes.add(rootNode.getChild(i))
            }
            if (rootNode.getChild(i).childCount > 0) {
                nodes.addAll(getNodeInfoByClass(rootNode.getChild(i), className))
            }
        }
        return nodes
    }

    /**
     * Sets edit text.
     *
     * @param editNodeInfo the edit node info
     * @param text the text
     * @return the edit text
     */
    fun setEditText(editNodeInfo: AccessibilityNodeInfo?, text: String): Boolean {
        if (editNodeInfo != null) {
            val arguments = Bundle()
            arguments.putCharSequence(
                    AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text)
            return editNodeInfo.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
        } else {
            return false
        }
    }

    fun performReturnBack(): Boolean {
//        LogUtils.d("performReturnBack: ")
        return assistService != null && assistService!!.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }
}