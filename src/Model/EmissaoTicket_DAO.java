/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author alunos
 */
public class EmissaoTicket_DAO {
    public static void imprimir(String placa, String marca, String modelo) {
        LocalDateTime agora = LocalDateTime.now();
        String data = agora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String hora = agora.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        
        Frame f = new Frame("Imp");
        Toolkit tk = f.getToolkit();
        PrintJob pj = tk.getPrintJob(f, "Ticket", null);
        
        if (pj != null) {
            Graphics m = pj.getGraphics();

            m.drawString("Placa: " + placa.toUpperCase(), 10, 40);
            m.drawString("Marca: " + marca, 10, 60);
            m.drawString("Modelo: " + modelo, 10, 80);
            m.drawString("Data: " + data, 10, 100);
            m.drawString("Hora: " + hora, 10, 120);
            
            m.dispose();
            pj.end();
        }
        f.dispose();
    }
     
}
