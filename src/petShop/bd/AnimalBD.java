
package src.petShop.bd;

import java.sql.*;
import java.util.*;

import src.petShop.model.Animal;

public class AnimalBD {

    public boolean cadastrar(Animal animal) {
        String sql = "INSERT INTO animal (nomeAnimal, especieAnimal, racaAnimal, idadeAnimal, idCliente) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, animal.getNomeAnimal());
            stmt.setString(2, animal.getEspecieAnimal());
            stmt.setString(3, animal.getRacaAnimal());
            stmt.setInt(4, animal.getIdadeAnimal());
            stmt.setInt(5, animal.getIdCliente());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Animal> listar() {
        List<Animal> animais = new ArrayList<>();
        String sql = "SELECT * FROM animal";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                Animal animal = new Animal(
                        resultado.getString("nomeAnimal"),
                        resultado.getString("racaAnimal"),
                        resultado.getString("especieAnimal"),
                        resultado.getInt("idadeAnimal"),
                        resultado.getInt("idCliente"));

                animal.setIdAnimal(resultado.getInt("idAnimal"));
                animais.add(animal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return animais;
    }

    public boolean atualizar(Animal animal) {
        String sql = "UPDATE animal SET nomeAnimal = ?, especieAnimal = ?, racaAnimal = ?, idadeAnimal = ?, idCliente = ? WHERE idAnimal = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, animal.getNomeAnimal());
            stmt.setString(2, animal.getEspecieAnimal());
            stmt.setString(3, animal.getRacaAnimal());
            stmt.setInt(4, animal.getIdadeAnimal());
            stmt.setInt(5, animal.getIdCliente());
            stmt.setInt(6, animal.getIdAnimal());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int idAnimal) {
        String sql = "DELETE FROM animal WHERE idAnimal = ?";

        try (Connection conexao = Conexao.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idAnimal);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
