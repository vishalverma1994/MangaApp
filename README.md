# 🌟 Manga App

**Manga** is a modern Android app built with **Jetpack Compose** and a robust **Clean Architecture (MVVM)** pattern. It blends user authentication, offline-capable manga browsing, and real-time face detection using the device’s front camera.

---

## 📱 Features

### 🔐 User Authentication
- Sign in using email and password.
- Auto-register and log in new users.
- Session persistence using Room and DataStore.

### 📚 Manga Browsing
- Fetch manga from the [MangaVerse API](https://rapidapi.com/sagararofie/api/mangaverse-api).
- Grid layout with 3 columns for image thumbnails.
- Pagination support with **Paging 3**.
- Offline caching using Room Database.
- Manga detail screen on thumbnail tap.

### 🧠 Real-Time Face Detection
- CameraX live preview using front camera.
- Integrated with **MediaPipe** for face detection.
- Visual reference box turns green when face is inside, red otherwise.

---

## 🧰 Tech Stack

| Layer                 | Tools / Libraries                          |
|----------------------|--------------------------------------------|
| UI                   | Jetpack Compose, Coil                      |
| Architecture         | MVVM + Clean Architecture                  |
| Navigation           | Jetpack Navigation Component (Single Activity) |
| Network              | Retrofit + OkHttp                          |
| Local Storage        | Room DB + DataStore                        |
| Pagination           | Paging 3                                   |
| Face Detection       | MediaPipe Tasks-Vision + CameraX           |
| Dependency Injection | Dagger Hilt                                |
| Language             | Kotlin                                     |

---

## 📂 Project Structure
- `presentation/` – UI Screens (Compose), Navigation
- `domain/` – Use cases, Entities, Repositories
- `data/` – API, Room DB, Mappers, Data Sources
- `di/` – Dagger Hilt Modules

---


