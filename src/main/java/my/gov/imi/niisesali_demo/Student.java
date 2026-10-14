package my.gov.imi.niisesali_demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class Student {
    @Autowired
    private Grades grades;

    public Student(Grades grades) {
        this.grades = grades;
    }
    public void retrieveGrades() {
        grades.getGrades();
    }
}