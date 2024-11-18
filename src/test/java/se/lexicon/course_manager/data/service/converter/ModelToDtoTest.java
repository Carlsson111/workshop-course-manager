package se.lexicon.course_manager.data.service.converter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.dto.views.CourseView;
import se.lexicon.course_manager.dto.views.StudentView;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {ModelToDto.class})
public class ModelToDtoTest {

    @Autowired
    private Converters testObject;

    private Student student;
    private Course course;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
    }

    @BeforeEach
    void setUp() {
        student = new Student(1,"Linus", "Linus.te@test.nu", "Vintergatan 8");
        course = new Course(1, "Java Advanced", LocalDate.of(2024, 11, 18), 10);


    }

    @Test
    void studentToStudentView() {
        StudentView studentView = testObject.studentToStudentView(student);
        assertNotNull(studentView);
        assertEquals(student.getId(), studentView.getId());
        assertEquals(student.getName(), studentView.getName());
        assertEquals(student.getEmail(), studentView.getEmail());
        assertEquals(student.getAddress(), studentView.getAddress());
    }

    @Test
    void courseToCourseView() {
        CourseView courseView = testObject.courseToCourseView(course);
        assertNotNull(courseView);
        assertEquals(course.getId(), courseView.getId());
        assertEquals(course.getCourseName(), courseView.getCourseName());
        assertEquals(course.getStartDate(), courseView.getStartDate());
        assertEquals(course.getWeekDuration(), courseView.getWeekDuration());
        assertEquals(course.getStudents().size(), courseView.getStudents().size());


    }

    @Test
    void coursesToCourseViews() {
        Collection<Course> courses = Collections.singletonList(course);
        List<CourseView> courseViews = testObject.coursesToCourseViews(courses);
        assertNotNull(courseViews);
        assertFalse(courseViews.isEmpty());
        assertEquals(course.getId(), courseViews.get(0).getId());
        assertEquals(course.getCourseName(), courseViews.get(0).getCourseName());

    }

    @Test
    void studentsToStudentViews() {
        Collection<Student> students = Collections.singletonList(student);
        List<StudentView> studentViews = testObject.studentsToStudentViews(students);
        assertNotNull(studentViews);
        assertFalse(studentViews.isEmpty());
        assertEquals(student.getId(), studentViews.get(0).getId());
        assertEquals(student.getName(), studentViews.get(0).getName());
    }


}
