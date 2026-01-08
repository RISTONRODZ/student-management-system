package com.example.studentmanagementsystem.repositories;

import com.example.studentmanagementsystem.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByRollNo(int rollNo);
    void deleteByRollNo(int rollNo);
}
