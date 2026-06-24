# 🎬 FilmFLEX - Enterprise Movie Rental & Review Platform

MOVIEFLEX is a comprehensive Java-based Web Application designed as an advanced Movie Rental and Review Management System. Built upon the strict **Model-View-Controller (MVC) Architectural Pattern**, the system achieves dynamic data persistence without a traditional SQL relational engine by executing optimized file-stream I/O transactions across isolated flat-text database structures.

The frontend is completely standardized on a modern, ultra-premium **Glassmorphic Dark Theme** engineered with responsive Bootstrap grids, integrated CSS core variables, and dynamic context control parameters.

---

## 🏗️ Architecture & Package Topography

The system enforces complete separation of concerns by partitioning application layers into three distinct logical boundaries under the master source container:

*   **`controller` (Application Control Layer):** Houses specialized `HttpServlet` implementations that bind runtime endpoints, parse multipart user payloads, check credentials, and dispatch session outcomes.
*   **`service` (Business Core Logic):** Implements system algorithms, data processing streams, unique identity generation loops, late fine evaluation formulas, and concurrent text-file database file synchronization.
*   **`model` (Data Encapsulation State):** Contains fully encapsulated POJO structures mapped with private attributes, designated class constructors, and standard getter/setter property boundaries.

```text
src/main/java/
├── controller/            # Servlets (AddMovie, Login, RentMovie, Payment, etc.)
├── service/               # Managers (MovieManager, RentalManager, UserManager, etc.)
└── model/                 # Data Models (Movie, Rental, User, Payment, Review)
