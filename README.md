# **Panini World Cup 2026 Digital Collection** 🏆⚽

[![My Skills](https://skillicons.dev/icons?i=py,docker,spring,elasticsearch,git,github,postgres,postman,angular,bootstrap,css,html,idea,npm)](https://skillicons.dev)


A **full-stack digital collection platform** for the **2026 FIFA World Cup**, combining **Angular UI**, **Spring Boot backend**, **Kafka event streaming**, and **PostgreSQL analytics** to manage, trade, and analyze digital stickers.

---

## ✨ **Features**

✅ **Sticker Collection Management**
- Track and own digital stickers from all 2026 World Cup teams
- Own logos, players, and team stickers

✅ **Trade & Exchange System**
- **AI-powered trade recommendations** based on collection gaps
- **Fairness scoring** to ensure balanced trades
- **Duplicate sticker marketplace** for trading extras

✅ **Analytics & Insights**
- **Nation-wise completion stats** (how close you are to completing a country)
- **Group & continent analytics** (how many stickers you have from each group)
- **Most common nationalities** in your collection

✅ **Social Networking**
- **Connect with friends** to share collections
- **Send & receive trade requests**
- **View trade history**

✅ **Real-Time Updates**
- **Kafka-based event streaming** for instant analytics updates
- **Dockerized microservices** for scalability

---

## 🛠️ **Tech Stack**

| **Category**       | **Technologies Used**                                                                 |
|--------------------|--------------------------------------------------------------------------------------|
| **Frontend**       | Angular 21.2.5, TypeScript, Bootstrap 5.3.8, Chart.js, Keycloak (Auth)               |
| **Backend**        | Spring Boot 4.0.6, Spring Data JPA, Spring Kafka, Spring Security, MongoDB          |
| **Database**       | PostgreSQL 16 (for stickers & ownership), MongoDB (for user relations)               |
| **Event Streaming**| Apache Kafka 7.6.1 (for analytics & trade recommendations)                          |
| **Data Processing**| Python (Pandas, NumPy, Matplotlib), SQLAlchemy, Kafka Producer/Consumer             |
| **DevOps**         | Docker, Docker Compose, Git, GitHub Actions (CI/CD)                                  |
| **Testing**        | Vitest (Unit Tests), JUnit (Backend Tests)                                           |

---

## 📦 **Installation**

### **Prerequisites**
- **Node.js** (v18+) & **npm/yarn** (for Angular frontend)
- **Python** (3.11+) & **pip** (for analytics service)
- **Java JDK 21** (for Spring Boot backend)
- **Docker** & **Docker Compose** (for containerized setup)
- **PostgreSQL** (local or via Docker)
- **MongoDB** (local or via Docker)

---

### **Quick Start (Dockerized Setup)**

1. **Clone the repository**
   ```bash
   git clone https://github.com/kerfaiyass54/panini-wc-26-digital.git
   cd panini-wc-26-digital
   ```

2. **Set up environment variables**
   Create a `.env` file in the root directory with:
   ```env
   POSTGRES_USER=admin
   POSTGRES_PASSWORD=secret
   POSTGRES_DB=appdb
   PGADMIN_EMAIL=admin@admin.com
   PGADMIN_PASSWORD=secret
   KAFKA_BOOTSTRAP_SERVERS=kafka:9092
   ```

3. **Start the services with Docker Compose**
   ```bash
   docker-compose -f panini-wc-env/docker-compose.yml up -d
   ```
   This will:
   - Spin up **PostgreSQL**, **pgAdmin**, **Kafka**, **Zookeeper**, and **MongoDB**
   - Initialize the database with stickers and tables

4. **Build & run the backend (Spring Boot)**
   ```bash
   cd panini-wc-backend
   ./mvnw spring-boot:run
   ```
   (Ensure the database is running before starting the backend.)

5. **Set up the analytics service (Python)**
   ```bash
   cd panini-wc-analysis
   pip install -r requirements.txt
   python app.py
   ```

6. **Build & run the Angular frontend**
   ```bash
   cd panini-wc-ui
   npm install
   ng serve
   ```
   Open `http://localhost:4200` in your browser.

---

### **Alternative: Local Setup (Without Docker)**

#### **Backend (Spring Boot)**
1. Import the project into **IntelliJ IDEA** or **VS Code**.
2. Configure **PostgreSQL** connection in `application.properties`.
3. Run the Spring Boot app:
   ```bash
   ./mvnw spring-boot:run
   ```

#### **Frontend (Angular)**
1. Navigate to `panini-wc-ui` and run:
   ```bash
   npm install
   ng serve
   ```

#### **Analytics (Python)**
1. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```
2. Run the Kafka consumer:
   ```bash
   python app.py
   ```



## 🔧 **Configuration**

### **Environment Variables**
| Variable | Description | Default |
|----------|-------------|---------|
| `POSTGRES_USER` | PostgreSQL username | `admin` |
| `POSTGRES_PASSWORD` | PostgreSQL password | `secret` |
| `POSTGRES_DB` | Database name | `appdb` |
| `KAFKA_BOOTSTRAP_SERVERS` | Kafka broker address | `kafka:9092` |
| `DB_USER` | MongoDB username (backend) | `admin` |
| `DB_PASSWORD` | MongoDB password (backend) | `secret` |

### **Database Initialization**
1. Run the SQL scripts in `panini-wc-backend/databases/init/` to:
   - Create tables (`tables.sql`)
   - Insert stickers (`stickers.sql`)
   - Set up indexes (`indexes.sql`)

2. The **Docker Compose** setup automatically initializes the database.

---

## 🤝 **Contributing**

We welcome contributions! Here’s how you can help:

### **1. Development Setup**
1. Fork the repository.
2. Clone your fork:
   ```bash
   git clone https://github.com/your-username/panini-wc-26-digital.git
   ```
3. Install dependencies:
   ```bash
   # Backend
   cd panini-wc-backend
   ./mvnw install

   # Frontend
   cd panini-wc-ui
   npm install

   # Analytics
   cd panini-wc-analysis
   pip install -r requirements.txt
   ```

### **2. Code Style Guidelines**
- **TypeScript/JavaScript**: Use **ESLint** (Angular CLI enforces this).
- **Python**: Follow **PEP 8** guidelines.
- **SQL**: Use **PostgreSQL best practices** (indexes, transactions).
- **Docker**: Keep `Dockerfile`s minimal and optimized.

### **3. Pull Request Process**
1. Create a **feature branch**:
   ```bash
   git checkout -b feature/your-feature
   ```
2. Commit changes with a **clear message**:
   ```bash
   git commit -m "feat: add trade recommendation API"
   ```
3. Push to your fork and open a **Pull Request** to `main`.

### **4. Testing**
- **Backend**: Use **JUnit** for unit tests.
- **Frontend**: Use **Vitest** for unit tests.
- **Analytics**: Test Kafka consumers manually.


## 🚀 **Get Started Today!**

🔥 **Star this repo** if you found it useful!
💬 **Share your feedback** in the discussions.
🛠️ **Contribute** by fixing bugs or adding features.

Let’s make the **2026 World Cup digital collection** the best it can be! ⚽🏆

---
**Happy coding!** 🚀
