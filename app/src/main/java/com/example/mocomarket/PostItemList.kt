package com.example.mocomarket

object PostItemList {
    val postItemList = mutableListOf<PostItem>(
        PostItem(aIndex = 1, aIcon = R.drawable.sample1, aName = "산지 한달된 선풍기 팝니다", aIntro = "이사가서 필요가 없어졌어요 급하게 내놓습니다", aUserName = "대현동", aArea = "서울 서대문구 창천동", aPrice = 1000, aLike = 13, aChat = 25, press = false),
        PostItem(aIndex = 2, aIcon = R.drawable.sample2, aName = "김치냉장고", aIntro = "이사로인해 내놔요", aUserName = "안마담", aArea = "인천 계양구 귤현동", aPrice = 20000, aLike = 8, aChat = 285, press = false),
        PostItem(aIndex = 3, aIcon = R.drawable.sample3, aName = "샤넬 카드지갑", aIntro = "고퀄지갑이구요\n사용감이 있어서 싸게 내어둡니다", aUserName = "코코유", aArea = "서울 서대문구 창천동", aPrice = 10000, aLike = 23, aChat = 5, press = false),
        PostItem(aIndex = 4, aIcon = R.drawable.sample4, aName = "금고", aIntro = "금고\n \n떼서 가져가야함\n \n대우월드마크센텀\n \n미국이주관계로 싸게 팝니다", aUserName = "Nicole", aArea = "서울 서대문구 창천동", aPrice = 10000, aLike = 14, aChat = 17, press = true),
        PostItem(aIndex = 5, aIcon = R.drawable.sample5, aName = "갤럭시Z플립3 팝니다", aIntro = "갤럭시 Z플립3 그린 팝니다\n항시 케이스 씌워서 썻고 필름 한장챙겨드립니다\n화면에 살짝 스크래치난거 말고 크게 이상은없습니다!", aUserName = "절명", aArea = "연제구 연산제8동", aPrice = 150000, aLike = 22, aChat = 9, press = false),
        PostItem(aIndex = 6, aIcon = R.drawable.sample6, aName = "프라다 복조리백", aIntro = "이사가서 필요가 없어졌어요 급하게 내놓습니다", aUserName = "미니멀하게", aArea = "수원시 영통구 원천동", aPrice = 50000, aLike = 25, aChat = 16, press = false),
        PostItem(aIndex = 7, aIcon = R.drawable.sample7, aName = "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장", aIntro = "울산 동해바다뷰 60평 복층 펜트하우스 1일 숙박권\n(에어컨이 없기에 낮은 가격으로 변경했으며 8월 초 가장 더운날 다녀가신 분 경우 시원했다고 잘 지내다 가셨습니다)\n1. 인원: 6명 기준입니다. 1인 10,000원 추가요금\n2. 장소: 북구 블루마시티, 32-33층\n3. 취사도구, 침구류, 세면도구, 드라이기 2개, 선풍기 4대 구비\n4. 예약방법: 예약금 50,000원 하시면 저희는 명함을 드리며 입실 오전 잔금 입금하시면 저희는 동.호수를 알려드리며 고객님은 예약자분 신분증 앞면 주민번호 뒷자리 가리시거나 지우시고 문자로 보내주시면 저희는 카드키를 우편함에 놓아 둡니다.\n5. 33층 옥상 야외 테라스 있음, 가스버너 있음\n6. 고기 굽기 가능\n7. 입실 오후 3시, 오전 11시 퇴실, 정리, 정돈 , 밸브 잠금 부탁드립니다.\n8. 층간소음 주의 부탁드립니다.\n9. 방3개, 화장실3개, 비데 3개\n10. 저희 집안이 쓰는 별장입니다.", aUserName = "굿리치", aArea = "남구 옥동", aPrice = 150000, aLike = 142, aChat = 54, press = false),
        PostItem(aIndex = 8, aIcon = R.drawable.sample8, aName = "샤넬 탑핸들 가방", aIntro = "샤넬 트랜디 CC 탑핸들 스몰 램스킨 블랙 금장 플랩백 ! \n \n색상 : 블랙\n사이즈 : 25.5cm * 17.5cm * 8cm \n구성 : 본품,더스트 \n \n급하게 돈이 필요해서 팝니다 ㅠ ㅠ", aUserName = "난쉽", aArea = "동래구 온천제2동", aPrice = 180000, aLike = 31, aChat = 7, press = false),
        PostItem(aIndex = 9, aIcon = R.drawable.sample9, aName = "4행정 엔진분무기 판매합니다.", aIntro = "3년전에 사서 한번 사용하고 그대로 둔 상태입니다. 요즘 사용은 안해봤습니다. 그래서 저렴하게 내 놓습니다. 중고라 반품은 어렵습니다.\\n", aUserName = "알뜰한", aArea = "원주시 명륜2동", aPrice = 30000, aLike = 7, aChat = 28, press = false),
        PostItem(aIndex = 10, aIcon = R.drawable.sample10, aName = "셀린느 버킷 가방", aIntro = "22년 신세계 대전 구매입니당\n \n 셀린느 버킷백\n \n구매해서 몇번사용했어요\n까짐 스크래치 없습니다.\n타지역에서 보내는거라 택배로 진행합니당!", aUserName = "똑태현", aArea = "중구 동화동", aPrice = 190000, aLike = 40, aChat = 6, press = false)
    )

    fun pushLike(aIndex: Int): Boolean {
        val post = postItemList.find { it.aIndex == aIndex }
        post?.let {
            if (it.press) {
                it.press = false
                it.aLike--
                return false
            } else {
                it.press = true
                it.aLike++
            }
        }
        return true
    }
}