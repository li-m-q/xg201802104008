package cn.edu.sdjzu.xg.bysj.service;

import cn.edu.sdjzu.xg.bysj.dao.UserDao;
import cn.edu.sdjzu.xg.bysj.domain.User;

import java.sql.SQLException;

public class UserService {
    private static UserDao userDao= UserDao.getInstance();
    private static UserService userService=new UserService();
    private UserService(){}
    public static UserService getInstance(){
        return userService;
    }

    public User find(Integer id)throws SQLException {
        return userDao.find(id);
    }
    public User findByUsername(String username) throws SQLException{
        return userDao.findByUsername(username);
    }
    public User login(String username,String password) throws SQLException{
        return userDao.login(username,password);
    }
    public boolean changePassword() throws SQLException {
        return  userDao.changePassword();
    }
    public boolean update(User user)throws SQLException {
        return userDao.update(user);
    }
    public boolean add(User user)throws SQLException{
        return userDao.add(user);
    }
    public boolean delete(Integer id)throws SQLException{
        return userDao.delete(id);
    }
}
