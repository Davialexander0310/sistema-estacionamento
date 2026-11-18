package Model;

import Controller.IniciarBD_DTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Car_DAO {

    static String url = "jdbc:mysql://localhost:3307/ESTACIONAMENTO";
    static String username = "root";
    static String password = "";

    public static DefaultTableModel tabelaCar(ResultSet rs) {
        try {
            Vector columnNames = new Vector();
            columnNames.addElement("Código");
            columnNames.addElement("Placa");
            columnNames.addElement("Tipo");
            columnNames.addElement("Valor");
            columnNames.addElement("Data");

            Vector rows = new Vector();
            while (rs.next()) {
                Vector newRow = new Vector();
                newRow.addElement(rs.getObject(1));
                newRow.addElement(rs.getObject(2));
                newRow.addElement(rs.getObject(3));
                newRow.addElement(rs.getObject(4));
                newRow.addElement(rs.getObject(5));
                rows.addElement(newRow);
            }
            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            return null;
        }
    }
}