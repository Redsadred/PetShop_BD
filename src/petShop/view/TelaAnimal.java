package src.petShop.view;
import javax.swing.*;
import java.awt.*;

public class TelaAnimal extends JFrame {

    private JTextField campoNome;
    private JComboBox<String> campoEspecie;
    private JTextField campoRaca;
    private JTextField campoIdade;
    private JTextField campoCliente;

    public TelaAnimal() {
        setTitle("Cadastro de Animal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        campoNome = new JTextField();
        campoEspecie = new JComboBox<>(new String[]{"Cachorro", "Gato",});
        campoRaca = new JTextField();
        campoIdade = new JTextField();
        campoCliente = new JTextField();

        painel.add(new JLabel("Nome: "));
        painel.add(campoNome);

        painel.add(new JLabel("Espécie: "));
        painel.add(campoEspecie);

        painel.add(new JLabel("Raça: "));
        painel.add(campoRaca);

        painel.add(new JLabel("Idade: "));
        painel.add(campoIdade);

        painel.add(new JLabel("ID do Cliente: "));
        painel.add(campoCliente);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        painel.add(btnCadastrar);
        painel.add(btnLimpar);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnLimpar.addActionListener(e -> limpar());

    }
    
    private void cadastrar() {
        String nome = campoNome.getText();
        String especie = (String) campoEspecie.getSelectedItem();
        String raca = campoRaca.getText();
        String idadeStr = campoIdade.getText();
        String idClienteStr = campoCliente.getText();

        if (nome.isEmpty() || especie.isEmpty() || raca.isEmpty() || idadeStr.isEmpty() || idClienteStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idade;
        int idCliente;
        try {
            idade = Integer.parseInt(idadeStr);
            idCliente = Integer.parseInt(idClienteStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade e ID do Cliente devem ser números.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Animal cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limpar();
    }
    
    private void limpar() {
        campoNome.setText("");
        campoEspecie.setSelectedIndex(0);
        campoRaca.setText("");
        campoIdade.setText("");
        campoCliente.setText("");
    }
    
}
