# 🎬 FILMFLEX 2.2 - Enterprise Movie Rental & Review Platform

FILMFLEX 2.2 is an advanced Java-based Web Application engineered as an enterprise-grade Movie Rental, Ticket Sales, and Review Management System. Built strictly upon the **Model-View-Controller (MVC) Architectural Pattern**, the system partitions logical boundaries into robust sub-packages to enforce optimal modularity and clean-code practices.

Data persistence is achieved dynamically without a traditional SQL relational engine by utilizing optimized file-stream I/O transactions across isolated flat-text database structures (.txt). The user experience features a premium **Glassmorphic Dark Interface** embedded with dynamic context parameters.

---

## 🏗️ Technical Architecture & Package Topography

The platform separates application lifecycle boundaries into specialized modular sub-packages within the core container framework (`src/main/java`):

*   **`controller` (Application Request Handlers):** Parses client payloads, maps route contexts, manages dynamic cookies/sessions, and delegates processing loads.
    *   `controller.admin`: High-privilege access control, tracking system analytics and dashboards.
    *   `controller.auth`: Core session handshake routines and authorization logic.
    *   `controller.movie`: Handles catalogs, media rentals, ticket checkout configurations, and live user feedback updates.
    *   `controller.user`: Enforces user identity profile loops, metrics management, and profile termination hooks.
*   **`filter` (Security Layer):** Intercepts system endpoints via `AdminAuthFilter` to block unauthorized access to core modules.
*   **`service` (Core Business Logic Layer):** Houses the transactional engines (`AdminService`, `MovieService`, `ReviewService`, `UserService`) managing structural checks.
*   **`repository` (Data Mapping Layer):** Bridges services and database utilities through streamlined access providers like `PurchaseTicketRepository` and `ReviewRepository`.
*   **`model` (Data Encapsulation State):** Contains encapsulated state objects (Admin, Movie, Payment, Review, Ticket, User) mapped securely with restricted getters and setters.
*   **`util` (Infrastructure Utilities):** Features low-level transaction tools (`FileUtil`) that handle concurrent record reading/writing safely.

---

## 👥 Engineering Team & Core Responsibilities

The compilation and code distribution across the enterprise stack are assigned precisely below:

| Student IT Number & Name | Component Specialization           | Package Core Deliverables |
| :--- |:-----------------------------------| :--- |
| **IT25102205** - Hewamana Y.C.K | **Admin Core Management**          | `controller.admin.*`, `adminAuthFilter.java`, `adminDashboard.html` |
| **IT25100021** - Weerasinghe V.C.S | **Movie Management**               | `controller.movie.MovieServlet`, `MovieService.java`, `movieManagement.html` |
| **IT25100393** - Pathirannehelage T.J.T | **Rental Management**              | `controller.movie.ReviewController`, `ReviewRepository.java`, `home.html` |
| **IT25102224** - Punsara P.S | **Invoice & Payment Management**   | `controller.movie.PaymentServlet`, `PaymentController.java`, `PaymentMethods.html` |
| **IT25101879** - Thenuja H.R.H | **Review & Moderation Management** | `ReviewService.java`, `reviewManagement.html`, `movie.html` |
| **IT25104007** - Induwara P.G.T | **User Management**                | `controller.user.*`, `UserService.java`, `UserDashboard.html` |

---

## ⚙️ Data Persistence Topology (.txt Database Mappings)

System memory tables are maintained dynamically inside flat-text arrays under `src/main/resources/data/`. Records are tokenized and updated concurrently via the `FileUtil` asset broker layer:

*   📂 `admins.txt` - Protected administration profile hashes.
*   📂 `movies.txt` - Centralized production catalogue indexes and streaming attributes.
*   📂 `payments.txt` - Serialized credit invoice transaction nodes.
*   📂 `reviews.txt` - Public opinion streams, rating pointers, and moderation status strings.
*   📂 `tickets.txt` - Active premium purchase access ticket licenses.
*   📂 `users.txt` - Encrypted profile datasets for authenticated platform handles.

---

## 🚀 Installation & Deployment Runbook

### System Prerequisites
* **Java Development Kit (JDK 17 or higher)**
* **Apache Tomcat Server (v9.0.x / v10.x)**
* **Apache Maven Core System**
* **IntelliJ IDEA Ultimate / Community**

### Execution Steps
1. Open your terminal cluster and download the source matrix locally:
   
     git clone [https://github.com/Tharushajayod/Filmflex.git](https://github.com/Tharushajayod/Filmflex.git) 

2. Launch the framework folder context securely inside IntelliJ IDEA.

3. Spin up your localized Smart Tomcat Server Configuration referencing the bundled deployment context paths.

4. Execute Maven source assembly cleanups and packages:

      mvn clean package

5. Initiate your Tomcat deployment routine and load the interface matrix using:

      http://localhost:8080/index.html

📄 Academic Standard & Licensing
This source release is developed strictly for the academic verification requirements of SLIIT Software Engineering Guidelines. All visual styles, database logic loops, file stream lock implementations, and endpoint routines remain private under verified development authorization terms.

Synchronized and compiled cleanly under Version 2.2 by Tharusha.
