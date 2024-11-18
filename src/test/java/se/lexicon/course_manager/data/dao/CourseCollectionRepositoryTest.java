package se.lexicon.course_manager.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.data.sequencers.CourseSequencer;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {
    private CourseCollectionRepository repository;
    private Course course1;
    private Course course2;
    private Student student;

    @Autowired
    private CourseDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    // TODO Write your tests here
    @BeforeEach
    void setup(){
        Collection<Course> courses= new HashSet<>();
        repository= new CourseCollectionRepository(courses);
        student = new Student(1, "Linus", "linus.tes@test.nu", "Vintergatan 8");
        course1 = new Course(1, "Java Advanced", LocalDate.of(2024, 11, 18), 10);
        course2 = new Course(2, "Java Beginner", LocalDate.of(2024, 12, 18), 10);
        course1.enrollStudent(student);
        courses.add(course1);
        courses.add(course2);
    }
    @Test
    void createCourseTest(){
        Course newCourse = repository.createCourse("Node Js", LocalDate.of(2024,1,1),10);
        assertNotNull(newCourse);
        assertEquals("Node Js", newCourse.getCourseName());
        assertEquals(LocalDate.of(2024,1,1), newCourse.getStartDate());
        assertEquals(10, newCourse.getWeekDuration());
    }
    @Test
    void findByIDTest(){
        Course found= repository.findById(1);
        assertEquals("Java Advanced", found.getCourseName());

    }

    @Test
    void findByNameContains() {
        Collection<Course> found= repository.findByNameContains("Java");
        assertEquals(2, found.size());
    }

    @Test
    void findByDateBefore() {
        Collection<Course> foundC= repository.findByDateBefore(LocalDate.of(2024,11,20));
        assertEquals(1, foundC.size());
    }

    @Test
    void findByDateAfter() {
        Collection<Course> foundCourses = repository.findByDateAfter(LocalDate.of(2024,11,18));
        assertEquals(1, foundCourses.size());
    }

    @Test
    void findAll() {
        Collection<Course> allCourses = repository.findAll();
        assertEquals(2, allCourses.size());
    }

    @Test
    void findByStudentId() {
        Collection<Course> studentC = repository.findByStudentId(1);
        assertEquals(1, studentC.size());

    }

    @Test
    void removeCourse() {
        boolean removed = repository.removeCourse(course1);
        assertTrue(removed);
        assertEquals(1, repository.findAll().size());
    }
    @AfterEach
    void tearDown() {
        testObject.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
