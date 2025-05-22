package ToDoList;

import java.sql.*;
import java.util.ArrayList;
import Database.DataBase;
import tarefa.tarefa;

public class ToDoList {

    public static boolean adicionarTarefa(tarefa t) {
        String sql = "INSERT INTO tarefas (titulo, descricao, status, usuario_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DataBase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getTitulo());
            stmt.setString(2, t.getDescricao());
            stmt.setString(3, t.getStatus());
            stmt.setInt(4, t.getUsuarioId());
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<tarefa> listarTarefas(int usuarioId) {
        ArrayList<tarefa> lista = new ArrayList<>();
        String sql = "SELECT * FROM tarefas WHERE usuario_id = ?";

        try (Connection conn = DataBase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                tarefa t = new tarefa();
                t.setId(rs.getInt("id"));
                t.setTitulo(rs.getString("titulo"));
                t.setDescricao(rs.getString("descricao"));
                t.setStatus(rs.getString("status"));
                t.setUsuarioId(rs.getInt("usuario_id"));
                lista.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static boolean excluirTarefa(int id) {
        String sql = "DELETE FROM tarefas WHERE id = ?";
        try (Connection conn = DataBase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean editarTarefa(tarefa t) {
        String sql = "UPDATE tarefas SET titulo = ?, descricao = ?, status = ? WHERE id = ?";
        try (Connection conn = DataBase.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, t.getTitulo());
            stmt.setString(2, t.getDescricao());
            stmt.setString(3, t.getStatus());
            stmt.setInt(4, t.getId());
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}