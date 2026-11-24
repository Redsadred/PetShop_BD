
package src.petShop.bd;

import java.sql.*;
import java.util.*;
import java.math.*;
import java.time.*;

import src.petShop.model.Procedimento;

public class ProcedimentoBD {

    public boolean cadastrar(Procedimento procedimento) {
        String sql = "INSERT INTO procedimento (nomeProcedimento, descricaoProcedimento, valorProcedimento, dataProcedimento, idAnimal, idVeterinario) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, procedimento.getNomeProcedimento());
            stmt.setString(2, procedimento.getDescricaoProcedimento());
            stmt.setBigDecimal(3, procedimento.getValorProcedimento());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(procedimento.getDataProcedimento()));
            stmt.setInt(5, procedimento.getIdAnimal());
            stmt.setInt(6, procedimento.getIdVeterinario());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Procedimento> listar() {
        List<Procedimento> procedimentos = new ArrayList<>();
        String sql = "SELECT * FROM procedimento";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                LocalDateTime data = resultado.getTimestamp("dataProcedimento").toLocalDateTime();

                Procedimento procedimento = new Procedimento(
                    resultado.getString("nomeProcedimento"),
                    resultado.getString("descricaoProcedimento"),
                    resultado.getBigDecimal("valorProcedimento"),
                    data,
                    resultado.getInt("idAnimal"),
                    resultado.getInt("idVeterinario")
                );

                procedimento.setIdProcedimento(resultado.getInt("idProcedimento"));
                procedimentos.add(procedimento);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return procedimentos;
    }

    public boolean atualizar(Procedimento procedimento) {
        String sql = "UPDATE procedimento SET nomeProcedimento = ?, descricaoProcedimento = ?, valorProcedimento = ?, dataProcedimento = ?, idAnimal = ?, idVeterinario = ? WHERE idProcedimento = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, procedimento.getNomeProcedimento());
            stmt.setString(2, procedimento.getDescricaoProcedimento());
            stmt.setBigDecimal(3, procedimento.getValorProcedimento());
            stmt.setTimestamp(4, java.sql.Timestamp.valueOf(procedimento.getDataProcedimento()));
            stmt.setInt(5, procedimento.getIdAnimal());
            stmt.setInt(6, procedimento.getIdVeterinario());
            stmt.setInt(7, procedimento.getIdProcedimento());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int idProcedimento) {
        String sql = "DELETE FROM procedimento WHERE idProcedimento = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idProcedimento);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
