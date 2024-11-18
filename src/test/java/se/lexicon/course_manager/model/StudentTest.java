package se.lexicon.course_manager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class StudentTest {
    // TODO Implement your tests here
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student(1, "Linus", "linus.tes@test.nu", "Vintergatan 8");

    }
    @Test
    public void constructorTest(){
        Student student = new Student(1, "Linus", "linus.tes@test.nu", "Vintergatan 8");
        assertEquals(1, student.getId());
        assertEquals("Linus", student.getName());
        assertEquals("linus.tes@test.nu", student.getEmail());
        assertEquals("Vintergatan 8", student.getAddress());
    }

    @Test
    public void EqualsAndHashCodeTest(){
        Student student1 = new Student(1, "Linus", "linus.tes@test.nu", "Vintergatan 8");
        Student student2 = new Student(1, "Linus", "linus.tes@test.nu", "Vintergatan 8");
        Student student3 = new Student(2, "Carl", "Carl.tes@test.nu", "Vintergatan 7");
        assertEquals(student1, student2);
        assertNotEquals(student1, student3);
        assertEquals(student1.hashCode(), student2.hashCode());
        assertNotEquals(student1.hashCode(), student3.hashCode());

    }
    @Test
    public void toStringTest(){
        String expected="Student{id=1, name='Linus', email='linus.tes@test.nu', address='Vintergatan 8'}";
        assertEquals(expected, student.toString());
    }

}
