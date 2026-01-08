package com.example.studentmanagementsystem.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private LocalDate dob;
    @Column(name = "roll_no", unique = true, nullable = false)
    private int rollNo;
    private String firstName;

    public Student(String lastName, String email, LocalDate dob, int rollNo, String firstName) {
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.rollNo = rollNo;
        this.firstName = firstName;
    }

    public Student() {

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

}
