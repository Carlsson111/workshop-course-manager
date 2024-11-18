package se.lexicon.course_manager.data.service.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager.data.dao.StudentDao;
import se.lexicon.course_manager.data.sequencers.StudentSequencer;
import se.lexicon.course_manager.data.service.converter.ModelToDto;
import se.lexicon.course_manager.dto.forms.CreateStudentForm;
import se.lexicon.course_manager.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager.dto.views.StudentView;
import se.lexicon.course_manager.model.Student;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentManager.class, CourseCollectionRepository.class, StudentCollectionRepository.class, ModelToDto.class})
public class StudentManagerTest {

    @Autowired
    private StudentService testObject;
    @Autowired
    private StudentDao studentDao;
    private Student student;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(studentDao);
    }

    // TODO Write your tests here

    @AfterEach
    void tearDown() {
        StudentSequencer.setStudentSequencer(0);
        studentDao.clear();
    }

    @BeforeEach
    void setUp() {
        student = new Student(1,"Linus", "Linus.te@test.nu", "Vintergatan 8");
        studentDao.createStudent(student.getName(), student.getEmail(), student.getAddress());

    }

    @Test
    void create() {
        CreateStudentForm form = new CreateStudentForm(2,"Carl", "Carl.te@test.nu", "Vintergatan 7");
        StudentView result = testObject.create(form);

        assertNotNull(result);
        assertEquals("Carl", result.getName());
        assertEquals("Carl.te@test.nu", result.getEmail());
        assertEquals("Vintergatan 7", result.getAddress());

    }

    @Test
    void update() {
        UpdateStudentForm form = new UpdateStudentForm(1, "Kalle", "Kalle.te@test.nu", "Vintergatan 7");

        assertNotNull(form);
        assertEquals("Kalle", form.getName());
        assertEquals("Kalle.te@test.nu", form.getEmail());
        assertEquals("Vintergatan 7", form.getAddress());

    }

    @Test
    void findById() {
        StudentView result = testObject.findById(1);
        assertNotNull(result);
        assertEquals("Linus", result.getName());

    }

    @Test
    void searchByEmail() {
        StudentView result = testObject.searchByEmail("Linus.te@test.nu");

        assertNotNull(result);
        assertEquals("Linus", result.getName());
    }

    @Test
    void searchByName() {
        List<StudentView> result = testObject.searchByName("Linus");
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Linus", result.get(0).getName());
    }

    @Test
    void findAll() {
        List<StudentView> result = testObject.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Linus", result.get(0).getName());
    }

    @Test
    void deleteStudent() {
        boolean result = testObject.deleteStudent(1);

        assertTrue(result);
        assertNull(studentDao.findById(1));
    }
}
