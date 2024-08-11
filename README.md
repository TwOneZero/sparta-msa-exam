# MSA_EXAM_CH1
- 기본적인 MSA 프로젝트를 구성
  - Eureka, Ribborn 을 이용한 분산처리 시스템 구성
    - 같은 서비스의 인스턴스를 여러 개 실행하여 라운드로빈으로 로드밸런싱 동작
  - ZipKin 으로 분산추적
- Redis 캐싱
  - 상품 생성 및 조회 캐싱
  - 주문 조회 캐싱
- MSA 에서의 인증/인가
  - Gateway 서비스에 filter 를 작성하여 처리
---

## 실행
- MySql 은 로컬로 실행 (필요하다면 server 패키지의 docker-compose.yml 에 추가)
- git clone 후 `server` folder 열기
  - .idea 가 포함되어 있으므로 다른 프로젝트와 gradle 링크됨
> intellij 의 서비스탭에 모든 서비스가 뜨지 않을 경우 gradle relaod 해주면 해결됨.


### Redis & Zipkin
> Open terminal
```bash
$ cd com.sparta.msa_exam.server
$ docker compose up
```

---

## 프로젝트 구성도
![image](https://github.com/user-attachments/assets/8c3d4bb6-9337-4fc7-83cb-dee1e34d892a)
