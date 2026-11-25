
package src.petShop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import src.petShop.bd.ProcedimentoBD;
import src.petShop.bd.AnimalBD;
import src.petShop.bd.VeterinarioBD;
import src.petShop.model.Procedimento;
import src.petShop.model.Animal;
import src.petShop.model.Veterinario;

public class TelaProcedimento extends JFrame {

    private JTextField campoNome;
    private JTextField campoValor;
    private JTextArea campoDescricao;
    private JTextField campoData;

    private JComboBox<Animal> campoAnimal;
    private JComboBox<Veterinario> campoVeterinario;

    private ProcedimentoBD procedimentoBD;
    private AnimalBD animalBD;
    private VeterinarioBD veterinarioBD;

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private int idProcedimentoSelecionado = -1;

    private final DateTimeFormatter formatoData =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TelaProcedimento() {

        procedimentoBD = new ProcedimentoBD();
        animalBD = new AnimalBD();
        veterinarioBD = new VeterinarioBD();

        setTitle("Cadastro de Procedimento");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        JPanel painelCadastro = new JPanel(new GridLayout(6, 2, 10, 10));

        campoNome = new JTextField();
        campoValor = new JTextField();

        campoDescricao = new JTextArea(3, 20);
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);

        campoData = new JTextField();

        campoAnimal = new JComboBox<>();
        campoVeterinario = new JComboBox<>();

        painelCadastro.add(new JLabel("Nome do procedimento:"));
        painelCadastro.add(campoNome);

        painelCadastro.add(new JLabel("Descrição:"));
        painelCadastro.add(new JScrollPane(campoDescricao));

        painelCadastro.add(new JLabel("Valor:"));
        painelCadastro.add(campoValor);

        painelCadastro.add(new JLabel("Data (AAAA-MM-DD HH:MM:SS):"));
        painelCadastro.add(campoData);

        painelCadastro.add(new JLabel("Animal:"));
        painelCadastro.add(campoAnimal);

        painelCadastro.add(new JLabel("Veterinário:"));
        painelCadastro.add(campoVeterinario);

