# 🎓 Premium Placement Tracker

An ultra-premium, modern **Placement Tracker** web application designed to manage student profiles, placement pipelines, and recruitment analytics. Featuring a stunning, custom-tailored glass layout themed with vibrant mint/green colors and a clean nature backdrop.

---

## ✨ Features

- **Premium Glass Showcase**: A gorgeous, non-blurry dashboard container (`.app-frame`) over a high-resolution backdrop featuring dynamic metrics odometers and modern visual styling.
- **Dynamic Metrics Odometer**: Interactive metrics trackers displaying active drives, average packages, and placement percentages.
- **Expanding Pipeline Accordion**: A clean, interactive visual timeline/accordion drawer to track students through different recruitment stages.
- **Secure Profiles & CRUD Operations**: Full student registration, authentication, profile creation, and real-time updates.
- **Developer Contact Widget**: A floating interactive widget at the bottom-left corner presenting professional contact links (GitHub, LinkedIn, and Email).
- **Staggered Typography**: Eye-catching branding and headers using custom Google Fonts: **Bowlby One SC** for branding and **Ultra** for hero messaging.

---

## 🛠️ Technology Stack

- **Backend**: Java / Spring Boot (MVC Architecture)
- **Frontend Template Engine**: Thymeleaf / HTML5
- **Styling**: Modern Vanilla CSS (featuring glassmorphism, responsive grid systems, and custom micro-animations)
- **Database**: MySQL / Spring Data JPA
- **Build Tool**: Maven

---

## 🚀 Getting Started

### 📋 Prerequisites

Before running the application, make sure you have the following installed:
- **Java JDK 17** or higher
- **Maven**
- **MySQL Database Server** (e.g., through XAMPP or standalone installation)

### ⚙️ Database Configuration

1. Start your MySQL server.
2. Create a new database named `placement_tracker`:
   ```sql
   CREATE DATABASE placement_tracker;
   ```
3. Update the database credentials in `src/main/resources/application.properties` if necessary:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/placement_tracker?useSSL=false&serverTimezone=UTC
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 💻 Running the Application

1. Clone this repository (or navigate to the project directory):
   ```bash
   cd "placement tracker"
   ```
2. Build and run the project using Maven:
   ```bash
   mvn clean spring-boot:run
   ```
3. Open your web browser and navigate to:
   ```
   http://localhost:8080
   ```

---

## 👨‍💻 Developer

Developed with ❤️ by **Ram Chandu Nandamuri**.
- **GitHub**: [@ramchandu438](https://github.com/ramchandu438)
- **LinkedIn**: [Ram Chandu Nandamuri](https://www.linkedin.com/in/ram-chandu-nandamuri-912015254/)
- **Email**: ramchandu438@gmail.com
