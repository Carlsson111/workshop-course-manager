package se.lexicon.course_manager.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager.data.sequencers.StudentSequencer;
import se.lexicon.course_manager.model.Student;


import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {
    private StudentCollectionRepository repository;
    private Student student1;
    private Student student2;


    @Autowired
    private StudentDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    // TODO Write your tests here


    @BeforeEach
    void setUp() {
        Collection<Student> students= new HashSet<>();
        repository = new StudentCollectionRepository(students);
        student1= new Student(1,"Linus", "Linus.te@test.nu", "Vintergatan 8");
        student2= new Student(2,"Carl", "Carl.te@test.nu", "Vintergatan 7");
        students.add(student1);
        students.add(student2);

    }

    @Test
    void createStudentTest() {
        Student newStudent= repository.createStudent("Kalle","Kalle.te@test.nu","Vintergatan 6");
        assertEquals("Kalle", newStudent.getName());
        assertEquals("Kalle.te@test.nu", newStudent.getEmail());
        assertEquals("Vintergatan 6", newStudent.getAddress());

    }

    @Test
    void findByEmailIgnoreCaseTest() {
        Student foundS = repository.findByEmailIgnoreCase("Linus.te@test.nu");
        assertEquals("Linus", foundS.getName());

    }

    @Test
    void findByNameContainsTest() {
        Collection<Student> foundStudents = repository.findByNameContains("Linus");
        assertEquals(1, foundStudents.size());
    }

    @Test
    void findByIdTest() {
        Student foundS = repository.findById(1);
        assertEquals("Linus", foundS.getName());
    }

    @Test
    void findAllTest() {
        Collection<Student> allS= repository.findAll();
        assertEquals(2, allS.size());

    }

    @Test
    void removeStudentTest() {
        boolean removed = repository.removeStudent(student1);
        assertTrue(removed);
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void clearTest() {
        repository.clear();
        assertEquals(0, repository.findAll().size());
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
