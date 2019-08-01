package com.zy.accessibilitylib.task

import com.zy.accessibilitylib.base.BaseTask
import java.util.*

/**
 *   created by zhangyong
 *   on 2019/7/31
 */
object TaskManager {

    private val mLock = Object()
    private val taskList = LinkedList<BaseTask>()
    private var currentTask: BaseTask? = null

    fun addTask(task: BaseTask) {
        synchronized(mLock) {
            taskList.add(task)
            if (currentTask == null) {
                nextTask()
            }
        }
    }

    private fun nextTask() {
        if (taskList.isNotEmpty()) {
            currentTask = taskList[0]
            currentTask!!.start()
        }
    }

    fun endTask(task: BaseTask) {
        synchronized(mLock) {
            taskList.remove(task)
            if (task == currentTask) {
                currentTask = null
                nextTask()
            }
        }
    }

    fun onPageOpen(currentPackage: CharSequence?, currentActivity: String) {
        if (currentTask != null) {
            currentTask!!.onPageOpen(currentPackage, currentActivity)
        }
    }
}