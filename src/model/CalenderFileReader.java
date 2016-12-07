package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class CalenderFileReader {

	//STRUTTURE DATI CHE SERVONO PER LA TABELLA
	
			ArrayList<String> keys; // numero colonne
			ArrayList<ArrayList<String>> valuesTable ;
			ArrayList<LocalDateTime> orari; // numero righe
			

		    public ArrayList<String> getKeys() {
				return keys;
			}

			public ArrayList<ArrayList<String>> getValuesTable() {
				return valuesTable;
			}
			
			public ArrayList<LocalDateTime> getOrari(){
				return orari;
			}
			
			//Delimiter used in CSV file
			private static final String COMMA_DELIMITER = ";";

		
		    
		    /*** Read the CSV file, save the headers in parameters, save the data
		     * in a list and in a Map
		     * @param fileName: a Csv file in the format output from the Calender 
		     * @throws FileNotFoundException 
		     */
			
		    // Salva le activities in lista e mappa
		    
		    public void readCsvFile(String fileName) throws FileNotFoundException {
				
		        BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
		        
		        
		        try {    


		            String line = "";


		            line = fileReader.readLine();
		            keys = new ArrayList<String>();
		            valuesTable = new ArrayList<ArrayList<String>>();
		            orari = new ArrayList<LocalDateTime>();
		            
		            for (String s : line.split(COMMA_DELIMITER)){
		            	keys.add(s);
		            }
		          
		            //keys.lenght mi dà il numero delle colonne
		            
		            
		            
		            //Read the file line by line starting from the second line

		            while ((line = fileReader.readLine()) != null) {

		                //Get all tokens available in line

		                String[] tokens = line.split(COMMA_DELIMITER);

		                if (tokens.length>144)
		                	JOptionPane.showMessageDialog(null, "C'è un errore nel formato del file, non è un csv della Calandra", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);	

		                
		                ArrayList<String> valuesLine = new ArrayList<String> ();
		                
		                
		                for (String s: tokens)
		                	valuesLine.add(s);
		                
		                valuesTable.add(valuesLine); // aggiungo riga di dati alla tabella
		                
		                
		                if (tokens.length > 0) {

		                 
		                	//Let us split the cell hour
		                	
		                	String ora = tokens[1];
		                	String[] parts = ora.split("-");
		                	String hour = parts[0]; // hh:mm:ss
		                	String part2 = parts[1]; // 034556
		                	
		                	String[] oreParti = hour.split(":");
		                	
		                	
		                	
		                	String data = tokens[0];
		                	
		                	final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		                	LocalDate date = LocalDate.parse(tokens[0], dtf);
		                	LocalTime time = LocalTime.of(Integer.parseInt((oreParti[0].trim())), Integer.parseInt(oreParti[1].trim()), Integer.parseInt(oreParti[2].trim()));               
		                	
		                	
		                	
		                	
		                	LocalDateTime ldt = time.atDate(date);
		                	
		                	orari.add(ldt);
							

								}
							}

		        }

		        catch (Exception e) {

		            System.out.println("Error in CsvFileReader !!!");

		            e.printStackTrace();

		        } finally {

		            try {

		                fileReader.close();

		            } catch (IOException e) {
		                System.out.println("Error while closing fileReader !!!");

		                e.printStackTrace();

		            }
		        }
		 
		    }

	
	
	
}
