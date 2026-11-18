package src.petShop.view;
import javax.swing.*;
import java.awt.*;

public class TelaFuncionario extends JFrame {

    private JTextField campoNome;
    private JTextField campoTelefone;
    private JTextField campoCargo;

    public TelaFuncionario() {

        setTitle("Cadastro de Funcionários");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        campoNome = new JTextField();
        campoTelefone = new JTextField();
        campoCargo = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);

        painel.add(new JLabel("Telefone:"));
        painel.add(campoTelefone);

        painel.add(new JLabel("Cargo:"));
        painel.add(campoCargo);

        painel.add(btnCadastrar);
        painel.add(btnLimpar);

        add(painel);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnLimpar.addActionListener(e -> limpar());

        setVisible(true);
    }

    private void cadastrar() {

        String nome = campoNome.getText();
        String telefone = campoTelefone.getText();
        String cargo = campoCargo.getText();

        if (nome.isEmpty() || telefone.isEmpty() || cargo.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos!");

            return;
        }

        JOptionPane.showMessageDialog(this,
            "Funcionário preparado para cadastro: " + nome);
    }

    private void limpar() {
        campoNome.setText("");
        campoTelefone.setText("");
        campoCargo.setText("");
    }
}

