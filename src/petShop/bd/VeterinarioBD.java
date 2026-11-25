
package src.petShop.bd;

import java.sql.*;
import java.util.*;

import src.petShop.model.Veterinario;

public class VeterinarioBD {

    public boolean cadastrar(Veterinario veterinario) {
        String sql = "INSERT INTO veterinario (nomeVeterinario, especialidadeVeterinario) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, veterinario.getNomeVeterinario());
            stmt.setString(2, veterinario.getEspecialidadeVeterinario());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Veterinario> listar() {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM veterinario";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                Veterinario veterinario = new Veterinario(
                        resultado.getString("nomeVeterinario"),
                        resultado.getString("especialidadeVeterinario"));

                veterinario.setIdVeterinario(resultado.getInt("idVeterinario"));
                veterinarios.add(veterinario);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veterinarios;
    }

    public boolean atualizar(Veterinario veterinario) {
        String sql = "UPDATE veterinario SET nomeVeterinario = ?, especialidadeVeterinario = ? WHERE idVeterinario = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, veterinario.getNomeVeterinario());
            stmt.setString(2, veterinario.getEspecialidadeVeterinario());
            stmt.setInt(3, veterinario.getIdVeterinario());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int idVeterinario) {
        String sql = "DELETE FROM veterinario WHERE idVeterinario = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idVeterinario);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cadastrarTelefone(int idVeterinario, String telefone) {
        String sql = "INSERT INTO telefone_veterinario (telefoneVeterinario, idVeterinario) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, telefone);
            stmt.setInt(2, idVeterinario);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> listarTelefones(int idVeterinario) {
        List<String> telefones = new ArrayList<>();

        String sql = "SELECT telefoneVeterinario FROM telefone_veterinario WHERE idVeterinario = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idVeterinario);

            try (ResultSet resultado = stmt.executeQuery()) {
                while (resultado.next()) {
                    telefones.add(resultado.getString("telefoneVeterinario"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return telefones;
    }

    public boolean cadastrarComTelefone(Veterinario veterinario, String telefone) {

        String sqlVeterinario = "INSERT INTO veterinario (nomeVeterinario, especialidadeVeterinario) VALUES (?, ?)";
        String sqlTelefone = "INSERT INTO telefone_veterinario (telefoneVeterinario, idVeterinario) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar()) {

            conexao.setAutoCommit(false);

            try {
                int idVeterinario;

                try (PreparedStatement stmt = conexao.prepareStatement(
                        sqlVeterinario, Statement.RETURN_GENERATED_KEYS)) {

                    stmt.setString(1, veterinario.getNomeVeterinario());
                    stmt.setString(2, veterinario.getEspecialidadeVeterinario());
                    stmt.executeUpdate();

                    try (ResultSet resultado = stmt.getGeneratedKeys()) {
                        if (!resultado.next()) {
                            throw new SQLException("Não foi possível obter o ID.");
                        }

                        idVeterinario = resultado.getInt(1);
                    }
                }

                try (PreparedStatement stmt = conexao.prepareStatement(sqlTelefone)) {
                    stmt.setString(1, telefone);
                    stmt.setInt(2, idVeterinario);
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

    public boolean atualizarTelefone(int idVeterinario, String telefone) {

        String sql = "UPDATE telefone_veterinario SET telefoneVeterinario = ? WHERE idVeterinario = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, telefone);
            stmt.setInt(2, idVeterinario);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                return true;
            }

            return cadastrarTelefone(idVeterinario, telefone);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
