package se.lexicon.course_manager.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CourseTest {
     Course course;
     Student student1;
     Student student2;

    @BeforeEach
    void setUp() {
        course = new Course(1, "Java Advanced", LocalDate.of(2024, 11, 18), 10);;
        student1 = new Student(1, "Linus", "linus.tes@test.nu", "Vintergatan 8");
        student2 = new Student(2,"Carl", "Carl.te@test.nu", "Vintergatan 7");
    }

    @Test
    void Constructor() {
        Course course = new Course(1, "Java Advanced", LocalDate.of(2024, 11, 18), 10);
        assertEquals(1, course.getId());
        assertEquals("Java Advanced" , course.getCourseName());
        assertEquals(LocalDate.of(2024, 11, 18), course.getStartDate());
        assertEquals(10, course.getWeekDuration());

    }


    @Test
    void enrollStudent(){
        assertTrue(course.enrollStudent(student1));
        assertFalse(course.enrollStudent(student1));
        assertTrue(course.getStudents().contains(student1));

    }
    @Test
    void unrollStudentTest(){
        course.enrollStudent(student1);
        assertTrue(course.unrollStudent(student1));
        assertFalse(course.getStudents().contains(student1));


    }
    @Test
    void toStringTest(){
        String expected = "Course{id=1, courseName='Java Advanced', startDate=2024-11-18, weekDuration=10, students=[]}";
        assertEquals(expected, course.toString());
    }



}







    // TODO Implement your tests here

