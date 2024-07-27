package com.chanhue.dps.ui.extensions

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