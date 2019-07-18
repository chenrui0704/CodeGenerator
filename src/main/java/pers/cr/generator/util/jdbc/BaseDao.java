package pers.cr.generator.util.jdbc;

import java.sql.*;
import java.util.List;
import java.util.Map;


public class BaseDao {

    private DataSource dataSource;

    private static Connection connection = null;

    private static PreparedStatement preparedStatement = null;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        if(null == this.dataSource){
            try {
                Class.forName(this.dataSource.getDriver());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public BaseDao() {

    }

    public BaseDao(DataSource dataSource){
        this.dataSource = dataSource;
        try {
            Class.forName(this.dataSource.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection(this.dataSource.getUrl(), this.dataSource.getUsername(), this.dataSource.getPassword());
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("MySql 连接失败");
            return null;
        }
    }

    public Connection getConnection2(String url,String username,String pwd) {
        try {
            connection = DriverManager.getConnection(url, username, pwd);
            return connection;
        } catch (SQLException e) {
            System.out.println("MySql 连接失败");
            return null;
        }
    }


    public void closeAll(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> select(String sql) {
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            return ResultData.getDataByResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement, resultSet);
        }
        return null;
    }

    public List<Map<String, Object>> select2(String sql,String url,String username,String pwd) {
        ResultSet resultSet = null;
        try {
            preparedStatement = getConnection2(url,username,pwd).prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            return ResultData.getDataByResult(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement, resultSet);
        }
        return null;
    }


    public void execute(String sql) {
        try {
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement, null);
        }
    }

    public void execute2(String sql,String url,String username,String pwd) {
        try {
            preparedStatement =  getConnection2(url,username,pwd).prepareStatement(sql);
            preparedStatement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(connection, preparedStatement, null);
        }
    }


}
