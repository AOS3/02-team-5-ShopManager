package com.lion.five.shopmanager.data

import com.lion.five.shopmanager.data.model.Product

object Storage {
    val products = listOf(
        Product(
            name = "그대들은 어떻게 살 것인가 특별 구성판 Blu-ray(2장 묶음)",
            price = 126500,
            type = "지브리",
            description = "스튜디오 지브리 최신작 <그대들은 어떻게 살 것인가>와 다큐멘터리 <미야자키 하야오와 왜가리 ~ 「그대들은 어떻게 살 것인가」로 가는길>이 세트로 구성된 특별 구성판 Blu-ray입니다.",
            images = listOf("ghibli_1.jpg", "ghibli_2.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "지브리 Movie Collection 포스터 & 팜플렛 「이웃집 토토로」 세트",
            price = 25000,
            type = "지브리",
            description = "스튜디오 지브리 장편 작품 포스터 & 팜플렛이 복귀!\n" +
                    "이웃집 토토로 포스터 1장과 팜플렛 1권 세트입니다.\n" +
                    "팜플렛은 극장 개봉시에 발매된 상태 그대로 복귀!\n" +
                    "부디 이 기회를 놓치지 마세요.",
            images = listOf("ghibli_3.jpg", "ghibli_4.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "지브리 Movie Collection 포스터 & 팜플렛 「센과 치히로의 행방불명」 세트",
            price = 50000,
            type = "지브리",
            description = "스튜디오 지브리 장편 작품 포스터 & 팜플렛이 복귀!\n" +
                    "센과 치히로의 행방불명 포스터 2장과 팜플렛 1권 세트입니다.\n" +
                    "팜플렛은 극장 개봉시에 발매된 상태 그대로 복귀!\n부디 이 기회를 놓치지 마세요.",
            images = listOf("ghibli_5.jpg", "ghibli_6.jpg", "ghibli_7.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "그림 콘티 벼랑 위의 포뇨",
            price = 40000,
            type = "지브리",
            description = "영화 「벼랑 위의 포뇨」의 그림 콘티집입니다.\n" +
                    "지문이나 대사를 꼼꼼히 따라가다 보면, 다양한 세부 사항과 연출 의도를 발견할 수 있어 영화를 더욱 깊이 즐길 수 있습니다.",
            images = listOf("ghibli_8.jpg", "ghibli_9.jpg", "ghibli_10.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "지브리 미술관 오리지널 토토로 인형 세트",
            price = 120000,
            type = "지브리",
            description = "손님의 손에 쥐어지는 모습을 상상하며 장인이 하나하나 정성스럽게 제작한, 지브리 미술관 오리지널 토토로 봉제 인형 세트 입니다.",
            images = listOf("ghibli_11.jpg", "ghibli_12.jpg"),
            stock = 0,
            isBest = true,
            reviewCount = 0,
        ),
        Product(
            name = "[MCS]마블 어벤저스 2024 캘린더(포스터형)",
            price = 19000,
            type = "마블",
            description = "달력의 쓰임에 충실하면서도 유용하게 사용될 수 있도록 만들었습니다.\n" +
                    "+멀리서도 잘보이는 시안성\n" +
                    "+한눈에 보이는 공휴일, 대체휴일 표기\n" +
                    "+분위기를 살려주는 감성소품 오케이!",
            images = listOf("marvel_1.jpg", "marvel_2.jpg", "marvel_3.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "INFINITY SAGA 포스터 세트",
            price = 45000,
            type = "마블",
            description = "INFINITY SAGA(MARVEL)의 포스터(Movie)입니다.\n" +
                    "MCU의 역대 영화 포스터 22매 세트!\n" +
                    "포스터는 B4사이즈입니다.",
            images = listOf("marvel_4.jpg", "marvel_5.jpg"),
            stock = 0,
            isBest = true,
            reviewCount = 0,
        ),
        Product(
            name = "INFINITY SAGA 체인징 카드 컬렉션(VOL.2)",
            price = 6000,
            type = "마블",
            description = "INFINITY SAGA(MARVEL)의 체인징 카드 컬렉션(VOL.2)입니다.\n" +
                    "보는 각도에 따라 그림이 다르게 보입니다.\n" +
                    "전 10종류 중, 랜덤으로 1종류가 들어있습니다.",
            images = listOf("marvel_6.jpg", "marvel_7.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "INFINITY SAGA 체인징 카드 컬렉션(VOL.1)",
            price = 6000,
            type = "마블",
            description = "INFINITY SAGA(MARVEL)의 체인징 카드 컬렉션(VOL.1)입니다.\n" +
                    "보는 각도에 따라 그림이 다르게 보입니다.\n" +
                    "전 10종류 중, 랜덤으로 1종류가 들어있습니다.",
            images = listOf("marvel_8.jpg", "marvel_9.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "베놈 라스트댄스 티셔츠(검정)",
            price = 48000,
            type = "마블",
            description = "베놈 라스트 댄스의 티셔츠(L사이즈)입니다.\n" +
                    "뒤쪽에도 디자인 있습니다.",
            images = listOf("marvel_10.jpg", "marvel_11.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "인사이드 아웃2 W포켓클리어파일",
            price = 5000,
            type = "픽사",
            description = "인사이드 아웃2의 W포켓 클리어파일입니다.",
            images = listOf("pixar_1.jpg", "pixar_2.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "PIXAR 아트카드 컬렉션 ※전19종",
            price = 5000,
            type = "픽사",
            description = "PIXAR의 아트카드 컬렉션입니다.\n" +
                    "엠포스 재질로 두툼한 아트카드!\n" +
                    "1매에 전19종 중 랜덤으로 1점이 들어있습니다.",
            images = listOf("pixar_3.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "버즈 라이트이어 도킹 피규어 토이스토리4",
            price = 34000,
            type = "픽사",
            description = "토이스토리의 버즈 라이트이어의 도킹 피규어가 등장!\n" +
                    "인기캐릭터 버즈 라이트이어.\n" +
                    "가슴의 위치에 있는 3종류의 버튼을 누르면 영어로 말합니다.\n" +
                    "붉은 버튼을 누르면 등쪽의 날개가 열리고 깜박이는 등 여러가지 기믹을 탑재!\n" +
                    "게다가, 같은 시리즈의 특정 캐릭터에 가까이 하면, 같이 이야기하는 기능도 있습니다.\n" +
                    "동료가 늘어나면 즐거움이 배가 됩니다.\n" +
                    "장난감으로는 물론, 방을 꾸이는 인테리어로 선물하는 데에도 적합한 아이템입니다.",
            images = listOf("pixar_4.jpg", "pixar_5.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "PIXAR UP 컬러포스터카드",
            price = 1500,
            type = "픽사",
            description = "PIXAR 캐릭터의 귀여운 포스터카드가 등장!\n" +
                    "친한 친구나 지인, 가족들에가 편지를 줄때뿐만아니라, 인테리어나 꾸미기 소품으로도 추천!\n" +
                    "PIXAR캐릭터의 팬인 분들이 꼭 보셔야할 카드입니다.",
            images = listOf("pixar_6.jpg", "pixar_7.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "토이스토리 티셔츠",
            price = 30000,
            type = "픽사",
            description = "영화 토이스토리의 로고가 앞면에 크게 프린트되어 있는 티셔츠입니다.\n" +
                    "뒷면에는 우디나 버즈를 시작으로 캐릭터들이 대집합!",
            images = listOf("pixar_8.jpg", "pixar_9.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "디즈니 캐릭터 포스터 카드",
            price = 1500,
            type = "디즈니",
            description = "디즈니 캐릭터의 귀여운 포스터카드가 등장!\n" +
                    "친한 친구나 지인, 가족들에가 편지를 줄때뿐만아니라, 인테리어나 꾸미기 소품으로도 추천!\n" +
                    "디즈니 캐릭터의 팬인 분들이 꼭 보셔야할 카드입니다.",
            images = listOf("disney_1.jpg", "disney_2.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "인어공주 홀로그램 클리어파일",
            price = 5000,
            type = "디즈니",
            description = "인어공주의 홀로그램 형태의 클리어 파일입니다.",
            images = listOf("disney_3.jpg", "disney_4.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "라이온 킹 직소 퍼즐 캔버스 스타일 1000피스 스페셜 아트 컬렉션",
            price = 45000,
            type = "디즈니",
            description = "〈스페셜 아트 컬렉션〉시리즈\n" +
                    "아트의 터치를 살리기 위해서, 퍼즐의 표면에 요철을 두어, 회화에서 이용하는 '캔버스'와 같은 가공을 한 <캔버스 스타일>로 준비했습니다. \n" +
                    "퍼즐 완성 후에는, 별도 판매의 전용 패널에 넣어 장식함으로써, 방을 멋지게 꾸미는 인테리어로도 즐길 수 있습니다!",
            images = listOf("disney_5.jpg", "disney_6.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "초콜릿 캔 미키 마우스와 친구들",
            price = 9000,
            type = "디즈니",
            description = "초콜릿의 맛은 우유, 스위트, 화이트 3종류입니다.\n" +
                    "개별 포장에는 미키와 미니, 미구엘, 엘사가 그려져 있어 설레는 디자인.\n" +
                    "매일의 간식으로도, 친구나 직장에 가는 선물로도 추천합니다.\n" +
                    "다 먹은 후에는 소품을 넣거나 방에 장식해도 즐길 수 있을 것 같네요.",
            images = listOf("disney_7.jpg", "disney_8.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "모아나 MovieNEX",
            price = 68000,
            type = "디즈니",
            description = "저 파도 너머로 나가자.\n" +
                    "그녀의 이름은 모아나. \n" +
                    "바다를 사랑하고 바다의 사랑을 받은 소녀.\n" +
                    "고민하면서도, 자신의 마음의 소리를 믿고 한결같이 나아가는 소녀 모아나의 모습에 용기와 기운을 받을 수 있는, 압권의 노래와 영상으로 보내는 감동의 판타지 어드벤처.",
            images = listOf("disney_9.jpg", "disney_10.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "2023년 세컨드 에디션 컬렉터 지팡이",
            price = 75000,
            type = "해리포터",
            description = "해리포터 마법의 세계의 컬렉터 에디션 지팡이.",
            images = listOf("harrypotter_1.jpg", "harrypotter_2.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "해리포터 어드벤트 캘린더 2023 악세사리 오너먼트 Christmas 호그와트 선물",
            price = 66000,
            type = "해리포터",
            description = "해리포터의 마법을 삼가해보세요.\n" +
                    "모든 팰들을 기쁘게 하기 위해 각각 HarryPotter 및 Wizarding World 관련 선물을 보여주는 24개의 창이 포함되어 있습니다.\n" +
                    "아름다운 문구류, 주얼리, 다양한 액세서리를 만나보세요!",
            images = listOf("harrypotter_3.jpg", "harrypotter_4.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "Golden Snitch Pin",
            price = 12000,
            type = "해리포터",
            description = "해리 포터의 마법의 세계에서 온 황금 스니치 핀",
            images = listOf("harrypotter_5.jpg", "harrypotter_6.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "호그와트 성 조각",
            price = 350000,
            type = "해리포터",
            description = "저 파도 너머로 나가자.\n" +
                    "그녀의 이름은 모아나. \n" +
                    "바다를 사랑하고 바다의 사랑을 받은 소녀.\n" +
                    "고민하면서도, 자신의 마음의 소리를 믿고 한결같이 나아가는 소녀 모아나의 모습에 용기와 기운을 받을 수 있는, 압권의 노래와 영상으로 보내는 감동의 판타지 어드벤처.",
            images = listOf("harrypotter_7.jpg", "harrypotter_8.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "해리포터 아크릴 마그넷 세트",
            price = 57000,
            type = "해리포터",
            description = "영화 해리포터(1~8편)를 아크릴 마그넷-공식 포스터 버전으로 모아보세요.",
            images = listOf("harrypotter_9.jpg", "harrypotter_10.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "캐치티니핑 봉제인형",
            price = 28000,
            type = "티니핑",
            description = "28cm 사이즈로 아이들의 품에 쏙 들어가는 웰메이드의 인형" +
                    "보들보들 부드러운 원단으로 아이들 애착인형, 선물용으로 좋아요." +
                    "애니메이션을 그대로 구현한 하츄핑, 꾸래핑, 나나핑, 솔찌핑!",
            images = listOf("teenieping_1.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "캐치티니핑 서프라이즈 오뚝이",
            price = 2800,
            type = "티니핑",
            description = "24종의 티니핑 오뚝이를 모두 모아보세요!",
            images = listOf("teenieping_2.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "캐치티니핑 장난감-플로라하트 왕관세트",
            price = 24000,
            type = "티니핑",
            description = "머리띠처럼 편안하게 착!" +
                    "반짝반짝 하트 보석과 홀로그램으로 사랑스러움을 더한 왕관세트." +
                    "하트보석으로 멀리서도 반짝반짝!" +
                    "귀찌타입으로 귀에 집어도 아프지 않아요!" +
                    "뒷면에 집게가 있어 가볍게 톡!" +
                    "옷에 쉽게 걸 수 있어요~",
            images = listOf("teenieping_3.jpg"),
            stock = 0,
            reviewCount = 0,
        ), Product(
            name = "캐치티니핑 하우스",
            price = 43000,
            type = "티니핑",
            description = "예쁜 컴팩트를 열면 나나핑, 솔찌핑, 꾸래핑, 하츄핑의 하우스가 짜잔!",
            images = listOf("teenieping_4.jpg"),
            stock = 0,
            reviewCount = 0,
        ),
        Product(
            name = "캐치티니핑 서프라이즈 인형",
            price = 16000,
            type = "티니핑",
            description = "티니핑 8마리를 모두 모아보세요.",
            images = listOf("teenieping_5.jpg"),
            stock = 0,
            reviewCount = 0,
        )
    )
}
