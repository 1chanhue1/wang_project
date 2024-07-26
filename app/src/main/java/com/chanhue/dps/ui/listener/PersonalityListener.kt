package com.chanhue.dps.ui.listener

interface PersonalityListener {

    fun onPersonalityUpdated(personality: String)

    fun onPersonalityDeleted(personality: String)
}