package com.chanhue.dps.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.chanhue.dps.model.Contact
import com.chanhue.dps.model.ContactManager

class ContactViewModel : ViewModel() {
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    val favoriteContacts: LiveData<List<Contact>> = _contacts.map { contacts ->
        contacts.filter { it.isFavorite }
    }

    private var currentRegionFilter: String? = null
    private var currentSpeciesFilter: String? = null
    private var currentAgeRangeFilter: String? = null

    init {
        _contacts.value = ContactManager.getContactListByDogName()
    }

    fun updateContacts(newContacts: List<Contact>) {
        _contacts.value = newContacts
        applyFilters()
    }

    fun addContact(contact: Contact) {
        val currentContacts = _contacts.value.orEmpty().toMutableList()
        currentContacts.add(contact)
        _contacts.value = currentContacts.sortedBy { it.petProfile.name }
        applyFilters()
    }

    fun setRegionFilter(region: String?) {
        currentRegionFilter = region
        applyFilters()
    }

    fun setSpeciesFilter(species: String?) {
        currentSpeciesFilter = species
        applyFilters()
    }

    fun setAgeRangeFilter(ageRange: String?) {
        currentAgeRangeFilter = ageRange
        applyFilters()
    }

    fun clearFilters() {
        currentRegionFilter = null
        currentSpeciesFilter = null
        currentAgeRangeFilter = null
        _contacts.value = ContactManager.getContactListByDogName()
    }

    private fun getAgeRange(age: Int): String {
        return when (age) {
            in 1..5 -> "1-5세"
            in 6..10 -> "6-10세"
            in 11..15 -> "11-15세"
            in 16..20 -> "16-20세"
            in 21..25 -> "21-25세"
            else -> "기타"
        }
    }
    // 필터 -> 연락처 목록 업데이트
    private fun applyFilters() {
        var filteredContacts = ContactManager.getContactListByDogName()

        currentRegionFilter?.let { region ->
            filteredContacts = filteredContacts.filter { it.owner.region == region }.toMutableList()
        }

        currentSpeciesFilter?.let { species ->
            filteredContacts = filteredContacts.filter { it.petProfile.species == species }.toMutableList()
        }

        currentAgeRangeFilter?.let { ageRange ->
            filteredContacts =
                filteredContacts.filter { getAgeRange(it.petProfile.age) == ageRange }.toMutableList()
        }

        _contacts.value = filteredContacts
    }
    // 연락처 정보 수정
    fun updateContact(contact: Contact) {
        val currentContacts = _contacts.value.orEmpty().toMutableList()
        val index = currentContacts.indexOfFirst { it.id == contact.id }
        if (index != -1) {
            currentContacts[index] = contact
            _contacts.value = currentContacts
        }
        Log.d("ContactViewModel", "contact: $contact")
        Log.d("ContactViewModel", "_contacts.value: ${_contacts.value}")
        Log.d("ContactViewModel", "currentContacts: ${currentContacts}")
    }

    fun toggleFavorite(contactId: Int) {
        val currentContacts = _contacts.value.orEmpty().toMutableList()
        val index = currentContacts.indexOfFirst { it.id == contactId }
        if (index != -1) {
            val contact = currentContacts[index]
            contact.isFavorite = !contact.isFavorite
            currentContacts[index] = contact
            _contacts.value = currentContacts
        }
    }
}
