/*
 * student ID:921808
 * student name:Qianyu Guo
 * 
 */

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class ui implements ActionListener {

	private JFrame frame;
	static JTextField textField;
	static JTextField textField_1;

	/**
	 * Launch the application.
	 */
	static DataOutputStream dos;
	static DataInputStream dis;

	public static void main(String[] args) throws UnknownHostException, IOException {
		if(args.length!=2) {
			System.out.print("java ¨Cjar DictionaryClient.jar <server-address> <server-port>");
			System.exit(0);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ui window = new ui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Socket client;
		try {
			String input1 = args[0];
			int input2 = Integer.parseInt(args[1]);

			try {
				//client = new Socket("localhost", 9999);
				  client = new Socket(args[0], Integer.parseInt(args[1]));
				BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

				dos = new DataOutputStream(client.getOutputStream());
				dis = new DataInputStream(client.getInputStream());
				while (true) {
					String msg = dis.readUTF();
					// System.out.println(msg);
					textField_1.setText(msg);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "address not reachable", "Address not reachable,please try again!",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		} catch (Exception e) {
			System.out.print("Invalid address");
		}

	}

	/**
	 * Create the application.
	 */
	public ui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		textField = new JTextField();
		textField.setBounds(20, 69, 130, 137);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnSearch = new JButton("Query");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText() != "") {
					String tmp = textField.getText();
					// Dictionary.search(tmp);
					String cmd = e.getActionCommand();
					try {

						String[] command = tmp.split(",");
						if (command.length != 1) {
							dos.writeUTF("invalid" + "," + tmp);
							// textField_1.setText("invalid input!");
						} else {
							dos.writeUTF(cmd + "," + tmp);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.print(tmp);
				} else
					return;
			}
		});
		btnSearch.setBounds(20, 10, 105, 23);
		frame.getContentPane().add(btnSearch);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText() != "") {
					// System.out.print(e.getActionCommand());
					String cmd = e.getActionCommand();
					String tmp = textField.getText();
					try {

						String[] command = tmp.split(",");
						if (command.length != 2) {
							dos.writeUTF("invalid" + "," + tmp);
							// textField_1.setText("invalid input!");
						} else {
							dos.writeUTF(cmd + "," + tmp);
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// String[] input = tmp.split("");
					// Dictionary.add(input[0], input[1]);
				} else
					return;
			}
		});
		btnAdd.setBounds(171, 10, 80, 23);
		frame.getContentPane().add(btnAdd);

		JButton btnDelete = new JButton("Remove");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText() != "") {
					System.out.print(e.getActionCommand());
					String tmp = textField.getText();
					String cmd = e.getActionCommand();
					try {
						String[] command = tmp.split(",");
						if (command.length != 1) {
							dos.writeUTF("invalid" + "," + tmp);
							// textField_1.setText("invalid input!");
						} else {
							dos.writeUTF(cmd + "," + tmp);

						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else
					return;
			}

		});
		btnDelete.setBounds(308, 10, 105, 23);
		frame.getContentPane().add(btnDelete);

		textField_1 = new JTextField();
		textField_1.setBounds(283, 69, 130, 137);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
