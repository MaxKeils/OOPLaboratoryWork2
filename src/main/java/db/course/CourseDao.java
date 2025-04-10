package db.course;

import db.BaseDaoImpl;
import model.Course;

public class CourseDao extends BaseDaoImpl<Course> {


    @Override
    protected Class<Course> getEntityClass() {
        return Course.class;
    }
}
