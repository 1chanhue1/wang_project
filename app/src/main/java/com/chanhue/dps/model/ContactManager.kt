package com.chanhue.dps.model

object ContactManager {
    private var contactList: MutableList<Contact> = getDummyData()

    private fun getDummyData(): MutableList<Contact> {
        return mutableListOf(
            Contact(
                1,
                PetProfile(
                    1,
                    "https://post-phinf.pstatic.net/MjAyNDAxMzBfMjQ3/MDAxNzA2NTg4MzUzNDE0.tnVXDqzdrbm9PLfoMttZyBuL0uzr1dbySOwbruch4a4g.ZyQBiTAwqe3DDruXKp5NyrP4wqpbzmM7UR10dD8B5KQg.JPEG/8a89645c136a15dbe15ce70525630cc835aa0e4c.jpg?type=w1200",
                    "두부",
                    false,
                    2,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMzcg/MDAxNzA2NTg4MzUzODI5.wHVggCZjQXHxxJbogMkC8b_bgyM3_2IpyLuIIvE3jdMg.DbSl3ZC2s3e-YmkUZ6yljB_I-Vns9XpjYd41P3JqFsYg.JPEG/fba12b9529d04f1d9e44b134a3d2fd6eb234be74.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg",
                        "https://i.namu.wiki/i/4BLiiPCZ592quXxgRmTRlKPilyQNk9a95Tjv929llJh2RGLo_1PcLqWC8FqFz5BcbuDqHwjCMQp9L4MnsAJ9vtHnhkeMcfzbW-EkHJxcL1z9R7l75dtOn8HzaVSRh46WvG1x5BZZlkQf3-io1oE1kw.webp"
                    ),
                    "저녁에 산책하는 거 좋아해요",
                    "활발, 자기 주장이 강함"
                ),
                Owner(
                    1,
                    "서정우",
                    false,
                    "010-1111-1111",
                    25,
                    "서울특별시 강남구"
                )
            ),
            Contact(
                2,
                PetProfile(
                    2,
                    "https://cdn.ngetnews.com/news/photo/202005/301734_12608_1735.jpg",
                    "몽이",
                    false,
                    5,
                    "비숑",
                    mutableListOf(
                        "https://mblogthumb-phinf.pstatic.net/MjAyMjA4MjRfMTU0/MDAxNjYxMzIwMDgzODc1.NhSveasGmH8V8ukDCWxW4aqzb5IHQKmFNR65Q8dnziUg.pXICC5EkLUc6Xgk6MW6nKtDML-eBxjypZpeCVlWXISUg.JPEG.ages9090/KakaoTalk_20220824_140238973_09.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MjNfNjkg/MDAxNTk1NTAxMzU3OTU0.c3cNYzSUnzwwFof34Ljr958vYCcDUZUTAju5AcPHncMg.GBkzSNM1CUEEtSP4RWJIxQ27_Qpx3eDyhS1zNwD1_VIg.JPEG.hyousang/2.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMjA4MjRfMTcw/MDAxNjYxMzIwNjIzOTI0.AyumUzOQX2zU39ehbtaqzPK9cGsZ9FOYuvU-e28LKOgg.nhN3IDo5yGiRXXrQrgdSlxTVLC2_d6fRc7l8TgxrrwAg.JPEG.ages9090/KakaoTalk_20220824_140238973_18.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MjNfMjQ4/MDAxNTk1NTAxMzU3OTQ1.miqOYPqXHmTThFB_r8n_pKpONGTwo2ezLkI86APYhAEg.HqCvzWtPAtyEAm5Ak6hRvL5KERbbLMxzT1hGhMSq3uQg.JPEG.hyousang/1.jpg?type=w800",
                        "https://img.famtimes.co.kr/news/photo/202102/502199_3315_3651.png",
                        "https://marikennel1989.com/web/product/big/202012/0361c5c0fc08ea3096c626e001d6bc0f.jpg",
                        "https://image-notepet.akamaized.net/resize/620x-/seimage/20190325/b6c456f4f6bf5d2560c26a780b7c36b9.jpg",
                        "https://png.pngtree.com/thumb_back/fh260/background/20230902/pngtree-a-white-bichon-puppy-standing-on-the-stairs-image_13129383.jpg"
                    ),
                    "사회성이 너무 좋아요",
                    "활발, 사람을 좋아해요"
                ),
                Owner(
                    2,
                    "김찬휘",
                    false,
                    "010-1111-2222",
                    25,
                    "서울특별시 서초구"
                )
            ),
            // 추가 데이터들
            Contact(
                3,
                PetProfile(
                    3,
                    "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                    "초코",
                    false,
                    3,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200"
                    ),
                    "항상 활기차고 친구들과 어울리기 좋아해요",
                    "밝고 긍정적인 성격"
                ),
                Owner(
                    3,
                    "이정현",
                    false,
                    "010-1111-3333",
                    25,
                    "서울특별시 종로구"
                )
            ),
            Contact(
                4,
                PetProfile(
                    4,
                    "https://perrodog.co.kr/files/attach/images/2023/06/07/7ec0544c0a3878c60cfaf48aaafc52c7.jpg",
                    "눈송이",
                    true,
                    4,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMzcg/MDAxNzA2NTg4MzUzODI5.wHVggCZjQXHxxJbogMkC8b_bgyM3_2IpyLuIIvE3jdMg.DbSl3ZC2s3e-YmkUZ6yljB_I-Vns9XpjYd41P3JqFsYg.JPEG/fba12b9529d04f1d9e44b134a3d2fd6eb234be74.jpg?type=w1200",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg"
                    ),
                    "조용한 것을 좋아하며, 혼자 있는 시간이 많아요",
                    "침착하고 차분한 성격"
                ),
                Owner(
                    4,
                    "박수진",
                    true,
                    "010-1111-4444",
                    25,
                    "서울특별시 송파구"
                )
            ),
            Contact(
                5,
                PetProfile(
                    5,
                    "https://img.famtimes.co.kr/resources/2019/02/15/USWOZ7hcLD3SnOv5.jpg",
                    "댕댕이",
                    false,
                    1,
                    "닥스훈트",
                    mutableListOf(
                        "https://cdn.pixabay.com/photo/2019/09/03/06/45/dachshund-4448740_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                        "https://i.namu.wiki/i/ThxW_nrt2bcTGt4DlOncR_zbrdEYE0k21V2GN4u47Tis1yohdSRB50vHrRMUl_wYBHyEMT5ITrQI50IXSl0c0w.webp",
                        "https://cdn.ecobs.co.kr/news/photo/202005/26017_22785_3939.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                        "https://i.namu.wiki/i/ThxW_nrt2bcTGt4DlOncR_zbrdEYE0k21V2GN4u47Tis1yohdSRB50vHrRMUl_wYBHyEMT5ITrQI50IXSl0c0w.webp",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFnlyzPp5qykyS36GW7j_GEzF6yJISxADm1PHqd17OJT6va24ZxNqA91YyKXtIvFma83U&usqp=CAU",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQR3sLWXzpeywivrovkSC1uxzlHD-3UDep2bw&s",
                        "https://cdn.daehanilbo.co.kr/news/photo/202005/39233_26414_4433.jpg"
                    ),
                    "활동적이고 호기심이 많습니다",
                    "호기심이 많고, 용감한 성격"
                ),
                Owner(
                    5,
                    "최민수",
                    false,
                    "010-1111-5555",
                    28,
                    "서울특별시 마포구"
                )
            ),
            Contact(
                6,
                PetProfile(
                    6,
                    "https://t1.daumcdn.net/news/202105/25/dogmate/20210525045430145gtll.jpg",
                    "멍멍이",
                    true,
                    2,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                        "https://i.namu.wiki/i/4BLiiPCZ592quXxgRmTRlKPilyQNk9a95Tjv929llJh2RGLo_1PcLqWC8FqFz5BcbuDqHwjCMQp9L4MnsAJ9vtHnhkeMcfzbW-EkHJxcL1z9R7l75dtOn8HzaVSRh46WvG1x5BZZlkQf3-io1oE1kw.webp"
                    ),
                    "사람과 잘 어울리고, 애교가 많습니다",
                    "사교적이고 애교 많은 성격"
                ),
                Owner(
                    6,
                    "김은정",
                    true,
                    "010-1111-6666",
                    28,
                    "서울특별시 은평구"
                )
            ),
            Contact(
                7,
                PetProfile(
                    7,
                    "https://i.namu.wiki/i/paGSt-Agyaph_hPmknmB4J0u6nqEP-DUdVfmP7Y61D_nvXozhm9s1_wWAzOcWF5m_T49cUZyfMIMDnCQZN7IaQ.webp",
                    "바둑이",
                    false,
                    4,
                    "진돗개",
                    mutableListOf(
                        "https://i.namu.wiki/i/aaMqYjVgNYiLx7jR_6jjhsBZJrcLUTgI3eOMq7QxanQwZZEezETQ9gNNUqOPJzO7FlMS5dUDbzjZc3p6YFnhUw.webp",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS0CjZ3J4LlgNSt9H8PUlnG2Ncksu0tR1dRQxouIiN1zkRK0tn32JVSSEXYFg-qHOYOyEg&usqp=CAU",
                        "https://flexible.img.hani.co.kr/flexible/normal/958/638/imgdb/original/2021/0414/20210414501879.jpg",
                        "https://image.dongascience.com/Photo/2017/06/14964815289707.jpg",
                        "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202105/01/736a415d-a55d-48b0-8192-c304793d9c7e.jpg"
                    ),
                    "충성심이 강하며 보호자에게 매우 충실합니다",
                    "충성심이 강하고 용맹한 성격"
                ),
                Owner(
                    7,
                    "박경훈",
                    false,
                    "010-1111-7777",
                    28,
                    "서울특별시 동작구"
                )
            ),
            Contact(
                8,
                PetProfile(
                    8,
                    "https://t1.daumcdn.net/news/202105/25/dogmate/20210525045430145gtll.jpg",
                    "달래",
                    true,
                    3,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMzcg/MDAxNzA2NTg4MzUzODI5.wHVggCZjQXHxxJbogMkC8b_bgyM3_2IpyLuIIvE3jdMg.DbSl3ZC2s3e-YmkUZ6yljB_I-Vns9XpjYd41P3JqFsYg.JPEG/fba12b9529d04f1d9e44b134a3d2fd6eb234be74.jpg?type=w1200",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200"
                    ),
                    "작고 귀여운 외모를 가지고 있으며, 온순합니다",
                    "온순하고 애교 많은 성격"
                ),
                Owner(
                    8,
                    "이수민",
                    true,
                    "010-1111-8888",
                    28,
                    "서울특별시 강동구"
                )
            ),
            Contact(
                9,
                PetProfile(
                    9,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJVgxx2MC4C4Ec_Bcz5cz0K1xtfDE-0u4iJDU_ixp0xiFYTtb6le1hAmWfVBkbFmJmlIk&usqp=CAU",
                    "까미",
                    false,
                    5,
                    "진돗개",
                    mutableListOf(
                        "https://i.namu.wiki/i/uIt7OBpwNR2Cgk90eW_s_0iAZ6628xqGiRY1YyG5drpYaFwXo4ZAKKLltMDxLc2qPyky0s6D9bociJ770v2mwA.webp",
                        "https://i.namu.wiki/i/aaMqYjVgNYiLx7jR_6jjhsBZJrcLUTgI3eOMq7QxanQwZZEezETQ9gNNUqOPJzO7FlMS5dUDbzjZc3p6YFnhUw.webp",
                        "https://i.namu.wiki/i/paGSt-Agyaph_hPmknmB4J0u6nqEP-DUdVfmP7Y61D_nvXozhm9s1_wWAzOcWF5m_T49cUZyfMIMDnCQZN7IaQ.webp",
                        "https://image.dongascience.com/Photo/2017/06/14964815289707.jpg"
                    ),
                    "집을 잘 지키고 낯선 사람에게 경계심이 강합니다",
                    "충직하고 경계심 강한 성격"
                ),
                Owner(
                    9,
                    "정명훈",
                    false,
                    "010-1111-9999",
                    21,
                    "서울특별시 중구"
                )
            ),
            Contact(
                10,
                PetProfile(
                    10,
                    "https://perrodog.co.kr/files/attach/images/2023/06/07/7ec0544c0a3878c60cfaf48aaafc52c7.jpg",
                    "아리",
                    true,
                    3,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg"
                    ),
                    "작고 활발하며 친구들과 어울리기 좋아합니다",
                    "밝고 활발한 성격"
                ),
                Owner(
                    10,
                    "최지우",
                    true,
                    "010-2222-1111",
                    21,
                    "서울특별시 동대문구"
                )
            ),
            Contact(
                11,
                PetProfile(
                    11,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJVgxx2MC4C4Ec_Bcz5cz0K1xtfDE-0u4iJDU_ixp0xiFYTtb6le1hAmWfVBkbFmJmlIk&usqp=CAU",
                    "구름이",
                    true,
                    2,
                    "진돗개",
                    mutableListOf(
                        "https://i.namu.wiki/i/aaMqYjVgNYiLx7jR_6jjhsBZJrcLUTgI3eOMq7QxanQwZZEezETQ9gNNUqOPJzO7FlMS5dUDbzjZc3p6YFnhUw.webp",
                        "https://i.namu.wiki/i/paGSt-Agyaph_hPmknmB4J0u6nqEP-DUdVfmP7Y61D_nvXozhm9s1_wWAzOcWF5m_T49cUZyfMIMDnCQZN7IaQ.webp",
                        "https://image.dongascience.com/Photo/2017/06/14964815289707.jpg"
                    ),
                    "사람을 잘 따르고 충직합니다",
                    "충직하고 온순한 성격"
                ),
                Owner(
                    11,
                    "이성준",
                    false,
                    "010-2222-2222",
                    21,
                    "서울특별시 노원구"
                )
            ),
            Contact(
                12,
                PetProfile(
                    12,
                    "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MjNfNjkg/MDAxNTk1NTAxMzU3OTU0.c3cNYzSUnzwwFof34Ljr958vYCcDUZUTAju5AcPHncMg.GBkzSNM1CUEEtSP4RWJIxQ27_Qpx3eDyhS1zNwD1_VIg.JPEG.hyousang/2.jpg?type=w800",
                    "복실이",
                    true,
                    4,
                    "비숑 프리제",
                    mutableListOf(
                        "https://mblogthumb-phinf.pstatic.net/MjAyMjA4MjRfMTU0/MDAxNjYxMzIwMDgzODc1.NhSveasGmH8V8ukDCWxW4aqzb5IHQKmFNR65Q8dnziUg.pXICC5EkLUc6Xgk6MW6nKtDML-eBxjypZpeCVlWXISUg.JPEG.ages9090/KakaoTalk_20220824_140238973_09.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MjNfMjQ4/MDAxNTk1NTAxMzU3OTQ1.miqOYPqXHmTThFB_r8n_pKpONGTwo2ezLkI86APYhAEg.HqCvzWtPAtyEAm5Ak6hRvL5KERbbLMxzT1hGhMSq3uQg.JPEG.hyousang/1.jpg?type=w800",
                        "https://img.famtimes.co.kr/news/photo/202102/502199_3315_3651.png"
                    ),
                    "항상 밝고 활발합니다",
                    "명랑하고 활기찬 성격"
                ),
                Owner(
                    12,
                    "김영희",
                    true,
                    "010-3333-3333",
                    21,
                    "서울특별시 강서구"
                )
            ),
            Contact(
                13,
                PetProfile(
                    13,
                    "https://i.namu.wiki/i/p8f5LesuquLp-iZqt7celuMAnechji6RS0HwQzOsF5BBMBrikXUpTDS7OR8s7bQahBhpAlLeNzH-Uqu2ksatabCHq0LH7gwkDtM45Pjlug66aQ_z75OCEPg1WvuYGvdtSG9gHiXPi7uoGL8Tfezpfw.webp",
                    "뭉치",
                    false,
                    5,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMzcg/MDAxNzA2NTg4MzUzODI5.wHVggCZjQXHxxJbogMkC8b_bgyM3_2IpyLuIIvE3jdMg.DbSl3ZC2s3e-YmkUZ6yljB_I-Vns9XpjYd41P3JqFsYg.JPEG/fba12b9529d04f1d9e44b134a3d2fd6eb234be74.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200"
                    ),
                    "아직 친해지지 못해서 산책 같이 해보고 싶다.",
                    "낯가림, 조용"
                ),
                Owner(
                    13,
                    "박지훈",
                    false,
                    "010-4444-4444",
                    21,
                    "서울특별시 서대문구"
                )
            ),
            Contact(
                14,
                PetProfile(
                    14,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRFnlyzPp5qykyS36GW7j_GEzF6yJISxADm1PHqd17OJT6va24ZxNqA91YyKXtIvFma83U&usqp=CAU",
                    "토리",
                    true,
                    2,
                    "닥스훈트",
                    mutableListOf(
                        "https://cdn.pixabay.com/photo/2019/09/03/06/45/dachshund-4448740_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                        "https://images.mypetlife.co.kr/content/uploads/2022/05/25101023/foxhound-g233c2e72d_1280-edited.jpg"
                    ),
                    "활동적이고 장난기가 많습니다",
                    "활발, 장난기 많음"
                ),
                Owner(
                    14,
                    "이민호",
                    false,
                    "010-5555-5555",
                    30,
                    "서울특별시 용산구"
                )
            ),
            Contact(
                15,
                PetProfile(
                    15,
                    "https://mblogthumb-phinf.pstatic.net/MjAyMjExMjhfMjk2/MDAxNjY5NjA2NDQwNjIx.CdPTYrTuwq-6S7llBAqjCdMoKioZx1aXQD7DPc79FHkg._Cvzm80uNCkc0habhhUOrz7DfD9uqH8bQERJ3GkPXHUg.JPEG.lael_v_v/IMG_0186.jpg?type=w800",
                    "솜사탕",
                    true,
                    1,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                        "https://i.namu.wiki/i/p8f5LesuquLp-iZqt7celuMAnechji6RS0HwQzOsF5BBMBrikXUpTDS7OR8s7bQahBhpAlLeNzH-Uqu2ksatabCHq0LH7gwkDtM45Pjlug66aQ_z75OCEPg1WvuYGvdtSG9gHiXPi7uoGL8Tfezpfw.webp"
                    ),
                    "작고 귀여운 외모로 인기가 많습니다",
                    "애교 많음, 활발"
                ),
                Owner(
                    15,
                    "한지우",
                    true,
                    "010-6666-6666",
                    30,
                    "서울특별시 금천구"
                )
            ),
            Contact(
                16,
                PetProfile(
                    16,
                    "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg",
                    "하늘",
                    false,
                    4,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMzcg/MDAxNzA2NTg4MzUzODI5.wHVggCZjQXHxxJbogMkC8b_bgyM3_2IpyLuIIvE3jdMg.DbSl3ZC2s3e-YmkUZ6yljB_I-Vns9XpjYd41P3JqFsYg.JPEG/fba12b9529d04f1d9e44b134a3d2fd6eb234be74.jpg?type=w1200",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg"
                    ),
                    "과일 챙겨주면 좋아한다.",
                    "사교적"
                ),
                Owner(
                    16,
                    "박상현",
                    false,
                    "010-7777-7777",
                    30,
                    "서울특별시 광진구"
                )
            ),
            Contact(
                17,
                PetProfile(
                    17,
                    "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/202105/01/736a415d-a55d-48b0-8192-c304793d9c7e.jpg",
                    "태양",
                    false,
                    3,
                    "진돗개",
                    mutableListOf(
                        "https://i.namu.wiki/i/uIt7OBpwNR2Cgk90eW_s_0iAZ6628xqGiRY1YyG5drpYaFwXo4ZAKKLltMDxLc2qPyky0s6D9bociJ770v2mwA.webp",
                        "https://i.namu.wiki/i/paGSt-Agyaph_hPmknmB4J0u6nqEP-DUdVfmP7Y61D_nvXozhm9s1_wWAzOcWF5m_T49cUZyfMIMDnCQZN7IaQ.webp",
                        "https://i.namu.wiki/i/aaMqYjVgNYiLx7jR_6jjhsBZJrcLUTgI3eOMq7QxanQwZZEezETQ9gNNUqOPJzO7FlMS5dUDbzjZc3p6YFnhUw.webp"
                    ),
                    "경계심이 강하고 보호자를 잘 지킵니다",
                    "충성심이 강함, 경계심 강함"
                ),
                Owner(
                    17,
                    "홍길동",
                    false,
                    "010-8888-8888",
                    30,
                    "서울특별시 중랑구"
                )
            ),
            Contact(
                18,
                PetProfile(
                    18,
                    "https://raingroup.co.kr/data/editor/2305/07f12eb7dd7a8321a87639b68bf165b8_1684475633_3204.jpg",
                    "해피",
                    true,
                    2,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200"
                    ),
                    "사람 많은 곳을 싫어한다.",
                    "온순, 낯가림"
                ),
                Owner(
                    18,
                    "유지현",
                    true,
                    "010-9999-9999",
                    30,
                    "서울특별시 양천구"
                )
            ),
            Contact(
                19,
                PetProfile(
                    19,
                    "https://cdn.ngetnews.com/news/photo/202005/301734_12608_1735.jpg",
                    "복덩이",
                    true,
                    4,
                    "비숑 프리제",
                    mutableListOf(
                        "https://mblogthumb-phinf.pstatic.net/MjAyMjA4MjRfMTU0/MDAxNjYxMzIwMDgzODc1.NhSveasGmH8V8ukDCWxW4aqzb5IHQKmFNR65Q8dnziUg.pXICC5EkLUc6Xgk6MW6nKtDML-eBxjypZpeCVlWXISUg.JPEG.ages9090/KakaoTalk_20220824_140238973_09.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MjNfMjQ4/MDAxNTk1NTAxMzU3OTQ1.miqOYPqXHmTThFB_r8n_pKpONGTwo2ezLkI86APYhAEg.HqCvzWtPAtyEAm5Ak6hRvL5KERbbLMxzT1hGhMSq3uQg.JPEG.hyousang/1.jpg?type=w800",
                        "https://img.famtimes.co.kr/news/photo/202102/502199_3315_3651.png"
                    ),
                    "사람과 잘 어울리며 활동적입니다",
                    "활발, 사교적"
                ),
                Owner(
                    19,
                    "김수진",
                    true,
                    "010-0000-1111",
                    30,
                    "서울특별시 성동구"
                )
            ),
            Contact(
                20,
                PetProfile(
                    20,
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQR3sLWXzpeywivrovkSC1uxzlHD-3UDep2bw&s",
                    "반달이",
                    false,
                    3,
                    "닥스훈트",
                    mutableListOf(
                        "https://cdn.pixabay.com/photo/2019/09/03/06/45/dachshund-4448740_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                        "https://i.namu.wiki/i/ThxW_nrt2bcTGt4DlOncR_zbrdEYE0k21V2GN4u47Tis1yohdSRB50vHrRMUl_wYBHyEMT5ITrQI50IXSl0c0w.webp"
                    ),
                    "공원을 좋아한다.",
                    "활발, 사교적"
                ),
                Owner(
                    20,
                    "정유진",
                    true,
                    "010-1111-0000",
                    30,
                    "서울특별시 도봉구"
                )
            ),
            Contact(
                21,
                PetProfile(
                    21,
                    "https://i.namu.wiki/i/z2O0p4vXdL7rV-QjZ1chB5_B39l9IO21-awQQdzeuyr7mDWvrwGHFUkex899yyLHwuFwYsp-wl8105822ym-Xw.webp",
                    "누렁이",
                    true,
                    4,
                    "진돗개",
                    mutableListOf(
                        "https://i.namu.wiki/i/aaMqYjVgNYiLx7jR_6jjhsBZJrcLUTgI3eOMq7QxanQwZZEezETQ9gNNUqOPJzO7FlMS5dUDbzjZc3p6YFnhUw.webp",
                        "https://i.namu.wiki/i/paGSt-Agyaph_hPmknmB4J0u6nqEP-DUdVfmP7Y61D_nvXozhm9s1_wWAzOcWF5m_T49cUZyfMIMDnCQZN7IaQ.webp",
                        "https://image.dongascience.com/Photo/2017/06/14964815289707.jpg"
                    ),
                    "아직 친해지지 못했다.",
                    "충성심 강함"
                ),
                Owner(
                    21,
                    "김영수",
                    false,
                    "010-1234-5678",
                    30,
                    "서울특별시 강북구"
                )
            ),
            Contact(
                22,
                PetProfile(
                    22,
                    "https://t1.daumcdn.net/tistoryfile/fs3/17_7_3_3_blog35112_attach_1_118.JPG?original",
                    "복돌이",
                    false,
                    2,
                    "진돗개",
                    mutableListOf(
                        "https://i.namu.wiki/i/uIt7OBpwNR2Cgk90eW_s_0iAZ6628xqGiRY1YyG5drpYaFwXo4ZAKKLltMDxLc2qPyky0s6D9bociJ770v2mwA.webp",
                        "https://i.namu.wiki/i/aaMqYjVgNYiLx7jR_6jjhsBZJrcLUTgI3eOMq7QxanQwZZEezETQ9gNNUqOPJzO7FlMS5dUDbzjZc3p6YFnhUw.webp",
                        "https://image.dongascience.com/Photo/2017/06/14964815289707.jpg"
                    ),
                    "잠이 많다",
                    "조용, 온순"
                ),
                Owner(
                    22,
                    "박준영",
                    false,
                    "010-8765-4321",
                    30,
                    "서울특별시 영등포구"
                )
            ),
            Contact(
                23,
                PetProfile(
                    23,
                    "https://cdn.ecobs.co.kr/news/photo/202005/26017_22785_3939.jpg",
                    "마루",
                    true,
                    3,
                    "닥스훈트",
                    mutableListOf(
                        "https://cdn.pixabay.com/photo/2019/09/03/06/45/dachshund-4448740_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                        "https://i.namu.wiki/i/ThxW_nrt2bcTGt4DlOncR_zbrdEYE0k21V2GN4u47Tis1yohdSRB50vHrRMUl_wYBHyEMT5ITrQI50IXSl0c0w.webp"
                    ),
                    "먹는 것을 좋아한다.",
                    "활발, 호기심 많음"
                ),
                Owner(
                    23,
                    "이현우",
                    false,
                    "010-5555-7777",
                    18,
                    "서울특별시 양재동"
                )
            ),
            Contact(
                24,
                PetProfile(
                    24,
                    "https://mblogthumb-phinf.pstatic.net/MjAyMjExMDFfMjIg/MDAxNjY3MzEwMjgzNDYw._v7Y2yUe0T3Nh9hKKIF1vQVJpe4dJGxSMumHkNsuxAsg.I4H7xDUh4DlFEoZA1JY--eDEsN8oUHFOxT0IsHEyl88g.JPEG.hwadojinhaus/20221101_(11).jpg?type=w800",
                    "꼬미",
                    true,
                    5,
                    "진돗개",
                    mutableListOf(
                        "https://i.namu.wiki/i/paGSt-Agyaph_hPmknmB4J0u6nqEP-DUdVfmP7Y61D_nvXozhm9s1_wWAzOcWF5m_T49cUZyfMIMDnCQZN7IaQ.webp",
                        "https://i.namu.wiki/i/aaMqYjVgNYiLx7jR_6jjhsBZJrcLUTgI3eOMq7QxanQwZZEezETQ9gNNUqOPJzO7FlMS5dUDbzjZc3p6YFnhUw.webp",
                        "https://image.dongascience.com/Photo/2017/06/14964815289707.jpg"
                    ),
                    "침착하고 차분합니다",
                    "침착, 조용"
                ),
                Owner(
                    24,
                    "최수정",
                    true,
                    "010-6666-8888",
                    18,
                    "서울특별시 도곡동"
                )
            ),
            Contact(
                25,
                PetProfile(
                    25,
                    "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                    "바비",
                    false,
                    1,
                    "닥스훈트",
                    mutableListOf(
                        "https://cdn.pixabay.com/photo/2019/09/03/06/45/dachshund-4448740_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                        "https://i.namu.wiki/i/ThxW_nrt2bcTGt4DlOncR_zbrdEYE0k21V2GN4u47Tis1yohdSRB50vHrRMUl_wYBHyEMT5ITrQI50IXSl0c0w.webp"
                    ),
                    "도도하고 산책하는 것을 싫어한다.",
                    "도도, 조용"
                ),
                Owner(
                    25,
                    "김민주",
                    true,
                    "010-7777-9999",
                    18,
                    "서울특별시 서초구"
                )
            ),
            Contact(
                26,
                PetProfile(
                    26,
                    "https://blog.kakaocdn.net/dn/ekbnmV/btqMgtiqXni/DG9CE8ZEllV5rKlDgbSQok/img.jpg",
                    "쿠키",
                    true,
                    3,
                    "비숑 프리제",
                    mutableListOf(
                        "https://mblogthumb-phinf.pstatic.net/MjAyMjA4MjRfMTU0/MDAxNjYxMzIwMDgzODc1.NhSveasGmH8V8ukDCWxW4aqzb5IHQKmFNR65Q8dnziUg.pXICC5EkLUc6Xgk6MW6nKtDML-eBxjypZpeCVlWXISUg.JPEG.ages9090/KakaoTalk_20220824_140238973_09.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MjNfMjQ4/MDAxNTk1NTAxMzU3OTQ1.miqOYPqXHmTThFB_r8n_pKpONGTwo2ezLkI86APYhAEg.HqCvzWtPAtyEAm5Ak6hRvL5KERbbLMxzT1hGhMSq3uQg.JPEG.hyousang/1.jpg?type=w800",
                        "https://img.famtimes.co.kr/news/photo/202102/502199_3315_3651.png"
                    ),
                    "귀엽고 애교가 많습니다",
                    "애교 많음, 부끄럼 많음"
                ),
                Owner(
                    26,
                    "박수진",
                    true,
                    "010-8888-0000",
                    18,
                    "서울특별시 송파구"
                )
            ),
            Contact(
                27,
                PetProfile(
                    27,
                    "https://i.namu.wiki/i/4BLiiPCZ592quXxgRmTRlKPilyQNk9a95Tjv929llJh2RGLo_1PcLqWC8FqFz5BcbuDqHwjCMQp9L4MnsAJ9vtHnhkeMcfzbW-EkHJxcL1z9R7l75dtOn8HzaVSRh46WvG1x5BZZlkQf3-io1oE1kw.webp",
                    "뽀삐",
                    false,
                    4,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                        "https://i.namu.wiki/i/p8f5LesuquLp-iZqt7celuMAnechji6RS0HwQzOsF5BBMBrikXUpTDS7OR8s7bQahBhpAlLeNzH-Uqu2ksatabCHq0LH7gwkDtM45Pjlug66aQ_z75OCEPg1WvuYGvdtSG9gHiXPi7uoGL8Tfezpfw.webp"
                    ),
                    "조용하고 낮에 산책하는 것을 좋아한다.",
                    "온순, 조용"
                ),
                Owner(
                    27,
                    "이정민",
                    false,
                    "010-0000-2222",
                    18,
                    "서울특별시 광진구"
                )
            ),
            Contact(
                28,
                PetProfile(
                    28,
                    "https://lh3.googleusercontent.com/proxy/XuiGDQkMhYFGfrVqyx67xoNzenc3i8w9YYelJbCdgs6mPAQQt8OYCJssOc8VahhucViQSMD85cBqzG_LR2Qa4dzqI90ydy6vHGA3b6rIrJKVMpYLvQ",
                    "코코",
                    true,
                    2,
                    "닥스훈트",
                    mutableListOf(
                        "https://cdn.pixabay.com/photo/2019/09/03/06/45/dachshund-4448740_1280.jpg",
                        "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                        "https://i.namu.wiki/i/ThxW_nrt2bcTGt4DlOncR_zbrdEYE0k21V2GN4u47Tis1yohdSRB50vHrRMUl_wYBHyEMT5ITrQI50IXSl0c0w.webp"
                    ),
                    "작고 활동적이며 장난기가 많습니다",
                    "활발, 장난기 많음"
                ),
                Owner(
                    28,
                    "최현우",
                    false,
                    "010-3333-6666",
                    34,
                    "서울특별시 강남구"
                )
            ),
            Contact(
                29,
                PetProfile(
                    29,
                    "https://perrodog.co.kr/files/attach/images/2023/06/07/7ec0544c0a3878c60cfaf48aaafc52c7.jpg",
                    "루피",
                    false,
                    3,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                        "https://i.namu.wiki/i/p8f5LesuquLp-iZqt7celuMAnechji6RS0HwQzOsF5BBMBrikXUpTDS7OR8s7bQahBhpAlLeNzH-Uqu2ksatabCHq0LH7gwkDtM45Pjlug66aQ_z75OCEPg1WvuYGvdtSG9gHiXPi7uoGL8Tfezpfw.webp"
                    ),
                    "활발하고 에너지가 넘칩니다",
                    "활발"
                ),
                Owner(
                    29,
                    "김지민",
                    true,
                    "010-5555-4444",
                    34,
                    "서울특별시 마포구"
                )
            ),
            Contact(
                30,
                PetProfile(
                    30,
                    "https://t1.daumcdn.net/news/202105/25/dogmate/20210525045430145gtll.jpg",
                    "메리",
                    true,
                    2,
                    "포메라니안",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                        "https://i.namu.wiki/i/4BLiiPCZ592quXxgRmTRlKPilyQNk9a95Tjv929llJh2RGLo_1PcLqWC8FqFz5BcbuDqHwjCMQp9L4MnsAJ9vtHnhkeMcfzbW-EkHJxcL1z9R7l75dtOn8HzaVSRh46WvG1x5BZZlkQf3-io1oE1kw.webp"
                    ),
                    "사람을 좋아하고 친화력이 좋습니다",
                    "활발, 고집이 쎔"
                ),
                Owner(
                    30,
                    "이은지",
                    true,
                    "010-6666-5555",
                    34,
                    "서울특별시 은평구"
                )
            )
        )
    }

    // 강아지 이름순 정렬
    fun getContactListByDogName(): MutableList<Contact> {
        return contactList.sortedBy { it.petProfile.name }.toMutableList()
    }

    // 연락처 하나 가져오기
    fun getContactById(id: Int): Contact {
        return contactList.find { it.petProfile.id == id }!!
    }

    // 임시) 연락처 첫 번째 하나 가져오기
    fun getFirstContact(): Contact {
        return contactList.first()
    }

    // 임시) 연락처 5개 리스트 가져오기
    fun getLikeContactList(): List<Contact> {
        return contactList.subList(0, 5)
    }

    fun getContactLastId(): Int {
        return contactList.last().petProfile.id
    }

    fun getOwnerLastId(): Int {
        return contactList.last().owner.id
    }

    fun getPetProfileLastId(): Int {
        return contactList.last().petProfile.id
    }

    fun getRegionList(): List<String> {
        return contactList.map { it.owner.region }.distinct()
    }

    fun getContactListByRegion(region: String): List<Contact> {
        return contactList.filter { it.owner.region == region }
    }

    fun getPetSpeciesList(): List<String> {
        return contactList.map { it.petProfile.species }.distinct()
    }

    fun getContactListByPetSpecies(species: String): List<Contact> {
        return contactList.filter { it.petProfile.species == species }
    }

    fun getContactListByPetAgeRange(range: Int): List<Contact> {
        return contactList.filter { it.petProfile.age in (range * 5 - 4)..(range * 5) }
    }

    fun addContact(contact: Contact): Boolean {
        return contactList.add(contact)
    }

    fun updateContact(contact: Contact): Boolean {
        val index = contactList.indexOfFirst { it.petProfile.id == contact.petProfile.id }
        if (index == -1) {
            return false
        }
        contactList[index] = contact
        return true
    }

    fun getDefaultContact(): Contact {
        return Contact(
            -1,
            PetProfile(
                0,
                "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                "바비",
                false,
                1,
                "닥스훈트",
                mutableListOf(
                    "https://cdn.pixabay.com/photo/2019/09/03/06/45/dachshund-4448740_1280.jpg",
                    "https://cdn.pixabay.com/photo/2021/08/18/09/34/dachshund-6555136_960_720.jpg",
                    "https://i.namu.wiki/i/ThxW_nrt2bcTGt4DlOncR_zbrdEYE0k21V2GN4u47Tis1yohdSRB50vHrRMUl_wYBHyEMT5ITrQI50IXSl0c0w.webp"
                ),
                "도도하고 산책하는 것을 싫어한다.",
                "도도, 조용"
            ),
            Owner(
                0,
                "김민주",
                true,
                "010-7777-9999",
                18,
                "서울특별시 서초구"
            )
        )
    }
}