# 🎓 Placement Tracker | Full-Stack Spring Boot Showcase

An ultra-premium, production-grade **Placement Tracker & Analytics Console** designed to streamline job applications, candidate profiles, and recruitment pipelines. Featuring a custom-tailored dark green glassmorphic design system themed with sharp nature backdrops, smooth micro-animations, and dynamic visual widgets.

---

## 💼 The Recruiter Pitch: Why This Project Stands Out

This project is a high-fidelity showcase of **Full-Stack Engineering Capability**, demonstrating an optimal balance between robust backend business logic, secure database management, and premium frontend design aesthetics.

### 🔑 Key Engineering Achievements:
1. **Clean MVC Architecture & Layered Design**: Implements standard enterprise-grade design patterns with clean separation of concerns: Model-View-Controller, Service Layer separation, and JpaRepository interfaces.
2. **Robust Security & Session Management**: Built a custom session-based security framework (no heavy Spring Security overhead) isolating student records, implementing SHA-256 cryptographic password hashing, and protecting CRUD endpoints from unauthorized access.
3. **Advanced OTP Password Reset System**: Engineered a 6-digit One-Time Password (OTP) verification system using **Spring Mail (SMTP)**. To ensure high testability, the system features a **Resilient Console Simulation Fallback** that prints OTPs to terminal logs if SMTP configurations are missing.
4. **Stunning Frontend Engineering (Vanilla CSS)**: Achieved premium glassmorphic UI aesthetics, smooth HSL gradients, and dynamic micro-animations (e.g. glowing pulses, card-tilts, chevron rotations) using **pure HTML5/CSS3** without the bloat of external client-side frameworks.
5. **Interactive UI Enhancements**:
   - **Dynamic Easing Odometer**: Custom-built counter engine that rolls up dashboard metrics from zero with a smooth easing formula.
   - **Visual Recruitment Pipeline Drawer**: Expandable table rows containing a 3D recruitment roadmap (Applied ➔ OA ➔ Interview ➔ Selected/Rejected). If rejected, the backend dynamically calculates and marks the exact technical round of failure.
6. **Cloud-Ready Docker Deployments**: Built with a multi-stage Docker container architecture, automatically compiling Maven targets and serving the JVM container live on hosting services (deployed on Render hooked to a remote Clever Cloud MySQL database).

---

## 🛠️ Technical Specifications

- **Backend Core**: Java (JDK 17) / Spring Boot (MVC, JPA, Hibernate)
- **Frontend Presentation**: Thymeleaf Template Engine, HTML5, Vanilla CSS3 (custom variables, responsive grids)
- **Database Layer**: MySQL / Spring Data JPA / Relational database mappings
- **Security & Mail Services**: Spring Mail Starter, Java Mail Sender, SHA-256 Digest Cryptography
- **Containerization & Deployment**: Docker (Multi-stage build), Maven, Render Cloud Services

---

## 🌟 Visual & Interactive Highlights

### 🎯 1. Interactive Recruitment Timeline
Clicking on any company application row dynamically expands a glass details drawer revealing the candidate's exact progress. 
- Successful milestones light up in **forest emerald**.
- If a candidate is rejected, the system automatically parses their technical rounds cleared, highlights the step where they got stuck, and paints it in a **crimson alert glow**.

### 📈 2. Dynamic Numbers Odometer
Rather than loading static statistics, the dashboard features custom Javascript-driven odometers that count up to the correct values (Active Drives, Average Package LPA, Placement Ratio %) immediately upon login.

### 🛡️ 3. Multi-Factor Password Recovery
Implements a clean 2-step password reset workflow:
- **Phase 1**: Email verification triggering a random 6-digit verification code.
- **Phase 2**: Active OTP verification checked against a 5-minute database expiry window.

---

## 🚀 Getting Started (Local Run)

### 📋 Prerequisites
- **Java JDK 17** or higher
- **Maven 3.9+**
- **MySQL Database Server** (e.g., through XAMPP or a standalone installation)

### ⚙️ Database Configuration
1. Start your local MySQL server.
2. Execute the following SQL script to initialize the database:
   ```sql
   CREATE DATABASE placement_tracker;
   ```
3. The application automatically handles table schemas and migrations via Hibernate `update` settings. If you need to custom-configure database usernames or passwords, update your local environment variables or edit [application.properties](src/main/resources/application.properties):
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/placement_tracker
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 💻 Running the Server
1. Navigate to the project directory:
   ```bash
   cd "placement tracker"
   ```
2. Build and run the Spring Boot project using Maven:
   ```bash
   mvn clean spring-boot:run
   ```
3. Open your web browser and go to:
   ```
   http://localhost:8080
   ```

---

## 🐳 Running with Docker

This project comes equipped with a highly optimized two-stage Docker container build. 

1. Build the Docker image locally:
   ```bash
   docker build -t placement-tracker .
   ```
2. Launch the container mapping the default port:
   ```bash
   docker run -p 8080:8080 placement-tracker
   ```

---

## 👨‍💻 Full-Stack Developer

Developed with ❤️ by **Ram Chandu Nandamuri**.
- **GitHub**: [@ramchandu438](https://github.com/ramchandu438)
- **LinkedIn**: [Ram Chandu Nandamuri](https://www.linkedin.com/in/ram-chandu-nandamuri-912015254/)
- **Email**: ramchandu438@gmail.com
- **Live URL**: [https://placement-tracker-qlp9.onrender.com](https://placement-tracker-qlp9.onrender.com)
