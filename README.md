# offer-project

## 거래제안 게시판

### 내용

타 기업에서 자사 서비스에 직접 거래를 제안하는 게시판

---

### 예시

제안자(기업A)가 자사 서비스 스낵24에 기업A의 제품 판매를 제안하는 글을 게시하면

관리자가 해당 제안글에 대한 답변을 진행

---

### 권한 설정

제안자(일반 사용자)  - 제안글 작성, 수정, 상세확인, 삭제

답변자(관리자)         - 제안글에 대한 답변 작성, 수정,상세 확인

---

### 기능 정의

- 전체 리스트 ( 검색 추가- 제목, 내용, 회사, 서비스 종류 ) - 날짜 내림차순 정렬
- 제안글 등록 : 서비스 종류, 제목, 내용, 회사명, 작성자명, 직급, 연락처, 첨부파일
- 제안글 수정 : 동일
- 제안글 삭제
- 제안글 상세 - 답변과 함께 표기
- 답변 등록 : 답변 내용
- 답변 수정 : 동일
- 답변 삭제

    🛑  **추가정보**

    - 모든 수정과 삭제는 등록자만 가능하도록 설정 / 열람은 등록자와 관리자가 가능

---

### API endpoint

| URL               | 실행 작업     | Method Type | Request      | Response     |
|:------------------|:------------|:------------|:-------------|:-------------|
| /offer/list       | 목록 조회(+ 검색)| GET | SearchCondition | List<OfferResponseDto> |
| /offer/           | 제안글 등록 | POST | OfferCreateRequestDto |       |
| /offer/{offerId}  | 제안글 수정 | PUT  | OfferUpdateRequestDto, offerId |       |
| /offer/{offerId}  | 제안글 삭제 | DELETE | offertId |       |
| /offer/{offerId}  | 제안글 상세(+답변상세) | GET | offertId | OfferResponseDto |
| /answer/          | 답변 등록 | POST | AnswerCreateDto |       |
| /answer/{answerId}| 답변 수정 | PUT  | AnswerUpdateDto,answerId |       |
| /answer/{answerId}| 답변 삭제 | DELETE | answerId |       |


---

### 주요 엔티티

- 사용자 (User)  - 일반, 관리자 ROLE로 분리
- 제안 (Offer)
- 답변 (Answer)
- 파일 ( File )

---

### 연관관계

- 제안 - 답변 (1:1)
- 제안 - 파일 (1:N)
- 사용자(일반) - 게시글 (1:N)
- 사용자(관리자) - 답변 (1:N)

---

### 엔티티 구조
<img width="300" alt="엔티티구조" src="https://user-images.githubusercontent.com/88015037/127769272-d729da1f-c780-4d0e-af96-b805bdef8d20.jpg">
