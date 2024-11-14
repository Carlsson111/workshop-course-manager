package se.lexicon.course_manager.data.dao;



import se.lexicon.course_manager.data.sequencers.StudentSequencer;
import se.lexicon.course_manager.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;



public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        int id = StudentSequencer.nextStudentId();
        Student newStudent = new Student(id, name, email, address);
        if (students.add(newStudent)){
            return newStudent;
        }
        return null;
    }


    @Override
    public Student findByEmailIgnoreCase(String email) {
        for (Student student : students){
            if (student.getEmail().trim().equalsIgnoreCase(email)){
                return student;
            }
        }

        return null;
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        Collection<Student> result = new ArrayList<>();
        for (Student s : students){
            if (s.getName().trim().toLowerCase().contains(name.trim().toLowerCase())){
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public Student findById(int id) {
        for (Student student: students){
            if (student.getId() == id){
                return student;
            }
        }
        return null;
    }

    @Override
    public Collection<Student> findAll() {
        return new HashSet<>(students);
    }

    @Override
    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }
}
