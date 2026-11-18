package Model;

import View.TelaSplash_GUI;
import static View.TelaSplash_GUI.barra;
import static View.TelaSplash_GUI.texto;
import View.TelaLogin_GUI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Splash_DAO {
    public static void carrega() {

    new Thread() {
        public void run() {

            for (int i = 0; i < 101; i++) {
                try {
                    sleep(60);

                    barra.setValue(i);

                    if (barra.getValue() == 10) {
                        texto.setText("Fazendo a conexão com o banco de dados");
                        Controller.IniciarBD_DTO.carregarDriver();
                        sleep(2000);

                    } else if (barra.getValue() <= 30) {
                        texto.setText("Carregando o sistema");
                        sleep(100);

                    } else if (barra.getValue() <= 99) {
                        texto.setText("Carregamento quase completo");

                    } else {
                        texto.setText("Carregamento completo. Seu programa será iniciado.");
                        sleep(3000);
                        new TelaLogin_GUI().setVisible(true);
                        }
                 

                } catch (InterruptedException ex) {
                    Logger.getLogger(TelaSplash_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }.start();
}

}
