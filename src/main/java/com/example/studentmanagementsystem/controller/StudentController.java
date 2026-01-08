package com.example.studentmanagementsystem.controller;
import com.example.studentmanagementsystem.entities.Student;
import com.example.studentmanagementsystem.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("")
    public List<Student> getAll(){
        return studentService.getAllStudents();
    }
    @GetMapping("/{rollNo}")
    public Student getByRollNo(@PathVariable int rollNo) {
        return studentService.getStudentByRollNo(rollNo);
    }
    @PostMapping
    public String saveStudent(@RequestBody Student student){
        studentService.saveStudent(student);
        return "Student Created Successfully";
    }
    @DeleteMapping("/{rollNo}")
    public String deleteStudent(@PathVariable int rollNo) {
        studentService.deleteStudentByRollNo(rollNo);
        return "Student with Roll No " + rollNo + " deleted successfully";
    }
    @PutMapping("/{rollNo}")
    public String updateStudents(@PathVariable int rollNo, @RequestBody Student studentDetails) {
        studentService.updateStudentByRollNo(rollNo, studentDetails);
        return "Student with Roll No " + rollNo + " updated successfully";
    }
}
