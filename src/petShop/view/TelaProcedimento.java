package src.petShop.view;
import javax.swing.*;
import java.awt.*;

public class TelaProcedimento extends JFrame {

    private JTextField campoNome;
    private JTextField campoValor;
    private JTextArea campoDescricao;

    public TelaProcedimento() {

        setTitle("Cadastro de Procedimentos");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(5, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        campoNome = new JTextField();
        campoValor = new JTextField();
        campoDescricao = new JTextArea(4, 20);
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        painel.add(new JLabel("Nome do procedimento:"));
        painel.add(campoNome);
        painel.add(new JLabel("Descrição:"));
        painel.add(new JScrollPane(campoDescricao));
        painel.add(new JLabel("Valor:"));

        JPanel botoes = new JPanel();
        botoes.add(btnCadastrar);
        botoes.add(btnLimpar);

        add(painel, BorderLayout.CENTER);
        add(campoValor, BorderLayout.NORTH);
        add(botoes, BorderLayout.SOUTH);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnLimpar.addActionListener(e -> limpar());

        setVisible(true);
    }

    private void cadastrar() {

        String nome = campoNome.getText();
        String descricao = campoDescricao.getText();
        String valor = campoValor.getText();

        if (nome.isEmpty() || descricao.isEmpty() || valor.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos!");
            return;
        }

        try {
            double valorProcedimento = Double.parseDouble(
                valor.replace(",", ".")
            );

            if (valorProcedimento < 0) {
                JOptionPane.showMessageDialog(this,
                    "O valor não pode ser negativo!");
                return;
            }

            JOptionPane.showMessageDialog(this,
                "Procedimento preparado para cadastro: " + nome);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Digite um valor válido!");
        }
    }

    private void limpar() {
        campoNome.setText("");
        campoDescricao.setText("");
        campoValor.setText("");
    }
}
