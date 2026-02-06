# 🚀 Quick Start Guide - Online Examination System

## ⚡ 5-Minute Setup

### Step 1: Database Setup (2 minutes)
```sql
-- 1. Create database
CREATE DATABASE online_exam_db;
USE online_exam_db;

-- 2. Your tables should already exist. Just fix the foreign keys:
ALTER TABLE exam DROP FOREIGN KEY IF EXISTS exam_ibfk_1;
ALTER TABLE exam MODIFY created_by INT;

ALTER TABLE exam ADD CONSTRAINT fk_exam_created_by 
FOREIGN KEY (created_by) REFERENCES user(user_id);

ALTER TABLE questions ADD CONSTRAINT fk_question_exam 
FOREIGN KEY (exam_id) REFERENCES exam(exam_id);

ALTER TABLE options ADD CONSTRAINT fk_option_question 
FOREIGN KEY (question_id) REFERENCES questions(question_id);

ALTER TABLE attempt ADD CONSTRAINT fk_attempt_user
FOREIGN KEY (user_id) REFERENCES user(user_id);

ALTER TABLE attempt ADD CONSTRAINT fk_attempt_exam
FOREIGN KEY (exam_id) REFERENCES exam(exam_id);

ALTER TABLE result ADD CONSTRAINT fk_result_attempt
FOREIGN KEY (attempt_id) REFERENCES attempt(attempt_id);
```

### Step 2: Configure Application (1 minute)
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 3: Run Application (2 minutes)
```bash
# Navigate to project
cd online-exam-system

# Run with Maven
mvn spring-boot:run

# OR if using IDE, just run OnlineExaminationApplication.java
```

Application starts at: http://localhost:8080

## 🧪 Test It Works

### Quick Test Sequence:

**1. Create a User**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Student",
    "email": "student@test.com",
    "password": "test123",
    "role": "STUDENT"
  }'
```

**2. Create an Exam**
```bash
curl -X POST http://localhost:8080/api/exams \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Sample Exam",
    "subject": "Testing",
    "duration": 30,
    "totalMarks": 50,
    "startTime": "2026-03-01T10:00:00",
    "endTime": "2026-03-01T10:30:00",
    "createdBy": 1
  }'
```

**3. Get All Exams**
```bash
curl http://localhost:8080/api/exams
```

If you see JSON responses, **it's working! 🎉**

## 📱 Use Postman

1. Import `postman_collection.json`
2. Start making requests!

## Common Issues

**Port 8080 already in use?**
Change in `application.properties`:
```properties
server.port=8081
```

**Can't connect to MySQL?**
Check:
- MySQL is running
- Username/password correct
- Database `online_exam_db` exists

**Build fails?**
```bash
mvn clean install -U
```

## 📚 What's Next?

1. Check README.md for full API documentation
2. Test all endpoints with Postman
3. Build your frontend!

## Project Structure
```
online-exam-system/
├── src/main/java/.../
│   ├── model/          ← Your entities (User, Exam, etc.)
│   ├── repository/     ← Database access
│   ├── service/        ← Business logic
│   └── controller/     ← REST APIs
├── pom.xml            ← Dependencies
└── README.md          ← Full documentation
```

Happy coding! 🚀
