
package src.petShop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import src.petShop.bd.VeterinarioBD;
import src.petShop.model.Veterinario;

public class TelaVeterinario extends JFrame {

    private JTextField campoNome;
    private JTextField campoTelefone;
    private JTextField campoEspecialidade;

    private VeterinarioBD veterinarioBD;

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private int idVeterinarioSelecionado = -1;

    public TelaVeterinario() {

        veterinarioBD = new VeterinarioBD();

        setTitle("Cadastro de Veterinário");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        JPanel painelCadastro = new JPanel(new GridLayout(3, 2, 10, 10));

        campoNome = new JTextField();
        campoTelefone = new JTextField();
        campoEspecialidade = new JTextField();

        painelCadastro.add(new JLabel("Nome:"));
        painelCadastro.add(campoNome);

        painelCadastro.add(new JLabel("Telefone:"));
        painelCadastro.add(campoTelefone);

        painelCadastro.add(new JLabel("Especialidade:"));
        painelCadastro.add(campoEspecialidade);

        modeloTabela = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Telefone", "Especialidade"}, 0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);
        JScrollPane scroll = new JScrollPane(tabela);

        JPanel painelBotoes = new JPanel(new FlowLayout());

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnLimpar = new JButton("Limpar");

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
                selecionarVeterinario();
            }
        });

        listarVeterinarios();

        setVisible(true);
    }

    private void cadastrar() {

        String nome = campoNome.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String especialidade = campoEspecialidade.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty() || especialidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        Veterinario veterinario = new Veterinario(nome, especialidade);

        if (veterinarioBD.cadastrarComTelefone(veterinario, telefone)) {
            JOptionPane.showMessageDialog(this, "Veterinário cadastrado!");
            limpar();
            listarVeterinarios();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar veterinário!");
        }
    }

    private void selecionarVeterinario() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) return;

        idVeterinarioSelecionado = Integer.parseInt(
            modeloTabela.getValueAt(linha, 0).toString()
        );

        campoNome.setText(modeloTabela.getValueAt(linha, 1).toString());
        campoTelefone.setText(modeloTabela.getValueAt(linha, 2).toString());
        campoEspecialidade.setText(modeloTabela.getValueAt(linha, 3).toString());
    }

    private void atualizar() {

        if (idVeterinarioSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veterinário na tabela!");
            return;
        }

        String nome = campoNome.getText().trim();
        String telefone = campoTelefone.getText().trim();
        String especialidade = campoEspecialidade.getText().trim();

        if (nome.isEmpty() || telefone.isEmpty() || especialidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        Veterinario veterinario = new Veterinario(nome, especialidade);
        veterinario.setIdVeterinario(idVeterinarioSelecionado);

        boolean atualizado = veterinarioBD.atualizar(veterinario);

        if (!atualizado) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar veterinário!");
            return;
        }

        boolean telefoneAtualizado = veterinarioBD.atualizarTelefone(
            idVeterinarioSelecionado, telefone
        );

        if (telefoneAtualizado) {
            JOptionPane.showMessageDialog(this, "Veterinário atualizado!");
        } else {
            JOptionPane.showMessageDialog(
                this, "Dados atualizados, mas houve um problema com o telefone."
            );
        }

        limpar();
        listarVeterinarios();
    }

    private void excluir() {

        if (idVeterinarioSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veterinário na tabela!");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este veterinário?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) return;

        if (veterinarioBD.excluir(idVeterinarioSelecionado)) {
            JOptionPane.showMessageDialog(this, "Veterinário excluído!");
            limpar();
            listarVeterinarios();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível excluir. Verifique se existem procedimentos vinculados."
            );
        }
    }

    private void listarVeterinarios() {

        modeloTabela.setRowCount(0);

        List<Veterinario> veterinarios = veterinarioBD.listar();

        for (Veterinario veterinario : veterinarios) {

            List<String> telefones = veterinarioBD.listarTelefones(
                veterinario.getIdVeterinario()
            );

            String telefone = String.join(", ", telefones);

            modeloTabela.addRow(new Object[]{
                veterinario.getIdVeterinario(),
                veterinario.getNomeVeterinario(),
                telefone,
                veterinario.getEspecialidadeVeterinario()
            });
        }
    }

    private void limpar() {

        campoNome.setText("");
        campoTelefone.setText("");
        campoEspecialidade.setText("");

        idVeterinarioSelecionado = -1;
        tabela.clearSelection();
    }
}
