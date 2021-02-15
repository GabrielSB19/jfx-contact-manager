package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ContactManager {
	
	public ArrayList<Contact> contacts;
	
	public ContactManager() {
		contacts = new ArrayList<Contact>();
	}
	
	public void addContact(String name, String email) {
		contacts.add(new Contact(name, email));
	}
	
	public ArrayList<Contact> getContact(){
		return contacts;
	}
	
	public void importData (String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while(line != null){
			String [] parts = line.split(";");
			addContact(parts[0], parts[1]);
			line = br.readLine();
		}
		br.close();
	}
	
	public void exportData (String fileName) throws FileNotFoundException{
	    PrintWriter pw = new PrintWriter(fileName);

	    for(int i = 0; i<contacts.size(); i++){
	      Contact myContact = contacts.get(i);
	      pw.println(myContact.getName()+";"+myContact.getEmail());
	    }
	    pw.close();
	}
}
