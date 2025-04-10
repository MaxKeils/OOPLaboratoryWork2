package db.enrollment;

import db.BaseDaoImpl;
import model.Enrollment;

public class EnrollmentDao extends BaseDaoImpl<Enrollment> {
    @Override
    protected Class<Enrollment> getEntityClass() {
        return Enrollment.class;
    }
}
