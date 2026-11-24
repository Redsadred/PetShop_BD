package src.petShop.view;

import javax.swing.*;
import java.awt.*;

public class TelaVeterinario extends JFrame {

    private JTextField campoNome;
    private JTextField campoTelefone;
    private JTextField campoEspecialidade;

    public TelaVeterinario() {
        setTitle("Cadastro de Veterinário");
        setSize(400, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        campoNome = new JTextField();
        campoTelefone = new JTextField();
        campoEspecialidade = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);

        painel.add(new JLabel("Telefone:"));
        painel.add(campoTelefone);

        painel.add(new JLabel("Especialidade:"));
        painel.add(campoEspecialidade);

        painel.add(btnCadastrar);
        painel.add(btnLimpar);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnLimpar.addActionListener(e -> limpar());

        setVisible(true);
    }

    private void cadastrar() {
        String nome = campoNome.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String especialidade = campoEspecialidade.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty()
                || especialidade.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Veterinário preparado para cadastro: " + nome);

        limpar();
    }

    private void limpar() {
        campoNome.setText("");
        campoTelefone.setText("");
        campoEspecialidade.setText("");
    }
}
