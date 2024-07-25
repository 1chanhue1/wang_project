package com.chanhue.dps

object DialogStateManager {
    var isShowing = false
        private set

    fun setIsShowing(isShowing: Boolean) {
        this.isShowing = isShowing
    }
}