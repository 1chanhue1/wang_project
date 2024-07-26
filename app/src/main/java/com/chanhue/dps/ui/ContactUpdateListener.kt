package com.chanhue.dps.ui

import com.chanhue.dps.model.Contact

interface ContactUpdateListener {

    fun onContactUpdated(contact: Contact)
}