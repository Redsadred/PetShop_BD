package src.petShop.view;

import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Pet Shop");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(6, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("Pet Shop");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));

        JButton btnAnimal = new JButton("Animal");
        JButton btnCliente = new JButton("Cliente");
        JButton btnVeterinario = new JButton("Veterinário");
        JButton btnProcedimento = new JButton("Procedimento");
        JButton btnSair = new JButton("Sair");

        btnAnimal.addActionListener(e -> new TelaAnimal());
        btnCliente.addActionListener(e -> new TelaCliente());
        btnVeterinario.addActionListener(e -> new TelaVeterinario());
        btnProcedimento.addActionListener(e -> new TelaProcedimento());
        btnSair.addActionListener(e -> System.exit(0));

        painel.add(titulo);
        painel.add(btnAnimal);
        painel.add(btnCliente);
        painel.add(btnVeterinario);
        painel.add(btnProcedimento);
        painel.add(btnSair);

        add(painel);

        setVisible(true);
    }
}
