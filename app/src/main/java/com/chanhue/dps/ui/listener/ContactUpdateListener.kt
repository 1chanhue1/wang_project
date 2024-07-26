package com.chanhue.dps.ui.listener

import com.chanhue.dps.model.Contact

interface ContactUpdateListener {

    fun onContactUpdated(contact: Contact)
}