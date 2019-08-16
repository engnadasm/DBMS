package eg.edu.alexu.csd.oop.db.cs14;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CreateDTD {
	private String tableName;
	private static String[][] table;
	
	public CreateDTD(String table_name) {
		this.tableName = table_name;
	}

	@SuppressWarnings("unused")
	public void create() throws IOException {
		Dealwithxml data = Dealwithxml.newInstance();
		myDataBase database = new myDataBase();
		FileWriter fileWriter;
		
		File main_folder = new File("main folder");
		if (!main_folder.exists()) {
			main_folder.mkdir();
			fileWriter = new FileWriter("main folder"+ System.getProperty("file.separator")
			+ myDataBase.currentDatabase + System.getProperty("file.separator") +  tableName + "type2.dtd");
		} else {
			fileWriter = new FileWriter("main folder"+ System.getProperty("file.separator")
			+ myDataBase.currentDatabase + System.getProperty("file.separator") +  tableName + "type2.dtd");
		}
		
	    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
     	bufferedWriter.write("<!ELEMENT " + tableName +" (row)*>\n");
     	bufferedWriter.write("<!ELEMENT " + "row (");
     	for (int i = 0; i < getTable().length; i++) {
     		if (i == (getTable().length - 1)) {
             	bufferedWriter.write(getTable()[i][0] +")>\n");
     		} else {
             	bufferedWriter.write(getTable()[i][0] +",");
     		}
     	}
     	for (int i = 0; i < getTable().length; i++) {
         	bufferedWriter.write("<!ELEMENT " + getTable()[i][0] +" (#PCDATA)>\n");
     	}
        bufferedWriter.close();   
	}
	
	public String[][] get(String path) throws FileNotFoundException {
	     String line = null;
	     String[] Column_name = new String[5];
	     String table_name;
	     String allColumn;
	     int noColumn;
	     
	     //System.out.println(path);
	     FileReader fileReader =  new FileReader(path);
	     BufferedReader bufferedReader = new BufferedReader(fileReader);
	        try {
	        	line = bufferedReader.readLine();
	            int firstIndex = line.indexOf("(row)*"); 
	            table_name = line.substring(10, firstIndex - 1);

	        	line = bufferedReader.readLine();
	        	allColumn = line.substring(12 + table_name.length(), 14 + table_name.length());
	        	Column_name = allColumn.split(",");
	        	noColumn = Column_name.length;
	        	
	        	setTable(new String[noColumn][2]);
	        	for (int i = 0; i < noColumn; i++) {
		        	line = bufferedReader.readLine();
		        	String[] row = line.split("#",2);
		        	getTable()[i][0] = Column_name[i];
		        	getTable()[i][1] = row[1].substring(0, row[1].length() - 2);
	        	}
	            bufferedReader.close(); 
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return getTable();	
	}
	
	public static String[][] getTable() {
		//System.out.println("gettable");
		return table;
	}

	@SuppressWarnings("static-access")
	public void setTable(String[][] table) {
		//System.out.println("settable");
		this.table = table;
	}
}
