## 목적
서킷브레이커 상태 변화에 따른 알림, 매트릭 적재 + 모니터링

## 사용
- Resilience4j
- Spring Actuator
- Prometheus
- Grafana
- Notifier (Slack 등)

## 시작
- docker-compose로 Prometheus, Grafana 실행
```
  $ docker-compose up
```

## 플로우
1. 서킷브레이커 오픈 시
2. Spring Actuator로 상태 확인
3. 슬랙 알림
4. 그라파나 모니터링 확인

![무제-페이지-2 drawio](https://user-images.githubusercontent.com/76584547/214617305-7c5231c2-2a35-4ac8-abe5-9608d2409cad.png)



## 그라파나 템플릿

### resilience4j (서킷)
- https://github.com/resilience4j/resilience4j/blob/master/grafana_dashboard.json
- https://resilience4j.readme.io/docs/grafana-1
```
  - Open 된 서킷
  - Close 된 서킷
  - Half-Open 된 서킷
  - 서킷 별 Failure Rate
  - Call Rate
```

<img width="1937" alt="image" src="https://user-images.githubusercontent.com/76584547/213922999-681b6228-abd3-41b0-bb2a-a67d60f9b292.png">

<br/>

### Spring boot
- https://github.com/gurumee92/getting-started-prometheus/blob/master/src/part2/ch08/grafana/dashboard.json
```
 - 힙 사용량
 - CPU 사용량
 - 쓰레드
 - GC
 - Http Statistic (요청건, 요청 url)
 - 로그 레벨별 
```

<img width="1988" alt="image" src="https://user-images.githubusercontent.com/76584547/213922560-fdb69efb-404a-48a9-9826-f514b3458ef5.png">

<img width="1945" alt="image" src="https://user-images.githubusercontent.com/76584547/213922524-f266071f-a0af-47be-831c-ee7bd947607f.png">


<br/>

### JVM

- https://grafana.com/grafana/dashboards/4701-jvm-micrometer/
```
- Rate
- Error
- JVM
- 쓰레드 상태
- 로그 이벤트
- GC
```

<img width="1895" alt="image" src="https://user-images.githubusercontent.com/76584547/213923317-65ce070b-29b7-463d-9869-6873ce82ec14.png">

등등 히카리풀 모니터링 템플릿도 존재
