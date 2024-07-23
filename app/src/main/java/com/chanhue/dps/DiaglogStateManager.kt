package com.chanhue.dps

object DiaglogStateManager {
    var isShowing = false
        private set

    fun setIsShowing(isShowing: Boolean) {
        this.isShowing = isShowing
    }
}