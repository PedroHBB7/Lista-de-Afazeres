package telas;

import ToDoList.ToDoList;
import tarefa.tarefa;

import javax.swing.*;
import java.awt.*;

public class TelaAdicionarTarefa extends JFrame {
    public TelaAdicionarTarefa(int usuarioId) {
        setTitle("Adicionar Tarefa");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField();

        JLabel lblDescricao = new JLabel("Descrição:");
        JTextField txtDescricao = new JTextField();

        JLabel lblStatus = new JLabel("Status:");
        String[] statusOptions = {"Pendente", "Concluído"};
        JComboBox<String> cbStatus = new JComboBox<>(statusOptions);

        JButton btnSalvar = new JButton("Salvar");

        btnSalvar.addActionListener(e -> {
            tarefa t = new tarefa();
            t.setTitulo(txtTitulo.getText());
            t.setDescricao(txtDescricao.getText());
            t.setStatus((String) cbStatus.getSelectedItem());
            t.setUsuarioId(usuarioId);

            if (ToDoList.adicionarTarefa(t)) {
                JOptionPane.showMessageDialog(this, "Tarefa adicionada!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao adicionar.");
            }
        });

        setLayout(new GridLayout(7, 1));
        add(lblTitulo);
        add(txtTitulo);
        add(lblDescricao);
        add(txtDescricao);
        add(lblStatus);
        add(cbStatus);
        add(btnSalvar);
    }
}