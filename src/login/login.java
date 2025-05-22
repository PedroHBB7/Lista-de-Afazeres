package login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Usuario.usuario;
import Database.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import telas.TelaPrincipal;  // import corrigido para o pacote 'telas'

public class login extends JFrame {
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton loginButton;

    public login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 20, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 20, 160, 25);
        add(emailField);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setBounds(20, 60, 80, 25);
        add(senhaLabel);

        senhaField = new JPasswordField();
        senhaField.setBounds(100, 60, 160, 25);
        add(senhaField);

        loginButton = new JButton("Entrar");
        loginButton.setBounds(100, 100, 100, 30);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String senha = String.valueOf(senhaField.getPassword());

                usuario u = autenticar(email, senha);
                if (u != null) {
                    JOptionPane.showMessageDialog(null, "Bem-vindo, " + u.getNome());
                    dispose();
                   
                    // Abre a tela principal a partir do pacote correto:
                    new TelaPrincipal(u.getId()).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou senha incorretos.");
                }
            }
        });

        // Botão Registrar
        JButton registerButton = new JButton("Registrar");
        registerButton.setBounds(100, 140, 100, 30);
        add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new register.register().setVisible(true);
            }
        });
    }

    public static usuario autenticar(String email, String senha) {
        try (Connection conn = DataBase.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario u = new usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                return u;
            }
        } catch (Exception e) {
            System.out.println("Erro na autenticação: " + e.getMessage());
        }
        return null;
    }
}
