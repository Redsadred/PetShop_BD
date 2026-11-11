package src.petShop.model;

public class Procedimento {
    private int idProcedimento;
    private String nomeProcedimento;
    private String descricaoProcedimento;
    private double precoProcedimento;

    public Procedimento(String nomeProcedimento, String descricaoProcedimento, double precoProcedimento) {
        this.nomeProcedimento = nomeProcedimento;
        this.descricaoProcedimento = descricaoProcedimento;
        this.precoProcedimento = precoProcedimento;
    }

    public int getIdProcedimento() {
        return idProcedimento;
    }
    public String getNomeProcedimento() {
        return nomeProcedimento;
    }
    public String getDescricaoProcedimento() {
        return descricaoProcedimento;
    }
    public double getPrecoProcedimento() {
        return precoProcedimento;
    }
    public void setIdProcedimento(int idProcedimento) {
        this.idProcedimento = idProcedimento;
    }
}
