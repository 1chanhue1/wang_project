package com.chanhue.dps.util

object DialogStateManager {
    var isShowing = false
        private set

    fun setIsShowing(isShowing: Boolean) {
        DialogStateManager.isShowing = isShowing
    }
}