package src.cn.edu.sdjzu.xg.bysj.dao;

import src.cn.edu.sdjzu.xg.bysj.domain.User;
import src.util.JdbcHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public final class UserDao {
    private static UserDao userDao=
            new UserDao();
    private UserDao(){}
    public static UserDao getInstance(){
        return userDao;
    }
    public User find(Integer id) throws SQLException {
        //声明一个User类型的变量
        User user = null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteDegree_sql = "SELECT * FROM degree WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteDegree_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,username,password,teacher_id值为参数，创建User对象
        if (resultSet.next()){
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
    }
    public User findByUsername(String username) throws SQLException {
        //声明一个User类型的变量
        User user = null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteDegree_sql = "SELECT * FROM user  WHERE username=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteDegree_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,username);
        //执行预编译语句
        ResultSet resultSet = preparedStatement.executeQuery();
        //由于id不能取重复值，故结果集中最多有一条记录
        //若结果集有一条记录，则以当前记录中的id,username,password,teacher_id值为参数，创建User对象
        if (resultSet.next()){
            user = new User(resultSet.getInt("id"),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
        }
        //关闭资源
        JdbcHelper.close(resultSet,preparedStatement,connection);
        return user;
}
public User login(String username, String password)throws  SQLException{
    //声明一个User类型的变量
    User user=null;
    //获得数据库连接对象
    Connection connection = JdbcHelper.getConn();
    //写sql语句
    String User_sql = "SELECT * FROM user WHERE username=?";
    //在该连接上创建预编译语句对象
    PreparedStatement preparedStatement = connection.prepareStatement(User_sql);
    //为预编译参数赋值
    preparedStatement.setString(1,username);
    //执行预编译语句
    ResultSet resultSet = preparedStatement.executeQuery();
    //如果输入用户名原表中有，且密码也一致，则创建User对象并返回user
    if(resultSet!=null&&password.equals(resultSet.getString("password"))){
        user = new User(resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                TeacherDao.getInstance().find(resultSet.getInt("teacher_id")));
    }
    return user;
}
    public User changePassword() throws ClassNotFoundException,SQLException{
        User user=null;
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String changePassword_sql = " update user set password=? where username=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(changePassword_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,user.getPassword());
        preparedStatement.setString(2,user.getUsername());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        ResultSet resultSet = preparedStatement.executeQuery();
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return user;
    }
    public boolean add(User user) throws SQLException,ClassNotFoundException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String addUser_sql = "INSERT INTO user(username,password,teacher_id)VALUES"+" (?,?,?)";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(addUser_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setInt(3,user.getTeacher().getId());
        //执行预编译语句，获取添加记录行数并赋值给affectedRowNum
        int affectedRowNum=preparedStatement.executeUpdate();
        System.out.println("添加了"+affectedRowNum+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRowNum>0;
    }
    public boolean delete(int id) throws ClassNotFoundException,SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String deleteUser_sql = "DELETE FROM user WHERE id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(deleteUser_sql);
        //为预编译参数赋值
        preparedStatement.setInt(1,id);
        //执行预编译语句，获取删除记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("删除了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
    public boolean update(User user) throws ClassNotFoundException,SQLException{
        //获得数据库连接对象
        Connection connection = JdbcHelper.getConn();
        //写sql语句
        String updateUser_sql = " update user set username=?,password=?,teacher_id=? where id=?";
        //在该连接上创建预编译语句对象
        PreparedStatement preparedStatement = connection.prepareStatement(updateUser_sql);
        //为预编译参数赋值
        preparedStatement.setString(1,user.getUsername());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setInt(3,user.getTeacher().getId());
        preparedStatement.setInt(4,user.getId());
        //执行预编译语句，获取改变记录行数并赋值给affectedRowNum
        int affectedRows = preparedStatement.executeUpdate();
        System.out.println("修改了"+affectedRows+"行记录");
        //关闭资源
        JdbcHelper.close(preparedStatement,connection);
        return affectedRows>0;
    }
}