package Model;

import Controller.IniciarBD_DTO;
import View.MenuIncial_GUI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class VeiculoDAO {

    static String placa;
    static String marca;
    static String modelo;

    static String url = "jdbc:mysql://localhost:3307/ESTACIONAMENTO";
    static String username = "root";
    static String password = "";

    public static void cadastrar(String Placa, String Marca, String Modelo) {

        placa = Placa;
        marca = Marca;
        modelo = Modelo;

        IniciarBD_DTO.carregarDriver();

        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "INSERT INTO VEICULO(veic_placa, veic_marca, veic_modelo, veic_he, veic_status) " + "VALUES('" + placa.toUpperCase() + "','" + marca + "','" + modelo + "', NOW(), 'ATIVO')";

        try {
            PreparedStatement inserir = (PreparedStatement) con.prepareStatement(sql);
            inserir.execute();

            JOptionPane.showMessageDialog(null, "Veículo cadastrado com sucesso!\n", "", -1);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar!", "ERRO!", 0);
        }
    }

    public static void pesquisar(String Placa) {

        placa = Placa;

        IniciarBD_DTO.carregarDriver();

        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sql = "SELECT veic_placa, veic_marca, veic_modelo, veic_he FROM VEICULO " + "WHERE veic_status = 'ATIVO' AND veic_placa = '" + placa.toUpperCase() + "'";

        try {
            PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            MenuIncial_GUI.tabelaRetirarEstatica.setModel(veiculoRetirar(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao pesquisar!", "ERRO!", 0);
        }
    }

    public static void calcularSaida(String Placa) {

        placa = Placa;

        IniciarBD_DTO.carregarDriver();

        Connection con = null;
        try {
            con = (Connection) DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        String sqlBusca = "SELECT veic_placa, veic_marca, veic_modelo, veic_he FROM VEICULO " + "WHERE veic_placa = '" + placa.toUpperCase() + "'";

        try {
            PreparedStatement psBusca = (PreparedStatement) con.prepareStatement(sqlBusca);
            ResultSet rs = psBusca.executeQuery();

            if (rs.next()) {

                java.sql.Timestamp heTimestamp = rs.getTimestamp("veic_he");
                LocalTime horaEntrada = heTimestamp.toLocalDateTime().toLocalTime();
                LocalTime horaSaida = LocalTime.now();

                long minutos = ChronoUnit.MINUTES.between(horaEntrada, horaSaida);
                double horasReais = minutos / 60.0;
                double total;

                if (minutos < 10) {
                    total = 0.00;
                } else {
                    long horas = minutos / 60;
                    if (horas == 0) {
                        horas = 1;
                    }
                    total = horas * 15.00;
                }

                String mensagem = "Placa: " + placa.toUpperCase() + "\nMarca: " + rs.getString("veic_marca") + " \nModelo: " + rs.getString("veic_modelo") + "\nTempo: " + minutos + " min (" + String.format("%.2f", horasReais) + " horas)\nValor por hora: R$ 15,00\n" + "\nTOTAL A COBRAR: R$ " + String.format("%.2f", total) + "\nConfirmar o pagamento?";

                int confirmar = JOptionPane.showConfirmDialog(null, mensagem, "Confirmação de Saída", JOptionPane.YES_NO_OPTION);

                if (confirmar != JOptionPane.YES_OPTION) {
                    return;
                }

                String sqlUpdate = "UPDATE VEICULO SET veic_hs = NOW(), veic_total = " + total + ", veic_status = 'INATIVO' WHERE veic_placa = '" + placa.toUpperCase() + "'";

                try {
                    PreparedStatement psUpdate = (PreparedStatement) con.prepareStatement(sqlUpdate);
                    psUpdate.execute();

                    String sqlCar = "INSERT INTO CAR(veic_placa, car_tipo, car_valor, car_data) " + "VALUES('" + placa.toUpperCase() + "', 'Estacionamento', " + total + ", NOW())";

                    PreparedStatement psCar = (PreparedStatement) con.prepareStatement(sqlCar);
                    psCar.execute();

                    JOptionPane.showMessageDialog(null, "Saída registrada com sucesso", "", -1);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao registrar saída", "ERRO!", 0);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Veículo não encontrado", "Atenção", 1);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar veículo", "ERRO!", 0);
        }
    }

    public static DefaultTableModel veiculo(ResultSet rs) {
        try {
            Vector columnNames = new Vector();
            columnNames.addElement("Placa");
            columnNames.addElement("Marca");
            columnNames.addElement("Modelo");
            columnNames.addElement("Hora Entrada");

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

    public static DefaultTableModel veiculoRetirar(ResultSet rs) {
        try {
            Vector columnNames = new Vector();
            columnNames.addElement("Placa");
            columnNames.addElement("Marca");
            columnNames.addElement("Modelo");
            columnNames.addElement("Hora Entrada");

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
