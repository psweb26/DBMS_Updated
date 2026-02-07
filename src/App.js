import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import ExamList from './components/ExamList';
import TakeExam from './components/TakeExam';
import Results from './components/Results';
import './App.css';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/exams" element={<ExamList />} />
        <Route path="/take-exam/:examId" element={<TakeExam />} />
        <Route path="/result/:attemptId" element={<Results />} />
        <Route path="/results" element={<Results />} />
        <Route path="/" element={<Navigate to="/login" />} />
      </Routes>
    </Router>
  );
}

export default App;