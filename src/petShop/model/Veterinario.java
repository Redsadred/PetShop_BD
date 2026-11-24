
package src.petShop.model;

public class Veterinario {
    private int idVeterinario;
    private String nomeVeterinario;
    private String especialidadeVeterinario;

    public Veterinario(String nomeVeterinario, String especialidadeVeterinario) {
        this.nomeVeterinario = nomeVeterinario;
        this.especialidadeVeterinario = especialidadeVeterinario;
    }

    public int getIdVeterinario() {
        return idVeterinario;
    }

    public String getNomeVeterinario() {
        return nomeVeterinario;
    }

    public String getEspecialidadeVeterinario() {
        return especialidadeVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }
}
