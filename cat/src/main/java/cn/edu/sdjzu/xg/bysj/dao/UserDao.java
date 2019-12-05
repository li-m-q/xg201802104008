package cn.edu.sdjzu.xg.bysj.dao;

import cn.edu.sdjzu.xg.bysj.domain.User;
import util.JdbcHelper;

import java.sql.*;
public class UserDao {
    private static UserDao userDao = new UserDao();
    private UserDao(){
    }
    public static UserDao getInstance() {
        return userDao;
    }
    public User find(Integer id) throws SQLException {
        //声明一个Degree类型的变量
        User user = null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String findUser_sql = "select * from user where id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(findUser_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1, id);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,description,no,remarks值为参数，创建Department对象
        //若结果集中没有记录，则本方法返回null
        if (resultSet.next()) {
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return user;
    }

    public boolean add(User user) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String addUser_sql = "INSERT INTO user(username,password,teacher_id) VALUES" + " (?,?,?)";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(addUser_sql);
        //为预编译参数赋值
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(4, user.getTeacher().getId());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum = preparedStatement.executeUpdate();
        System.out.println("添加了" + affectedRowNum + "行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRowNum > 0;
    }

    //delete方法，根据department的id值，删除数据库中对应的department对象
    public boolean delete(int id) throws  SQLException {
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteUser_sql = "DELETE FROM user WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteUser_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1, id);
        //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("删除了" + affectedRows + "行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows > 0;
    }

    public boolean update(User user) throws  SQLException {
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateUser_sql = "update user set password where id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateUser_sql);
        //为预编译参数赋值
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(4, user.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("修改了" + affectedRows + "行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows > 0;
    }
    public boolean changePassword() throws SQLException{
        User user = null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateUser_sql = "update user set password where username=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateUser_sql);
        //为预编译参数赋值
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("修改了" + affectedRows + "行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement, connection);
        return affectedRows > 0;
    }

    public User findByUsername(String username) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String username_sql = "SELECT * FROM user WHERE username=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(username_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,username);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                   TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return user;
    }

    public User login(String username, String password) throws SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String login_sql = "SELECT * FROM user WHERE username=? and password=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(login_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet, preparedStatement, connection);
        return user;
    }
}
