# Link Vault - Windows Desktop Setup Guide

Welcome to the Windows Desktop version of Link Vault!

## 🚀 How to Run
1. Ensure you have **Java 17** or newer installed.
1. Open the project folder in **IntelliJ IDEA** (Recommended for Compose Multiplatform).
2. Open the Gradle tab and run: `app > Tasks > compose desktop > run`.
   - Or run via command line: `./gradlew :app:run`

## 📦 Features (Windows Native)
- **Side Navigation**: Modern Windows 11 style side rail.
- **Link Scraping**: Automatically extracts metadata from URLs.
- **Offline Reader**: Read your articles without an internet connection.
- **Modern Notes App**: Create, edit, and pin notes with a fast, grid-based interface.
- **Primary Recovery Folder**: Choose a custom path during setup to store your primary data backups.
- **Feedback Form**: Submit problems or suggestions directly to the developer.
- **Encrypted Vault**: Secure your links and notes with AES-GCM security.
- **Search**: Fast Full-Text Search across all links.

## 🛠 Project Structure
- `app/src/commonMain`: Shared logic and UI (Cross-platform).
- `app/src/desktopMain`: Windows-specific entry point and SQLite config.

---
Developed by: **VINAYAK KUMAR**
Contact: codesharecommunityonly1@gmail.com
