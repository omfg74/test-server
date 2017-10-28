package dbworker;

import Objects.RegistrationData;

import java.sql.*;

public class DBWorker {
Connection connectionToBD = null;
String jdbc = "jdbc:postgresql://localhost:5432/testdb";
String dbuser = "postgres";
String passwd = "101541";
String dbname = "testdb";
String tablename = "testtable";
    public  Connection getConnection(){
        Connection connection = null;
        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/"
                    ,"postgres"
                    ,"101541");
//createDB(connection);


        }catch (SQLException e){
            e.printStackTrace();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ;
        if(null!=connection){
            System.out.println("Connected to db");
        }else {
            System.out.println("Not conncted to db");
        }
        return connection;
        }
        public void createDB(Connection connection){
            try {
                if(connection!=null) {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("CREATE DATABASE testdb");
                    connection.close();
                }else {
                    System.out.println("Error creating db");
                    connection.close();
                    return;
                }
            } catch (SQLException e) {
                System.out.println("db already created");
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            }

        }
        public Connection workConnection(){
            Connection connection= null;
            try {
                 connection = DriverManager.getConnection(
                        "jdbc:postgresql://localhost:5432/testdb"
                        ,"postgres"
                        ,"101541");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }

    public void createTables() {
            Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb"
                    ,"postgres"
                    ,"101541");
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS testtable (name VARCHAR," +
                    "surmane VARCHAR," +
                    "login VARCHAR," +
                    "password VARCHAR" +
                    ");");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewUser(RegistrationData registrationData) {
        try {
          Connection  connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb"
                    ,"postgres"
                    ,"101541");


          connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Boolean checkIfUserExists(String login) {
            boolean exists =false;
        try {
            Connection  connection = DriverManager.getConnection(jdbc,dbuser,passwd);

            Statement statement = connection.createStatement();
            ResultSet result1 = statement.executeQuery("SELECT * FROM TABLE "+tablename+" WHERE Login = "+login);
           if (result1.next()){
             exists=true;
            } else {
               exists=false;
           }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }
}



