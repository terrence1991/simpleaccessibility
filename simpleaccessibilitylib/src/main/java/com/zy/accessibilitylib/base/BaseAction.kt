package com.zy.accessibilitylib.base

/**
 *   created by zhangyong
 *   on 2019/7/25
 */
abstract class BaseAction(parentTask: BaseTask) {

    val parentTask: BaseTask = parentTask

    abstract fun execute(): Boolean

    open fun onPreExecute() {}

    open fun onPostExecute() {}
}