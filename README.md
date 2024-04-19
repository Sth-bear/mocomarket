기존의 코드를 ViewModel과 LiveData를 활용하는 방식으로 변경에 있습니다. 해당단계는 Lv3이 작동하지 않습니다.
다음과 같은 문제가 있습니다.

Detail페이지에서 좋아요 처리시 PostViewModel에서 함수를 사용하여 _postItemLiveData를 변경시키고 이를 적용시키기 위해 Lobby에 옵저버를 달아놓은 상태입니다. 하지만 정상작동하지 않습니다.
