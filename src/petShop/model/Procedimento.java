
package src.petShop.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Procedimento {
    private int idProcedimento;
    private String nomeProcedimento;
    private String descricaoProcedimento;
    private BigDecimal valorProcedimento;
    private LocalDateTime dataProcedimento;
    private int idAnimal;
    private int idVeterinario;

    public Procedimento(String nomeProcedimento, String descricaoProcedimento,
            BigDecimal valorProcedimento, LocalDateTime dataProcedimento,
            int idAnimal, int idVeterinario) {

        this.nomeProcedimento = nomeProcedimento;
        this.descricaoProcedimento = descricaoProcedimento;
        this.valorProcedimento = valorProcedimento;
        this.dataProcedimento = dataProcedimento;
        this.idAnimal = idAnimal;
        this.idVeterinario = idVeterinario;
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

    public BigDecimal getValorProcedimento() {
        return valorProcedimento;
    }

    public LocalDateTime getDataProcedimento() {
        return dataProcedimento;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdProcedimento(int idProcedimento) {
        this.idProcedimento = idProcedimento;
    }
}
