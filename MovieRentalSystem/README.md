# 🎬 CineRent — Movie Rental & Review System
### SE1020 Object Oriented Programming — Group Project

---

## Project Structure

```
MovieRentalSystem/
├── frontend/                  ← HTML/CSS/JS Frontend
│   ├── index.html             ← Main single-page application
│   ├── css/
│   │   └── style.css          ← All styles (cinema-noir theme)
│   └── js/
│       └── app.js             ← All frontend logic
│
├── backend/                   ← Java Spring Boot Backend (to be built)
│   └── src/main/java/com/cinerent/
│       ├── model/             ← Java model classes
│       ├── filehandler/       ← File I/O handlers
│       └── servlet/           ← JSP Servlets
│
└── data/                      ← Flat-file data storage
    ├── users.txt
    ├── movies.txt
    ├── rentals.txt
    ├── reviews.txt
    ├── payments.txt
    └── admins.txt
```

---

## How to Run Frontend

1. Open `frontend/index.html` in any modern browser
2. No server required — runs entirely in the browser
3. Data persists via `localStorage` (simulating .txt file storage)

---

## Modules / Components

| # | Component | Member | Data File |
|---|-----------|--------|-----------|
| 01 | User Management | Member 01 | users.txt |
| 02 | Movie Management | Member 02 | movies.txt |
| 03 | Rental Management | Member 03 | rentals.txt |
| 04 | Review & Rating | Member 04 | reviews.txt |
| 05 | Admin Management | Member 05 | admins.txt |
| 06 | Payment & Fines | Member 06 | payments.txt |

---

## Tech Stack

- **Frontend:** HTML5, CSS3, Vanilla JavaScript
- **Backend (planned):** Java, Spring Boot, JSP Servlets
- **Storage:** Flat-file (.txt) via Java File I/O
- **UI Theme:** Cinema-noir (Red, Black, White)

---

## Features

- ✅ Full CRUD for all 6 modules
- ✅ Responsive design (desktop + mobile)
- ✅ Modals for create/edit
- ✅ Toast notifications
- ✅ Live search & filters
- ✅ Star rating system
- ✅ Auto fine calculation on overdue returns
- ✅ Payment receipts
- ✅ Role-based admin management
- ✅ Dashboard with activity feed

---

*SE1020 OOP Project — Team of 6*
