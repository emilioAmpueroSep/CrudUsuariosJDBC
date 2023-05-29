package com.eampuero.repositories;

import com.eampuero.models.Usuario;
import com.eampuero.utils.ConexionBaseDatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorioImpl implements IRepositoriable<Usuario> {

    private Connection getConnection() throws SQLException {
        return ConexionBaseDatos.getConnection();
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios")) {
            while (rs.next()) {
                Usuario u = crearUsuarios(rs);
                usuarios.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }

    @Override
    public Usuario porId(Long id) {
        Usuario usuario = null;

        try (PreparedStatement stmt = getConnection().
                prepareStatement("SELECT * FROM usuarios WHERE id = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = crearUsuarios(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public void guardar(Usuario usuario) {
        String sql;
        if (usuario.getId() != null && usuario.getId() > 0) {
            sql = "UPDATE usuarios SET username=?, password=?, email=? WHERE id=?";
        } else {
            sql = "INSERT INTO usuarios(username, password, email) VALUES(?,?,?)";
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getEmail());
            if (usuario.getId() != null && usuario.getId() > 0) {
                stmt.setLong(4, usuario.getId());
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(Long id) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM usuarios WHERE id=?")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Usuario crearUsuarios(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong(1));
        u.setUsername(rs.getString(2));
        u.setPassword(rs.getString(3));
        u.setEmail(rs.getString(4));
        return u;
    }
}
