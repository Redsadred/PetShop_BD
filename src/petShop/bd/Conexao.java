package src.petShop.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.util.Properties;

public class Conexao {

    public static Connection conectar() {
        try {
            Properties propriedades = new Properties();
            propriedades.load(new FileInputStream("config.properties"));

            String url = propriedades.getProperty("db.url");
            String usuario = propriedades.getProperty("db.usuario");
            String senha = propriedades.getProperty("db.senha");

            return DriverManager.getConnection(url, usuario, senha);

        } catch (Exception e) {
            throw new RuntimeException("Erro na conexão: " + e.getMessage());
        }
    }
}