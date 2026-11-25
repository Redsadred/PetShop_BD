
package src.petShop.view;

import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Pet Shop - Sistema de Gerenciamento");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 20));
        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
        );

        // Cabeçalho
        JPanel painelTitulo = new JPanel(new GridLayout(2, 1, 5, 5));

        JLabel titulo = new JLabel("PET SHOP", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        JLabel subtitulo = new JLabel(
            "Sistema de gerenciamento",
            SwingConstants.CENTER
        );
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));

        painelTitulo.add(titulo);
        painelTitulo.add(subtitulo);

        // Botões
        JPanel painelBotoes = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton btnCliente = new JButton("Clientes");
        JButton btnAnimal = new JButton("Animais");
        JButton btnVeterinario = new JButton("Veterinários");
        JButton btnProcedimento = new JButton("Procedimentos");
        JButton btnSair = new JButton("Sair");

        Font fonteBotoes = new Font("Arial", Font.PLAIN, 16);

        JButton[] botoes = {
            btnCliente,
            btnAnimal,
            btnVeterinario,
            btnProcedimento,
            btnSair
        };

        for (JButton botao : botoes) {
            botao.setFont(fonteBotoes);
            botao.setFocusPainted(false);
            painelBotoes.add(botao);
        }

        // Ações
        btnCliente.addActionListener(e -> new TelaCliente());
        btnAnimal.addActionListener(e -> new TelaAnimal());
        btnVeterinario.addActionListener(e -> new TelaVeterinario());
        btnProcedimento.addActionListener(e -> new TelaProcedimento());
        btnSair.addActionListener(e -> System.exit(0));

        painelPrincipal.add(painelTitulo, BorderLayout.NORTH);
        painelPrincipal.add(painelBotoes, BorderLayout.CENTER);

        add(painelPrincipal);

        setVisible(true);
    }
}
