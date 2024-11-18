package se.lexicon.course_manager.data.service.converter;

import org.springframework.stereotype.Component;
import se.lexicon.course_manager.dto.views.CourseView;
import se.lexicon.course_manager.dto.views.StudentView;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;


import java.util.*;



@Component
public class ModelToDto implements Converters {
    @Override
    public StudentView studentToStudentView(Student student) {
        if (student==null) return null;
        return new StudentView(student.getId(), student.getName(), student.getEmail(), student.getAddress());
    }

    @Override
    public CourseView courseToCourseView(Course course) {
        if (course==null) return null;
        return  new CourseView(course.getId(), course.getCourseName(), course.getStartDate(), course.getWeekDuration(), studentsToStudentViews(course.getStudents()));
    }

    @Override
    public List<CourseView> coursesToCourseViews(Collection<Course> courses) {

        if (courses== null) return null;

        List<CourseView> courseViews = new ArrayList<>();
        for (Course course: courses){
            courseViews.add(courseToCourseView(course));

        }
        return courseViews;

    }

    @Override
    public List<StudentView> studentsToStudentViews(Collection<Student> students) {
        if (students == null) return null;

        List <StudentView> studentViews = new ArrayList<>();
        for (Student s : students){
            studentViews.add(studentToStudentView(s));
        }
        return studentViews;
    }

}
