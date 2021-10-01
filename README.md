# java_socket
> 소켓 프로그래밍을 알아보기 위한 리포지토리

### Socket 프로그래밍을 알아보기 앞서 기본적인 용어를 알아보자.

* 네트워크(Network)
    > 단말들이 서로 통신이 가능하도록 연결해주는 것

* 인터넷(Internet)
    > 여러 네트워크가 모여 이루어진 컴퓨터 네트워크 시스템

* IP(Internet Protocol)
    > 인터넷에서 컴퓨터의 위치를 찾아서 데이터를 전송하기 위해 지켜야 할 규약

* TCP(Transmission Control Protocol)
    > 소켓 프로그래밍 중의 하나로 스트림 통신 프로토콜이라고 부르며, 양쪽의 소켓이 연결된상태여야만 가능 하기 때문에 연결지향 프로토콜이라고도 한다.

* 패킷 
    1. 데이터를 일정한 크기로 자른 단위이자 인터넷에서 정보를 전달하는 단위
    2. 나누어진 패킷이 순서대로 돡한다는 보장은 없어 규칙이 필요함.

## 소켓이란?
> TCP 프로토콜을 사용하여 네트워크 상에서 서버-클라이언트 간의 양방향 통신이 가능하도록 만들어주는 소프트웨어 장치

![capture1](https://user-images.githubusercontent.com/63029576/128367306-3326b864-58d2-4109-b70d-3cccafe05002.jpg)

### 소켓의 특징
1. 통신을 하기 위해 IP / Port 번호를 필요로한다.
2. 네트워크의 끝 부분을 나타내며, 읽기/쓰기 인터페이스를 제공한다.
3. 네트워크 계층과 전송 계층이 캡슐화 되어 있기 때문에 두 개의 계층을 신경 쓰지 않고 프로그램을 만들 수 있다.
4. server와 client가 특정 port를 통해 지속적으로 연결유지 하여 실시간으로 양방향 통신을 할 수 있다.
5. 주로 채팅 같은 실시간성을 요구하는 서비스에서 많이 사용된다.

### 자바 소켓 프로그래밍
1. 소켓 프로그래밍 중의 하나. 스트림 통신 프로토콜.
2. 송신-수신시 차례대로, 연결된 순서대로 데이터를 교환해야 함(신뢰성이 높음).
3. 서버측에서 클라이언트의  접속을 대기하기 위해 `java.net.ServerSocket` 클래를 사용한다.
4. 서버-클라이언트 간의 통신을 위해 `java.net.Socket` 클래스를 사용한다.
5. `InputStream`, `OutputStream` 클래스를 통해 읽기/쓰기 인터페이스를 제공한다. 

### 자바 소켓 프로그래밍 실행 순서
![capture2](https://user-images.githubusercontent.com/63029576/128367519-606fc636-542e-479e-b8a0-d431bd4b4348.jpg)
