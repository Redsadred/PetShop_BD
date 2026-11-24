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
        setSize(400, 330);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(6, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        campoNome = new JTextField();
        campoEspecie = new JComboBox<>(new String[]{"Cachorro", "Gato"});
        campoRaca = new JTextField();
        campoIdade = new JTextField();
        campoCliente = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);

        painel.add(new JLabel("Espécie:"));
        painel.add(campoEspecie);

        painel.add(new JLabel("Raça:"));
        painel.add(campoRaca);

        painel.add(new JLabel("Idade:"));
        painel.add(campoIdade);

        painel.add(new JLabel("ID do Cliente:"));
        painel.add(campoCliente);

        painel.add(btnCadastrar);
        painel.add(btnLimpar);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnLimpar.addActionListener(e -> limpar());

        setVisible(true);
    }

    private void cadastrar() {
        String nome = campoNome.getText().trim();
        String especie = (String) campoEspecie.getSelectedItem();
        String raca = campoRaca.getText().trim();
        String idadeStr = campoIdade.getText().trim();
        String idClienteStr = campoCliente.getText().trim();

        if (nome.isEmpty() || raca.isEmpty()
                || idadeStr.isEmpty() || idClienteStr.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int idade = Integer.parseInt(idadeStr);
            int idCliente = Integer.parseInt(idClienteStr);

            if (idade < 0 || idCliente <= 0) {
                JOptionPane.showMessageDialog(this,
                        "A idade não pode ser negativa e o ID deve ser positivo!");
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Animal preparado para cadastro: " + nome);

            limpar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Idade e ID do cliente devem ser números!");
        }
    }

    private void limpar() {
        campoNome.setText("");
        campoEspecie.setSelectedIndex(0);
        campoRaca.setText("");
        campoIdade.setText("");
        campoCliente.setText("");
    }
}
