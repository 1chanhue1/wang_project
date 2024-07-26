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

    fun addContact(contact: Contact) {
        val currentContacts = _contacts.value.orEmpty().toMutableList()
        // currentContacts에서 contact를 추가하고 이름순으로 정렬된 리스트를 만들어서 _contacts에 저장
        currentContacts.add(contact)
        _contacts.value = currentContacts.sortedBy { it.petProfile.name }
    }
}