# Online Examination System - Spring Boot

A complete REST API for an online examination system built with Spring Boot and MySQL.

## 📋 Features

- **User Management**: Create, read, update, delete users with roles
- **Exam Management**: Manage exams with scheduling
- **Question Bank**: Create questions with different types
- **Options Management**: Add multiple choice options
- **Attempt Tracking**: Track user exam attempts
- **Result System**: Auto-calculate grades and percentages

## 🗄️ Database Schema

### Tables:
1. **user** - Stores user information
2. **exam** - Exam details and scheduling
3. **questions** - Question bank linked to exams
4. **options** - Multiple choice options for questions
5. **attempt** - Tracks exam attempts by users
6. **result** - Stores graded results

## 🚀 Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

### Database Setup

1. Create MySQL database:
```sql
CREATE DATABASE online_exam_db;
```

2. Run the foreign key fixes (from earlier):
```sql
-- Fix created_by type mismatch
ALTER TABLE exam DROP FOREIGN KEY IF EXISTS exam_ibfk_1;
ALTER TABLE exam MODIFY created_by INT;

-- Add all foreign keys
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

3. Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/online_exam_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

### Running the Application

1. Navigate to project directory:
```bash
cd online-exam-system
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 📡 API Endpoints

### User Endpoints
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `GET /api/users/role/{role}` - Get users by role
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Exam Endpoints
- `GET /api/exams` - Get all exams
- `GET /api/exams/{id}` - Get exam by ID
- `GET /api/exams/creator/{createdBy}` - Get exams by creator
- `GET /api/exams/subject/{subject}` - Get exams by subject
- `GET /api/exams/active` - Get active exams
- `POST /api/exams` - Create new exam
- `PUT /api/exams/{id}` - Update exam
- `DELETE /api/exams/{id}` - Delete exam

### Question Endpoints
- `GET /api/questions` - Get all questions
- `GET /api/questions/{id}` - Get question by ID
- `GET /api/questions/exam/{examId}` - Get questions by exam
- `POST /api/questions` - Create new question
- `PUT /api/questions/{id}` - Update question
- `DELETE /api/questions/{id}` - Delete question

### Option Endpoints
- `GET /api/options` - Get all options
- `GET /api/options/{id}` - Get option by ID
- `GET /api/options/question/{questionId}` - Get options for question
- `GET /api/options/question/{questionId}/correct` - Get correct answers
- `POST /api/options` - Create new option
- `PUT /api/options/{id}` - Update option
- `DELETE /api/options/{id}` - Delete option

### Attempt Endpoints
- `GET /api/attempts` - Get all attempts
- `GET /api/attempts/{id}` - Get attempt by ID
- `GET /api/attempts/user/{userId}` - Get attempts by user
- `GET /api/attempts/exam/{examId}` - Get attempts by exam
- `POST /api/attempts/start?userId={id}&examId={id}` - Start new attempt
- `PUT /api/attempts/{id}/submit?score={score}` - Submit attempt
- `PUT /api/attempts/{id}` - Update attempt
- `DELETE /api/attempts/{id}` - Delete attempt

### Result Endpoints
- `GET /api/results` - Get all results
- `GET /api/results/{id}` - Get result by ID
- `GET /api/results/attempt/{attemptId}` - Get result by attempt
- `GET /api/results/grade/{grade}` - Get results by grade
- `GET /api/results/passed/{percentage}` - Get passed results
- `POST /api/results` - Create new result
- `POST /api/results/calculate?attemptId={id}&totalMarks={marks}&maxMarks={max}` - Calculate result
- `PUT /api/results/{id}` - Update result
- `DELETE /api/results/{id}` - Delete result

## 🧪 Testing with Postman

### Example Requests:

**Create User:**
```json
POST http://localhost:8080/api/users
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "STUDENT"
}
```

**Create Exam:**
```json
POST http://localhost:8080/api/exams
Content-Type: application/json

{
  "title": "Java Programming Exam",
  "subject": "Computer Science",
  "duration": 60,
  "totalMarks": 100,
  "startTime": "2026-03-01T10:00:00",
  "endTime": "2026-03-01T11:00:00",
  "createdBy": 1
}
```

**Create Question:**
```json
POST http://localhost:8080/api/questions
Content-Type: application/json

{
  "examId": 1,
  "questionText": "What is polymorphism in Java?",
  "questionType": "MCQ",
  "marks": 5
}
```

**Create Options:**
```json
POST http://localhost:8080/api/options
Content-Type: application/json

{
  "questionId": 1,
  "optionText": "Ability to take many forms",
  "isCorrect": true
}
```

**Start Exam Attempt:**
```
POST http://localhost:8080/api/attempts/start?userId=1&examId=1
```

**Submit Attempt:**
```
PUT http://localhost:8080/api/attempts/1/submit?score=85
```

**Calculate Result:**
```
POST http://localhost:8080/api/results/calculate?attemptId=1&totalMarks=85&maxMarks=100
```

## 🏗️ Project Structure

```
online-exam-system/
├── src/
│   ├── main/
│   │   ├── java/com/exampleonlineexamination/twd/
│   │   │   ├── model/          # Entity classes
│   │   │   ├── repository/     # JPA repositories
│   │   │   ├── service/        # Business logic
│   │   │   ├── controller/     # REST controllers
│   │   │   └── OnlineExaminationApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
└── README.md
```

## 🔒 Security Note

This is a basic implementation. For production use, add:
- Spring Security for authentication/authorization
- Password encryption (BCrypt)
- JWT tokens for API security
- Input validation
- Exception handling
- HTTPS/SSL

## 📝 License

This project is for educational purposes.

## 🤝 Contributing

Feel free to fork and enhance this project!
