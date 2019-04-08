package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionClass {


    private static String url = "jdbc:mysql://localhost:3306/olivedb";
    private static String user = "root";
    private static String pass = "";

    Connection connector; //Must return type of ccnnection

    public Connection connect()  {

        try {

            //register driver
            Class.forName("com.mysql.jdbc.Driver");

            //open connection
            connector = DriverManager.getConnection(url,user,pass);

            if (connector != null){
                System.out.println("Connection Successful");
            }else{
                System.out.println("Db Connection not successful");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            //return;


        }

        //I wont forget you costed me a whole 12hr to fix
        return connector;

    }


}
