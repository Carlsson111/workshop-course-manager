package se.lexicon.course_manager.data.service.course;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager.data.dao.CourseDao;
import se.lexicon.course_manager.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager.data.dao.StudentDao;
import se.lexicon.course_manager.data.sequencers.CourseSequencer;
import se.lexicon.course_manager.data.service.converter.ModelToDto;
import se.lexicon.course_manager.dto.forms.CreateCourseForm;
import se.lexicon.course_manager.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager.dto.views.CourseView;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;


import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {CourseManager.class, CourseCollectionRepository.class, ModelToDto.class, StudentCollectionRepository.class})
public class CourseManagerTest {

    @Autowired
    private CourseService testObject;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentDao studentDao;

    private Course course;
    private Student student;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(courseDao);
    }





    @BeforeEach
    void setUp() {
        course = courseDao.createCourse("Java Advanced", LocalDate.of(2024, 11, 18),10 );
        student = studentDao.createStudent("Linus", "Linus.te@test.nu", "Vintergatan 8");


    }


    @Test
    void create() {
        CreateCourseForm form = new CreateCourseForm(2,"Java Beginner", LocalDate.of(2024, 11, 18), 10);
        CourseView result = testObject.create(form);

        assertNotNull(result);
        assertEquals("Java Beginner", result.getCourseName());
        assertEquals(LocalDate.of(2024,11,18), result.getStartDate());
        assertEquals(10, result.getWeekDuration());

    }

    @Test
    void update() {
        UpdateCourseForm form = new UpdateCourseForm(1, "Java Beginner", LocalDate.of(2025, 11, 18), 10);
        CourseView result = testObject.update(form);

        assertNotNull(result);
        assertEquals("Java Beginner", result.getCourseName());
        assertEquals(LocalDate.of(2025,11,18), result.getStartDate());
    }



    @Test
    void searchByCourseName() {
        List<CourseView> result = testObject.searchByCourseName("Java");

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Java Advanced", result.get(0).getCourseName());
    }

    @Test
    void searchByDateBefore() {
        List<CourseView> result = testObject.searchByDateBefore(LocalDate.of(2024, 12, 1));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Java Advanced", result.get(0).getCourseName());


    }

    @Test
    void searchByDateAfter() {
        List<CourseView> result = testObject.searchByDateAfter(LocalDate.of(2024, 10, 1));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Java Advanced", result.get(0).getCourseName());

    }

    @Test
    void addStudentToCourse() {
        boolean result = testObject.addStudentToCourse(1, 1);

        assertTrue(result);
        assertTrue(course.getStudents().contains(student));

    }

    @Test
    void removeStudentFromCourse() {
        boolean result = testObject.removeStudentFromCourse(1, 1);
        Course updatedCourse = courseDao.findById(1);

        assertTrue(result);
        assertFalse(updatedCourse.getStudents().contains(student));    }

    @Test
    void findById() {
        CourseView result = testObject.findById(1);
        assertNotNull(result);
        assertEquals("Java Advanced", result.getCourseName());
    }

    @Test
    void findAll() {
        List<CourseView> result = testObject.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Java Advanced", result.get(0).getCourseName());
    }


    @Test
    void findByStudentId() {
        // Enroll the student in the course
        course.enrollStudent(student);
        // Print debug statements
        System.out.println("Course: " + course); System.out.println("Student: " + student);
        // Find courses by student ID
        List<CourseView> result = testObject.findByStudentId(student.getId());
        // Print debug statements
        System.out.println("Result: " + result);
        assertEquals("Java Advanced", result.get(0).getCourseName());
    }

    @Test
    void deleteCourse() {
        boolean result = testObject.deleteCourse(1);
        assertTrue(result);
        assertNull(courseDao.findById(1));
    }

    @AfterEach
    void tearDown() {
        courseDao.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
