package model;

import javafx.beans.property.StringProperty;

public class Activity {
	
	StringProperty date;
	StringProperty time;
	StringProperty currentCoolingDrumActualValue;
	StringProperty currentPullUnitPrecalandraActualValue;
	StringProperty currentRoll1ActualValue;
	StringProperty currentRoll2ActualValue;
	StringProperty currentRoll3MasterActualValue;
	StringProperty currentRoll4ActualValue;
	StringProperty currentWindUp1ActualValue;
	StringProperty currentWindUp2ActualValue;
	StringProperty bobbinLength1ActualValue;
	StringProperty bobbinLength2ActualValue;
	StringProperty centering26pActualValue;
	StringProperty centering28pActualValue;
	StringProperty centering34pActualValue;
	StringProperty centering56pActualValue;
	StringProperty expander28pActualValue;
	StringProperty expander35pActualValue;
	StringProperty expander36pActualValue;
	StringProperty positionRoll12DSActualValue;
	StringProperty positionRoll12WSActualValue;
	StringProperty positionRoll23DSActualValue;
	StringProperty positionRoll23WSActualValue;
	StringProperty positionRoll34DSActualValue;
	
	
//	"Position roll 1-2 DS [mm] - Actual Value",
//	"Position roll 1-2 WS [mm] - Actual Value",
//	"Position roll 2-3 DS [mm] - Actual Value",
//	"Position roll 2-3 WS [mm] - Actual Value",
//	"Position roll 3-4 DS [mm] - Actual Value",
//	"Position roll 3-4 WS [mm] - Actual Value",
//	"Rolliner [mm] - Actual Value",
//	"Speed Roll 1 [m/min] - Actual Value",
//	"Speed Roll 2 [m/min] - Actual Value",
//	"Speed Roll 3 Master [m/min] - Actual Value",
//	"Speed Roll 4 [m/min] - Actual Value",
//	"Temperature Roll 1 [°C] - Actual Value",
//	"Temperature Roll 2 [°C] - Recipe Set",
//	"Temperature Roll 2 [°C] - Actual Value",
//	"Temperature Roll 3 [°C] - Recipe Set",
//	"Temperature Roll 3 [°C] - Actual Value",
//	"Temperature Roll 4 [°C] - Actual Value",
//	"Temperature Heater [°C] - Recipe Set",
//	"Temperature Heater [°C] - Actual Set", temperatura cilindri riscaldatori 

//	"Temperature Heater [°C] - Actual Value",
//	"Temperature Cooling Drum 1 [°C] - Actual Value",
//	"Temperature Cooling Drum 2 [°C] - Actual Value",
//	"Tension Cooling Drum [Kg] - Actual Set",
//	"Tension Cooling Drum [Kg] - Actual Value",
//	"Tension Let Off [Kg] - Actual Value",
//	"Tension Pull Unit Pre Calandra [Kg] - Recipe Set",
//	"Tension Pull Unit Pre Calandra [Kg] - Actual Value",
//	"Temperature Lower Bank [°C] - Actual Value",
//	"Temperature Upper Bank [°C] - Actual Value",
//	"Temperature Ruberized Textile [°C] - Actual Value",
//	"Centering 26p Automatic/Manual/Neutro",
//	"Centering 28p Automatic/Manual/Neutro",
//	"Centering 34p Automatic/Manual/Neutro",
//	"Centering 56p Automatic/Manual/Neutro",
//	"Expander 28p Automatic/Manual/Neutro",
//	"Expander 35p Automatic/Manual/Neutro",
//	"Expander 36p Automatic/Manual/Neutro",
//	"Creel Inside Temperature [°C] - Actual Value",
//	"Creel Inside Umidity [%] - Actual Value",
//	"Pirometer Temperature [°C] - Actual Value" Temperatura Tessuto alla Raccolta

	
	
	
//	private String[] colonneRilevanti =
//    	{		"Date",
//    			"Time - # day",
//    			"Current Cooling Drum [A] - Actual Value",
//    			"Current Pull Unit Precalandra [A] - Actual Value",
//    			"Current Roll 1 [A] - Actual Value",
//    			"Current Roll 2 [A] - Actual Value",
//    			"Current Roll 3 Master [A] - Actual Value",
//    			"Current Roll 4 [A] - Actual Value",
//    			"Current Wind Up 1 [A] - Actual Value",
//    			"Current Wind Up 2 [A] - Actual Value",
//    			"Bobbin Lenght 1 [m] - Actual Value",
//    			"Bobbin Lenght 2 [m] - Actual Value",
//    			"Centering 26p [mm] - Actual Value",
//    			"Centering 28p [mm] - Actual Value",
//    			"Centering 34p [mm] - Actual Value",
//    			"Centering 56p [mm] - Actual Value",
//    			"Expander 28p [mm] - Actual Value",
//    			"Expander 35p [mm] - Actual Value",
//    			"Expander 36p [mm] - Actual Value",
//    			"Position roll 1-2 DS [mm] - Actual Value",
//    			"Position roll 1-2 WS [mm] - Actual Value",
//    			"Position roll 2-3 DS [mm] - Actual Value",
//    			"Position roll 2-3 WS [mm] - Actual Value",
//    			"Position roll 3-4 DS [mm] - Actual Value",
//    			"Position roll 3-4 WS [mm] - Actual Value",
//    			"Rolliner [mm] - Actual Value",
//    			"Speed Roll 1 [m/min] - Actual Value",
//    			"Speed Roll 2 [m/min] - Actual Value",
//    			"Speed Roll 3 Master [m/min] - Actual Value",
//    			"Speed Roll 4 [m/min] - Actual Value",
//    			"Temperature Roll 1 [°C] - Actual Value",
//    			"Temperature Roll 2 [°C] - Recipe Set",
//    			"Temperature Roll 2 [°C] - Actual Value",
//    			"Temperature Roll 3 [°C] - Recipe Set",
//    			"Temperature Roll 3 [°C] - Actual Value",
//    			"Temperature Roll 4 [°C] - Actual Value",
//    			"Temperature Heater [°C] - Recipe Set",
//    			"Temperature Heater [°C] - Actual Set",
//    			"Temperature Heater [°C] - Actual Value",
//    			"Temperature Cooling Drum 1 [°C] - Actual Value",
//    			"Temperature Cooling Drum 2 [°C] - Actual Value",
//    			"Tension Cooling Drum [Kg] - Actual Set",
//    			"Tension Cooling Drum [Kg] - Actual Value",
//    			"Tension Let Off [Kg] - Actual Value",
//    			"Tension Pull Unit Pre Calandra [Kg] - Recipe Set",
//    			"Tension Pull Unit Pre Calandra [Kg] - Actual Value",
//    			"Temperature Lower Bank [°C] - Actual Value",
//    			"Temperature Upper Bank [°C] - Actual Value",
//    			"Temperature Ruberized Textile [°C] - Actual Value",
//    			"Centering 26p Automatic/Manual/Neutro",
//    			"Centering 28p Automatic/Manual/Neutro",
//    			"Centering 34p Automatic/Manual/Neutro",
//    			"Centering 56p Automatic/Manual/Neutro",
//    			"Expander 28p Automatic/Manual/Neutro",
//    			"Expander 35p Automatic/Manual/Neutro",
//    			"Expander 36p Automatic/Manual/Neutro",
//    			"Creel Inside Temperature [°C] - Actual Value",
//    			"Creel Inside Umidity [%] - Actual Value",
//    			"Pirometer Temperature [°C] - Actual Value"
//    		}	;

}
