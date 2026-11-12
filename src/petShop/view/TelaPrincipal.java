package src.petShop.view;
import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {
    public TelaPrincipal() {
        setTitle("Pet Shop");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel titulo = new JLabel("Pet Shop");
        titulo.setFont(new Font("Arial", Font.BOLD,20));
        
        JButton btnAnimal = new JButton("Animal");
        JButton btnCliente = new JButton("Cliente");
        JButton btnFuncionario = new JButton("Funcionário");
        JButton btnProcedimento = new JButton("Procedimento");
        JButton btnSair = new JButton("Sair");

        panel.add(titulo);
        panel.add(btnAnimal);
        panel.add(btnCliente);
        panel.add(btnFuncionario);
        panel.add(btnProcedimento);
        panel.add(btnSair);

        add(panel);
    }
}
