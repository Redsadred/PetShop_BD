
package src.petShop.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import src.petShop.bd.AnimalBD;
import src.petShop.bd.ClienteBD;
import src.petShop.model.Animal;
import src.petShop.model.Cliente;

public class TelaAnimal extends JFrame {

    private JTextField campoNome;
    private JComboBox<String> campoEspecie;
    private JTextField campoRaca;
    private JTextField campoIdade;
    private JComboBox<Cliente> campoCliente;

    private AnimalBD animalBD;
    private ClienteBD clienteBD;

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private int idAnimalSelecionado = -1;

    public TelaAnimal() {

        animalBD = new AnimalBD();
        clienteBD = new ClienteBD();

        setTitle("Cadastro de Animal");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        JPanel painelCadastro = new JPanel(new GridLayout(5, 2, 10, 10));

        campoNome = new JTextField();
        campoEspecie = new JComboBox<>(
            new String[]{"Cachorro", "Gato"}
        );
        campoRaca = new JTextField();
        campoIdade = new JTextField();
        campoCliente = new JComboBox<>();

        painelCadastro.add(new JLabel("Nome:"));
        painelCadastro.add(campoNome);

        painelCadastro.add(new JLabel("Espécie:"));
        painelCadastro.add(campoEspecie);

        painelCadastro.add(new JLabel("Raça:"));
        painelCadastro.add(campoRaca);

        painelCadastro.add(new JLabel("Idade:"));
        painelCadastro.add(campoIdade);

        painelCadastro.add(new JLabel("Cliente:"));
        painelCadastro.add(campoCliente);

        modeloTabela = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Espécie", "Raça", "Idade", "ID Cliente"}, 0
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
                selecionarAnimal();
            }
        });

        carregarClientes();
        listarAnimais();

        setVisible(true);
    }

    private void carregarClientes() {

        campoCliente.removeAllItems();

        List<Cliente> clientes = clienteBD.listar();

        for (Cliente cliente : clientes) {
            campoCliente.addItem(cliente);
        }
    }

    private void cadastrar() {

        try {
            String nome = campoNome.getText().trim();
            String especie = (String) campoEspecie.getSelectedItem();
            String raca = campoRaca.getText().trim();
            String idadeTexto = campoIdade.getText().trim();

            Cliente cliente = (Cliente) campoCliente.getSelectedItem();

            if (nome.isEmpty() || raca.isEmpty() || idadeTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Cadastre um cliente primeiro!");
                return;
            }

            int idade = Integer.parseInt(idadeTexto);

            if (idade < 0) {
                JOptionPane.showMessageDialog(this, "A idade não pode ser negativa!");
                return;
            }

            Animal animal = new Animal(
                nome, raca, especie, idade, cliente.getIdCliente()
            );

            if (animalBD.cadastrar(animal)) {
                JOptionPane.showMessageDialog(this, "Animal cadastrado!");
                limpar();
                listarAnimais();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar animal!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "A idade deve ser um número inteiro!");
        }
    }

    private void selecionarAnimal() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) return;

        idAnimalSelecionado = Integer.parseInt(
            modeloTabela.getValueAt(linha, 0).toString()
        );

        campoNome.setText(modeloTabela.getValueAt(linha, 1).toString());
        campoEspecie.setSelectedItem(modeloTabela.getValueAt(linha, 2).toString());
        campoRaca.setText(modeloTabela.getValueAt(linha, 3).toString());
        campoIdade.setText(modeloTabela.getValueAt(linha, 4).toString());

        int idCliente = Integer.parseInt(
            modeloTabela.getValueAt(linha, 5).toString()
        );

        for (int i = 0; i < campoCliente.getItemCount(); i++) {
            Cliente cliente = campoCliente.getItemAt(i);

            if (cliente.getIdCliente() == idCliente) {
                campoCliente.setSelectedIndex(i);
                break;
            }
        }
    }

    private void atualizar() {

        if (idAnimalSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um animal na tabela!");
            return;
        }

        try {
            String nome = campoNome.getText().trim();
            String especie = (String) campoEspecie.getSelectedItem();
            String raca = campoRaca.getText().trim();
            String idadeTexto = campoIdade.getText().trim();

            Cliente cliente = (Cliente) campoCliente.getSelectedItem();

            if (nome.isEmpty() || raca.isEmpty() || idadeTexto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
                return;
            }

            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente!");
                return;
            }

            int idade = Integer.parseInt(idadeTexto);

            if (idade < 0) {
                JOptionPane.showMessageDialog(this, "A idade não pode ser negativa!");
                return;
            }

            Animal animal = new Animal(
                nome, raca, especie, idade, cliente.getIdCliente()
            );

            animal.setIdAnimal(idAnimalSelecionado);

            if (animalBD.atualizar(animal)) {
                JOptionPane.showMessageDialog(this, "Animal atualizado!");
                limpar();
                listarAnimais();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar animal!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "A idade deve ser um número inteiro!");
        }
    }

    private void excluir() {

        if (idAnimalSelecionado == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um animal na tabela!");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este animal?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) return;

        if (animalBD.excluir(idAnimalSelecionado)) {
            JOptionPane.showMessageDialog(this, "Animal excluído!");
            limpar();
            listarAnimais();
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Não foi possível excluir. Verifique se existem procedimentos vinculados."
            );
        }
    }

    private void listarAnimais() {

        modeloTabela.setRowCount(0);

        for (Animal animal : animalBD.listar()) {
            modeloTabela.addRow(new Object[]{
                animal.getIdAnimal(),
                animal.getNomeAnimal(),
                animal.getEspecieAnimal(),
                animal.getRacaAnimal(),
                animal.getIdadeAnimal(),
                animal.getIdCliente()
            });
        }
    }

    private void limpar() {

        campoNome.setText("");
        campoEspecie.setSelectedIndex(0);
        campoRaca.setText("");
        campoIdade.setText("");

        if (campoCliente.getItemCount() > 0) {
            campoCliente.setSelectedIndex(0);
        }

        idAnimalSelecionado = -1;
        tabela.clearSelection();
    }
    
}
