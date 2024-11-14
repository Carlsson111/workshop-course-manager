package se.lexicon.course_manager.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class CourseTest {
     Course testObject;
     Student student;

    @BeforeEach
    void setUp() {
        testObject = new Course(1, "Java Advanced", LocalDate.of(2023, 1, 1), 10);;
        student = new Student(1, "Linus", "linus.tes@test.nu", "123 Main St");    }
    @Test
    void enrollStudent(){
        testObject.enrollStudent(student);

        Assertions.assertTrue(testObject.getStudents().contains(student));
    }



}







    // TODO Implement your tests here

