package com.chanhue.dps.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PetProfile(

    val id: Int,
    val thumbnailImage: String,
    val name: String,
    val gender: Boolean,
    val age: Int,
    val species: String,
    val dogImageList: MutableList<String>,
    val memo: String,
    val personality: String

) : Parcelable
