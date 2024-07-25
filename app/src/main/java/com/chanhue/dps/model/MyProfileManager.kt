package com.chanhue.dps.model

object MyProfileManager {


    private var myProfile: MyProfile? = MyProfile("홍길동","010-1234-1234","서울특별시 강남구","010-4321-4321")

    fun setProfile(profile: MyProfile) { // 나의 프로필 설정하기
        myProfile = profile
    }

    fun getProfile(): MyProfile? { // 나의 프로필 가져오기
        return myProfile
    }

    fun clearProfile() { // 프로필 초기화
        myProfile = null
    }
}