        modeloTabela = new DefaultTableModel(
            new Object[]{"ID", "Procedimento", "Valor", "Data", "ID Animal", "ID Veterinário"}, 0
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
                selecionarProcedimento();
            }
        });

        carregarAnimais();
        carregarVeterinarios();
        listarProcedimentos();

        setVisible(true);
    }

    private void carregarAnimais() {

        campoAnimal.removeAllItems();

        List<Animal> animais = animalBD.listar();

        for (Animal animal : animais) {
            campoAnimal.addItem(animal);
        }
    }

    private void carregarVeterinarios() {

        campoVeterinario.removeAllItems();

        List<Veterinario> veterinarios = veterinarioBD.listar();

        for (Veterinario veterinario : veterinarios) {
            campoVeterinario.addItem(veterinario);
        }
    }

    private void cadastrar() {

        try {
            String nome = campoNome.getText().trim();
            String descricao = campoDescricao.getText().trim();
            String valorTexto = campoValor.getText().trim();
            String dataTexto = campoData.getText().trim();

            Animal animal = (Animal) campoAnimal.getSelectedItem();
            Veterinario veterinario =
                (Veterinario) campoVeterinario.getSelectedItem();

            if (nome.isEmpty() || valorTexto.isEmpty() || dataTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!");
                return;
            }

            if (animal == null || veterinario == null) {
                JOptionPane.showMessageDialog(
                    this, "Cadastre pelo menos um animal e um veterinário primeiro!"
                );
                return;
            }

            BigDecimal valor = new BigDecimal(
                valorTexto.replace(",", ".")
            );

            LocalDateTime data = LocalDateTime.parse(
                dataTexto, formatoData
            );

            if (valor.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "O valor não pode ser negativo!");
                return;
            }

            Procedimento procedimento = new Procedimento(
                nome,
                descricao,
                valor,
                data,
                animal.getIdAnimal(),
                veterinario.getIdVeterinario()
            );

            if (procedimentoBD.cadastrar(procedimento)) {
                JOptionPane.showMessageDialog(this, "Procedimento cadastrado!");
                limpar();
                listarProcedimentos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar procedimento!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um valor válido!");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(
                this, "Data inválida! Use AAAA-MM-DD HH:MM:SS."
            );
        }
    }

    private void selecionarProcedimento() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) return;

        idProcedimentoSelecionado = Integer.parseInt(
            modeloTabela.getValueAt(linha, 0).toString()
        );

        campoNome.setText(
            modeloTabela.getValueAt(linha, 1).toString()
        );

        campoValor.setText(
            modeloTabela.getValueAt(linha, 2).toString()
        );

        int idAnimal = Integer.parseInt(
            modeloTabela.getValueAt(linha, 4).toString()
        );

        int idVeterinario = Integer.parseInt(
            modeloTabela.getValueAt(linha, 5).toString()
        );

        Procedimento procedimento = procedimentoBD.listar()
            .stream()
            .filter(p -> p.getIdProcedimento() == idProcedimentoSelecionado)
            .findFirst()
            .orElse(null);

        if (procedimento != null) {
            campoDescricao.setText(procedimento.getDescricaoProcedimento());

            campoData.setText(
                procedimento.getDataProcedimento().format(formatoData)
            );
        }

        for (int i = 0; i < campoAnimal.getItemCount(); i++) {
            if (campoAnimal.getItemAt(i).getIdAnimal() == idAnimal) {
                campoAnimal.setSelectedIndex(i);
                break;
            }
        }

        for (int i = 0; i < campoVeterinario.getItemCount(); i++) {
            if (campoVeterinario.getItemAt(i).getIdVeterinario() == idVeterinario) {
                campoVeterinario.setSelectedIndex(i);
                break;
            }
        }
    }

    private void atualizar() {

        if (idProcedimentoSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um procedimento na tabela!");
            return;
        }

        try {
            String nome = campoNome.getText().trim();
            String descricao = campoDescricao.getText().trim();
            String valorTexto = campoValor.getText().trim();
            String dataTexto = campoData.getText().trim();

            Animal animal = (Animal) campoAnimal.getSelectedItem();
            Veterinario veterinario =
                (Veterinario) campoVeterinario.getSelectedItem();

            if (nome.isEmpty() || valorTexto.isEmpty() || dataTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!");
                return;
            }

            if (animal == null || veterinario == null) {
                JOptionPane.showMessageDialog(this, "Selecione um animal e um veterinário!");
                return;
            }

            BigDecimal valor = new BigDecimal(
                valorTexto.replace(",", ".")
            );

            LocalDateTime data = LocalDateTime.parse(
                dataTexto, formatoData
            );

            if (valor.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "O valor não pode ser negativo!");
                return;
            }

            Procedimento procedimento = new Procedimento(
                nome,
                descricao,
                valor,
                data,
                animal.getIdAnimal(),
                veterinario.getIdVeterinario()
            );

            procedimento.setIdProcedimento(idProcedimentoSelecionado);

            if (procedimentoBD.atualizar(procedimento)) {
                JOptionPane.showMessageDialog(this, "Procedimento atualizado!");
                limpar();
                listarProcedimentos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar procedimento!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um valor válido!");
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(
                this, "Data inválida! Use AAAA-MM-DD HH:MM:SS."
            );
        }
    }

    private void excluir() {

        if (idProcedimentoSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um procedimento na tabela!");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este procedimento?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) return;

        if (procedimentoBD.excluir(idProcedimentoSelecionado)) {
            JOptionPane.showMessageDialog(this, "Procedimento excluído!");
            limpar();
            listarProcedimentos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir procedimento!");
        }
    }

    private void listarProcedimentos() {

        modeloTabela.setRowCount(0);

        for (Procedimento procedimento : procedimentoBD.listar()) {

            modeloTabela.addRow(new Object[]{
                procedimento.getIdProcedimento(),
                procedimento.getNomeProcedimento(),
                procedimento.getValorProcedimento(),
                procedimento.getDataProcedimento().format(formatoData),
                procedimento.getIdAnimal(),
                procedimento.getIdVeterinario()
            });
        }
    }

    private void limpar() {

        campoNome.setText("");
        campoDescricao.setText("");
        campoValor.setText("");
        campoData.setText("");

        if (campoAnimal.getItemCount() > 0) {
            campoAnimal.setSelectedIndex(0);
        }

        if (campoVeterinario.getItemCount() > 0) {
            campoVeterinario.setSelectedIndex(0);
        }

        idProcedimentoSelecionado = -1;
        tabela.clearSelection();
    }
}
    