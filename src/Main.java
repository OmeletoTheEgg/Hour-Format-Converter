import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Cursor;

public class Main {

	private JFrame frmHourFormat;
	private JFormattedTextField txt24Hour;
	private JTextField txt12Hour;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmHourFormat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHourFormat = new JFrame();
		frmHourFormat.setLocation(new Point(960, 540));
		frmHourFormat.setTitle("24 Hour to 12 Hour Format Converter");
		frmHourFormat.setResizable(false);
		frmHourFormat.setBounds(100, 100, 441, 288);
		frmHourFormat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmHourFormat.getContentPane().setLayout(new BorderLayout(0, 0));
		frmHourFormat.setLocationRelativeTo(null);
		
		final Background panel = new Background();
		frmHourFormat.getContentPane().add(panel);
		panel.setLayout(null);
		
		Font font = new Font("Arial", Font.PLAIN, 17);

		MaskFormatter mask = null;
        try {
            mask = new MaskFormatter("##:##");
            mask.setPlaceholderCharacter('0');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txt24Hour = new JFormattedTextField(mask);
		txt24Hour.setCaretColor(Color.BLUE);
		txt24Hour.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		txt24Hour.setBackground(Color.BLACK);
		txt24Hour.setForeground(Color.WHITE);
		txt24Hour.setOpaque(false);
		txt24Hour.setFont(font.deriveFont(17f));
		txt24Hour.setHorizontalAlignment(SwingConstants.CENTER);	
		txt24Hour.setBounds(158, 110, 123, 41);
		panel.add(txt24Hour);
		txt24Hour.setColumns(10);
		txt24Hour.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				
				if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
			        e.consume();
			    } 
				
				if (Integer.parseInt(txt24Hour.getText().substring(0, 2)) > 24) {
					txt24Hour.setText(new String("24:00"));
				}
				if (Integer.parseInt(txt24Hour.getText().substring(3, 5)) >= 60) {
					txt24Hour.setText(new String(txt24Hour.getText().substring(0, 2) + "59"));
				}
			}
		});
		
		txt24Hour.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				updateTxt12Hour();
			}
			public void removeUpdate(DocumentEvent e) {
				updateTxt12Hour();
			}
			public void insertUpdate(DocumentEvent e) {
				updateTxt12Hour();
			}

			public void updateTxt12Hour() {	
				if (!txt24Hour.getText().isEmpty()) {
					String apm = "";
					try {
						apm = (Integer.parseInt(txt24Hour.getText().substring(0, 2)) % 24 <= 24 && Integer.parseInt(txt24Hour.getText().substring(0, 2)) % 24 >= 12) ? "PM" : "AM";
					} catch (NumberFormatException e) {
					}
					
					txt12Hour.setText(Operations.convert24To12(txt24Hour.getText()) + apm);
				}
				
			}}
		);
		
		JLabel title_label = new JLabel("24 Hour to 12 Hour Format");
		title_label.setBackground(Color.WHITE);
		
		title_label.setFont(font.deriveFont(30f));
		title_label.setForeground(Color.WHITE);
		title_label.setHorizontalAlignment(SwingConstants.CENTER);
		title_label.setBounds(0, 0, 435, 132);
		panel.add(title_label);
		
		txt12Hour = new JTextField();
		txt12Hour.setText("00:00AM");
		txt12Hour.setBackground(Color.BLACK);
		txt12Hour.setEditable(false);
		txt12Hour.setOpaque(false);
		txt12Hour.setHorizontalAlignment(SwingConstants.CENTER);
		txt12Hour.setForeground(Color.WHITE);
		txt12Hour.setFont(font.deriveFont(17f));
		txt12Hour.setColumns(10);
		txt12Hour.setBounds(158, 162, 123, 41);
		panel.add(txt12Hour);
		
		JLabel creatorName = new JLabel("Created by: Moscosa, Arjhi P.");
		creatorName.setForeground(Color.WHITE);
		creatorName.setHorizontalAlignment(SwingConstants.RIGHT);
		creatorName.setBounds(235, 234, 190, 14);
		panel.add(creatorName);
		frmHourFormat.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel, frmHourFormat.getContentPane(), title_label, txt24Hour, txt12Hour}));
		panel.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				panel.mouseX = e.getX();
				panel.mouseY = e.getY();
			}
		});
	}
}
