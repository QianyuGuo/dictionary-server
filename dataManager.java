/*
 * student ID:921808
 * student name:Qianyu Guo
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class dataManager {

	//public static final String path = Server.docpath;

	public JSONObject dataJson = null;
	private String path;
	public dataManager(String docpath) {
		this.path= docpath;
		dataJson = read();
	}
	

	public JSONObject read() {
		StringBuilder builder = new StringBuilder();
		JSONObject dataJson = null;
		try {
			File f = new File(path);
			if(f.exists()==true) {
				try {
		BufferedReader br = new BufferedReader(new FileReader(path));

			String s = null;
			if ((s = br.readLine()) != null) {
				builder.append(s);
			}

			br.close();

			dataJson = new JSONObject(builder.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("cannot read the dictionary file");
		}
			}
		}catch (Exception e) {
			e.printStackTrace();
			System.out.print("cannot find the dictionary file");
		}
		

		return dataJson;
	}

	public void write(JSONObject dataJson) {
		String ws = dataJson.toString();
		try {
			File f = new File(path);
			if(f.exists()==true) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(path));

			bw.write(ws);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("cannot find the dictionary file");
		}

	}
			}catch (Exception e) {
		e.printStackTrace();
		System.out.print("cannot find the dictionary file");}
	}

	public JSONObject getDataJson() {
		return dataJson;
	}

	public void setDataJson(JSONObject dataJson) {
		this.dataJson = dataJson;
	}

}