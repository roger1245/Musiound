package com.rg.musiound.view.widget

/**
 * Create by roger
 * on 2019/8/19
 */
class MessageEvent internal constructor(message: String) {

    private var message: String? = null
    init {
        this.message = message
    }
    internal fun getMessage(): String? {
        return message
    }
    fun setMessage(message: String) {
        this.message = message
    }
}

class PlayStateChangeEvent internal constructor(message: Int) {
    private var message: Int? = null
    init {
        this.message = message
    }
    internal fun getMessage(): Int? {
        return message
    }
    fun setMessage(message: Int) {
        this.message = message
    }
}