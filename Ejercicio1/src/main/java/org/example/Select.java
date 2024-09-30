package org.example;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Select {
    public static void main(String[] args){
        //registrar el driver
        String driver = "org.apache.derby.jdbc.EmbeddedDriver";

        //
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
            throw new RuntimeException();
        }

        String uri = "jdbc:derby:MyDB;create=true";

        try {
            Connection conn = DriverManager.getConnection(uri);
            String select = "SELECT * FROM persona"; //este es un select basico, pero se puede hacer uno mas complejo
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery(); //nos devuelve un resultSet
            while(rs.next()){
                System.out.println(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3));
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
