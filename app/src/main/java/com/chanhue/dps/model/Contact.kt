package com.chanhue.dps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int,
    val petProfile: PetProfile,
    val owner: Owner,
) : Parcelable
