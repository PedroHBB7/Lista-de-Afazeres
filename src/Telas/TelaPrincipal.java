package telas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import ToDoList.ToDoList;
import tarefa.tarefa;

public class TelaPrincipal extends JFrame {
    private JTable tabela;
    private DefaultTableModel modelo;
    private int usuarioId;

    public TelaPrincipal(int usuarioId) {
        this.usuarioId = usuarioId;

        setTitle("Minhas Tarefas");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        modelo = new DefaultTableModel(new String[]{"ID", "Título", "Descrição", "Status"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        btnAdicionar.addActionListener(e -> adicionarTarefa());
        btnEditar.addActionListener(e -> editarTarefa());
        btnExcluir.addActionListener(e -> excluirTarefa());

        JPanel botoes = new JPanel();
        botoes.add(btnAdicionar);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);

        add(scroll, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        carregarTarefas();
    }

    private void carregarTarefas() {
        modelo.setRowCount(0); // limpar tabela
        ArrayList<tarefa> tarefas = ToDoList.listarTarefas(usuarioId);
        for (tarefa t : tarefas) {
            modelo.addRow(new Object[]{
                t.getId(), t.getTitulo(), t.getDescricao(), t.getStatus()
            });
        }
    }

    private void adicionarTarefa() {
        String titulo = JOptionPane.showInputDialog(this, "Título:");
        String descricao = JOptionPane.showInputDialog(this, "Descrição:");
        if (titulo != null && descricao != null) {
            tarefa t = new tarefa();
            t.setTitulo(titulo);
            t.setDescricao(descricao);
            t.setStatus("pendente");
            t.setUsuarioId(usuarioId);
            ToDoList.adicionarTarefa(t);
            carregarTarefas();
        }
    }

    private void editarTarefa() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            int id = (int) modelo.getValueAt(linha, 0);
            String novoTitulo = JOptionPane.showInputDialog(this, "Novo título:", modelo.getValueAt(linha, 1));
            String novaDescricao = JOptionPane.showInputDialog(this, "Nova descrição:", modelo.getValueAt(linha, 2));
            String novoStatus = JOptionPane.showInputDialog(this, "Novo status (pendente ou concluído):", modelo.getValueAt(linha, 3));

            tarefa t = new tarefa();
            t.setId(id);
            t.setTitulo(novoTitulo);
            t.setDescricao(novaDescricao);
            t.setStatus(novoStatus);
            t.setUsuarioId(usuarioId);
            ToDoList.editarTarefa(t);
            carregarTarefas();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para editar.");
        }
    }

    private void excluirTarefa() {
        int linha = tabela.getSelectedRow();
        if (linha >= 0) {
            int id = (int) modelo.getValueAt(linha, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir esta tarefa?");
            if (confirm == JOptionPane.YES_OPTION) {
                ToDoList.excluirTarefa(id);
                carregarTarefas();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para excluir.");
        }
    }
}