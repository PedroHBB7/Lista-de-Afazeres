package register;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Usuario.usuario;
import Database.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class register extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton registerButton;

    public register() {
        setTitle("Registrar Usuário");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(20, 20, 80, 25);
        add(nomeLabel);

        nomeField = new JTextField();
        nomeField.setBounds(100, 20, 200, 25);
        add(nomeField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 60, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 60, 200, 25);
        add(emailField);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(20, 100, 80, 25);
        add(senhaLabel);

        senhaField = new JPasswordField();
        senhaField.setBounds(100, 100, 200, 25);
        add(senhaField);

        registerButton = new JButton("Cadastrar");
        registerButton.setBounds(100, 150, 120, 30);
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String email = emailField.getText();
                String senha = String.valueOf(senhaField.getPassword());

                if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
                    return;
                }

                usuario u = new usuario();
                u.setNome(nome);
                u.setEmail(email);
                u.setSenha(senha);

                if (cadastrar(u)) {
                    JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                    dispose();  // fecha a janela de cadastro após sucesso
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário.");
                }
            }
        });
    }

    public static boolean cadastrar(usuario u) {
        try (Connection conn = DataBase.conectar()) {
            String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, u.getNome());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getSenha());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }
}

