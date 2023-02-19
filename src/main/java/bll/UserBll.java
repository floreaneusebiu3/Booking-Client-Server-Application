package bll;

import dao.UserDao;
import model.User;

public class UserBll {
   private UserDao userDao;
   public UserBll() {
      userDao = new UserDao();
   }
   public void insertUser(User user)
   {
      userDao.insert(user);
   }
}
