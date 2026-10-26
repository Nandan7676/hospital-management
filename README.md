# MeddiBuddy

A full-stack healthcare appointment booking app built with Java 17, Spring Boot, Spring Security, BCrypt, JWT, Spring Data JPA/Hibernate, PostgreSQL, Maven, and a React/Vite JavaScript frontend.

## Requirements

- Java 17 or later
- Maven 3.8 or later
- Node.js 20 or later and npm
- PostgreSQL installed and running locally

## Run on Windows

Open the project folder in VS Code. Open **Terminal → New Terminal** twice so you have two terminals.

### 1. Start PostgreSQL

Make sure the PostgreSQL Windows service is running. The backend launcher expects the PostgreSQL `postgres` user password to be `test`. If your password differs, edit `DB_PASSWORD` in `backend/run-backend.ps1` first.

### 2. Start the backend and create the database

In Terminal 1, run:

```powershell
cd "C:\Users\Tarunya H M\Documents\Codex\2026-10-24\build-meddibuddy-full-stack-healthcare-appointment-3\backend"
.\run-backend.cmd
```

The launcher checks PostgreSQL and creates the `meddibuddy` database if it does not exist. Spring Boot then creates or updates the database tables and seeds the local administrator account if it is not already present. Keep this terminal open while using the website.

### 3. Start the website

In Terminal 2, run:

```powershell
cd "C:\Users\Tarunya H M\Documents\Codex\2026-10-24\build-meddibuddy-full-stack-healthcare-appointment-3\frontend"
npm install
npm run dev
```

Open the Vite address, usually http://localhost:5173.

### 4. Sign in

- Create a patient account with **Get started**. Choose an email and password of at least 8 characters. The account is saved in PostgreSQL's `app_users` table, with the password stored as a BCrypt hash.
- Sign in as the local administrator with:
  - Email: `admin@meddibuddy.local`
  - Password: `Admin@MeddiBuddy2026`

The admin email is an app login name, not a Gmail inbox. Public sign-up only creates patient accounts. Admin credentials are local development credentials; change them before sharing or deployment.

After signing in as admin, choose **Manage doctors** in the navigation. Use the form to add a name, specialty, experience, consultation fee, location, photo identifier, and bio. The doctor is saved in PostgreSQL and appears in the public doctor directory. For a photo, choose an image file from your laptop in **Doctor photo**. JPG, PNG, WebP, and GIF files up to 10 MB are accepted. The backend saves image files under `backend/uploads` and stores the path in PostgreSQL. You can remove a profile from the directory there as well.

## Database and local settings

The PostgreSQL database name is `meddibuddy`; the launcher uses `jdbc:postgresql://localhost:5432/meddibuddy`, PostgreSQL user `postgres`, and password `test`. It requires `psql.exe` and `createdb.exe`; it checks the PATH and the standard PostgreSQL installation folder. PostgreSQL itself must already be installed and running. Hibernate creates the application tables automatically after the database is available.

To use H2 instead of PostgreSQL for development, set `$env:SPRING_PROFILES_ACTIVE='h2'` and run the backend with Maven. H2 uses a separate local database at `backend/data/meddibuddy-app`.

## API

- Swagger UI: http://localhost:8080/swagger-ui.html
- Public doctor listing: `GET /api/doctors`
- Account registration/login: `POST /api/auth/register`, `POST /api/auth/login`
- Patient appointments: `POST /api/appointments`, `GET /api/appointments/my`, `PATCH /api/appointments/{id}/cancel` (JWT required)
- Admin stats: `GET /api/admin/dashboard` (ADMIN role required)
- Doctor management: `GET`/`POST /api/admin/doctors`, `PUT`/`DELETE /api/admin/doctors/{id}` (ADMIN role required)

Sample doctor profiles and appointment slots are illustrative, not live hospital availability. Patient appointments sync with PostgreSQL through the backend and are mirrored in browser storage for the UI.




