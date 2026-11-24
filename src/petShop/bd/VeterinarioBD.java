
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
                    resultado.getString("especialidadeVeterinario")
                );

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
}
