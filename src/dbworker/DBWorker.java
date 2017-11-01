package dbworker;

import Objects.RegistrationData;
import Objects.Task;
import com.sun.org.apache.regexp.internal.RE;

import java.sql.*;
import java.util.ArrayList;

public class DBWorker {
Connection connectionToBD = null;
String jdbc = "jdbc:postgresql://localhost:5432/testdb";
String dbuser = "postgres";
String passwd = "101541";
String dbname = "testdb";
String tablename = "testtable";
String tasktable = "tasktable";
    public  Connection getConnection(){
        Connection connection = null;
        try{
            DriverManager.registerDriver(new org.postgresql.Driver());
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/"
                    ,dbuser
                    ,passwd);
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
                 connection = DriverManager.getConnection(jdbc,dbuser,passwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }

    public void createTables() {
            Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbc,dbuser,passwd);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS testtable (name VARCHAR," +
                    "surname VARCHAR," +
                    "login VARCHAR," +
                    "password VARCHAR" +
                    ");");
            System.out.println("TestTable created....");
            Statement statement1 = connection.createStatement();
            statement1.executeUpdate("CREATE TABLE IF NOT EXISTS "+tasktable+" (id SERIAL," +
                    "login VARCHAR," +
                    "task VARCHAR," +
                            "status VARCHAR," +
                            "result VARCHAR" +
                            ");");
            System.out.println("TaskTable created...");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createNewUser(RegistrationData registrationData) {
        try {
          Connection  connection = DriverManager.getConnection(jdbc,dbuser,passwd);
          Statement statement = connection.createStatement();
          statement.executeUpdate("INSERT INTO "+tablename+" (NAME,SURNAME,LOGIN,PASSWORD) " +
                  " VALUES ('" +registrationData.getName()+"' , '"+registrationData.getSurname()+"' , '"
                  +registrationData.getLogin()+"' , '"+registrationData.getPassword()+"' );");

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
            ResultSet result1 = statement.executeQuery("SELECT * FROM "+tablename+" WHERE Login = '"+login+"'");
          while (result1.next()){
             String lo = result1.getString("Login");
             if(lo.equalsIgnoreCase(login)){
               exists=true;
            } else {
               exists=false;
           }
          }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public boolean authorise(RegistrationData user) {
            boolean autorised = false;
        try {
            Connection  connection = DriverManager.getConnection(jdbc,dbuser,passwd);
            Statement statement = connection.createStatement();
            ResultSet result1 = statement.executeQuery("SELECT password FROM "
                    +tablename+
                    " WHERE Login = '"
                    +user.getLogin()+"'");
//
            while (result1.next()){
                String pas = result1.getString("password");

                if (pas.equals(user.getPassword())){
                    autorised=true;
                }else {
                    autorised=false;
                }
            }
        } catch (SQLException e) {
            autorised=false;
            e.printStackTrace();

        }


        return autorised;
    }

    public RegistrationData getNameSurname(RegistrationData user) {
        try {
            Connection  connection = DriverManager.getConnection(jdbc,dbuser,passwd);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+tablename+" WHERE Login = '"+user.getLogin()+"'");
           while (resultSet.next()){
               user.setName(resultSet.getString("name"));
               user.setSurname(resultSet.getString("surname"));
           }
    } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Long createNewTask(Task task) {
        Connection  connection = null;
        long id =0L;
        try {
            connection = DriverManager.getConnection(jdbc,dbuser,passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
           PreparedStatement statement= connection.prepareStatement("INSERT INTO "+tasktable+
                   " (LOGIN,TASK,STATUS,RESULT)"+
                    " VALUES(?,?,?,? ) RETURNING id;");
           statement.setString(1,task.getLogin());
           statement.setString(2,task.getName());
           statement.setString(3,task.getStatus());
           statement.setString(4,task.getResult());
           statement.execute();
           ResultSet resultSet = statement.getResultSet();
           if(resultSet.next()) {
               id = resultSet.getLong(1);
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void updateTaskStatus(Task task1) {
//        System.out.println(task1.getStatus());

        Connection  connection = null;

        try {
            connection = DriverManager.getConnection(jdbc,dbuser,passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            PreparedStatement statement= connection.prepareStatement("UPDATE "+tasktable+" SET status "+"=  '"+task1.getStatus()+
                    "' WHERE id = "+task1.getId()+";");
//
            statement.executeUpdate();
//
//            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public ArrayList createList(RegistrationData user) {
        ArrayList<Task> tasks=null;
        Connection  connection = null;

        try {
            connection = DriverManager.getConnection(jdbc,dbuser,passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+tasktable+" WHERE Login = '"+user.getLogin()+"'");
            tasks = new ArrayList<>();
            while (resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("task"));
                task.setLogin(resultSet.getString("login"));
                task.setStatus(resultSet.getString("status"));
                task.setResult(resultSet.getString("result"));
                tasks.add(task);
//                System.out.println(task.getId()+task.getStatus());
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;

    }
}



