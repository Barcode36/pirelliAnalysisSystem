package model;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class Model {

	//TODO Salvare i dati dal file reader
	//TODO Mettere i dati in formato numerico per i grafici
	
	private String[] colonneRilevanti =
    	{		"Date",
    			"Time - # day",
    			"Current Cooling Drum [A] - Actual Value",
    			"Current Pull Unit Precalandra [A] - Actual Value",
    			"Current Roll 1 [A] - Actual Value",
    			"Current Roll 2 [A] - Actual Value",
    			"Current Roll 3 Master [A] - Actual Value",
    			"Current Roll 4 [A] - Actual Value",
    			"Current Wind Up 1 [A] - Actual Value",
    			"Current Wind Up 2 [A] - Actual Value",
    			"Bobbin Lenght 1 [m] - Actual Value",
    			"Bobbin Lenght 2 [m] - Actual Value",
    			"Centering 26p [mm] - Actual Value",
    			"Centering 28p [mm] - Actual Value",
    			"Centering 34p [mm] - Actual Value",
    			"Centering 56p [mm] - Actual Value",
    			"Expander 28p [mm] - Actual Value",
    			"Expander 35p [mm] - Actual Value",
    			"Expander 36p [mm] - Actual Value",
    			"Position roll 1-2 DS [mm] - Actual Value",
    			"Position roll 1-2 WS [mm] - Actual Value",
    			"Position roll 2-3 DS [mm] - Actual Value",
    			"Position roll 2-3 WS [mm] - Actual Value",
    			"Position roll 3-4 DS [mm] - Actual Value",
    			"Position roll 3-4 WS [mm] - Actual Value",
    			"Rolliner [mm] - Actual Value",
    			"Speed Roll 1 [m/min] - Actual Value",
    			"Speed Roll 2 [m/min] - Actual Value",
    			"Speed Roll 3 Master [m/min] - Actual Value",
    			"Speed Roll 4 [m/min] - Actual Value",
    			"Temperature Roll 1 [°C] - Actual Value",
    			"Temperature Roll 2 [°C] - Recipe Set",
    			"Temperature Roll 2 [°C] - Actual Value",
    			"Temperature Roll 3 [°C] - Recipe Set",
    			"Temperature Roll 3 [°C] - Actual Value",
    			"Temperature Roll 4 [°C] - Actual Value",
    			"Temperature Heater [°C] - Recipe Set",
    			"Temperature Heater [°C] - Actual Set",
    			"Temperature Heater [°C] - Actual Value",
    			"Temperature Cooling Drum 1 [°C] - Actual Value",
    			"Temperature Cooling Drum 2 [°C] - Actual Value",
    			"Tension Cooling Drum [Kg] - Actual Set",
    			"Tension Cooling Drum [Kg] - Actual Value",
    			"Tension Let Off [Kg] - Actual Value",
    			"Tension Pull Unit Pre Calandra [Kg] - Recipe Set",
    			"Tension Pull Unit Pre Calandra [Kg] - Actual Value",
    			"Temperature Lower Bank [°C] - Actual Value",
    			"Temperature Upper Bank [°C] - Actual Value",
    			"Temperature Ruberized Textile [°C] - Actual Value",
    			"Centering 26p Automatic/Manual/Neutro",
    			"Centering 28p Automatic/Manual/Neutro",
    			"Centering 34p Automatic/Manual/Neutro",
    			"Centering 56p Automatic/Manual/Neutro",
    			"Expander 28p Automatic/Manual/Neutro",
    			"Expander 35p Automatic/Manual/Neutro",
    			"Expander 36p Automatic/Manual/Neutro",
    			"Creel Inside Temperature [°C] - Actual Value",
    			"Creel Inside Umidity [%] - Actual Value",
    			"Pirometer Temperature [°C] - Actual Value"
    		}	;
	
	private CalenderFileReader cfr ;
	
	private ArrayList<String> keys; 
	private List<ArrayList<String>> valuesTable ;
	private List<ArrayList<Double>> valuesTableD;
	private ArrayList<LocalDateTime> orari;
	
	
	
	
	public List<ArrayList<String>> getValuesTable(){
		return valuesTable;
	}
	
	public List<ArrayList<Double>> getValuesTableD()
	{
		return valuesTableD;
	}
	
	
	
	
	public void readFile(String filepath){
		
		cfr = new CalenderFileReader();
		
		try {
			cfr.readCsvFile(filepath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		keys = cfr.getKeys();
		valuesTable = cfr.getValuesTable();
		orari = cfr.getOrari();
		
	}
	
	
	public String[] getColonneRilevanti (){
		
		return colonneRilevanti;
	}
	
	/*** Generate the data structure of the table with the already selected fields.***/
	public void generateTable(String filepath, int oraInizio, int oraFine){
		
		readFile (filepath);
		ArrayList<Integer> index = new ArrayList<Integer>();
		ArrayList<ArrayList<String>> newTable = new ArrayList<ArrayList<String>>();
		ArrayList<String> tempList;
		
		int indiceInizio = 0;
		//TODO Gestione delle ore inserite
		for (LocalDateTime l: orari){
			if (oraInizio==l.getHour())
				{
				indiceInizio = orari.indexOf(l);
				break;
				}
			
		}
		
		Collections.reverse(orari);
		int indiceFine = 0; 
		
		for (LocalDateTime l : orari){
			
			if (oraFine==l.getHour())
			{
				indiceFine = orari.size()-orari.indexOf(l);		
			}
		
		}
		
		Collections.sort(orari);
		
		
		// Lavoriamo sulle keys
		for (String s : keys) {
			for (String p: colonneRilevanti){
				
				if (s.equals(p)){
					index.add(keys.indexOf(s));
				}	
			}
		}
		//Struttura dati caratterizzata dagli indici e da colonneRilevanti che saranno gli headers
		
		for (ArrayList<String> line: valuesTable){
			tempList = new ArrayList<String>();
			for (Integer n: index)
				{
				tempList.add(line.get(n));
				}
			newTable.add(tempList);
		}
		// Adesso ho la struttura dati pulita.
		// Gli orari dovrebbero rimanere gli stessi, perché non ho eliminato righe.
		// Ora devo salvare le nuove strutture dati.
		
		valuesTable = newTable;
		List<ArrayList<String>> val = valuesTable.subList(indiceInizio, indiceFine+1);
		valuesTable = val;
		
	}
	
	
	
	/***Trasformare i valori in dati numerici per i grafici
	 * -10000 settato in caso di valore non numerico ***/
	
	public void numerizeTable (){
		
		valuesTableD = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> temp ;
		
		for (ArrayList<String> line: valuesTable)
		{
			temp = new ArrayList<Double>();
			
			for (String s : line)
			{
					try{ 
						temp.add(Double.parseDouble(s));
					} catch (NumberFormatException e){
						temp.add(-10000.00);
					}
				}
			valuesTableD.add(temp);
				
			}
		}
		
		
		
	}
	
	

