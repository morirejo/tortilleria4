/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tortilleriapresentacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author jorge
 */
public class PantallaLogin extends JFrame {
    

    private static final String USUARIO_ADMIN    = "admin";
    private static final String PASSWORD_ADMIN   = "admin123";
    private static final String USUARIO_EMPLEADO = "empleado";
    private static final String PASSWORD_EMPLEADO = "emp123";

    private JTextField txtUsuario;
    private JPasswordField txtPassword;

    public PantallaLogin() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Tortillería — Inicio de Sesión");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));
        panel.setBackground(Color.WHITE);


        JLabel lblTitulo = new JLabel("TORTILLERÍA");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Iniciar Sesión");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(Color.GRAY);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsuario = new JTextField();
        txtUsuario.setMaximumSize(new Dimension(260, 30));
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword = new JPasswordField();
        txtPassword.setMaximumSize(new Dimension(260, 30));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));


        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.setBackground(new Color(34, 139, 34));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setBorderPainted(false);
        btnEntrar.setOpaque(true);
        btnEntrar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEntrar.setMaximumSize(new Dimension(260, 35));
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEntrar.addActionListener(e -> intentarLogin());

        txtPassword.addActionListener(e -> intentarLogin());


        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblSubtitulo);
        panel.add(Box.createVerticalStrut(24));
        panel.add(lblUsuario);
        panel.add(Box.createVerticalStrut(4));
        panel.add(txtUsuario);
        panel.add(Box.createVerticalStrut(12));
        panel.add(lblPassword);
        panel.add(Box.createVerticalStrut(4));
        panel.add(txtPassword);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnEntrar);

        setContentPane(panel);
        pack();
        setMinimumSize(new Dimension(340, 280));
    }

    private void intentarLogin() {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        String rol = null;

        if (USUARIO_ADMIN.equals(usuario) && PASSWORD_ADMIN.equals(password)) {
            rol = "ADMIN";
        } else if (USUARIO_EMPLEADO.equals(usuario) && PASSWORD_EMPLEADO.equals(password)) {
            rol = "EMPLEADO";
        }

        if (rol == null) {
            JOptionPane.showMessageDialog(this,
                    "Usuario o contraseña incorrectos.",
                    "Acceso denegado",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            return;
        }

        ControlPresentacionVenta mediador = new ControlPresentacionVenta(rol);
        this.dispose();
        new PantallaVenta(mediador, rol).setVisible(true);
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {}

        java.awt.EventQueue.invokeLater(() -> new PantallaLogin().setVisible(true));
    }

}
