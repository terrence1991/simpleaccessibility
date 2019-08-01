package com.zy.accessibilitylib.base

/**
 *   created by zhangyong
 *   on 2019/7/31
 */
class EndAction(parentTask: BaseTask): BaseAction(parentTask) {
    override fun execute(): Boolean {
        return true
    }
}