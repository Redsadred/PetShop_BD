package src.petShop.view;
import javax.swing.*;
import java.awt.*;

public class TelaCliente extends JFrame {
    
    private JTextField campoNome;
    private JTextField campoTelefone;

    public TelaCliente() {
        setTitle("Cadastro de Cliente");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel titulo = new JLabel("Cadastro de Cliente");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));

        campoNome = new JTextField();
        campoTelefone = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        painel.add(titulo);
        painel.add(new JLabel("Nome: "));
        painel.add(campoNome);
        painel.add(new JLabel("Telefone: "));
        painel.add(campoTelefone);

        JPanel botoes = new JPanel();
        botoes.add(btnCadastrar);
        botoes.add(btnLimpar);

        add(painel, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnLimpar.addActionListener(e -> limpar());

        setVisible(true);
    }
    
    private void cadastrar() {
        String nome = campoNome.getText();
        String telefone = campoTelefone.getText();

        if (nome.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        limpar();
    }
    private void limpar() {
        campoNome.setText("");
        campoTelefone.setText("");
    }
}
