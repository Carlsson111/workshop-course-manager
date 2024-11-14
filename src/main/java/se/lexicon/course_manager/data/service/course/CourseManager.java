package se.lexicon.course_manager.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager.data.dao.CourseDao;
import se.lexicon.course_manager.data.dao.StudentDao;
import se.lexicon.course_manager.data.service.converter.Converters;
import se.lexicon.course_manager.dto.forms.CreateCourseForm;
import se.lexicon.course_manager.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager.dto.views.CourseView;
import se.lexicon.course_manager.model.Course;
import se.lexicon.course_manager.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        Course course = courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration());
        return new CourseView(course.getId(),course.getCourseName(), course.getStartDate(), course.getWeekDuration(), converters.studentsToStudentViews(course.getStudents()));

    }

    @Override
    public CourseView update(UpdateCourseForm form) {
        Course course = courseDao.findById(form.getId());

        if(course == null) return null;
        course.setCourseName(form.getCourseName());
        course.setStartDate(form.getStartDate());

        return converters.courseToCourseView(course);
    }


    @Override
    public List<CourseView> searchByCourseName(String courseName) {

        Collection<Course> courses = courseDao.findByNameContains(courseName);
        return converters.coursesToCourseViews(courses);
    }

    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        Collection<Course> courses = courseDao.findByDateBefore(end);
        return converters.coursesToCourseViews(courses);

    }

    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {

        Collection<Course> courses = courseDao.findByDateAfter(start);

        return converters.coursesToCourseViews(courses);

    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        Course course = courseDao.findById(courseId);
        Student student = studentDao.findById(studentId);

        if(course == null || student == null) return false;
        else{
            course.enrollStudent(student);
            return true;
        }
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        Course course = courseDao.findById(courseId);
        Student student = studentDao.findById(studentId);

        if(course == null || student == null) return false;
        else{
            course.unrollStudent(student);
            return true;
        }
    }

    @Override
    public CourseView findById(int id) {
        Course course = courseDao.findById(id);

        if(course == null) return null;
        else return converters.courseToCourseView(course);

    }

    @Override
    public List<CourseView> findAll() {
        return converters.coursesToCourseViews(courseDao.findAll());
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        return converters.coursesToCourseViews(courseDao.findByStudentId(studentId));
    }

    @Override
    public boolean deleteCourse(int id) {
        Course courseToRemove = courseDao.findById(id);

        if (courseToRemove == null)return false;
        else {
            courseDao.removeCourse(courseToRemove);
            return true;
        }
    }
}
