package com.zy.accessibilitylib.base

import com.zy.accessibilitylib.task.TaskManager

/**
 *   created by zhangyong
 *   on 2019/7/29
 */
abstract class BaseTask {

    var id = 0L
    var actions = mutableListOf<BaseAction>()
    var isRunnng: Boolean = false
    var step = 0

    open fun start() {
        if (actions.size > 0) {
            step = -1
            isRunnng = true
            nextStep()
        }
    }

    open fun nextStep() {
        step++
        if (step >= actions.size) {
            endTask(true)
            return
        }
        if (actions[step] is OpenActivityAction) {
            actions[step].execute()
            nextStep()
        } else if (actions[step] is AccessibilityAction) {
            if ((actions[step] as AccessibilityAction).keepupFront) {
                (actions[step] as AccessibilityAction).onPageOpen()
            }
        } else if (actions[step] is EndAction) {
            endTask(true)
        }
    }

    open fun onPageOpen(currentPackage: CharSequence?, currentActivity: String) {
        if (isRunnng) {
            if (actions[step] is AccessibilityAction) {
                if ((actions[step] as AccessibilityAction).isInTaskPage(currentPackage, currentActivity)) {
                    (actions[step] as AccessibilityAction).onPageOpen()
                }
            }
        }
    }

    open fun endTask(success: Boolean) {
        isRunnng = false
        TaskManager.endTask(this)
    }
}