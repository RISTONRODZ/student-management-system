package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
    public Student getStudentByRollNo(int rollNo) {
        return studentRepository.findByRollNo(rollNo)
                .orElseThrow(() -> new RuntimeException("Student with Roll No " + rollNo + " not found"));
    }
    public void saveStudent(Student student){
        studentRepository.save(student);
    }
    public void deleteStudentByRollNo(int rollNo) {
        if (studentRepository.findByRollNo(rollNo).isEmpty()) {
            throw new RuntimeException("Cannot delete: Student with Roll No " + rollNo + " not found");
        }
        studentRepository.deleteByRollNo(rollNo);
    }
    @Transactional
    public void updateStudentByRollNo(int rollNo, Student studentDetails) {
        Student existingStudent = studentRepository.findByRollNo(rollNo)
                .orElseThrow(() -> new RuntimeException("Student with Roll No " + rollNo + " not found"));
        existingStudent.setFirstName(studentDetails.getFirstName());
        existingStudent.setLastName(studentDetails.getLastName());
        existingStudent.setEmail(studentDetails.getEmail());
        existingStudent.setDob(studentDetails.getDob());

        studentRepository.save(existingStudent);
    }
}
