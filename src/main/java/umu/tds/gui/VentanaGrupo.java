package umu.tds.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import umu.tds.dominio.Usuario;
import umu.tds.gui.VentanaPrincipal.UserListModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.Box;

public class VentanaGrupo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaGrupo frame = new VentanaGrupo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaGrupo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(100, 100, 1000, 620);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOeste = new JPanel();
		contentPane.add(panelOeste, BorderLayout.WEST);
		
		JPanel panelCentro = new JPanel();
		contentPane.add(panelCentro, BorderLayout.CENTER);
		
		JPanel panelEste = new JPanel();
		contentPane.add(panelEste, BorderLayout.EAST);
		
		JPanel panelNorte = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelNorte.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panelNorte, BorderLayout.NORTH);
		
		JPanel panelSur = new JPanel();
		contentPane.add(panelSur, BorderLayout.SOUTH);
		
		JButton btnAceptar = new JButton("Aceptar");
		JButton btnCancelar = new JButton("Cancelar");
		btnAceptar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 15));
		panelSur.add(btnAceptar);
		panelSur.add(btnCancelar);
		
		JLabel user = new JLabel("Jose Luis Fenoll ");
		user.setFont(new Font("Segoe UI", Font.BOLD, 15));
		user.setIcon(VentanaPrincipal.getScaledIcon("src/main/resources/user.png", 40, 40));
		user.setBorder(new LineBorder(Color.BLACK, 1));
		
		panelNorte.add(user);
		
		JList<Usuario> listaContactos = new JList<>(new UserListModel());
		listaContactos.setCellRenderer(new UserCellRenderer());
		
		JScrollPane scrollContactos = new JScrollPane(listaContactos);
		scrollContactos.setPreferredSize(new Dimension(380,450));
		
		JScrollPane scrollContactosAdded = new JScrollPane(listaContactos);
		scrollContactosAdded.setPreferredSize(new Dimension(380,450));
		
		
		
		scrollContactos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollContactosAdded.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		panelOeste.add(scrollContactos);
		panelOeste.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Contactos", TitledBorder.LEADING, TitledBorder.TOP));
		panelEste.add(scrollContactosAdded);
		panelEste.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1), "Contactos añadidos", TitledBorder.LEADING, TitledBorder.TOP));
        GridBagLayout gbl_panelCentro = new GridBagLayout();
        gbl_panelCentro.columnWidths = new int[]{0};
        gbl_panelCentro.rowHeights = new int[]{1, 1, 1, 1, 1, 1, 1};
        gbl_panelCentro.columnWeights = new double[]{0.0};
        gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        panelCentro.setLayout(gbl_panelCentro);
        
        JLabel nombre = new JLabel("Nombre del grupo");
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        GridBagConstraints gbc_nombre = new GridBagConstraints();
        gbc_nombre.anchor = GridBagConstraints.WEST;
        gbc_nombre.insets = new Insets(0, 0, 5, 0);
        gbc_nombre.gridx = 0;
        gbc_nombre.gridy = 1;
        panelCentro.add(nombre, gbc_nombre);
        
        JTextField nombregrupo = new JTextField();
        nombregrupo.setColumns(10);
        nombregrupo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        GridBagConstraints gbc_nombregrupo = new GridBagConstraints();
        gbc_nombregrupo.insets = new Insets(0, 0, 5, 0);
        gbc_nombregrupo.gridx = 0;
        gbc_nombregrupo.gridy = 2;
        panelCentro.add(nombregrupo, gbc_nombregrupo);        
        
        Component verticalStrut = Box.createVerticalStrut(80);
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 0);
        gbc_verticalStrut.gridx = 0;
        gbc_verticalStrut.gridy = 3;
        panelCentro.add(verticalStrut, gbc_verticalStrut);
        
        JButton btnAdd = new JButton(VentanaPrincipal.getScaledIcon("src/main/resources/person-add.png", 40, 40));
        btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAdd.setBackground(Color.WHITE);
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.insets = new Insets(0, 0, 6, 0);
        gbc_btnAdd.gridx = 0;
        gbc_btnAdd.gridy = 4;
        panelCentro.add(btnAdd, gbc_btnAdd);
        
        
        JButton btnRemove = new JButton(VentanaPrincipal.getScaledIcon("src/main/resources/person-remove.png", 40, 40));
        btnRemove.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRemove.setBackground(Color.WHITE);
        GridBagConstraints gbc_btnRemove = new GridBagConstraints();
        gbc_btnRemove.insets = new Insets(0, 0, 5, 0);
        gbc_btnRemove.gridx = 0;
        gbc_btnRemove.gridy = 5;
        panelCentro.add(btnRemove, gbc_btnRemove);
		
		
		
	}

}
