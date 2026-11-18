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

public class Cap_DAO {

    static String tipo;
    static String valor;

    static String url = "jdbc:mysql://localhost:3307/ESTACIONAMENTO";
    static String username = "root";
    static String password = "";

    public static void registrar(String Tipo, String Valor) {

        tipo  = Tipo;
        valor = Valor;

        IniciarBD_DTO.carregarDriver();

        try {
            Connection con = null;
            try {
                con = (Connection) DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                Logger.getLogger(Cap_DAO.class.getName()).log(Level.SEVERE, null, ex);
            }

            String sql = "INSERT INTO CAP(cap_tipo, cap_valor, cap_data) " + "VALUES('" + tipo + "', " + valor + ", NOW())";

            try {
                PreparedStatement inserir = (PreparedStatement) con.prepareStatement(sql);
                inserir.execute();

                JOptionPane.showMessageDialog(null, "Despesa registrada com sucesso", "", -1);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao registrar despesa!", "ERRO", 0);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null, "Erro de conexão", "ERRO", 0);
        }
    }

    public static DefaultTableModel tabelaCap(ResultSet rs) {
        try {
            Vector columnNames = new Vector();
            columnNames.addElement("Código");
            columnNames.addElement("Descrição");
            columnNames.addElement("Valor");
            columnNames.addElement("Data");

            Vector rows = new Vector();
            while (rs.next()) {
                Vector newRow = new Vector();
                newRow.addElement(rs.getObject(1));
                newRow.addElement(rs.getObject(2));
                newRow.addElement(rs.getObject(3));
                newRow.addElement(rs.getObject(4));
                rows.addElement(newRow);
            }
            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            return null;
        }
    }
}