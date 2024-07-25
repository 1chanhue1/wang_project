package com.chanhue.dps.ui.extensions

//private var isValidPetProfileImage = false // isValidInput
//private var isValidOwnerName = false      // isValidInput
//private var isValidOwnerGender = false        // isValidInput
//private var isValidPhoneNumber = false         // isValidPhoneNumber
//private var isValidOwnerAge = false // isValidInput, 차피 넘버피커에서 1~100 사이 값만 받음
//private var isValidRegion = false // isValidInput
//
//private var isValidPetName = false // isValidInput
//private var isValidPetGender = false // isValidInput
//private var isValidPetSpecies = false // isValidInput
//private var isValidPetAge = false // isValidInput, 차피 넘버피커에서 1~30 사이 값만 받음
//private var isValidPersonality = false // 리스트의 길이가 0 초과 2이하 인지 확인
//
//private var isValidMemo = false // 50글자 이하인지 확인

fun String.isValidInput(): Boolean {
    return isNotBlank()
}

fun String.isValidPhoneNumber(): Boolean {
    // 전화번호 형식에 맞는지 확인, - 없이 10~11자리 숫자, 근데 - 있이 전달 받음
    return isNotBlank() && matches(Regex("^\\d{3}-\\d{3,4}-\\d{4}$"))
}

fun String.isValidPersonality(): Boolean {
    // 리스트의 길이가 0 초과 2이하 인지 확인
    return isNotBlank() && split(",").size in 1..2
}

fun String.isValidMemo(): Boolean {
    // 50글자 이하인지 확인
    return length <= 50
}