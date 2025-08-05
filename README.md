# Aurelius

_Aurelius_ is an early-stage spaced-repetition flashcard web application. Designed for learners who 
value simplicity and focus, Aurelius helps you retain knowledge through proven techniques. While Aurelius 
is a web application accessible from any device, it is designed with the assumption that users will create 
and manage decks on a desktop, while card review will primarily take place on mobile.

---

## 🚧 Project Status

**Early Development** — Not yet production-ready. Core functionality is under active development.

---

## ✨ Features (Planned & In Progress)

- [ ] Create decks
- [ ] Create and review flashcards
- [ ] Spaced repetition study sessions
- [ ] Daily review queue
- [ ] Progress reporting and statistics dashboard
- [ ] Mobile-focused review mode
- [ ] AI integration?

---

## 🔧 Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 3.5.3
- **View Engine:** Thymeleaf 
- **Database:** PostgreSQL 17
- **Build Tool:** Maven

---

## 📦 Getting Started

### Prerequisites

- Java 21
- PostgreSQL 17
- Maven

### Running the App

```bash
# Clone the repo
git clone https://github.com/aitkenably/aurelius.git
```
Copy `application.properties.example` to `application.properties` and update your PostgreSQL 
connection information.

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/aurelius
spring.datasource.username=youruser
spring.datasource.password=yourpassword
```

The application uses Flyway to manage database migrations. When the application starts, the schema and 
necessary tables will be automatically created. 

```bash
# Run the application
cd aurelius
./mvnw spring-boot:run

# App will be available at http://localhost:8080
```

---

## 🧪 Development Notes

This project is still in flux. Folder structure, domain models, and the persistence layer are subject to change as core features stabilize.

---

## 📜 License

MIT License — see [`LICENSE`](LICENSE) for details.

---
