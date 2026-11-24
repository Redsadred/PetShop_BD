package src.petShop.view;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TelaProcedimento extends JFrame {

    private JTextField campoNome;
    private JTextField campoValor;
    private JTextArea campoDescricao;
    private JTextField campoData;
    private JTextField campoAnimal;
    private JTextField campoVeterinario;

    public TelaProcedimento() {
        setTitle("Cadastro de Procedimento");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(6, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        campoNome = new JTextField();
        campoValor = new JTextField();
        campoDescricao = new JTextArea(3, 20);
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        campoData = new JTextField();
        campoAnimal = new JTextField();
        campoVeterinario = new JTextField();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        painel.add(new JLabel("Nome do procedimento:"));
        painel.add(campoNome);

        painel.add(new JLabel("Descrição:"));
        painel.add(new JScrollPane(campoDescricao));

        painel.add(new JLabel("Valor:"));
        painel.add(campoValor);

        painel.add(new JLabel("Data (AAAA-MM-DD HH:MM:SS):"));
        painel.add(campoData);

        painel.add(new JLabel("ID do Animal:"));
        painel.add(campoAnimal);

        painel.add(new JLabel("ID do Veterinário:"));
        painel.add(campoVeterinario);

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
        String nome = campoNome.getText().trim();
        String descricao = campoDescricao.getText().trim();
        String valor = campoValor.getText().trim();
        String data = campoData.getText().trim();
        String animal = campoAnimal.getText().trim();
        String veterinario = campoVeterinario.getText().trim();

        if (nome.isEmpty() || descricao.isEmpty() || valor.isEmpty()
                || data.isEmpty() || animal.isEmpty() || veterinario.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Preencha todos os campos!");
            return;
        }

        try {
            BigDecimal valorProcedimento = new BigDecimal(valor.replace(",", "."));
            LocalDateTime dataProcedimento = LocalDateTime.parse(
                    data,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );

            int idAnimal = Integer.parseInt(animal);
            int idVeterinario = Integer.parseInt(veterinario);

            if (valorProcedimento.compareTo(BigDecimal.ZERO) < 0
                    || idAnimal <= 0 || idVeterinario <= 0) {

                JOptionPane.showMessageDialog(this,
                        "Valor não pode ser negativo e os IDs devem ser positivos!");
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Procedimento preparado para cadastro: " + nome);

            limpar();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Valor e IDs devem ser válidos!");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Data inválida! Use AAAA-MM-DD HH:MM:SS.");
        }
    }

    private void limpar() {
        campoNome.setText("");
        campoDescricao.setText("");
        campoValor.setText("");
        campoData.setText("");
        campoAnimal.setText("");
        campoVeterinario.setText("");
    }
}
