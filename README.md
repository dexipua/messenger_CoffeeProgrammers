# Messenger

[![CI](https://github.com/dexipua/messenger_CoffeeProgrammers/actions/workflows/ci.yml/badge.svg)](https://github.com/dexipua/messenger_CoffeeProgrammers/actions/workflows/ci.yml)

Web messenger with real-time chats. Built by team **CoffeeProgrammers** for the All-Ukrainian Team Programming
Tournament 2024, where the team took **2nd place**.

## Overview

A simple messenger for chatting with your friends, relatives and other people you know. Users sign up with email
verification, add contacts, start chats and exchange messages in real time.

## Team and my role

Team: [Artem Moseichenko](https://github.com/dexipua) (team lead), [Vladyslav](https://github.com/Vladobrod),
[Yaroslav Holobyn](https://github.com/Yaroslav-Holobyn), [Vadym Honcharuk](https://github.com/VadymHoncharuk).

**Artem Moseichenko, team lead and full-stack developer** (about half of all commits):

- Planned the project, designed the architecture and database schema, split the tasks and reviewed code.
- Built security with Spring Security, JWT and sessions; worked with Vladyslav on email verification.
- Set up real-time messaging with WebSocket (STOMP).
- Wrote account, contact and chat REST controllers and services, and the Flyway migrations.
- Built frontend pages for registration, login verification and accounts, and the auth/account services in React.

## Key Roles and Functionalities

User:
- can write, read, update and delete 
messages in already exists chats
- add new chats with his contacts
- add new contacts
- see all users
- search for user by his name

## Technologies Used

- **Backend:** Java 22, Spring Boot 3.2 (Web, Data JPA, Security, Validation, WebSocket), JWT, JavaMail, MapStruct,
  Lombok, Flyway
- **Database:** H2
- **Frontend:** React 18, MUI, axios, STOMP / SockJS
- **Testing:** JUnit, Spring Boot Test, Instancio

## Installation Instructions

1. Clone the repository:

   ```
      git clone https://github.com/dexipua/messenger_CoffeeProgrammers.git
   ```

2. Navigate to the project directory:

   ```
      cd messenger_CoffeeProgrammers
   ```

3. Build the project:

   ```
      mvn clean install
   ```


## Usage Instructions

1. Start the backend server:

   ```
      mvn spring-boot:run
   ```

2. Navigate to the frontend directory:

   ```
      cd frontend
   ```

3. Before this if you have something on port 3000 use:

   ```
      npx kill-port 3000
   ```
4. Install dependencies and start the frontend server:

   ```
      npm install
      npm start
   ```

4. Open your browser and go to http://localhost:3000 to access the application.

---

## Contribution Guidelines

1. Fork the repository.
2. Create a new branch for your feature or bugfix:

   ```
      git checkout -b feature-name
   ```

3. Commit your changes:

   ```
      git commit -m "Description of your changes"
   ```

4. Push to your branch:

   ```
      git push origin feature-name
   ```

5. Create a pull request.

---

## Demo accounts

Demo video: https://youtu.be/Kank-xE7LG0

emails:
- am@gmail.com
- yh@gmail.com
- vb@gmail.com
- vh@gmail.com

password:
"passWord1" - one for all

code for verification is 12 for this accounts

Note: sending real verification emails needs your own SMTP credentials.