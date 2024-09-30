package org.example;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class Select {
    public static void main(String[] args){
        //registrar el driver
        String driver = "com.mysql.cj.jdbc.Driver";

        //
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();  //pedimos el driver, el contructor y una nueva intancia del driver
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
            //throw new RuntimeException();
        }

        /* para conectarnos a la bd, definimos
        la direccion de la base de datos (uri)
         */
        String uri = "jdbc:mysql://localhost:3306/dbArqui";

        //definida la uri, creamos la conexion
        try {
            Connection conn = DriverManager.getConnection(uri,"root", "rp");
            conn.setAutoCommit(false);
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
