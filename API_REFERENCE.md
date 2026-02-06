# 📡 Complete API Reference

## Base URL: `http://localhost:8080`

---

## 👥 USER ENDPOINTS

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| GET | `/api/users/email/{email}` | Find user by email |
| GET | `/api/users/role/{role}` | Get users by role (STUDENT/TEACHER) |
| POST | `/api/users` | Create new user |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

**Example JSON (Create User):**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "role": "STUDENT"
}
```

---

## 📝 EXAM ENDPOINTS

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/exams` | Get all exams |
| GET | `/api/exams/{id}` | Get exam by ID |
| GET | `/api/exams/creator/{userId}` | Get exams created by user |
| GET | `/api/exams/subject/{subject}` | Get exams by subject |
| GET | `/api/exams/active` | Get currently active exams |
| POST | `/api/exams` | Create new exam |
| PUT | `/api/exams/{id}` | Update exam |
| DELETE | `/api/exams/{id}` | Delete exam |

**Example JSON (Create Exam):**
```json
{
  "title": "Java Programming Final",
  "subject": "Computer Science",
  "duration": 60,
  "totalMarks": 100,
  "startTime": "2026-03-15T10:00:00",
  "endTime": "2026-03-15T11:00:00",
  "createdBy": 1
}
```

---

## ❓ QUESTION ENDPOINTS

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/questions` | Get all questions |
| GET | `/api/questions/{id}` | Get question by ID |
| GET | `/api/questions/exam/{examId}` | Get all questions for an exam |
| POST | `/api/questions` | Create new question |
| PUT | `/api/questions/{id}` | Update question |
| DELETE | `/api/questions/{id}` | Delete question |

**Example JSON (Create Question):**
```json
{
  "examId": 1,
  "questionText": "What is polymorphism in OOP?",
  "questionType": "MCQ",
  "marks": 5
}
```

---

## ✅ OPTION ENDPOINTS

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/options` | Get all options |
| GET | `/api/options/{id}` | Get option by ID |
| GET | `/api/options/question/{questionId}` | Get all options for a question |
| GET | `/api/options/question/{questionId}/correct` | Get correct answer(s) |
| POST | `/api/options` | Create new option |
| PUT | `/api/options/{id}` | Update option |
| DELETE | `/api/options/{id}` | Delete option |
| DELETE | `/api/options/question/{questionId}` | Delete all options for question |

**Example JSON (Create Option):**
```json
{
  "questionId": 1,
  "optionText": "Ability of objects to take many forms",
  "isCorrect": true
}
```

---

## 🎯 ATTEMPT ENDPOINTS

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/attempts` | Get all attempts |
| GET | `/api/attempts/{id}` | Get attempt by ID |
| GET | `/api/attempts/user/{userId}` | Get attempts by user |
| GET | `/api/attempts/exam/{examId}` | Get attempts for an exam |
| POST | `/api/attempts/start?userId={id}&examId={id}` | Start new exam attempt |
| PUT | `/api/attempts/{id}/submit?score={score}` | Submit attempt with score |
| PUT | `/api/attempts/{id}` | Update attempt |
| DELETE | `/api/attempts/{id}` | Delete attempt |

**Example (Start Attempt):**
```
POST /api/attempts/start?userId=1&examId=1
```

**Example (Submit Attempt):**
```
PUT /api/attempts/1/submit?score=85
```

---

## 🏆 RESULT ENDPOINTS

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/results` | Get all results |
| GET | `/api/results/{id}` | Get result by ID |
| GET | `/api/results/attempt/{attemptId}` | Get result for an attempt |
| GET | `/api/results/grade/{grade}` | Get results by grade (A+, A, B, C, D, F) |
| GET | `/api/results/passed/{percentage}` | Get results above percentage |
| POST | `/api/results` | Create result manually |
| POST | `/api/results/calculate?attemptId={id}&totalMarks={marks}&maxMarks={max}` | Auto-calculate result |
| PUT | `/api/results/{id}` | Update result |
| DELETE | `/api/results/{id}` | Delete result |

**Example (Calculate Result):**
```
POST /api/results/calculate?attemptId=1&totalMarks=85&maxMarks=100
```

**Auto-calculated Response:**
```json
{
  "resultId": 1,
  "attemptId": 1,
  "totalMarks": 85,
  "percentage": 85.0,
  "grade": "A",
  "publishedAt": "2026-02-06T10:30:00"
}
```

---

## 📊 Grade Calculation

| Percentage | Grade |
|------------|-------|
| 90% - 100% | A+ |
| 80% - 89% | A |
| 70% - 79% | B |
| 60% - 69% | C |
| 50% - 59% | D |
| Below 50% | F |

---

## 🔄 Typical Workflow

1. **Create User** → `POST /api/users`
2. **Create Exam** → `POST /api/exams`
3. **Add Questions** → `POST /api/questions`
4. **Add Options** → `POST /api/options`
5. **Student Starts Exam** → `POST /api/attempts/start`
6. **Submit Exam** → `PUT /api/attempts/{id}/submit`
7. **Calculate Result** → `POST /api/results/calculate`
8. **View Result** → `GET /api/results/attempt/{attemptId}`

---

## 🛠️ Response Codes

| Code | Meaning |
|------|---------|
| 200 | Success |
| 201 | Created |
| 204 | No Content (successful delete) |
| 400 | Bad Request |
| 404 | Not Found |
| 500 | Internal Server Error |

---

## 💡 Tips

- All endpoints support **CORS** (CrossOrigin enabled)
- Use **Content-Type: application/json** for POST/PUT requests
- DateTime format: `yyyy-MM-dd'T'HH:mm:ss` (e.g., `2026-03-15T10:00:00`)
- All IDs are integers except `result_id` which is Long

Happy testing! 🚀
