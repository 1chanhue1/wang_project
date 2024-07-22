package com.chanhue.dps.model

data class DogProfile(

    val id:Int,
    val name: String,
    val dogImage: MutableList<String>,
    val gender: Boolean,
    val livingArea: String,
    val age: Int,
    val species: String,
    val introduce: String,
    val personality: String
)
