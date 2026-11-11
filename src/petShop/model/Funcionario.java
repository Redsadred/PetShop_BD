package src.petShop.model;

public class Funcionario {
    private int idFuncionario;
    private String nomeFuncionario;
    private String telefoneFuncionario;
    private String cargoFuncionario;

    public Funcionario(String nomeFuncionario, String telefoneFuncionario, String cargoFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
        this.telefoneFuncionario = telefoneFuncionario;
        this.cargoFuncionario = cargoFuncionario;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }
    public String getNomeFuncionario() {
        return nomeFuncionario;
    }
    public String getTelefoneFuncionario() {
        return telefoneFuncionario;
    }
    public String getCargoFuncionario() {
        return cargoFuncionario;
    }
    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}
