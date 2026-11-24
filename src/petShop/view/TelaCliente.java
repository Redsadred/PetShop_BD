
package src.petShop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import src.petShop.bd.ClienteBD;
import src.petShop.model.Cliente;

public class TelaCliente extends JFrame {

    private JTextField campoNome;
    private JTextField campoTelefone;

    private ClienteBD clienteBD;

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private JButton btnCadastrar;
    private JButton btnAtualizar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    private int idClienteSelecionado = -1;

    public TelaCliente() {

        clienteBD = new ClienteBD();

        setTitle("Cadastro de Cliente");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        JPanel painelCadastro = new JPanel(new GridLayout(2, 2, 10, 10));

        campoNome = new JTextField();
        campoTelefone = new JTextField();

        painelCadastro.add(new JLabel("Nome:"));
        painelCadastro.add(campoNome);

        painelCadastro.add(new JLabel("Telefone:"));
        painelCadastro.add(campoTelefone);

        modeloTabela = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Telefone"}, 0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelBotoes = new JPanel(new FlowLayout());

        btnCadastrar = new JButton("Cadastrar");
        btnAtualizar = new JButton("Atualizar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        painelPrincipal.add(painelCadastro, BorderLayout.NORTH);
        painelPrincipal.add(scroll, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> atualizar());
        btnExcluir.addActionListener(e -> excluir());
        btnLimpar.addActionListener(e -> limpar());

        tabela.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                selecionarCliente();
            }

        });

        listarClientes();

        setVisible(true);
    }

    private void cadastrar() {

        String nome = campoNome.getText().trim();
        String telefone = campoTelefone.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        Cliente cliente = new Cliente(nome);

        boolean cadastrado = clienteBD.cadastrarComTelefone(cliente, telefone);

        if (cadastrado) {
            JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
            limpar();
            listarClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente!");
        }
    }

    private void selecionarCliente() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            return;
        }

        idClienteSelecionado = Integer.parseInt(
            modeloTabela.getValueAt(linha, 0).toString()
        );

        campoNome.setText(
            modeloTabela.getValueAt(linha, 1).toString()
        );

        campoTelefone.setText(
            modeloTabela.getValueAt(linha, 2).toString()
        );
    }

    private void atualizar() {

        if (idClienteSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela!");
            return;
        }

        String nome = campoNome.getText().trim();
        String telefone = campoTelefone.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        Cliente cliente = new Cliente(nome);
        cliente.setIdCliente(idClienteSelecionado);

        boolean atualizado = clienteBD.atualizar(cliente);

        if (!atualizado) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente!");
            return;
        }

        // Atualiza o telefone selecionado
        boolean telefoneAtualizado = clienteBD.atualizarTelefone(
            idClienteSelecionado, telefone
        );

        if (telefoneAtualizado) {
            JOptionPane.showMessageDialog(this, "Cliente atualizado!");
        } else {
            JOptionPane.showMessageDialog(this, "Nome atualizado, mas houve um problema no telefone.");
        }

        limpar();
        listarClientes();
    }

    private void excluir() {

        if (idClienteSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente na tabela!");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este cliente?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        boolean excluido = clienteBD.excluir(idClienteSelecionado);

        if (excluido) {
            JOptionPane.showMessageDialog(this, "Cliente excluído!");
            limpar();
            listarClientes();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível excluir. Verifique se existem animais vinculados a este cliente."
            );
        }
    }

    private void listarClientes() {

        modeloTabela.setRowCount(0);

        for (Cliente cliente : clienteBD.listar()) {

            java.util.List<String> telefones =
                clienteBD.listarTelefones(cliente.getIdCliente());

            String telefone = String.join(", ", telefones);

            modeloTabela.addRow(new Object[]{
                cliente.getIdCliente(),
                cliente.getNomeCliente(),
                telefone
            });
        }
    }

    private void limpar() {

        campoNome.setText("");
        campoTelefone.setText("");

        idClienteSelecionado = -1;
        tabela.clearSelection();
    }
}
