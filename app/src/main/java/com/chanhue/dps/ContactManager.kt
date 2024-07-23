package com.chanhue.dps

import com.chanhue.dps.model.ContactInfo
import com.chanhue.dps.model.DogProfile
import com.chanhue.dps.model.Owner

class ContactManager {
    private val contactList: List<ContactInfo> by lazy { getDummyData() }

    private fun getDummyData(): List<ContactInfo> {
        return listOf(
            ContactInfo(
                Owner(
                    1,
                    30,
                    "https://img.freepik.com/premium-photo/closeup-landscape-with-unrecognizable-person-beautiful-spring-day_499642-65.jpg",
                    false,
                    "홍길동",
                    "010-1111-1111"
                ),
                DogProfile(
                    1,
                    "두부",
                    mutableListOf(
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMjQ3/MDAxNzA2NTg4MzUzNDE0.tnVXDqzdrbm9PLfoMttZyBuL0uzr1dbySOwbruch4a4g.ZyQBiTAwqe3DDruXKp5NyrP4wqpbzmM7UR10dD8B5KQg.JPEG/8a89645c136a15dbe15ce70525630cc835aa0e4c.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTM2/MDAxNzA2NTg4MzUzNzUw.u8ut73TSYvMh1EDBku5xLOqtY6QeEqmCxKenXbPKo5Qg.f0qOv-kr0yuREvjQtiwV9AsoR7HrdB_HILcMdRs64EMg.JPEG/3cy7du4n5rky38z207iu.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMzcg/MDAxNzA2NTg4MzUzODI5.wHVggCZjQXHxxJbogMkC8b_bgyM3_2IpyLuIIvE3jdMg.DbSl3ZC2s3e-YmkUZ6yljB_I-Vns9XpjYd41P3JqFsYg.JPEG/fba12b9529d04f1d9e44b134a3d2fd6eb234be74.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTQ2/MDAxNzA2NTg4MzUzNjg3.CVn_9Q6znbgaWq8yTSnQMyqY_UNCDcxsgtJBoqiFjwog.DiRcun03d7Jtjwdp21blmsIjaNcZAMZXXWMly3x64ocg.JPEG/DktqMi3U8AAMjGA.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMTg4/MDAxNzA2NTg4MzUzNzUx.5zKpsYHCu5Ytxr9QjkAqf-6IytgeLndBZ7wPd3AYXS4g.eouiZGK32ErX8JT7fEcd6XzZ1sKLkOVphOeJicDLA3Mg.JPEG/b84e4a32f0afead898e9b20beef5723107aa8dbb.jpg?type=w1200",
                        "https://post-phinf.pstatic.net/MjAyNDAxMzBfMSAg/MDAxNzA2NTg4MzUzNDM2.Yiw9kxwOcD6ZhQtAk6DU4lF3huLBnCuKOeLdveRrFosg.wN9bGkTIFVLyITOPTPiIcmp8ZqEAg0S59fOsCnTfV5Qg.JPEG/DktsLh2UwAATeKP.jpg?type=w1200",
                        "https://i.namu.wiki/i/p8f5LesuquLp-iZqt7celuMAnechji6RS0HwQzOsF5BBMBrikXUpTDS7OR8s7bQahBhpAlLeNzH-Uqu2ksatabCHq0LH7gwkDtM45Pjlug66aQ_z75OCEPg1WvuYGvdtSG9gHiXPi7uoGL8Tfezpfw.webp",
                        "https://i.namu.wiki/i/4BLiiPCZ592quXxgRmTRlKPilyQNk9a95Tjv929llJh2RGLo_1PcLqWC8FqFz5BcbuDqHwjCMQp9L4MnsAJ9vtHnhkeMcfzbW-EkHJxcL1z9R7l75dtOn8HzaVSRh46WvG1x5BZZlkQf3-io1oE1kw.webp",
                        "https://i.pinimg.com/originals/10/74/25/107425756bbfd5102867ed0f02c69095.jpg",
                        "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyNDAzMjhfMjUz%2FMDAxNzExNjE0NTk5MjU4.9TrZkp3PSvfb8A8NQ_7xNh-aGhNzK5qsnY0e56-JweYg.6MvoHZ8uu09D8MSrhcXiBcOTFeAE2mk8s8ht4xWhfewg.JPEG%2FKakaoTalk_20240328_165629663.jpg&type=sc960_832",
                    ),
                    false,
                    "서울특별시 강남구",
                    3,
                    "포메라니안",
                    "밤에 산책하는 거 좋아해요!",
                    "활발, 애교 많음"
                )
            ),
            ContactInfo(
                Owner(
                    2,
                    30,
                    "https://sitem.ssgcdn.com/87/70/47/item/1000026477087_i1_750.jpg",
                    false,
                    "홍길동",
                    "010-1111-1111"
                ),
                DogProfile(
                    2,
                    "두부",
                    mutableListOf(
                        "https://mblogthumb-phinf.pstatic.net/MjAyMjA4MjRfMTU0/MDAxNjYxMzIwMDgzODc1.NhSveasGmH8V8ukDCWxW4aqzb5IHQKmFNR65Q8dnziUg.pXICC5EkLUc6Xgk6MW6nKtDML-eBxjypZpeCVlWXISUg.JPEG.ages9090/KakaoTalk_20220824_140238973_09.jpg?type=w800",
                        "https://cdn.ngetnews.com/news/photo/202005/301734_12608_1735.jpg",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MjNfNjkg/MDAxNTk1NTAxMzU3OTU0.c3cNYzSUnzwwFof34Ljr958vYCcDUZUTAju5AcPHncMg.GBkzSNM1CUEEtSP4RWJIxQ27_Qpx3eDyhS1zNwD1_VIg.JPEG.hyousang/2.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMjA4MjRfMTcw/MDAxNjYxMzIwNjIzOTI0.AyumUzOQX2zU39ehbtaqzPK9cGsZ9FOYuvU-e28LKOgg.nhN3IDo5yGiRXXrQrgdSlxTVLC2_d6fRc7l8TgxrrwAg.JPEG.ages9090/KakaoTalk_20220824_140238973_18.jpg?type=w800",
                        "https://mblogthumb-phinf.pstatic.net/MjAyMDA3MjNfMjQ4/MDAxNTk1NTAxMzU3OTQ1.miqOYPqXHmTThFB_r8n_pKpONGTwo2ezLkI86APYhAEg.HqCvzWtPAtyEAm5Ak6hRvL5KERbbLMxzT1hGhMSq3uQg.JPEG.hyousang/1.jpg?type=w800",
                        "https://img.famtimes.co.kr/news/photo/202102/502199_3315_3651.png",
                        "https://marikennel1989.com/web/product/big/202012/0361c5c0fc08ea3096c626e001d6bc0f.jpg",
                        "https://blog.kakaocdn.net/dn/ekbnmV/btqMgtiqXni/DG9CE8ZEllV5rKlDgbSQok/img.jpg",
                        "https://image-notepet.akamaized.net/resize/620x-/seimage/20190325/b6c456f4f6bf5d2560c26a780b7c36b9.jpg",
                    ),
                    false,
                    "서울특별시 서초구",
                    7,
                    "비숑",
                    "밤에 산책하는 거 좋아해요!",
                    "활발, 애교 많음"
                )
            )
        )
    }
}