<h2>Spring Boot Docker Image</h2>

**[Docker image Environment]**
- Docker image
- Language : Java
- Framework : Spring Boot(Gradle)
- View template : Thymeleaf (추가예정)
- Access DB : JPA (추가예정)
<br><br><br>

<h3> Ver 1.0 : 간단한 스프링 부트 이미지</h3>

- Docker Image : `docker pull coji68/web-server:1.0`<br>

<h3> Ver 1.1 : 스프링 부트 도커 이미지를 이용한 통신(현재 git에 올라와있는게 Ver 1.1이며 Docker Image는 따로 올리겠습니다.)</h3>

- Docker Image(Server) : `docker pull coji68/web-server:1.1`<br>
- Client : `git clone https://github.com/YongBhin-Kim/WebServer.git` <br>
<br><br>


<h3>1. Docker Image 다운 및 실행 방법</h3> <br>

**[Spring Boot Docker image (Server) 실행하기]** <br>
1. Docker image 다운 <br>
- Docker image 링크 - https://hub.docker.com/r/coji68/web-server/tags <br>
- 터미널에 명령어 `docker pull coji68/web-server:ver1.x` 을 입력한다. <br>
<img width="800" height="30" alt="image" src="https://user-images.githubusercontent.com/98372474/166339548-7a06b54f-304e-4016-8d87-5a3c66c9c7f9.png"> <br>
2. 다운받은 image 확인 명령어 : `docker images` <br>
<img width="800" height="50" alt="image" src="https://user-images.githubusercontent.com/98372474/166339612-af0c191b-e070-455e-a3a2-55cbe5c2ce85.png"> <br>
3. 다운받은 image를 컨테이너 생성과 동시에 실행 `docker run -p 8080:8080 coji68/web-server:1.0` <br>

- Docker Image 실행 명령어 <br>
`docker run -p [브라우저포트번호][컨테이너포트번호] -t [레포지이름/이미지이름:태그]` <br><br>

<img width="800" height="40" alt="image" src="https://user-images.githubusercontent.com/98372474/166337688-b2a4e403-d952-4773-842b-ec4349bf4cfa.png"> <br>



- 명령어 실행 결과 Spring이 정상 작동<br>
<img width="500" height="250" alt="image" src="https://user-images.githubusercontent.com/98372474/166337984-dd53531a-1544-418a-9091-6c83e8d8f386.png">

- 브라우저를 통해 localhost로 접속한 결과 <br>
<img width="500" height="250" alt="image" src="https://user-images.githubusercontent.com/98372474/166337952-26e86920-adf7-4ed7-9ce5-8c0130c6d516.png"><br><br>


<h3>2. Server-Client 통신</h3> <br>

**[통신을 위한 Client]** <br>
- git clone한 Client.java 파일을 컴파일한다. `java Client.java`
- 다음과 같이 Server-Client가 통신이 가능하다.(왼쪽 : Client / 오른쪽 : Server) <br>

<img width="1340" alt="image" src="https://user-images.githubusercontent.com/98372474/167169077-e27e5564-705c-49d2-9243-fc14b9234eee.png"> <br><br>

<br><br><br>
  




------------------------------------------------------------------------------------------------------------------------------
<h3>[Spring Boot Docker image 만들기]</h3> <br>

- 먼저 Docker Desktop을 실행합니다.

- Spring Boot 코드 clone, SNAPSHOT 제대로 동작 하는지 체크
```
git clone https://github.com/spring-guides/gs-spring-boot-docker.git // spring image clone

./gradlew build && java -jar build/libs/initial-0.0.1-SNAPSHOT.jar // SNAPSHOT check

```
<img width="400" height="280" alt="image" src="https://user-images.githubusercontent.com/98372474/166336173-c5f9382b-d004-44e1-a95e-1427e35b16ff.png"> <br>


- [Dockerfile 생성]

-Docker Image를 만들기 위해 Dockerfile이 필요하다. gradlew와 동등한 위치 `.../gs-spring-boot-docker/initial/Dockerfile` 에 만들어준다.

```
FROM openjdk:8-jdk-alpine // openjdk 사용
ARG JAR_FILE=target/*.jar // SNAPSHOT 사용
COPY ${JAR_FILE} app.jar 
ENTRYPOINT ["java","-jar","/app.jar"]
```
<br>

- [Spring boot Docker Image 생성]<br>

- Spring Boot Docker Image 생성 명령어는 다음과 같습니다.

`docker build --build-arg JAR_FILE=[jar파일 경로] -t [도커레포지토리:태그] [Dockerfile위치](.은 현재디렉토리)` <br>
<img width="900" height="20" alt="image" src="https://user-images.githubusercontent.com/98372474/166337129-e494bf25-8913-41b9-933a-c4f5ee2e5e62.png"> <br>

<img width="1370" alt="image" src="https://user-images.githubusercontent.com/98372474/167170931-856ab12d-58d1-425f-a47b-6c4aff755353.png">


- `docker images` 명령으로 생성된 이미지 확인 <br>

<img width="1002" alt="image" src="https://user-images.githubusercontent.com/98372474/167173172-f1ea37b2-c369-4b10-830e-11496fd6a9e7.png"> <br>



- docker image 삭제 명령어 : `docker image rm [Image ID]` <br>

