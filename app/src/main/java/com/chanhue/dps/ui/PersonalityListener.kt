package com.chanhue.dps.ui

interface PersonalityListener {

    fun onPersonalityUpdated(personality: String)

    fun onPersonalityDeleted(personality: String)
}