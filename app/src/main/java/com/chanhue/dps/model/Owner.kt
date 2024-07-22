package com.chanhue.dps.model

import android.app.backup.BackupAgent
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(

    val id:Int,
    val age:Int,
    val gender:Boolean, // 0 남자 1 여자
    val nickname:String,
    val phonenumber:String
):Parcelable
