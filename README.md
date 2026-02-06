# Online Examination System - Spring Boot

A completeProject for an online examination system built with Spring Boot and MySQL.

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


This project is for educational purposes.

## 🤝 Contributing

Feel free to fork and enhance this project!
