/*
 * student ID:921808
 * student name:Qianyu Guo
 * 
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

public class Server {
	private List<Clients> all = new ArrayList<Clients>();

	private static dataManager manager = null;

	private JSONObject wordTable = null;

	public static void main(String[] args) throws IOException {
		if(args.length!=2) {
			System.out.print("command shoud be :> java ¨Cjar DictionaryServer.jar <port> <dictionary-file>");
			System.exit(0);
		}
		if(!args[1].equals("dict.json")){
			System.out.print("File not find");
			System.exit(0);
		}
		
		new Server().start(args);

	}

	public void start(String[] args) throws IOException {
		// ServerSocket server = new ServerSocket(9999);
		try {
			int input0 = Integer.parseInt(args[0]);
			String input1 = args[1];

			ServerSocket server = new ServerSocket(Integer.parseInt(args[0]));
			manager = new dataManager(args[1]);
			wordTable = manager.getDataJson();
			while (true) {
				Socket client = server.accept();
				Clients channel = new Clients(client);
				all.add(channel);
				new Thread(channel).start();
			}
		} catch (Exception e) {
			System.out.print("Invalid address");
		}

	}

	/**
	 * public static String path(String[] args) { return args[1];
	 * 
	 * }
	 * 
	 * 
	 * @author Administrator
	 */
	private class Clients implements Runnable {
		private DataInputStream dis;
		private DataOutputStream dos;
		private boolean isRunning = true;

		public Clients(Socket client) {
			try {
				dis = new DataInputStream(client.getInputStream());
				dos = new DataOutputStream(client.getOutputStream());
			} catch (IOException e) {
				// e.printStackTrace();
				CloseUtil.closeAll(dis, dos);
				isRunning = false;
			}
		}

		private void delete(String word) throws IOException {
			if(wordTable!=null) {
				synchronized (wordTable) {
				
					if (wordTable.has(word)) {
					wordTable.remove(word);
					// Return the action result.
					dos.writeUTF("Delete completed " + "\n");
					dos.flush();
					// Updating the dictionary.
					manager.write(wordTable);
				} else {
					dos.writeUTF("The word does not exist" + "\n");
					dos.flush();
				}
			}
				}else {
					System.out.print("file not find!");
				}
				// TODO Auto-generated method stub
				

		}

		private void search(String key) throws IOException, JSONException {
			
				if(wordTable!=null) {
					if (wordTable.has(key)) {
				String result = wordTable.getString(key);
				System.out.println(result);

				dos.writeUTF(result + "\n");
				dos.flush();
			} else {
				dos.writeUTF("The word does not exist" + "\n");
				dos.flush();
			}}else {
				System.out.print("file not find!");
			}
			
				
				
			

		}

		private void add(String word, String meaning) throws IOException, JSONException {
			if(wordTable!=null) {
				synchronized (wordTable) {
				if (wordTable.has(word)) {
					dos.writeUTF("The word already exists" + "\n");
					dos.flush();
					return;
				}

				wordTable.put(word, meaning);
				manager.write(wordTable);
				dos.writeUTF("Add completed " + "\n");
				dos.flush();
			}
			}else {
				System.out.print("file not find!");
			}
			
		}

		@Override
		public void run() {
			while (true) {

				try {
					String msg = dis.readUTF();
					String[] command = msg.split(",");
					System.out.println(msg);
					if (command[0].equals("Add")) {
						add(command[1], command[2]);
					} else if (command[0].equals("Query")) {
						search(command[1]);
					} else if (command[0].equals("Remove")) {
						delete(command[1]);
					} else if (command[0].equals("invalid")) {
						dos.writeUTF("Invalid input " + "\n");
						dos.flush();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();

				}
			}
		}

	}

}
