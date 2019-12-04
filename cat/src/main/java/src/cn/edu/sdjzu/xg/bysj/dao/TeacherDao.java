package src.cn.edu.sdjzu.xg.bysj.dao;

import src.cn.edu.sdjzu.xg.bysj.domain.Degree;
import src.cn.edu.sdjzu.xg.bysj.domain.Department;
import src.cn.edu.sdjzu.xg.bysj.domain.ProfTitle;
import src.cn.edu.sdjzu.xg.bysj.domain.Teacher;
import src.util.JdbcHelper;

import java.sql.*;
import java.util.Collection;
import java.util.TreeSet;

public final class TeacherDao {
	private static TeacherDao teacherDao=
			new TeacherDao();
	private TeacherDao(){}
	public static TeacherDao getInstance(){
		return teacherDao;
	}
	//返回结果集对象
	public Collection<Teacher> findAll(){
		Collection<Teacher> teachers = new TreeSet<Teacher>();
		try{
			//获得数据库连接对象
			Connection connection = JdbcHelper.getConn();
			//在该连接上创建语句盒子对象
			Statement stmt = connection.createStatement();
			//执行SQL查询语句并获得结果集对象
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM teacher");
			//若结果存在下一条，执行循环体
			while (resultSet.next()) {
				//打印结果集中记录的id字段
				System.out.print(resultSet.getInt("id"));
				System.out.print(",");
				//打印结果集中记录的name字段
				System.out.print(resultSet.getString("name"));
				System.out.print(",");
				//打印结果集中记录的profTitle字段
				System.out.print(resultSet.getInt("title_id"));
				System.out.print(",");
				//打印结果集中记录的degree字段
				System.out.print(resultSet.getInt("degree_id"));
				System.out.print(",");
				//打印结果集中记录的department字段
				System.out.print(resultSet.getInt("dept_id"));
				//根据数据库中的数据,创建Teacher类型的对象
				Teacher teacher = new Teacher(resultSet.getInt("id"),
						resultSet.getString("name"),
						ProfTitleDao.getInstance().find(resultSet.getInt("title_id")),
						DegreeDao.getInstance().find(resultSet.getInt("degree_id")),
						DepartmentDao.getInstance().find(resultSet.getInt("dept_id")),
						resultSet.getString("no"));
				//添加到集合teachers中
				teachers.add(teacher);
			}
			connection.close();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return teachers;
	}
	public Teacher find(Integer id) throws SQLException{
		//声明一个Teacher类型的变量
		Teacher teacher = null;
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String deleteTeacher_sql = "SELECT * FROM teacher WHERE id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(deleteTeacher_sql);
		//为预编译参数赋值
		preparedStatement.setInt(1,id);
		//执行预编译语句
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()){
			teacher = new Teacher(resultSet.getInt("id"),
					resultSet.getString("name"),
					ProfTitleDao.getInstance().find(resultSet.getInt("title_id")),
					DegreeDao.getInstance().find(resultSet.getInt("degree_id")),
					DepartmentDao.getInstance().find(resultSet.getInt("dept_id")),
		             resultSet.getString("no"));
		}
		//关闭资源
		JdbcHelper.close(resultSet,preparedStatement,connection);
		return teacher;
	}
	public boolean add(Teacher teacher) throws SQLException,ClassNotFoundException{
		int affectedRows=2;
		Connection connection=null;
		PreparedStatement preparedStatement=null;
	try {
			//获得数据库连接对象
			connection = JdbcHelper.getConn();
			connection.setAutoCommit(false);
			//写sql语句
			String addTeacher_sql = "INSERT INTO teacher (name,title_id,degree_id,dept_id,no) VALUES" + " (?,?,?,?,?)";
			//在该连接上创建预编译语句对象
			preparedStatement = connection.prepareStatement(addTeacher_sql);
			//为预编译参数赋值
			preparedStatement.setString(1, teacher.getName());
			preparedStatement.setInt(2,teacher.getTitle().getId());
			preparedStatement.setInt(3,teacher.getDegree().getId());
			preparedStatement.setInt(4,teacher.getDepartment().getId());
			preparedStatement.setString(5,teacher.getNo());
			preparedStatement.executeUpdate();
			String teacher_id_sql="select max(id) from teacher";
			ResultSet resultSet=preparedStatement.executeQuery(teacher_id_sql);
			int id=0;
			if(resultSet.next()){
				id=resultSet.getInt("max(id)");
			}
		    String addUser_sql = "INSERT INTO user(username,password,teacher_id) VALUES" + "(?,?,?)";
			preparedStatement = connection.prepareStatement(addUser_sql);
			preparedStatement.setString(1, teacher.getNo());
			preparedStatement.setString(2, teacher.getNo());
			preparedStatement.setInt(3, id);
			//执行预编译语句，获取添加记录行数并赋值给affectedRowNum
			 affectedRows=preparedStatement.executeUpdate();
		    System.out.println("增加了"+affectedRows+"行记录");
			connection.commit();
	}catch (SQLException e) {
			System.out.println(e.getMessage()+"\n errorCode="+e.getErrorCode());
			try{
				if (connection!=null){
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
			  try{
				if(connection!=null){
					connection.setAutoCommit(true);
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			JdbcHelper.close(preparedStatement,connection);
		}}
		return affectedRows >0;
	}
	//delete方法，根据teacher的id值，删除数据库中对应的teacher对象
	public boolean delete(int id) throws ClassNotFoundException,SQLException{
		int affectedRows=2;
		//User user=null;
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		try{
		//获得数据库连接对象
		 connection = JdbcHelper.getConn();
		 connection.setAutoCommit(false);
		 //删除user表里的对应的数据
		String  deleteUser_sql="DELETE FROM user WHERE teacher_id=?";
		 preparedStatement=connection.prepareStatement(deleteUser_sql);
		preparedStatement.setInt(1,id);
		//执行预编译语句，获取删除记录行数并赋值给affectedRowNum
		preparedStatement.executeUpdate();
		//写sql语句
			String deleteTeacher_sql = "DELETE FROM teacher WHERE id=?";
			//在该连接上创建预编译语句对象
			preparedStatement = connection.prepareStatement(deleteTeacher_sql);
			//为预编译参数赋值
			preparedStatement.setInt(1,id);
			affectedRows=preparedStatement.executeUpdate();
			System.out.println("删除了"+affectedRows+"行记录");
		   connection.commit();
		}catch (SQLException e) {
			System.out.println(e.getMessage()+"\n errorCode="+e.getErrorCode());
			try{
				if (connection!=null){
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		finally {
			try{
				if(connection!=null){
					connection.setAutoCommit(true);
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			JdbcHelper.close(preparedStatement,connection);
		}}
		return affectedRows>0;
	}
	public boolean update(Teacher teacher) throws ClassNotFoundException,SQLException{
		//获得数据库连接对象
		Connection connection = JdbcHelper.getConn();
		//写sql语句
		String updateDegree_sql = " update teacher set name=?,title_id=?,degree_id=?,dept_id=?,no=? where id=?";
		//在该连接上创建预编译语句对象
		PreparedStatement preparedStatement = connection.prepareStatement(updateDegree_sql);
		//为预编译参数赋值
		preparedStatement.setString(1,teacher.getName());
		preparedStatement.setInt(2,teacher.getTitle().getId());
		preparedStatement.setInt(3,teacher.getDegree().getId());
		preparedStatement.setInt(4,teacher.getDepartment().getId());
		preparedStatement.setInt(5,teacher.getId());
		//执行预编译语句，获取改变记录行数并赋值给affectedRowNum
		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("修改了"+affectedRows+"行记录");
		//关闭资源
		JdbcHelper.close(preparedStatement,connection);
		return affectedRows>0;
	}
	public static void main(String[] args) throws ClassNotFoundException,SQLException{
		Degree degree=DegreeDao.getInstance().find(1);
		System.out.println(degree);
		Department departmrnt= DepartmentDao.getInstance().find(1);
		System.out.println(departmrnt);
		ProfTitle profTitle=ProfTitleDao.getInstance().find(2);
		Teacher teacher=new Teacher(6,"1",profTitle,degree,departmrnt,"01");
		teacherDao.delete(15);
	}

}