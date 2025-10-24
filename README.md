<h1 align="center">📚 KD BookStore Project</h1>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange?logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Database-Oracle-red?logo=oracle&logoColor=white"/>
  <img src="https://img.shields.io/badge/Frontend-HTML5%2C%20CSS3%2C%20JS-blue"/>
  <img src="https://img.shields.io/badge/Tools-Eclipse%2C%20GitHub%2C%20AWS-lightgrey"/>
</p>

<p align="center">
  <b>온라인 서점 플랫폼 | 사용자 & 관리자 통합 관리 시스템</b><br/>
  팀장 👨‍💻 안준석 &nbsp;&nbsp; 팀원 👨‍💻 차광원
</p>

---

## 🧭 목차
- [프로젝트 개요](#-프로젝트-개요)
- [개발 환경 및 도구](#-개발-환경-및-도구)
- [프로젝트 상세](#-프로젝트-상세)
- [DB 설계](#-db-설계)
- [Spring Boot 작업내용](#-spring-boot-작업내용)
- [프로젝트 자체평가](#-프로젝트-자체평가)
- [프로젝트 시연](#-프로젝트-시연)
- [Contact](#-contact)

---

## 📘 프로젝트 개요

> **KD BookStore**는 사용자가 책을 검색, 주문, 결제하고, 관리자가 회원 및 주문을 효율적으로 관리할 수 있는  
> 통합형 온라인 서점 시스템입니다.

### 🎯 개발 목적
- 온라인 도서 판매 및 관리 자동화
- 사용자 경험(UX) 향상
- 관리자 편의성을 위한 통계 및 카테고리 자동화

---

## 🧰 개발 환경 및 도구

| 구분 | 내용 |
|------|------|
| **Frontend** | HTML5, CSS3, JavaScript, jQuery |
| **Backend** | Java (Spring Boot Framework) |
| **Database** | Oracle Database |
| **Tools** | Eclipse, DBeaver, GitHub, Git Bash |
| **AI 활용** | ChatGPT, Gemini, Cursor |
| **배포환경** | AWS (EC2, RDS) |

---

## 🧩 프로젝트 상세

### 👤 사용자단 (개발: 안준석)
- 📖 사용자 친화적인 도서 목록 UI
- 💳 **카카오 로그인 / 카카오페이 결제 모듈** 연동
- 📧 **실시간 주문 메일 자동 발송**
- 📱 반응형 웹 디자인 적용 (모바일 지원)

### 🛠 관리자단 (개발: 차광원)
- 👥 **회원 / 상품 / 주문 / 후기 관리**
- 📊 **통계 대시보드 시각화 (Chart.js, Recharts 등)**
- 🧩 **카테고리 관리 자동화**
- 🔐 **관리자 전용 접근 권한 및 보안 설정**

---

## 🗃 DB 설계

> 주요 테이블 구조

| 테이블 | 설명 |
|--------|------|
| MEMBER | 회원 정보 저장 |
| BOOK | 도서 정보 관리 |
| ORDER | 주문 내역 |
| REVIEW | 후기 관리 |
| CATEGORY | 카테고리 자동화 관리 |

📎 ERD 및 구조도는 `/docs/erd.png` 참고  
(또는 추후 ERD 이미지 추가)

---

## ⚙️ Spring Boot 작업내용

- MVC 아키텍처 기반 설계  
- REST API + JSON 데이터 송수신  
- Controller, Service, DAO 계층 분리  
- 이메일, 결제 API 연동  
- 예외처리 / 로그 / 트랜잭션 관리 구축  

---

## 🧾 프로젝트 자체평가

### ✅ 성과
- 사용자 및 관리자 핵심 기능 안정적 구현  
- GitHub를 통한 **협업 및 형상관리** 경험  
- AWS를 이용한 **실제 서버 배포** 성공  

### 💡 배운점
- Spring Boot의 내부 구조 및 DI, MVC 패턴 이해  
- REST API 설계 및 JSON 기반 데이터 흐름 구현  
- 협업 시 Git 브랜치 전략 및 병합 충돌 해결 경험  

---

## 🎬 프로젝트 시연

> 시연 영상 및 스크린샷은 `/docs/demo/` 폴더에 추가 예정입니다.  
> 아래는 예시 UI 미리보기 섹션입니다 👇

<p align="center">
  <img src="https://via.placeholder.com/800x400?text=User+Main+Page+Preview" width="80%"/>
</p>

---

## 🏷 라이선스
본 프로젝트는 **학습 및 포트폴리오 용도**로 제작되었습니다.  
무단 복제 및 상업적 이용을 금합니다.

---

## 💬 Contact
📧 이메일: kdbookstore.team@gmail.com  
📍 KD 아카데미 IT전문학원 구리점  
🌐 GitHub Repository: [https://github.com/kdbookstore-team/KD-BookStore](https://github.com/kdbookstore-team/KD-BookStore)

---

<p align="center">
  <b>💡 KD BookStore — Better Reading, Smarter Management 📚</b>
</p>
