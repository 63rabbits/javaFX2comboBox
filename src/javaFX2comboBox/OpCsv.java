package javaFX2comboBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class OpCsv {

	private URL url;
	private TreeMap<Integer, String[]> csv = new TreeMap<>();

	public OpCsv(URL url) {
		this.url = url;
		readCsvFile();
	}

	public URL getUrl() {
		return url;
	}

	public String getCsvFileName() {
		return url.toString();
	}

	public int size() {
		return csv.size();
	}

	public TreeMap<Integer, String[]> getCsv() {
		return csv;
	}

	private void readCsvFile() {

		try {
			InputStream is = url.openStream();
//			BufferedReader br = new BufferedReader(new InputStreamReader(is, "Shift-JIS"));
//			BufferedReader br = new BufferedReader(new InputStreamReader(is, "JISAutoDetect"));
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String line = "";
			int lineNo = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				String[] words = new String[st.countTokens()];
				for (int i = 0; st.hasMoreTokens(); i++) {
					words[i] = st.nextToken();
				}
				csv.put(lineNo, words);
				lineNo++;
			}
			br.close();
			is.close();
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
