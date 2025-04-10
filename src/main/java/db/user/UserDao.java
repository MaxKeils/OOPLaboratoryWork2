package db.user;

import db.BaseDaoImpl;
import model.User;

public class UserDao extends BaseDaoImpl<User> {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
