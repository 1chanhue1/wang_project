package com.chanhue.dps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chanhue.dps.model.Contact
import com.chanhue.dps.model.ContactManager

class ContactViewModel : ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    init {

        _contacts.value = ContactManager.getContactListByDogName()
    }

    fun updateContacts(newContacts: List<Contact>) {
        _contacts.value = newContacts
    }
}