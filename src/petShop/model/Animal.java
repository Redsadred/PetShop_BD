package src.petShop.model;

public class Animal {
    private int idAnimal;
    private String nomeAnimal;
    private String racaAnimal;
    private String especieAnimal;
    private int idadeAnimal;
    private int idCliente;

    public Animal(String nomeAnimal, String racaAnimal, String especieAnimal, int idadeAnimal, int idCliente) {
        this.nomeAnimal = nomeAnimal;
        this.racaAnimal = racaAnimal;
        this.especieAnimal = especieAnimal;
        this.idadeAnimal = idadeAnimal;
        this.idCliente = idCliente;
    }
    
    public int getIdAnimal() {
        return idAnimal;
    }
    
    public String getNomeAnimal() {
        return nomeAnimal;
    }
    
    public String getRacaAnimal() {
        return racaAnimal;
    }
    
    public String getEspecieAnimal() {
        return especieAnimal;
    }
    
    public int getIdadeAnimal() {
        return idadeAnimal;
    }
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }
}
