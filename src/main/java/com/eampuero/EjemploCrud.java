package com.eampuero;

import com.eampuero.models.Usuario;
import com.eampuero.repositories.IRepositoriable;
import com.eampuero.repositories.UsuarioRepositorioImpl;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class EjemploCrud {
    public static void main(String[] args) {
        IRepositoriable<Usuario> repositorio = new UsuarioRepositorioImpl();
        Usuario usuario = new Usuario();
        int opcionIndice;
        do {
            opcionIndice = 0;

            Map<String, Integer> operaciones = new HashMap<>();
            operaciones.put("Actualizar", 1);
            operaciones.put("Eliminar", 2);
            operaciones.put("Agregar", 3);
            operaciones.put("Listar", 4);
            operaciones.put("Salir", 5);

            Object[] opArreglo = operaciones.keySet().toArray();

            Object opcion = JOptionPane.showInputDialog(null,
                    "Seleccione un Operación",
                    "Mantenedor de Usuarios",
                    JOptionPane.INFORMATION_MESSAGE, null, opArreglo, opArreglo[0]);

            if (opcion == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una operación");
            } else {
                opcionIndice = operaciones.get(opcion.toString());
                long id;
                String username;
                String password;
                String email;

                switch (opcionIndice) {

                    case 1 -> {

                        id = Long.parseLong(JOptionPane.showInputDialog(null, "Ingresar el id del usuario para Actualizar:"));

                        usuario = repositorio.porId(id);
                        if (usuario != null) {
                            username = JOptionPane.showInputDialog(null, "Ingresar el username:", usuario.getUsername());
                            password = JOptionPane.showInputDialog(null, "Ingresar el password:", usuario.getPassword());
                            email = JOptionPane.showInputDialog(null, "Ingresar el email:", usuario.getEmail());

                            usuario.setUsername(username);
                            usuario.setPassword(password);
                            usuario.setEmail(email);

                            repositorio.guardar(usuario);

                            JOptionPane.showMessageDialog(null, "Usuario actualizado correctamente: (Ver en consola)");
                            repositorio.listar().forEach(System.out::println);
                        } else {
                            JOptionPane.showMessageDialog(null, "No existe el id del usuario en la base de datos");
                        }

                    }
                    case 2 -> {
                        id = Long.parseLong(JOptionPane.showInputDialog(null, "Ingresa id de usuario a modificar"));
                        repositorio.eliminar(id);
                        System.out.println("Usuario eliminado con éxito:");
                        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente: (Ver en consola)");
                        repositorio.listar().forEach(System.out::println);
                    }
                    case 3 -> {
                        username = JOptionPane.showInputDialog(null, "Ingresa un nombre");
                        password = JOptionPane.showInputDialog(null, "Ingresa un password");
                        email = JOptionPane.showInputDialog(null, "Ingresa un email");

                        usuario.setUsername(username);
                        usuario.setPassword(password);
                        usuario.setEmail(email);

                        repositorio.guardar(usuario);
                        JOptionPane.showMessageDialog(null, "usuario creado: (Ver en consola)");
                        repositorio.listar().forEach(System.out::println);
                    }
                    case 4 -> {
                        JOptionPane.showMessageDialog(null, "Lista de usuarios: (Ver en consola)");
                        repositorio.listar().forEach(System.out::println);
                    }
                }
            }
        } while (opcionIndice != 5);
        JOptionPane.showMessageDialog(null, "Haz salido con exito!");

    }
}