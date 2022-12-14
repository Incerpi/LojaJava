package br.com.fiap;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import br.com.fiap.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ListaController implements Initializable{

    @FXML TableView<Usuario> tabela;
    @FXML TableColumn<Usuario, String> colunaNome;
    @FXML TableColumn<Usuario, String> colunaEmail;
    @FXML TableColumn<Usuario, String> colunaPerfil;

    public void abrirCadastroDeUsuario() throws IOException{
        App.setRoot("primary");
    }

    public void carregarDados(){
        String username = "rm93918";
        String pass = "310703";
        String url = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL";

        try {
            Connection conexao = DriverManager.getConnection(url, username, pass);
            Statement stm = conexao.createStatement();

            String sql = "SELECT * FROM USUARIOS";
            ResultSet resultado = stm.executeQuery(sql);

            while(resultado.next()){
               var usuario = new Usuario(
                    resultado.getString("nome"), resultado.getString("email"), resultado.getString("senha"), resultado.getString("perfil"));
                tabela.getItems().add(usuario);
            }

            conexao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));

        carregarDados();        
    }
    
}
