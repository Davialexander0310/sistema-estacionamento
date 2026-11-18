package Model;

import static View.TelaLogin_GUI.senhaTelaLogin_txt;
import static View.TelaLogin_GUI.usuarioTelaLogin_txt;

public class VerificarLogin_DAO {

    private static String loginAdm  = "Gerente";
    private static String senhaAdm  = "admin";

    private static String loginFunc = "Funcionario";
    private static String senhaFunc = "abc1234";

    public static String login = "";
    public static String senha = "";

    public static boolean verificarLogin() {

        login = usuarioTelaLogin_txt.getText();
        senha = new String(senhaTelaLogin_txt.getPassword());

        if (login.equals(loginAdm) && senha.equals(senhaAdm)) {
            return true;
        }

        if (login.equals(loginFunc) && senha.equals(senhaFunc)) {
            return true;
        }

        return false;
    }

    public static boolean verificarTelasMostrar() {

        if (login.equals(loginAdm) && senha.equals(senhaAdm)) {
            return true;
        }

        return false;
    }

    public static void limparCampos() {
        usuarioTelaLogin_txt.setText("");
        senhaTelaLogin_txt.setText("");
    }
}