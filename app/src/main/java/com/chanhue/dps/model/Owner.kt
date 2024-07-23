package com.chanhue.dps.model

import android.app.backup.BackupAgent
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(

    val id:Int,
    val age:Int,
    val image: String,
    val gender:Boolean, // false 남자 true 여자
    val nickname:String,
    val phoneNumber:String
):Parcelable
