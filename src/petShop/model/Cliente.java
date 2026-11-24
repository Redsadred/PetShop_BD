package src.petShop.model;

public class Cliente {
    private int idCliente;
    private String nomeCliente;

    public Cliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
