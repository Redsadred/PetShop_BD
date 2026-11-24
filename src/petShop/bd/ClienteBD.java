package src.petShop.bd;

import java.sql.*;
import java.util.*;
import src.petShop.model.Cliente;

public class ClienteBD {

    public boolean cadastrar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nomeCliente) VALUES (?)";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                Cliente cliente = new Cliente(
                        resultado.getString("nomeCliente"));

                cliente.setIdCliente(resultado.getInt("idCliente"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nomeCliente = ? WHERE idCliente = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNomeCliente());
            stmt.setInt(2, cliente.getIdCliente());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int idCliente) {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrarTelefone(int idCliente, String telefone) {
        String sql = "INSERT INTO telefone_cliente (telefoneCliente, idCliente) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, telefone);
            stmt.setInt(2, idCliente);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> listarTelefones(int idCliente) {
        List<String> telefones = new ArrayList<>();

        String sql = "SELECT telefoneCliente FROM telefone_cliente WHERE idCliente = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet resultado = stmt.executeQuery()) {
                while (resultado.next()) {
                    telefones.add(resultado.getString("telefoneCliente"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return telefones;
    }

    public boolean cadastrarComTelefone(Cliente cliente, String telefone) {

        String sqlCliente = "INSERT INTO cliente (nomeCliente) VALUES (?)";
        String sqlTelefone = "INSERT INTO telefone_cliente (telefoneCliente, idCliente) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar()) {

            conexao.setAutoCommit(false);

            try {
                int idCliente;

                // Cadastra o cliente e recupera o ID gerado
                try (PreparedStatement stmt = conexao.prepareStatement(
                        sqlCliente, Statement.RETURN_GENERATED_KEYS)) {

                    stmt.setString(1, cliente.getNomeCliente());
                    stmt.executeUpdate();

                    try (ResultSet resultado = stmt.getGeneratedKeys()) {

                        if (!resultado.next()) {
                            throw new SQLException("Não foi possível obter o ID do cliente.");
                        }

                        idCliente = resultado.getInt(1);
                    }
                }

                // Cadastra o telefone usando o ID do cliente
                try (PreparedStatement stmt = conexao.prepareStatement(sqlTelefone)) {

                    stmt.setString(1, telefone);
                    stmt.setInt(2, idCliente);
                    stmt.executeUpdate();
                }

                conexao.commit();
                return true;

            } catch (SQLException e) {

                conexao.rollback();
                throw e;

            }

        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarTelefone(int idCliente, String telefone) {

        String sql = "UPDATE telefone_cliente SET telefoneCliente = ? WHERE idCliente = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, telefone);
            stmt.setInt(2, idCliente);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
