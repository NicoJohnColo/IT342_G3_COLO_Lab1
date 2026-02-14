# User Registration and Authentication App

## Project Description
This project is a User Registration and Authentication system that allows users to register, log in, and manage their profile. It contains:
- Backend: Spring Boot (Java)
- Database: MySQL
- Web frontend: React

The backend provides JWT-based authentication and protected endpoints; the frontend is a React app that uses the backend API.

## Technologies Used
- **Backend:** Spring Boot (Java 17), Spring Data JPA, Spring Security
- **Database:** MySQL
- **Web App:** React (Create React App)
- **Authentication:** JWT (JSON Web Tokens) + BCrypt password hashing
- **Version Control:** Git & GitHub

## Steps to Run Backend (Spring Boot)

Prerequisites:
- Java 17+ installed
- Maven installed
- MySQL server running (default host: `127.0.0.1`, port `3306`)

1. Clone the repository:
```bash
git clone https://github.com/NicoJohnColo/IT342_G3_COLO_Lab1.git
cd IT342_G3_COLO_Lab1/backend/mini-app
```

2. Create the database (use MySQL Workbench or CLI). Example SQL:
```sql
CREATE DATABASE IF NOT EXISTS mini_app CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mini_app;
```

3. Configure connection and secret in `src/main/resources/application.properties` (or set environment variables):
- `spring.datasource.url=jdbc:mysql://localhost:3306/mini_app`
- `spring.datasource.username=root`
- `spring.datasource.password=` (set your DB password)
- `app.jwtSecret=` (replace with a strong 32+ char secret)

4. Build and run the backend:
```bash
mvn clean package
mvn spring-boot:run
```

5. Verify the backend is running on http://localhost:8080 and test endpoints (examples below).

## Steps to Run Web App (React)

Prerequisites:
- Node.js (LTS) and npm

1. Install dependencies and start dev server:
```bash
cd web
npm install
npm start
```

2. The React dev server runs at http://localhost:3000 and is configured to proxy `/api` requests to the backend during development. If you run backend on another host/port, update the proxy in `web/package.json` or configure CORS on the backend.

## Steps to Run Mobile App (optional)

This repo does not include a finished mobile app, but here are quick starter steps for two common approaches.

- React Native (recommended for faster cross-platform development):
  1. Install Node.js, npm, and Expo CLI: `npm install -g expo-cli`.
  2. Create a new app (or use an existing RN project): `expo init mobile` and choose a template.
  3. In your React Native app, call the same backend endpoints (`/api/auth/register`, `/api/auth/login`, `/api/user/me`). Use the backend URL (e.g., `http://<your-host>:8080`) instead of relative `/api` when running on device/emulator.
  4. Start the Metro bundler: `expo start` and use the Expo Go app or emulator to run the app.

- Native Android (Kotlin):
  1. Use Android Studio to create a new Kotlin project.
  2. From Kotlin, make HTTP requests to the backend (e.g., using `OkHttp` or `Retrofit`) to call the same API endpoints.
  3. Remember to handle JWT storage securely on the device (SharedPreferences with encryption or Android Keystore) and use HTTPS in production.

Notes:
- When testing on a mobile device/emulator, replace `localhost` with your machine IP (e.g., `192.168.1.10`) so the device can reach the backend.
- Secure your backend with HTTPS before publishing a mobile app to production.

## API Endpoints (implemented)

- `POST /api/auth/register` — Register new user. JSON body: `{ "username", "email", "password", "firstName", "lastName" }`. Returns `{ "token": "<JWT>" }` on success.
- `POST /api/auth/login` — Login. JSON body: `{ "username", "password" }`. Returns `{ "token": "<JWT>" }` on success.
- `GET /api/user/me` — Protected. Requires header `Authorization: Bearer <JWT>`. Returns user profile fields.

## Quick curl examples

Register:
```bash
curl -X POST http://localhost:8080/api/auth/register \
   -H "Content-Type: application/json" \
   -d '{"username":"alice","email":"alice@example.com","password":"secret","firstName":"Alice","lastName":"A"}'
```

Login:
```bash
curl -X POST http://localhost:8080/api/auth/login \
   -H "Content-Type: application/json" \
   -d '{"username":"alice","password":"secret"}'
```

Get profile (replace `<JWT>`):
```bash
curl -H "Authorization: Bearer <JWT>" http://localhost:8080/api/user/me
```

## Notes & Next Steps
- Ensure `app.jwtSecret` is set to a secure value in production and not checked into source control.
- The backend currently uses `spring.jpa.hibernate.ddl-auto=update` so Hibernate will create/update the `users` table automatically; you can also run the provided SQL to create the DB manually.
- The project includes frontend components for Register, Login, Dashboard, and a protected route implementation.
