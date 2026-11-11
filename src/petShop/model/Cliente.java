package src.petShop.model;

public class Cliente {
    
    private int idCliente;
    private String nomeCliente;
    private String telefoneCliente;

    public Cliente(String nomeCliente, String telefoneCliente) {
        this.nomeCliente = nomeCliente;
        this.telefoneCliente = telefoneCliente;
    }
    
    public int getIdCliente() {
        return idCliente;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public String getTelefoneCliente() {
        return telefoneCliente;
    }
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
