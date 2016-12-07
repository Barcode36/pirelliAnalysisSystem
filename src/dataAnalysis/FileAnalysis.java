package dataAnalysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import objectClasses.RecipePerformanceDuplex;
import objectClasses.RecipePerformanceInnerLiner;
import objectClasses.RecipePerformanceQuadruplex;

public class FileAnalysis {
	
	private static final int CQuadruplex = 350;
	private static final int JQuadruplex = 100;
	private static final int CDuplex = 130;
	private static final int JDuplex = 100;
	private static final int CInnerLiner = 20;
	private static final int JInnerLiner = 100;
	
	private String filepath;
	private String filename;
	private String SAPFilepath;
	private String KPITable[][];
	private int numCols;
	private int numRecords;
	private String SAPTable[][];
	private int numSAPCols;
	private int numSAPRecords;
	private List<String> recipesList;
	private int recipeCol;
	private int metersToCut;
	private int dateIndex;
	private int parameterIndexWidthLine;
	private int parameterIndexWidthMarker;	
	private int parameterIndexWeightLine;
	private int parameterIndexWeightMarker;
	private int widthLineRecipeIndex;
	private int weightLineRecipeIndex;	
	private int widthUpperToleranceIndex;
	private int widthLowerToleranceIndex;
	private int weightUpperToleranceIndex;
	private int weightLowerToleranceIndex;
	private int parameterIndexWidthLineDS;
	private int parameterIndexWidthMarkerDS;
	private int parameterIndexWeightLineDS;
	private int parameterIndexWeightMarkerDS;
	private int parameterIndexRecipeWidthLineOSDuplex;
	private int parameterIndexWidthLineOSDuplexUpperTolerance;
	private int parameterIndexWidthLineOSDuplexLowerTolerance;
	private Map<String, RecipePerformanceQuadruplex> recipesPerformanceQuadruplex;
	private Map<String, RecipePerformanceDuplex> recipesPerformanceDuplex;
	private Map<String, RecipePerformanceInnerLiner> recipesPerformanceInnerLiner;
	private Map<String, String[]> recipesToLimits;
	private List<String> totParamList;
    private List<String> totParamSAPList;
	private String machine;

	
	public FileAnalysis(String filepath, String SAPFilePath){
		
		this.filepath = filepath;	
		this.filename = filepath.substring(filepath.lastIndexOf("\\", filepath.length())+1);
		this.SAPFilepath = SAPFilePath;
		
	}
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String[][] getKPITable() {
	  return KPITable;
	}

	public void setKPITable(String[][] kPITable) {
		KPITable = kPITable;
	}
	
	public void cleanKPITable(){
		
		if(this.machine.equals("InnerLiner")){
			this.cleanRecipesInnerLiner();
			this.addWidthUpperAndLowerToleranceInnerLiner();
		}
				
		int cLimit=0;
		int jLimit=0;
		
		switch(machine){
		
		  case "Quadruplex":
			  cLimit = CQuadruplex;
			  jLimit = JQuadruplex;
			  break;
			  
		  case "Duplex":
			  cLimit = CDuplex;
			  jLimit = JDuplex;
			  break;
			  
		  case "InnerLiner":
			  cLimit = CInnerLiner;
			  jLimit = JInnerLiner;
			  break;
		
		}
		
		this.generateRecipeList();

				
		String recipe;
		String precRecipe = "";
		String oldRecipeProcessed = "";
		int startIndex = 0;
		boolean find = false;
		int c = 0;
		int i = 1;
		int j = 0;
		int start = 1;
		int prodIndex = this.howManyProductionIndexes(this.recipesList.get(0));
				
		for(int k=0;k<this.recipesList.size();k++){
			
			if(k==1)
				oldRecipeProcessed = this.recipesList.get(0);
			
			c = 0;
			j = 0;
			
			find = false;
			startIndex = 0;

			recipe = this.recipesList.get(k);
			
			if(this.machine.equals("Duplex") && recipe.equals("6"))
				continue;

			if(!recipe.equals(oldRecipeProcessed)){
				
				oldRecipeProcessed = recipe;
				start = 1;
				
			}
			
			for(i=start;i<this.numRecords;i++){
				
				if(this.KPITable[i][this.recipeCol]==null)
					continue;
						
				
				if(this.KPITable[i][this.recipeCol].equals(recipe)){
										
					if(!find)
						startIndex = i;

				  find = true;
				  c++;

				  if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0)
				    j++;
				  
				}
				
				if(!this.KPITable[i][this.recipeCol].equals(recipe) && find){
					break;
				}
				
				
			}


			if(startIndex == 0)
				continue;

			if(c<cLimit || j<jLimit){
				
				precRecipe = this.KPITable[startIndex-1][this.recipeCol];
				
				for(int y=1;y<this.numRecords;y++){
					
					if(y>=startIndex && y<=i)
						this.KPITable[y][this.recipeCol] = precRecipe;
					
				}
				
				precRecipe = "";
				start = startIndex-1;
				k--;

				
			}

			if(c>=cLimit && j>=jLimit){

              if(prodIndex>1){ 
				if(i<this.numRecords){
				  start = i;
				  prodIndex--;
				  k--;
				}
              }
              else{
            	  
            	  start = 1;
            	  
            	  if((k+1)<this.recipesList.size())
            	    prodIndex = this.howManyProductionIndexes(this.recipesList.get(k+1));
            	  
              }
            	 
            	  
            }

		}
		
		//==== At the end of the method, the recipes list is regenerated, in order to have the correct order, without errors =====// 
		this.generateRecipeList();

		if(this.recipesList.contains("SAP Code"))
			this.recipesList.remove(this.recipesList.indexOf("SAP Code"));
		
						
		
	}
	
	
	public void generateSummaryTable(){
		
		switch(this.machine){
		
		case "Quadruplex":
			this.generateQuadruplexSummaryTable();
			break;
			
		case "Duplex":
			if(this.recipesList.indexOf("6")!=-1)
			  this.recipesList.remove(this.recipesList.indexOf("6"));
			this.generateDuplexSummaryTable();
			break;
			
		case "InnerLiner":
			this.generateInnerLinerSummaryTable();
			break;
		
		}
		
	}
	
	public void generateQuadruplexSummaryTable(){

		String recipe;
		boolean rewind;
		boolean find;
		boolean findOther;
		int totRecordWidthRecipe;
		int totRecordWeightRecipe;
		int totRecordWidthMarkerRecipe;
		int totRecordWeightMarkerRecipe;
		int widthLineSum;
		int widthMarkerSum;
		int weightLineSum;
		int weightMarkerSum;
		float widthLineRecipe;
		float weightLineRecipe;
		float widthUpperTolerance;
		float widthLowerTolerance;
		float weightUpperTolerance;
		float weightLowerTolerance;
		double avgSpeed;
		boolean isStartFound;
		boolean canStart;
		long time;
		float meters;
		int cutCells;
		int lastIndex = -1;
		boolean thereIsAZeroAsStart;
		int prodIndex = 1;
		String recipeKey;
		int c;
		int f;
		List<Integer> lastIndexes = new LinkedList<Integer>();
		
		this.recipesPerformanceQuadruplex = new HashMap<String,RecipePerformanceQuadruplex>();
		this.recipesToLimits = new HashMap<String, String[]>();
        rewind = false;

        for(int k=0;k<this.recipesList.size();k++){
        	
        	recipe = this.recipesList.get(k);
	
        	if(recipe.equals("SAP Code"))
        		continue;
        	
          try{
          
        	find = false;
        	findOther = false;
			totRecordWidthRecipe = 0;
			totRecordWeightRecipe = 0;
			totRecordWidthMarkerRecipe = 0;
			totRecordWeightMarkerRecipe = 0;
			widthLineSum = 0;
			widthMarkerSum = 0;
			weightLineSum = 0;
			weightMarkerSum = 0;
			widthLineRecipe = 0;
			weightLineRecipe = 0;
			widthUpperTolerance = 0;
			widthLowerTolerance = 0;
			weightUpperTolerance = 0;
			weightLowerTolerance = 0;
			isStartFound = false;
			canStart = false;
			time = 0;
			meters = 0;
			avgSpeed = this.calculateAvgSpeed(recipe);
			cutCells = 0;
			if(prodIndex>1)
				lastIndex = -1;
			thereIsAZeroAsStart = this.thereIsAZeroAsStart(recipe, lastIndex);	
			lastIndex = -1;
			recipeKey = null;
			prodIndex = 1;
			c=1;
			f=0;
			lastIndexes.clear();
			
			if(!this.thereAreAtLeastOneValue(recipe))
				continue;

			for(int i=1;i<this.numRecords;i++){
				
				if(prodIndex>1)
					recipeKey = recipe+"-"+prodIndex;
				else
					recipeKey = recipe;
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe)){
					
					find = true;
					
					if(((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])==0) && !isStartFound && f<20)){
						f++;
					}
					
					if(((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])==0) && !isStartFound && f>19) || !thereIsAZeroAsStart){
						
						isStartFound = true;
						
					}


					if(!rewind){

					  if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && isStartFound && !canStart){

						  meters+=avgSpeed*time;
						  time+=2;
						  cutCells++;
						  
					  }
					}
					else{
						
						 if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && !canStart){
								
							  meters+=avgSpeed*time;
							  time+=2;
							  cutCells++;
							 
						  }
						
					}
					
					
					if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0){
						
						if(Math.round(meters)>(this.metersToCut-1)){
							
							canStart = true;

							if(!this.recipesToLimits.containsKey(recipeKey)){
								
								String[] temp = new String[2];
								temp[0] = this.KPITable[i][this.dateIndex];
								this.recipesToLimits.put(recipeKey, temp);
							}
							
						
				            widthLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine]);	
					  
					        totRecordWidthRecipe++;
					        
						}
										
					}
					
                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarker])>0){
						
						if(Math.round(meters)>(this.metersToCut-1)){
								
					        widthMarkerSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarker]);
					  
					        totRecordWidthMarkerRecipe++;
					        
						}
						
                    }
					
					if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLine])>2){
							
						if(Math.round(meters)>(this.metersToCut-1)){
							
							weightLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLine]);	
					    
					        totRecordWeightRecipe++;
					        
						}

					}
					
					if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarker])>2){
						
						if(Math.round(meters)>(this.metersToCut-1)){
							
					        weightMarkerSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarker]);
					    
					        totRecordWeightMarkerRecipe++;
					        
						}

					}
					
					if(Math.round(meters)>(this.metersToCut-1)){

		               widthLineRecipe = Float.parseFloat(this.KPITable[i][this.widthLineRecipeIndex]);
		               weightLineRecipe = Float.parseFloat(this.KPITable[i][this.weightLineRecipeIndex]);
		               widthUpperTolerance = Float.parseFloat(this.KPITable[i][this.widthUpperToleranceIndex]);
				       widthLowerTolerance = Float.parseFloat(this.KPITable[i][this.widthLowerToleranceIndex]);
				       weightUpperTolerance = Float.parseFloat(this.KPITable[i][this.weightUpperToleranceIndex]);
				       weightLowerTolerance = Float.parseFloat(this.KPITable[i][this.weightLowerToleranceIndex]);
				    
					}
					
				}
				
				
				if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe) && find){
					findOther = true;
					
					if(lastIndex>-1 && prodIndex>1 && c==prodIndex){
						lastIndex = -1;
					}
					
					if(lastIndex==-1){
					  lastIndex = this.findNextStart(i-1);
					  c++;

					  if(lastIndex == -1){
						  if(this.recipesToLimits.containsKey(recipeKey)){
							    lastIndex = (i+this.countCellsFromHeadtoCollection(i, recipe))-cutCells;
							    lastIndexes.add(lastIndex);
	                    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex][this.dateIndex];
	                    		break;
	                    	}
					  }
					  else{
					    if(lastIndex<i){
					    	
					    	boolean brk = false;
					    	
					    	if(Float.parseFloat(this.KPITable[lastIndex-cutCells][this.parameterIndexWidthLine])==0){
					    		
					    		for(int y=(lastIndex-cutCells);y>0;y--){
					    			if(Float.parseFloat(this.KPITable[y][this.parameterIndexWidthLine])>0){
					    				this.recipesToLimits.get(recipeKey)[1]=this.KPITable[y-cutCells][this.dateIndex];
					    				lastIndex = y;
					    				lastIndexes.add(lastIndex);
					    				brk = true;
					    				break;
					    				
					    			}
					    			
					    		}
					    		
					    		if(brk)
					    			continue;
					    		
					    	 }
					    	else{
					    		
					    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex-cutCells][this.dateIndex];
			    				lastIndexes.add(lastIndex);
			    				continue;
					    		
					    	}
					       }
					    	else{
						      if(this.recipesToLimits.containsKey(recipeKey)){
						    	  lastIndexes.add(lastIndex);
	                    	  	  this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex-cutCells][this.dateIndex];
	                    		  continue;
	                          }  
						    
					    	}
					    
					  }
					  
					}
					

				}
				

				if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe) && findOther){
					
					if(lastIndex!=-1 && i<(lastIndex-cutCells)){

						if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0){
							
							
					            widthLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine]);	
						  
						        totRecordWidthRecipe++;
											
						}
						
						if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarker])>0){
							
					        widthMarkerSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarker]);
					  
					        totRecordWidthMarkerRecipe++;
										
					    }
	                    
	                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLine])>2){
							
					            weightLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLine]);	
						  
						        totRecordWeightRecipe++;
						        										
						}
	                    
	                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarker])>2){
							
					        weightMarkerSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarker]);
					  
					        totRecordWeightMarkerRecipe++;
					        										
					    }
	                   
	                    if(i==((lastIndex-cutCells)-1)){
	                    	if(this.recipesToLimits.containsKey(recipeKey)){
	                    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[i][this.dateIndex];
	                    	}
	                    	
	                    }
						
					}
					
				}
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe) && findOther){
					meters = 0;
					time = 0;
					isStartFound = false;
					canStart = false;
					findOther = false;
					thereIsAZeroAsStart = this.thereIsAZeroAsStart(recipe, i);
					prodIndex++;
					
				}
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe) && !findOther && i==(this.numRecords-1)){
					this.recipesToLimits.get(recipeKey)[1] = this.KPITable[i][this.dateIndex];
					lastIndex = i;
					lastIndexes.add(lastIndex);
				    break;
				}
				
				
			}
			
			if(totRecordWidthRecipe == 0 || totRecordWeightRecipe == 0){
				rewind = true;
				k--;
				continue;
			}
				

			this.recipesPerformanceQuadruplex.put(recipe, new RecipePerformanceQuadruplex(recipe, String.valueOf(widthLineRecipe), this.round((float)widthLineSum/totRecordWidthRecipe), this.round((float)widthMarkerSum/totRecordWidthMarkerRecipe), (float)((widthLineSum/totRecordWidthRecipe)-widthLineRecipe), String.valueOf(weightLineRecipe), this.round((float)weightLineSum/totRecordWeightRecipe), this.round((float)weightMarkerSum/totRecordWeightMarkerRecipe), (float)((weightLineSum/totRecordWeightRecipe)-weightLineRecipe), widthUpperTolerance, widthLowerTolerance, weightUpperTolerance, weightLowerTolerance, totRecordWidthRecipe, totRecordWeightRecipe));
			this.calculateRecipeVariance(recipe, rewind, avgSpeed, lastIndexes, thereIsAZeroAsStart);
			this.calculateRecipeCp(recipe);
			this.calculateRecipeCpk(recipe);
			this.calculateProductionIndex(recipe);
			this.addSAPData(recipe.trim());

			rewind = false;


		 }catch(Exception e){
	      
			 JOptionPane.showMessageDialog(null, "RECIPE PROCESSING ERROR: \n An error occurred while processing the recipe "+recipe+"! \n This recipe will be excluded from the analysis", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			 this.recipesList.remove(this.recipesList.indexOf(recipe));
			 
			 if(this.recipesPerformanceQuadruplex.containsKey(recipe))
				 this.recipesPerformanceQuadruplex.remove(recipe);
			 
			 if(this.recipesList.isEmpty()){
				 JOptionPane.showMessageDialog(null, "FATAL RECIPE PROCESSING ERROR: \n All recipes excluded from the analysis! \n Control your input data files", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				 System.exit(-1);
			 }
			 
			 continue;
	        	
	      }
       }

	}
	
	public void generateDuplexSummaryTable(){

		String recipe;
		boolean rewind;
		boolean find;
		boolean findOther;
		int totRecordWidthRecipe;
		int totRecordWeightRecipe;
		int totRecordWidthMarkerRecipe;
		int totRecordWeightMarkerRecipe;
		int totRecordWidthRecipeDS;
		int totRecordWeightRecipeDS;
		int totRecordWidthMarkerRecipeDS;
		int totRecordWeightMarkerRecipeDS;
		int widthLineSum;
		int widthMarkerSum;
		int weightLineSum;
		int weightMarkerSum;
		int widthLineSumDS;
		int widthMarkerSumDS;
		int weightLineSumDS;
		int weightMarkerSumDS;
		float widthLineRecipe;
		float weightLineRecipe;
		float widthUpperTolerance;
		float widthLowerTolerance;
		float weightUpperTolerance;
		float weightLowerTolerance;
		double avgSpeed;
		boolean isStartFound;
		boolean canStart;
		long time;
		float meters;
		int cutCells;
		int lastIndex = -1;
		boolean thereIsAZeroAsStart;
		int prodIndex = 1;
		String recipeKey;
		int c;
		List<Integer> lastIndexes = new LinkedList<Integer>();
		
		this.recipesPerformanceDuplex = new HashMap<String,RecipePerformanceDuplex>();
		this.recipesToLimits = new HashMap<String, String[]>();
        rewind = false;

        for(int k=0;k<this.recipesList.size();k++){
        	
        	recipe = this.recipesList.get(k);

          try{
          
        	find = false;
        	findOther = false;
			totRecordWidthRecipe = 0;
			totRecordWeightRecipe = 0;
			totRecordWidthMarkerRecipe = 0;
			totRecordWeightMarkerRecipe = 0;
			totRecordWidthRecipeDS = 0;
			totRecordWeightRecipeDS = 0;
			totRecordWidthMarkerRecipeDS = 0;
			totRecordWeightMarkerRecipeDS = 0;
			widthLineSum = 0;
			widthMarkerSum = 0;
			weightLineSum = 0;
			weightMarkerSum = 0;
			widthLineSumDS = 0;
			widthMarkerSumDS = 0;
			weightLineSumDS = 0;
			weightMarkerSumDS = 0;
			widthLineRecipe = 0;
			weightLineRecipe = 0;
			widthUpperTolerance = 0;
			widthLowerTolerance = 0;
			weightUpperTolerance = 0;
			weightLowerTolerance = 0;
			isStartFound = false;
			canStart = false;
			time = 0;
			meters = 0;
			avgSpeed = this.calculateAvgSpeed(recipe);
			cutCells = 0;
			if(prodIndex>1)
				lastIndex = -1;
			thereIsAZeroAsStart = this.thereIsAZeroAsStart(recipe, lastIndex);	
			lastIndex = -1;
			recipeKey = null;
			prodIndex = 1;
			c=1;
			lastIndexes.clear();
			
			if(!this.thereAreAtLeastOneValue(recipe))
				continue;

			for(int i=1;i<this.numRecords;i++){
				
				if(prodIndex>1)
					recipeKey = recipe+"-"+prodIndex;
				else
					recipeKey = recipe;
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe)){
					
					find = true;
					if(((Float.parseFloat(this.KPITable[i][this.parameterIndex("LINE - Width Winder Zone Upper Tolerance  (mm)", this.KPITable)])!=Float.parseFloat(this.KPITable[i-1][this.parameterIndex("LINE - Width Winder Zone Upper Tolerance  (mm)", this.KPITable)])) && !isStartFound)){
						
						isStartFound = true;
						
					}


					if(!rewind){

					  if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && isStartFound && !canStart){

						  meters+=avgSpeed*time;
						  time+=2;
						  cutCells++;
						  
					  }
					}
					else{
						
						 if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && !canStart){

							  meters+=avgSpeed*time;
							  time+=2;
							  cutCells++;
							 
						  }
						
					}
					
					
					if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0){
												
						if(Math.round(meters)>(this.metersToCut-1)){
							
							canStart = true;
							
							if(!this.recipesToLimits.containsKey(recipeKey)){
								
								String[] temp = new String[2];
								temp[0] = this.KPITable[i][this.dateIndex];
								this.recipesToLimits.put(recipeKey, temp);
							}
							
						
				            widthLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine]);	
					  
					        totRecordWidthRecipe++;
					        
						}
										
					}
					
                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLineDS])>0){
						
						if(Math.round(meters)>(this.metersToCut-1)){
								
					        widthLineSumDS+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLineDS]);
					  
					        totRecordWidthRecipeDS++;
					        
						}
						
                    }
					
                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarker])>0){
						
						if(Math.round(meters)>(this.metersToCut-1)){
								
					        widthMarkerSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarker]);
					  
					        totRecordWidthMarkerRecipe++;
					        
						}
						
                    }
                    
                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarkerDS])>0){
						
						if(Math.round(meters)>(this.metersToCut-1)){
								
					        widthMarkerSumDS+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarkerDS]);
					  
					        totRecordWidthMarkerRecipeDS++;
					        
						}
						
                    }
					
					if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLine])>2){
							
						if(Math.round(meters)>(this.metersToCut-1)){
							
							weightLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLine]);	
					    
					        totRecordWeightRecipe++;
					        
						}

					}
					
					if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLineDS])>2){
						
						if(Math.round(meters)>(this.metersToCut-1)){
							
							weightLineSumDS+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLineDS]);	
					    
					        totRecordWeightRecipeDS++;
					        
						}

					}
					
					if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarker])>2){
						
						if(Math.round(meters)>(this.metersToCut-1)){
							
					        weightMarkerSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarker]);
					    
					        totRecordWeightMarkerRecipe++;
					        
						}

					}
					
                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarkerDS])>2){
						
						if(Math.round(meters)>(this.metersToCut-1)){
							
					        weightMarkerSumDS+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarkerDS]);
					    
					        totRecordWeightMarkerRecipeDS++;
					        
						}

					}
					
					if(Math.round(meters)>(this.metersToCut-1)){

		               widthLineRecipe = Float.parseFloat(this.KPITable[i][this.widthLineRecipeIndex]);
		               weightLineRecipe = Float.parseFloat(this.KPITable[i][this.weightLineRecipeIndex]);
		               widthUpperTolerance = Float.parseFloat(this.KPITable[i][this.widthUpperToleranceIndex]);
				       widthLowerTolerance = Float.parseFloat(this.KPITable[i][this.widthLowerToleranceIndex]);
				       weightUpperTolerance = Float.parseFloat(this.KPITable[i][this.weightUpperToleranceIndex]);
				       weightLowerTolerance = Float.parseFloat(this.KPITable[i][this.weightLowerToleranceIndex]);
				    
					}
					
				}
				
				
				if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe) && find){
					findOther = true;
					
					if(lastIndex>-1 && prodIndex>1 && c==prodIndex){
						lastIndex = -1;
					}
					
					if(lastIndex==-1){
					  lastIndex = this.findNextStart(i-1);
					  c++;

					  if(lastIndex == -1){
						  if(this.recipesToLimits.containsKey(recipeKey)){
							    lastIndex = (i+this.countCellsFromHeadtoCollection(i, recipe))-cutCells;
							    lastIndexes.add(lastIndex);
	                    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex][this.dateIndex];
	                    		break;
	                    	}
					  }
					  else{
					    if(lastIndex<i){
					    	
					    	boolean brk = false;
					    	
					    	if(Float.parseFloat(this.KPITable[lastIndex-cutCells][this.parameterIndexWidthLine])==0){
					    		
					    		for(int y=(lastIndex-cutCells);y>-1;y--){
					    			if(Float.parseFloat(this.KPITable[y][this.parameterIndexWidthLine])>0){
					    				this.recipesToLimits.get(recipeKey)[1]=this.KPITable[y-cutCells][this.dateIndex];
					    				lastIndex = y;
					    				lastIndexes.add(lastIndex);
					    				brk = true;
					    				break;
					    				
					    			}
					    			
					    		}
					    		
					    		if(brk)
					    			continue;
					    		
					    	 }
					    	else{
					    		
					    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex-cutCells][this.dateIndex];
			    				lastIndexes.add(lastIndex);
			    				continue;
					    		
					    	}
					       }
					    	else{
						      if(this.recipesToLimits.containsKey(recipeKey)){
						    	  lastIndexes.add(lastIndex);
	                    	  	  this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex-cutCells][this.dateIndex];
	                    		  continue;
	                          }  
						    
					    	}
					    
					  }
					  
					}
					
					

				}

				if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe) && findOther){
					
					if(lastIndex!=-1 && i<(lastIndex-cutCells)){

						if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0){
							
							
					            widthLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine]);	
						  
						        totRecordWidthRecipe++;
											
						}
						
						if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLineDS])>0){
							
							
				            widthLineSumDS+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLineDS]);	
					  
					        totRecordWidthRecipeDS++;
										
						}
						
						if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarker])>0){
							
					        widthMarkerSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarker]);
					  
					        totRecordWidthMarkerRecipe++;
										
					    }
						
                        if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarkerDS])>0){
							
					        widthMarkerSumDS+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthMarkerDS]);
					  
					        totRecordWidthMarkerRecipeDS++;
										
					    }
	                    
	                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLine])>2){
							
					            weightLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLine]);	
						  
						        totRecordWeightRecipe++;
						        										
						}
	                    
	                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLineDS])>2){
							
				            weightLineSumDS+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightLineDS]);	
					  
					        totRecordWeightRecipeDS++;
					        										
	                    }
	                    
	                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarker])>2){
							
					        weightMarkerSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarker]);
					  
					        totRecordWeightMarkerRecipe++;
					        										
					    }
	                    
	                    if(Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarkerDS])>2){
							
					        weightMarkerSumDS+=Float.parseFloat(this.KPITable[i][this.parameterIndexWeightMarkerDS]);
					  
					        totRecordWeightMarkerRecipeDS++;
					        										
					    }
	                   
	                    if(i==((lastIndex-cutCells)-1)){
	                    	if(this.recipesToLimits.containsKey(recipeKey)){
	                    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[i][this.dateIndex];
	                    	}
	                    	
	                    }
						
					}
					
				}
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe) && findOther){
					meters = 0;
					time = 0;
					isStartFound = false;
					canStart = false;
					findOther = false;
					thereIsAZeroAsStart = this.thereIsAZeroAsStart(recipe, i);
					prodIndex++;
					
				}
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe) && !findOther && i==(this.numRecords-1)){
					
					if(meters>0 || this.metersToCut==0){	
					   this.recipesToLimits.get(recipeKey)[1] = this.KPITable[i][this.dateIndex];
					}
					lastIndex = i;
					lastIndexes.add(lastIndex);
				    break;
				}
				
				
			}
			
			if((totRecordWidthRecipe == 0 || totRecordWeightRecipe == 0) && !rewind){
				rewind = true;
				k--;
				continue;
			}
				

			if(meters>0 || this.metersToCut==0){	

			   this.recipesPerformanceDuplex.put(recipe, new RecipePerformanceDuplex(recipe, String.valueOf(widthLineRecipe), this.round((float)widthLineSum/totRecordWidthRecipe), this.round((float)widthMarkerSum/totRecordWidthMarkerRecipe), (float)((widthLineSum/totRecordWidthRecipe)-widthLineRecipe), this.round((float)widthLineSumDS/totRecordWidthRecipeDS), this.round((float)widthMarkerSumDS/totRecordWidthMarkerRecipeDS), (float)((widthLineSumDS/totRecordWidthRecipeDS)-widthLineRecipe), String.valueOf(weightLineRecipe), this.round((float)weightLineSum/totRecordWeightRecipe), this.round((float)weightMarkerSum/totRecordWeightMarkerRecipe), (float)((weightLineSum/totRecordWeightRecipe)-weightLineRecipe), this.round((float)weightLineSumDS/totRecordWeightRecipeDS), this.round((float)weightMarkerSumDS/totRecordWeightMarkerRecipeDS), (float)((weightLineSumDS/totRecordWeightRecipeDS)-weightLineRecipe), widthUpperTolerance, widthLowerTolerance, weightUpperTolerance, weightLowerTolerance, totRecordWidthRecipe, totRecordWeightRecipe, totRecordWidthRecipeDS, totRecordWeightRecipeDS));
			   this.calculateRecipeVariance(recipe, rewind, avgSpeed, lastIndexes, thereIsAZeroAsStart);
			   this.calculateRecipeCp(recipe);
			   this.calculateRecipeCpk(recipe);
			   this.calculateProductionIndex(recipe);
		
			   this.addSAPData(recipe.trim());
			}
        

			rewind = false;


		 }catch(Exception e){
	      
			 JOptionPane.showMessageDialog(null, "RECIPE PROCESSING ERROR: \n An error occurred while processing the recipe "+recipe+"! \n This recipe will be excluded from the analysis", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			 this.recipesList.remove(this.recipesList.indexOf(recipe));
			 
			 if(this.recipesPerformanceDuplex.containsKey(recipe))
				 this.recipesPerformanceDuplex.remove(recipe);
			 
			 if(this.recipesList.isEmpty()){
				 JOptionPane.showMessageDialog(null, "FATAL RECIPE PROCESSING ERROR: \n All recipes excluded from the analysis! \n Control your input data files", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				 System.exit(-1);
			 }
			 
			 continue;
	        	
	      }
       }

	}
	
	public void generateInnerLinerSummaryTable(){

		String recipe;
		boolean rewind;
		boolean find;
		boolean findOther;
		int totRecordWidthRecipe;
		int widthLineSum;
		float widthLineRecipe;
		float widthUpperTolerance;
		float widthLowerTolerance;
		double avgSpeed;
		boolean isStartFound;
		boolean canStart;
		long time;
		float meters;
		int cutCells;
		int lastIndex = -1;
		boolean thereIsAZeroAsStart;
		int prodIndex = 1;
		String recipeKey;
		int c;
		List<Integer> lastIndexes = new LinkedList<Integer>();
		
		this.recipesPerformanceInnerLiner = new HashMap<String,RecipePerformanceInnerLiner>();
		this.recipesToLimits = new HashMap<String, String[]>();
        rewind = false;

        for(int k=0;k<this.recipesList.size();k++){
        	
        	recipe = this.recipesList.get(k);

          try{
          
        	find = false;
        	findOther = false;
			totRecordWidthRecipe = 0;
			widthLineSum = 0;
			widthLineRecipe = 0;
			widthUpperTolerance = 0;
			widthLowerTolerance = 0;
			isStartFound = false;
			canStart = false;
			time = 1;
			meters = 0;
			avgSpeed = this.calculateAvgSpeed(recipe);
			cutCells = 0;
			if(prodIndex>1)
				lastIndex = -1;
			thereIsAZeroAsStart = this.thereIsAZeroAsStart(recipe, lastIndex);	
			lastIndex = -1;
			recipeKey = null;
			prodIndex = 1;
			c=1;
			lastIndexes.clear();
			
			if(!this.thereAreAtLeastOneValue(recipe))
				continue;

			for(int i=0;i<this.numRecords;i++){
				
				if(prodIndex>1)
					recipeKey = recipe+"-"+prodIndex;
				else
					recipeKey = recipe;
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe)){
															
					find = true;
					
					if((((i-1==0) || Integer.parseInt(this.KPITable[i][this.parameterIndex("Calender - Bobbin Numer (--)", this.KPITable)])<Integer.parseInt(this.KPITable[i-1][this.parameterIndex("Calender - Bobbin Numer (--)", this.KPITable)])) && !isStartFound)){			
						isStartFound = true;
					}


					if(!rewind){

					  if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && isStartFound && !canStart){

						  meters+=avgSpeed*time;
						  time+=5;
						  cutCells++;
						  
					  }
					}
					else{
						
						 if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && !canStart){

							  meters+=avgSpeed*time;
							  time+=5;
							  cutCells++;
							 
						  }
						
					}
					
					
					if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0){
												
						if(Math.round(meters)>(this.metersToCut-1)){

							canStart = true;
							
							if(!this.recipesToLimits.containsKey(recipeKey)){
								
								String[] temp = new String[2];
								temp[0] = this.KPITable[i][this.dateIndex];
								this.recipesToLimits.put(recipeKey, temp);
							}
							
						
				            widthLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine]);	
					  
					        totRecordWidthRecipe++;
					        

					        
						}
										
					}
					
                   					
					if(Math.round(meters)>(this.metersToCut-1)){

		               widthLineRecipe = this.getRecipeWidthForInnerLiner(recipe);
		               widthUpperTolerance = Float.parseFloat(this.KPITable[i][this.parameterIndex("Width Upper Tolerance", this.KPITable)]); 
				       widthLowerTolerance = Float.parseFloat(this.KPITable[i][this.parameterIndex("Width Lower Tolerance", this.KPITable)]);
				    
					}
					
				}
				
				
				if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe) && find){
					findOther = true;
					
					if(lastIndex>-1 && prodIndex>1 && c==prodIndex){
						lastIndex = -1;
					}
					
					if(lastIndex==-1){
					  lastIndex = this.findNextStart(i-1);
					  c++;

					  if(lastIndex == -1){
						  if(this.recipesToLimits.containsKey(recipeKey)){
							    lastIndex = (i+this.countCellsFromHeadtoCollection(i, recipe))-cutCells;
							    lastIndexes.add(lastIndex);
	                    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex][this.dateIndex];
	                    		break;
	                    	}
					  }
					  else{
					    if(lastIndex<i){
					    	
					    	boolean brk = false;
					    	
					    	if(Float.parseFloat(this.KPITable[lastIndex-cutCells][this.parameterIndexWidthLine])==0){
					    		
					    		for(int y=(lastIndex-cutCells);y>-1;y--){
					    			if(Float.parseFloat(this.KPITable[y][this.parameterIndexWidthLine])>0){
					    				this.recipesToLimits.get(recipeKey)[1]=this.KPITable[y-cutCells][this.dateIndex];
					    				lastIndex = y;
					    				lastIndexes.add(lastIndex);
					    				brk = true;
					    				break;
					    				
					    			}
					    			
					    		}
					    		
					    		if(brk)
					    			continue;
					    		
					    	 }
					    	else{
					    		
					    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex-cutCells][this.dateIndex];
			    				lastIndexes.add(lastIndex);
			    				continue;
					    		
					    	}
					       }
					    	else{
						      if(this.recipesToLimits.containsKey(recipeKey)){
						    	  lastIndexes.add(lastIndex);
	                    	  	  this.recipesToLimits.get(recipeKey)[1]=this.KPITable[lastIndex-cutCells][this.dateIndex];
	                    		  continue;
	                          }  
						    
					    	}
					    
					  }
					  
					}
					
					

				}

				if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe) && findOther){
					
					if(lastIndex!=-1 && i<(lastIndex-cutCells)){

						if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0){
							
							
					            widthLineSum+=Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine]);	
						  
						        totRecordWidthRecipe++;
											
						}
						
							                   
	                    if(i==((lastIndex-cutCells)-1)){
	                    	
	                    	if(this.recipesToLimits.containsKey(recipeKey)){
	                    		this.recipesToLimits.get(recipeKey)[1]=this.KPITable[i][this.dateIndex];
	                    	}
	                    	
	                    }
						
					}
					
				}
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe) && findOther){
					meters = 0;
					time = 0;
					isStartFound = false;
					canStart = false;
					findOther = false;
					thereIsAZeroAsStart = this.thereIsAZeroAsStart(recipe, i);
					prodIndex++;
					
				}
				
				if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe) && !findOther && i==(this.numRecords-1)){
					
					if(meters>0 || this.metersToCut==0){	
					   this.recipesToLimits.get(recipeKey)[1] = this.KPITable[i][this.dateIndex];
					}
					lastIndex = i;
					lastIndexes.add(lastIndex);
				    break;
				}
				
				
			}
			
			if((totRecordWidthRecipe == 0) && !rewind){
				rewind = true;
				k--;
				continue;
			}
				

			if(meters>0 || this.metersToCut==0){	

			   this.recipesPerformanceInnerLiner.put(recipe, new RecipePerformanceInnerLiner(recipe, String.valueOf(widthLineRecipe), this.round((float)widthLineSum/totRecordWidthRecipe), (float)((widthLineSum/totRecordWidthRecipe)-widthLineRecipe), widthUpperTolerance, widthLowerTolerance, totRecordWidthRecipe));
			   this.calculateRecipeVariance(recipe, rewind, avgSpeed, lastIndexes, thereIsAZeroAsStart);
			   this.calculateRecipeCp(recipe);
			   this.calculateRecipeCpk(recipe);
			   this.calculateProductionIndex(recipe);
		
			   this.addSAPData(recipe.trim());
			}
        

			rewind = false;


		 }catch(Exception e){
	      
			 JOptionPane.showMessageDialog(null, "RECIPE PROCESSING ERROR: \n An error occurred while processing the recipe "+recipe+"! \n This recipe will be excluded from the analysis", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			 this.recipesList.remove(this.recipesList.indexOf(recipe));
			 
			 if(this.recipesPerformanceInnerLiner.containsKey(recipe))
				 this.recipesPerformanceInnerLiner.remove(recipe);
			 			 
			 if(this.recipesList.isEmpty()){
				 JOptionPane.showMessageDialog(null, "FATAL RECIPE PROCESSING ERROR: \n All recipes excluded from the analysis! \n Control your input data files", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				 System.exit(-1);
			 }
			 
			 continue;
	        	
	      }
       }

	}
	
    private void cleanRecipesInnerLiner(){
    	
    	this.recipeCol = this.parameterIndex("SAP Code", this.KPITable);
    	
    	for(int i=1;i<this.numRecords;i++){
    		if(this.KPITable[i][this.recipeCol].equals("9999A0"))
    			continue;
    		
    		this.KPITable[i][this.recipeCol] = ""+Integer.parseInt(this.KPITable[i][this.recipeCol]);
    		
    	}
    	
    }
	
	
	private boolean thereAreAtLeastOneValue(String recipe){
		
		for(int i=0;i<this.numRecords;i++){
			
			if(this.KPITable[i][this.recipeCol].equals(recipe))
				if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0)
					return true;
			
		}
		
		return false;
		
	}
	
	private int countCellsFromHeadtoCollection(int k, String recipe){
		
		int index = 0;
		int head=0;
		int collection = 0;
		boolean findHeadStart = false;
		boolean findCollectionStart = false;
		
		for(int i=k;i>-1;i--){
			
			if(!this.KPITable[i][this.recipeCol].equals(recipe))
				index = i+1;
			
		}
		
		for(int i=index;i<this.numRecords;i++){
			
			if(this.KPITable[i][this.recipeCol].equals(recipe)){
				
				if((Float.parseFloat(this.KPITable[i][this.parameterIndex(this.getStartParameter(), this.KPITable)])==0))
					findHeadStart = true;
				
				if(findHeadStart && (Float.parseFloat(this.KPITable[i][this.parameterIndex(this.getStartParameter(), this.KPITable)])>0)){
					head = i;
					break;
				}
				
			}
			
		}
				
        for(int i=index;i<this.numRecords;i++){
			
			if(this.KPITable[i][this.recipeCol].equals(recipe)){
				
				if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])==0)){
					findCollectionStart = true;
				}
				
				if(findCollectionStart && (Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0)){
					collection = i;
					break;
				}
				
			}
			
		}

		return collection-head;
		
	}
	
	private boolean thereIsAZeroAsStart(String recipe, int precLI){
		
		int start;
		
		if(precLI>-1)
			start = precLI;
		else
			start = 0;
		
		for(int i=start;i<this.numRecords;i++){
			
			if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe)){
				
				if(Double.parseDouble(this.KPITable[i][this.parameterIndexWidthLine])==0)
					return true;
				
			}
			
		}
		
		return false;
		
	}
	
	private int findNextStart(int i){
		
		switch(this.machine){		
		
			case "Quadruplex":
	
				for(int j=i;j<this.numRecords;j++){
			       if(Float.parseFloat(this.KPITable[j][this.parameterIndexWidthLine])==0)
			    	   return j-1;
				}
				break;	
				
			case "Duplex": 
				
				int actRecipeVal = Integer.parseInt(this.KPITable[i][this.parameterIndexRecipeWidthLineOSDuplex]);
				int actUpTolVal = Integer.parseInt(this.KPITable[i][this.parameterIndexWidthLineOSDuplexUpperTolerance]);
				int actLowTolVal= Integer.parseInt(this.KPITable[i][this.parameterIndexWidthLineOSDuplexLowerTolerance]);
				
				for(int j=i;j<this.numRecords;j++){
			       if((Integer.parseInt(this.KPITable[j][this.parameterIndexRecipeWidthLineOSDuplex])!=actRecipeVal) || (Integer.parseInt(this.KPITable[j][this.parameterIndexWidthLineOSDuplexUpperTolerance])!=actUpTolVal) || (Integer.parseInt(this.KPITable[j][this.parameterIndexWidthLineOSDuplexLowerTolerance])!=actLowTolVal))
			    	   return j-1;
				}
				break;
				
			case "InnerLiner": 
							
				for(int j=i;j<this.numRecords;j++){
			       if(Integer.parseInt(this.KPITable[j][this.parameterIndex("Calender - Bobbin Numer (--)", this.KPITable)])<Integer.parseInt(this.KPITable[j-1][this.parameterIndex("Calender - Bobbin Numer (--)", this.KPITable)]))
			    	   return j;
				}
				break;
				
		}
		
		return -1;
		
	}
	
	private double calculateAvgSpeed(String recipe){
		
		double totSpeed = 0;
		long numRec = 0;
		
		String paramName = "";
		
		switch(this.machine){
		
		  case "Quadruplex":
			  paramName = "LINE - LINE Speed Feedback (m/min)";
			  break;
			  
		  case "Duplex":
			  paramName = "LINE - LINE Speed Feedback (m/min)";
			  break;
			  
		  case "InnerLiner":
			  paramName = "Calender - Line Speed Actual (m/min)";
			  break;
		
		}
		
		for(int i=1;i<this.numRecords;i++){
			
			if(recipe.equals(this.KPITable[i][this.recipeCol])){
		
				 totSpeed+=Double.parseDouble(this.KPITable[i][this.parameterIndex(paramName, this.KPITable)])*0.0166667;
				 numRec++;
				 
			}
			
		}

		return totSpeed / numRec;
		
	}
	
	private void calculateRecipeVariance(String recipe, boolean rewind, double avgSpeed, List<Integer> lastIndexes, boolean thereIsAZeroAsStart){
		
		double sumWidthVar = 0;
		double sumWeightVar = 0;
		double sumWidthVarDS = 0;
		double sumWeightVarDS = 0;
		boolean isStartFound = false;
		boolean canStart = false;
		boolean findOther = false;
		long time = 0;
		double meters = 0;
		int cutCells = 0;
		int k = 0;
	
		for(int i=1;i<this.numRecords;i++){
				
			if(recipe.equals(this.KPITable[i][this.recipeCol])){
				
			  if(this.machine.equals("Quadruplex")){
				
				if(((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])==0) && !isStartFound) || !thereIsAZeroAsStart){
					
					isStartFound = true;
					
				}
				
				if(!rewind){
				  if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && isStartFound && !canStart){
					
					  meters+=avgSpeed*time;
					  time+=2;
					  cutCells++;
					
				  }
				}
				else{
					
					 if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && !canStart){
							
						  meters+=avgSpeed*time;
						  time+=2;
						  cutCells++;
						
					  }
					
				}
				
			  }
			  
			  if(this.machine.equals("Duplex")){
					
					if(((Float.parseFloat(this.KPITable[i][this.parameterIndex("LINE - Width Winder Zone Upper Tolerance  (mm)", this.KPITable)])!=Float.parseFloat(this.KPITable[i-1][this.parameterIndex("LINE - Width Winder Zone Upper Tolerance  (mm)", this.KPITable)])) && !isStartFound)){
						isStartFound = true;
					}
					
					if(!rewind){
					  if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && isStartFound && !canStart){
						
						  meters+=avgSpeed*time;
						  time+=2;
						  cutCells++;
						
					  }
					}
					else{
						
						 if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && !canStart){
								
							  meters+=avgSpeed*time;
							  time+=2;
							  cutCells++;
							
						  }
						
					}
					
				  }
			  
			  if(this.machine.equals("InnerLiner")){
					
					if((((i-1)==0 || Integer.parseInt(this.KPITable[i][this.parameterIndex("Calender - Bobbin Numer (--)", this.KPITable)])<Integer.parseInt(this.KPITable[i-1][this.parameterIndex("Calender - Bobbin Numer (--)", this.KPITable)])) && !isStartFound)){
						isStartFound = true;
					}
					
					if(!rewind){
					  if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && isStartFound && !canStart){
						
						  meters+=avgSpeed*time;
						  time+=5;
						  cutCells++;
						
					  }
					}
					else{
						
						 if((Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0) && !canStart){
								
							  meters+=avgSpeed*time;
							  time+=5;
							  cutCells++;
							
						  }
						
					}
					
			}

				if(Float.parseFloat(this.KPITable[i][this.parameterIndexWidthLine])>0 && Math.round(meters)>(this.metersToCut-1)){

				  canStart = true;
					
				  if(Float.parseFloat((this.KPITable[i][this.parameterIndexWidthLine]))!=0){	
					  if(this.machine.equals("Quadruplex"))
						  sumWidthVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWidthLine]))-Double.parseDouble(this.recipesPerformanceQuadruplex.get(recipe).getAvgWidth())),2);
				  	  if(this.machine.equals("Duplex"))
				  		  sumWidthVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWidthLine]))-Double.parseDouble(this.recipesPerformanceDuplex.get(recipe).getAvgWidth())),2);
				  	  if(this.machine.equals("InnerLiner"))
				  		  sumWidthVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWidthLine]))-Double.parseDouble(this.recipesPerformanceInnerLiner.get(recipe).getAvgWidth())),2);
				  }
				  if((this.machine.equals("Quadruplex") || this.machine.equals("Duplex")) && Float.parseFloat((this.KPITable[i][this.parameterIndexWeightLine]))>2){
					  if(this.machine.equals("Quadruplex"))
				         sumWeightVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWeightLine]))-Double.parseDouble(this.recipesPerformanceQuadruplex.get(recipe).getAvgWeight())),2);
					  if(this.machine.equals("Duplex"))
					         sumWeightVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWeightLine]))-Double.parseDouble(this.recipesPerformanceDuplex.get(recipe).getAvgWeight())),2);
				  }
				  if(this.machine.equals("Duplex")){
					  if(Float.parseFloat((this.KPITable[i][this.parameterIndexWidthLineDS]))!=0)				
						    sumWidthVarDS += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWidthLineDS]))-Double.parseDouble(this.recipesPerformanceDuplex.get(recipe).getAvgWidthDS())),2);
					  if(Float.parseFloat((this.KPITable[i][this.parameterIndexWeightLineDS]))>2)					
						  sumWeightVarDS += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWeightLineDS]))-Double.parseDouble(this.recipesPerformanceDuplex.get(recipe).getAvgWeightDS())),2);
				   }
				
				}

			}
			
			if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe) && canStart){
				findOther = true;
			}
			
			
			if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe) && findOther){
				
				if(lastIndexes!=null && lastIndexes.size()!=0){
				
				if(lastIndexes.get(k)!=-1 && i<(lastIndexes.get(k)-cutCells)){
					 if(Float.parseFloat((this.KPITable[i][this.parameterIndexWidthLine]))!=0){
					    if(this.machine.equals("Quadruplex"))	 
					      sumWidthVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWidthLine]))-Double.parseDouble(this.recipesPerformanceQuadruplex.get(recipe).getAvgWidth())),2);
					    if(this.machine.equals("Duplex"))
					    	sumWidthVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWidthLine]))-Double.parseDouble(this.recipesPerformanceDuplex.get(recipe).getAvgWidth())),2);
					    if(this.machine.equals("InnerLiner"))
					    	sumWidthVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWidthLine]))-Double.parseDouble(this.recipesPerformanceInnerLiner.get(recipe).getAvgWidth())),2);
					 }
					 if((this.machine.equals("Quadruplex") || this.machine.equals("Duplex")) && Float.parseFloat((this.KPITable[i][this.parameterIndexWeightLine]))>2){
						   if(this.machine.equals("Quadruplex"))
						     sumWeightVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWeightLine]))-Double.parseDouble(this.recipesPerformanceQuadruplex.get(recipe).getAvgWeight())),2);
						   if(this.machine.equals("Duplex"))
							 sumWeightVar += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWeightLine]))-Double.parseDouble(this.recipesPerformanceDuplex.get(recipe).getAvgWeight())),2);
						  }
						  if(this.machine.equals("Duplex")){
							  if(Float.parseFloat((this.KPITable[i][this.parameterIndexWidthLineDS]))!=0)				
								   sumWidthVarDS += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWidthLineDS]))-Double.parseDouble(this.recipesPerformanceDuplex.get(recipe).getAvgWidthDS())),2);
							  if(Float.parseFloat((this.KPITable[i][this.parameterIndexWeightLineDS]))>2)					
								  sumWeightVarDS += Math.pow((Double.parseDouble((this.KPITable[i][this.parameterIndexWeightLineDS]))-Double.parseDouble(this.recipesPerformanceDuplex.get(recipe).getAvgWeightDS())),2);
						   }
					
					}
				
				}
								
			}
			
			if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe) && findOther){
				meters = 0;
				time = 0;
				isStartFound = false;
				canStart = false;
				findOther = false;
				thereIsAZeroAsStart = this.thereIsAZeroAsStart(recipe, i);
				k++;

			}
				
		}
		
		
		switch(this.machine){
		
			case "Quadruplex":
				this.recipesPerformanceQuadruplex.get(recipe).setWidthSigma(sumWidthVar/(this.recipesPerformanceQuadruplex.get(recipe).getRecipeWidthRecords()-1));
				this.recipesPerformanceQuadruplex.get(recipe).setWeightSigma(sumWeightVar/(this.recipesPerformanceQuadruplex.get(recipe).getRecipeWeightRecords()-1));
				break;
				 
			case "Duplex":
				this.recipesPerformanceDuplex.get(recipe).setWidthSigma(sumWidthVar/(this.recipesPerformanceDuplex.get(recipe).getRecipeWidthRecords()-1));
				this.recipesPerformanceDuplex.get(recipe).setWeightSigma(sumWeightVar/(this.recipesPerformanceDuplex.get(recipe).getRecipeWeightRecords()-1));
				this.recipesPerformanceDuplex.get(recipe).setWidthSigmaDS(sumWidthVarDS/(this.recipesPerformanceDuplex.get(recipe).getRecipeWidthRecordsDS()-1));
				this.recipesPerformanceDuplex.get(recipe).setWeightSigmaDS(sumWeightVarDS/(this.recipesPerformanceDuplex.get(recipe).getRecipeWeightRecordsDS()-1));
				break;
				
			case "InnerLiner":
				this.recipesPerformanceInnerLiner.get(recipe).setWidthSigma(sumWidthVar/(this.recipesPerformanceInnerLiner.get(recipe).getRecipeWidthRecords()-1));
				break;
		
		}
		 
			
	}
	
	private void calculateRecipeCp(String recipe){
		
		double widthCp;
		double weightCp;
		double widthCpDS;
		double weightCpDS;
		
		switch(this.machine){
		
			case "Quadruplex":
				widthCp = ((this.recipesPerformanceQuadruplex.get(recipe).getWidthUpperTolerance())-(this.recipesPerformanceQuadruplex.get(recipe).getWidthLowerTolerance()))/(6*this.recipesPerformanceQuadruplex.get(recipe).getWidthSigma());
				weightCp = ((this.recipesPerformanceQuadruplex.get(recipe).getWeightUpperTolerance())-(this.recipesPerformanceQuadruplex.get(recipe).getWeightLowerTolerance()))/(6*this.recipesPerformanceQuadruplex.get(recipe).getWeightSigma());

				this.recipesPerformanceQuadruplex.get(recipe).setCpWidth(this.round(widthCp));
				this.recipesPerformanceQuadruplex.get(recipe).setCpWeight(this.round(weightCp));
				break;
				
			case "Duplex":
				widthCp = ((this.recipesPerformanceDuplex.get(recipe).getWidthUpperTolerance())-(this.recipesPerformanceDuplex.get(recipe).getWidthLowerTolerance()))/(6*this.recipesPerformanceDuplex.get(recipe).getWidthSigma());
				weightCp = ((this.recipesPerformanceDuplex.get(recipe).getWeightUpperTolerance())-(this.recipesPerformanceDuplex.get(recipe).getWeightLowerTolerance()))/(6*this.recipesPerformanceDuplex.get(recipe).getWeightSigma());
				
				widthCpDS = ((this.recipesPerformanceDuplex.get(recipe).getWidthUpperTolerance())-(this.recipesPerformanceDuplex.get(recipe).getWidthLowerTolerance()))/(6*this.recipesPerformanceDuplex.get(recipe).getWidthSigmaDS());
				weightCpDS = ((this.recipesPerformanceDuplex.get(recipe).getWeightUpperTolerance())-(this.recipesPerformanceDuplex.get(recipe).getWeightLowerTolerance()))/(6*this.recipesPerformanceDuplex.get(recipe).getWeightSigmaDS());

				this.recipesPerformanceDuplex.get(recipe).setCpWidth(this.round(widthCp));
				this.recipesPerformanceDuplex.get(recipe).setCpWeight(this.round(weightCp));
				
				this.recipesPerformanceDuplex.get(recipe).setCpWidthDS(this.round(widthCpDS));
				this.recipesPerformanceDuplex.get(recipe).setCpWeightDS(this.round(weightCpDS));
				
				break;
				
			case "InnerLiner":
				widthCp = ((this.recipesPerformanceInnerLiner.get(recipe).getWidthUpperTolerance())-(this.recipesPerformanceInnerLiner.get(recipe).getWidthLowerTolerance()))/(6*this.recipesPerformanceInnerLiner.get(recipe).getWidthSigma());
				this.recipesPerformanceInnerLiner.get(recipe).setCpWidth(this.round(widthCp));
				break;
		}
		
		
				
	}
	
	private void calculateRecipeCpk(String recipe){
		
		double widthCpu;
		double widthCpl;
		double weightCpu;
		double weightCpl;
		
		double widthCpuDS;
		double widthCplDS;
		double weightCpuDS;
		double weightCplDS;
		
		switch(this.machine){
		
			case "Quadruplex":
				widthCpu = ((this.recipesPerformanceQuadruplex.get(recipe).getWidthUpperTolerance())-Double.parseDouble((this.recipesPerformanceQuadruplex.get(recipe).getAvgWidth()))) / (3*this.recipesPerformanceQuadruplex.get(recipe).getWidthSigma());
				widthCpl = (Double.parseDouble((this.recipesPerformanceQuadruplex.get(recipe).getAvgWidth()))-(this.recipesPerformanceQuadruplex.get(recipe).getWidthLowerTolerance())) / (3*this.recipesPerformanceQuadruplex.get(recipe).getWidthSigma());
				weightCpu = ((this.recipesPerformanceQuadruplex.get(recipe).getWeightUpperTolerance())-Double.parseDouble((this.recipesPerformanceQuadruplex.get(recipe).getAvgWeight()))) / (3*this.recipesPerformanceQuadruplex.get(recipe).getWeightSigma());
				weightCpl = (Double.parseDouble((this.recipesPerformanceQuadruplex.get(recipe).getAvgWeight()))-(this.recipesPerformanceQuadruplex.get(recipe).getWeightLowerTolerance())) / (3*this.recipesPerformanceQuadruplex.get(recipe).getWeightSigma());
				
				this.recipesPerformanceQuadruplex.get(recipe).setCpkWidth(this.round(Math.max(widthCpu, widthCpl)));
				this.recipesPerformanceQuadruplex.get(recipe).setCpkWeight(this.round(Math.max(weightCpu, weightCpl)));
				break;
				
			case "Duplex":
				widthCpu = ((this.recipesPerformanceDuplex.get(recipe).getWidthUpperTolerance())-Double.parseDouble((this.recipesPerformanceDuplex.get(recipe).getAvgWidth()))) / (3*this.recipesPerformanceDuplex.get(recipe).getWidthSigma());
				widthCpl = (Double.parseDouble((this.recipesPerformanceDuplex.get(recipe).getAvgWidth()))-(this.recipesPerformanceDuplex.get(recipe).getWidthLowerTolerance())) / (3*this.recipesPerformanceDuplex.get(recipe).getWidthSigma());
				weightCpu = ((this.recipesPerformanceDuplex.get(recipe).getWeightUpperTolerance())-Double.parseDouble((this.recipesPerformanceDuplex.get(recipe).getAvgWeight()))) / (3*this.recipesPerformanceDuplex.get(recipe).getWeightSigma());
				weightCpl = (Double.parseDouble((this.recipesPerformanceDuplex.get(recipe).getAvgWeight()))-(this.recipesPerformanceDuplex.get(recipe).getWeightLowerTolerance())) / (3*this.recipesPerformanceDuplex.get(recipe).getWeightSigma());
				
				widthCpuDS = ((this.recipesPerformanceDuplex.get(recipe).getWidthUpperTolerance())-Double.parseDouble((this.recipesPerformanceDuplex.get(recipe).getAvgWidthDS()))) / (3*this.recipesPerformanceDuplex.get(recipe).getWidthSigmaDS());
				widthCplDS = (Double.parseDouble((this.recipesPerformanceDuplex.get(recipe).getAvgWidthDS()))-(this.recipesPerformanceDuplex.get(recipe).getWidthLowerTolerance())) / (3*this.recipesPerformanceDuplex.get(recipe).getWidthSigmaDS());
				weightCpuDS = ((this.recipesPerformanceDuplex.get(recipe).getWeightUpperTolerance())-Double.parseDouble((this.recipesPerformanceDuplex.get(recipe).getAvgWeightDS()))) / (3*this.recipesPerformanceDuplex.get(recipe).getWeightSigmaDS());
				weightCplDS = (Double.parseDouble((this.recipesPerformanceDuplex.get(recipe).getAvgWeightDS()))-(this.recipesPerformanceDuplex.get(recipe).getWeightLowerTolerance())) / (3*this.recipesPerformanceDuplex.get(recipe).getWeightSigmaDS());
				
				this.recipesPerformanceDuplex.get(recipe).setCpkWidth(this.round(Math.max(widthCpu, widthCpl)));
				this.recipesPerformanceDuplex.get(recipe).setCpkWeight(this.round(Math.max(weightCpu, weightCpl)));
				
				this.recipesPerformanceDuplex.get(recipe).setCpkWidthDS(this.round(Math.max(widthCpuDS, widthCplDS)));
				this.recipesPerformanceDuplex.get(recipe).setCpkWeightDS(this.round(Math.max(weightCpuDS, weightCplDS)));
				break;
				
			case "InnerLiner":
				widthCpu = ((this.recipesPerformanceInnerLiner.get(recipe).getWidthUpperTolerance())-Double.parseDouble((this.recipesPerformanceInnerLiner.get(recipe).getAvgWidth()))) / (3*this.recipesPerformanceInnerLiner.get(recipe).getWidthSigma());
				widthCpl = (Double.parseDouble((this.recipesPerformanceInnerLiner.get(recipe).getAvgWidth()))-(this.recipesPerformanceInnerLiner.get(recipe).getWidthLowerTolerance())) / (3*this.recipesPerformanceInnerLiner.get(recipe).getWidthSigma());
				
				this.recipesPerformanceInnerLiner.get(recipe).setCpkWidth(this.round(Math.max(widthCpu, widthCpl)));
				break;
		
		}
		
		
		
	}
	
	private void calculateProductionIndex(String recipe){
		
	   boolean find = false;
	   int productionIndex = 0;
		
	   
       for(int i=0;i<this.numRecords;i++){
    	   
    	 if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe)){
    		 
    		 if(!find){
    			 
    			 find = true;
    			 productionIndex++;
    			 
    		 }
    		 
    		 this.KPITable[i][this.numCols-1] = String.valueOf(productionIndex);
    		 
    	 }
    	 
    	 if(find)    		 
    	   if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe))
    		   find = false;
    	
    	 
    	}
		
       switch(machine){
          case "Quadruplex":
		    this.recipesPerformanceQuadruplex.get(recipe).setProductionIndex(productionIndex);
		    break;
          case "Duplex":
        	this.recipesPerformanceDuplex.get(recipe).setProductionIndex(productionIndex);
  		    break;
          case "InnerLiner":
          	this.recipesPerformanceInnerLiner.get(recipe).setProductionIndex(productionIndex);
    		break;  		    
       }
		
	}
	
	private int howManyProductionIndexes(String recipe){
		
		   boolean find = false;
		   int productionIndex = 0;
		   
	       for(int i=0;i<this.numRecords;i++){
	    	   
	    	 if(this.KPITable[i][this.recipeCol]!=null && this.KPITable[i][this.recipeCol].equals(recipe)){
	    		 
	    		 if(!find){
	    			 
	    			 find = true;
	    			 productionIndex++;
	    			 
	    		 }
	    		 
	    		 this.KPITable[i][this.numCols-1] = String.valueOf(productionIndex);
	    		 
	    	 }
	    	 
	    	 if(find)    		 
	    	   if(this.KPITable[i][this.recipeCol]!=null && !this.KPITable[i][this.recipeCol].equals(recipe))
	    		   find = false;
	    	
	    	 
	    	}
			
	       
			return productionIndex;
			
		}
	
	
	private void addSAPData(String recipe){
		
		int recipeSAPIndex = -1;
		
		if(this.machine.equals("Quadruplex"))
			recipeSAPIndex = this.parameterIndex("AVAA :Codice Corto", this.SAPTable);
		else
			if(this.machine.equals("Duplex"))
				recipeSAPIndex = this.parameterIndex("FNAB :Codice Corto", this.SAPTable);
			else
				if(this.machine.equals("InnerLiner"))
					recipeSAPIndex = this.parameterIndex("ShortCode", this.SAPTable);
		
		if(!this.isParameterIndexCorrect(recipeSAPIndex)){
			
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			
		}
		
		int widthSAPIndex = -1;
		
		if(this.machine.equals("Quadruplex"))
			widthSAPIndex = this.parameterIndex("FAAO :Largh totale fascia+mini", this.SAPTable);
		else
			if(this.machine.equals("Duplex"))
				widthSAPIndex = this.parameterIndex("FNAB :Larghezza totale assembl", this.SAPTable);
			else
				if(this.machine.equals("InnerLiner"))
					widthSAPIndex = this.parameterIndex("TargetWidth", this.SAPTable);
		
		if(!this.isParameterIndexCorrect(widthSAPIndex))
			if(this.machine.equals("Quadruplex"))
			  this.recipesPerformanceQuadruplex.get(recipe).setTargetWidth("N/A");
			else
				if(this.machine.equals("Duplex"))
					this.recipesPerformanceDuplex.get(recipe).setTargetWidth("N/A");
				else
					if(this.machine.equals("InnerLiner"))
						this.recipesPerformanceInnerLiner.get(recipe).setTargetWidth("N/A");
		
		int weightSAPIndex = -1;
		int weightSAPQtyIndex = -1;
		
		if(this.machine.equals("Quadruplex") || this.machine.equals("Duplex")){
		
			if(this.machine.equals("Quadruplex"))
				weightSAPIndex = this.parameterIndex("FAAO :Racc bilancia continua", this.SAPTable);
			else
				if(this.machine.equals("Duplex")){
					weightSAPIndex = this.parameterIndex("FNAB_WEIGHT", this.SAPTable);
					weightSAPQtyIndex = this.parameterIndex("AGAA :FNAB1_QTY", this.SAPTable);
				}
		
			if(!this.isParameterIndexCorrect(weightSAPIndex))
				if(this.machine.equals("Quadruplex"))
					this.recipesPerformanceQuadruplex.get(recipe).setTargetWeight("N/A");
				else
					if(this.machine.equals("Duplex"))
						this.recipesPerformanceDuplex.get(recipe).setTargetWeight("N/A");
		
		}
		
		boolean find=false;

		for(int i=0;i<this.numSAPRecords-1;i++){

			if(this.SAPTable[i][recipeSAPIndex].equals(recipe)){

				if(this.SAPTable[i][widthSAPIndex]==null){
					if(this.machine.equals("Quadruplex"))
						this.recipesPerformanceQuadruplex.get(recipe).setTargetWidth("N/A");
					else
						if(this.machine.equals("Duplex"))
							this.recipesPerformanceDuplex.get(recipe).setTargetWidth("N/A");
						else
							if(this.machine.equals("InnerLiner"))
								this.recipesPerformanceInnerLiner.get(recipe).setTargetWidth("N/A");
				}
				else{
					if(this.machine.equals("Quadruplex"))
						this.recipesPerformanceQuadruplex.get(recipe).setTargetWidth(this.SAPTable[i][widthSAPIndex]);
					else
						if(this.machine.equals("Duplex"))
							this.recipesPerformanceDuplex.get(recipe).setTargetWidth(this.SAPTable[i][widthSAPIndex]);
						else
							if(this.machine.equals("InnerLiner")){
								this.recipesPerformanceInnerLiner.get(recipe).setTargetWidth(this.SAPTable[i][widthSAPIndex]);
							}
				}
		
				if(this.machine.equals("Quadruplex") || this.machine.equals("Duplex")){		
				
					if(this.SAPTable[i][weightSAPIndex]==null){
						if(this.machine.equals("Quadruplex"))
							this.recipesPerformanceQuadruplex.get(recipe).setTargetWeight("N/A");
						else
							if(this.machine.equals("Duplex"))
								this.recipesPerformanceDuplex.get(recipe).setTargetWeight("N/A");
					}
					else{
						if(this.machine.equals("Quadruplex"))
							this.recipesPerformanceQuadruplex.get(recipe).setTargetWeight(this.SAPTable[i][weightSAPIndex]);
						else
							if(this.machine.equals("Duplex"))							
								this.recipesPerformanceDuplex.get(recipe).setTargetWeight(""+Math.round(((Double.parseDouble(this.SAPTable[i][weightSAPIndex].replace(",", "."))/Double.parseDouble(this.SAPTable[i][weightSAPQtyIndex].replace(",", ".")))*1000)));
						}
				
				}
				
				find = true;
			}
			
		}
		
		if(!find){
			
			if(this.machine.equals("Quadruplex")){
				this.recipesPerformanceQuadruplex.get(recipe).setTargetWidth("N/A");
				this.recipesPerformanceQuadruplex.get(recipe).setTargetWeight("N/A");
			}
			else
				if(this.machine.equals("Duplex")){
					this.recipesPerformanceDuplex.get(recipe).setTargetWidth("N/A");
					this.recipesPerformanceDuplex.get(recipe).setTargetWeight("N/A");
				}
			
		}
		
		
	}
	
	private void setParameterIndex(String[][] table){
		
		this.dateIndex = this.parameterIndex("DateTimeRec", table);
		
		if(!this.isParameterIndexCorrect(this.dateIndex)){
  	  		
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
		this.parameterIndexWidthLine = this.parameterIndex("LINE - Width Winder Zone Measure OS (Or Single Stripe) (mm)", table);
		
		if(!this.isParameterIndexCorrect(this.parameterIndexWidthLine)){
      	  		
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
		if(!this.machine.equals("Duplex")){
			
			this.parameterIndexWidthMarker = this.parameterIndex("LINE - Width Marking Zone Measure OS (Or Single Stripe) (mm)", table);
        
			if(!this.isParameterIndexCorrect(this.parameterIndexWidthMarker)){
					
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);

			}
			
		}
		
        this.parameterIndexWeightLine = this.parameterIndex("LINE - Weight Winder Zone Measure OS (Or Single Stripe) (mm)", table);
        
		if(!this.isParameterIndexCorrect(this.parameterIndexWeightLine)){
					
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
        this.parameterIndexWeightMarker = this.parameterIndex("LINE - Weight Marking Zone Measure OS (Or Single Stripe) (mm)", table);
        
		if(!this.isParameterIndexCorrect(this.parameterIndexWeightMarker)){
						
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
		this.widthLineRecipeIndex = this.parameterIndex("LINE - Width Winder Zone Preset from Recipe (mm)", table);
		
			if(!this.isParameterIndexCorrect(this.widthLineRecipeIndex)){
								
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
				
			}
			
			this.weightLineRecipeIndex = this.parameterIndex("LINE - Weight Winder Zone Preset from Recipe (g)", table);
			
			if(!this.isParameterIndexCorrect(this.weightLineRecipeIndex)){
				
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
				
			}
			
            this.widthUpperToleranceIndex = this.parameterIndex("LINE - Width Winder Zone Upper Tolerance  (mm)", table);

			if(!this.isParameterIndexCorrect(this.widthUpperToleranceIndex)){
				
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
				
			}
			
            this.widthLowerToleranceIndex = this.parameterIndex("LINE - Width Winder Zone Lower Tolerance  (mm)", table);

			if(!this.isParameterIndexCorrect(this.widthLowerToleranceIndex)){
				
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
				
			}
			
            this.weightUpperToleranceIndex = this.parameterIndex("LINE - Weight Winder Zone Upper Tolerance  (g)", table);

			if(!this.isParameterIndexCorrect(this.weightUpperToleranceIndex)){
				
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
				
			}
			
            this.weightLowerToleranceIndex = this.parameterIndex("LINE - Weight Winder Zone Lower Tolerance  (g)", table);

			if(!this.isParameterIndexCorrect(this.weightLowerToleranceIndex)){
				
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);
				
			}
					
		
	}
	
	private void setDuplexParameterIndex(String[][] table){
		
       
		this.parameterIndexRecipeWidthLineOSDuplex = this.parameterIndex("LINE - Width Winder Zone Upper Tolerance  (mm)", table);
		
		if(!this.isParameterIndexCorrect(this.parameterIndexRecipeWidthLineOSDuplex)){
      	  		
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
		this.parameterIndexWidthLineOSDuplexUpperTolerance = this.parameterIndex("LINE - Width Winder Zone Upper Tolerance  (mm)", table);
		
		if(!this.isParameterIndexCorrect(this.parameterIndexWidthLineOSDuplexUpperTolerance)){
      	  		
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
       this.parameterIndexWidthLineOSDuplexLowerTolerance = this.parameterIndex("LINE - Width Winder Zone Lower Tolerance  (mm)", table);
		
		if(!this.isParameterIndexCorrect(this.parameterIndexWidthLineOSDuplexLowerTolerance)){
      	  		
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
		this.parameterIndexWidthLineDS = this.parameterIndex("LINE - Width Winder Zone Measure DS (mm)", table);
		
		if(!this.isParameterIndexCorrect(this.parameterIndexWidthLineDS)){
      	  		
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
		this.parameterIndexWidthMarker = this.parameterIndex("LINE - Width Extruder Zone Measure OS (Or Single Stripe) (mm)", table);
        
		if(!this.isParameterIndexCorrect(this.parameterIndexWidthMarker)){
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
        this.parameterIndexWidthMarkerDS = this.parameterIndex("LINE - Width Extruder Zone Measure DS (mm)", table);
        
		if(!this.isParameterIndexCorrect(this.parameterIndexWidthMarkerDS)){	
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
        this.parameterIndexWeightLineDS = this.parameterIndex("LINE - Weight Winder Zone Measure DS (g)", table);
        
		if(!this.isParameterIndexCorrect(this.parameterIndexWeightLineDS)){
					
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
        this.parameterIndexWeightMarkerDS = this.parameterIndex("LINE - Weight Marking Zone Measure DS (g)", table);
        
		if(!this.isParameterIndexCorrect(this.parameterIndexWeightMarkerDS)){
						
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);

		}
		
	}
		
	private void setInnerLinerParameterIndex(String[][] table){
			
			this.parameterIndexWidthLine = this.parameterIndex("Post Calender - Width Camera 1 Actual (mm)", table);
			
			if(!this.isParameterIndexCorrect(this.parameterIndexWidthLine)){
	      	  		
				JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
				System.exit(-1);

			}
							
		
	}
	
	private boolean isParameterIndexCorrect(int parameterIndex){
		
		if(parameterIndex != -1) return true;
		return false;
		
	}
	
	public void generateRecipeList(){
		
		this.recipesList = new LinkedList<String>();
		
		this.recipeCol = this.parameterIndex("SAP Code", this.KPITable);
		
		if(recipeCol==-1){
			
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			
		}
		
		for(int i=1;i<this.numRecords;i++){
			
//			if(this.machine.equals("InnerLiner")){
//				
//				if(!(""+Integer.parseInt(this.KPITable[i][recipeCol])).equals("9999A0") && !this.isJustAnalyzedRecipe(""+Integer.parseInt(this.KPITable[i][recipeCol])) && this.KPITable[i][recipeCol]!=null){
//					this.recipesList.add(""+Integer.parseInt(this.KPITable[i][recipeCol]));
//				}
//				
//			}
//			else{			
				if(!this.KPITable[i][recipeCol].equals("9999A0") && !this.isJustAnalyzedRecipe(this.KPITable[i][recipeCol]) && this.KPITable[i][recipeCol]!=null){
		  			this.recipesList.add(this.KPITable[i][recipeCol]);
				}
//			}
				
		}
		
	}
	
	private boolean isJustAnalyzedRecipe(String currRecipe){
		
		for(String s: this.recipesList){
			
			if(s.equals(currRecipe))
				return true;
			
		}
		
		return false;
		
	}
	
    private int parameterIndex(String parameterName, String table[][]){

    	
		for(int j=0;j<this.numCols;j++){

			if(table[0][j].equals(parameterName))
				return j;
			
		}
		
		return -1;
		
	}

    
    public void loadLimitedKPITable(String recipeToSearch,String dateStringUpper, String dateStringLower){
 	   
		BufferedReader br = null;	
		
		int recipeCols = -1;
		int prodType = -1;
		int recipeName = -1;
		int dateCol = -1;
		
		String oldRecipe="";
		String precRecipe="";
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date dateUpper = null;
		Date dateLower = null;
		try {
			dateUpper = formatter.parse(dateStringUpper);
			dateLower = formatter.parse(dateStringLower);
		} catch (ParseException e1) {}
		
       //==== The sw finds parameters indexes / position in the original file =====//
		
		List<Integer> indexes = new LinkedList<Integer>();
		
	    indexes.add(totParamList.indexOf("DateTimeRec"));
		indexes.add(totParamList.indexOf("SAP Code"));
		indexes.add(totParamList.indexOf("Product type"));
		indexes.add(totParamList.indexOf("Recipe name"));
		
	
		switch(this.machine){
		
		  case "Quadruplex":
			  
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Marking Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Marking Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Preset from Recipe (g)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Lower Tolerance  (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Upper Tolerance  (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Preset from Recipe (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Upper Tolerance  (g)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Lower Tolerance  (g)"));
				indexes.add(totParamList.indexOf("LINE - LINE Speed Preset from Recipe (m/min)"));
				indexes.add(totParamList.indexOf("LINE - LINE Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("LINE - Windup Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Screw Speed Preset from Recipe (rpm)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Screw Pressure After Filter (Bar)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Screw Motor Torque (%)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Feeder Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Dancer Position Feedback (%)"));
				indexes.add(totParamList.indexOf("ProductionIndex"));
				break;
				
		  case "InnerLiner":
			  
			    indexes.add(totParamList.indexOf("Post Calender - Width Camera 1 Actual (mm)"));
				indexes.add(totParamList.indexOf("Post Calender - Windup 1 Speed Actual (m/min)"));
				indexes.add(totParamList.indexOf("Post Calender - Windup 2 Speed Actual (m/min)"));
				indexes.add(totParamList.indexOf("Calender - Line Speed Preset from Recipe (m/min)"));
				indexes.add(totParamList.indexOf("Calender - Line Speed Actual (m/min)"));
				indexes.add(totParamList.indexOf("Calender - Lower Cutter Actual (mm)"));
				indexes.add(totParamList.indexOf("Calender - Upp. Ext. Cutter Actual (mm)"));
				indexes.add(totParamList.indexOf("Calender - Bobbin Numer (--)"));
				indexes.add(totParamList.indexOf("Width Upper Tolerance"));
				indexes.add(totParamList.indexOf("Width Lower Tolerance"));
				indexes.add(totParamList.indexOf("ProductionIndex"));
				break;
				
		  case "Duplex":
			  
			    indexes.add(totParamList.indexOf("LINE - LINE Speed Preset from Recipe (m/min)"));
				indexes.add(totParamList.indexOf("LINE - LINE Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Preset from Recipe (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Measure DS (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Upper Tolerance  (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Lower Tolerance  (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Preset from Recipe (g)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Measure DS (g)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Upper Tolerance  (g)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Lower Tolerance  (g)"));
				indexes.add(totParamList.indexOf("LINE - Windup Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("LINE - Width Extruder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Extruder Zone Measure DS (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Marking Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Marking Zone Measure DS (g)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Screw Speed Preset from Recipe (rpm)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Screw Motor Torque (%)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Screw Pressure After Filter (Bar)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Feeder Speed (m/min)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Dancer Position (%)"));	
				indexes.add(totParamList.indexOf("ProductionIndex"));
				break;
			  
		
		}

			
			Collections.sort(indexes,new Comparator<Integer>(){

				@Override
				public int compare(Integer i1, Integer i2) {
					return i1-i2;
				}
				
				
				
			});
		
		  
		  try {
			br = new BufferedReader(new FileReader(this.filepath));
		  } catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		  }
		 
		  String riga = null;
		  StringTokenizer st;
		  this.numRecords = this.countRecords(this.filepath);
		  this.numCols = indexes.size();
		  
		  //======== If an error occurred in "countRecords" or "countCols" methods, the program returns at the initial idle state ========
		  
		  if(this.numRecords == -1 || this.numCols == -1)
			  return;
			  
		  
		  this.KPITable = new String[this.numRecords][this.numCols];
		  int i=0;
		  String cell;
		  
		  try {
			while((riga = br.readLine()) != null ){
				
				  if(i==1){
					  
					  recipeCols = totParamList.indexOf("SAP Code");
						
					  prodType = totParamList.indexOf("Product type");
						
					  recipeName = totParamList.indexOf("Recipe name");
					  
					  dateCol = totParamList.indexOf("DateTimeRec");
					  
					   
					  
				  }
				
				  int j=0;
				  int k=0;
				  st = new StringTokenizer(riga, ";");
				  
				  while(st.hasMoreTokens()){
					  cell = st.nextToken();		  
					  
					  if(j==recipeCols && this.machine.equals("InnerLiner") && !cell.equals("9999A0"))
						  cell = ""+Integer.parseInt(cell);
					  
					  if(((cell.equals("0") && (j==recipeCols || j==prodType || j==recipeName)))){
						  i-=1;
						  break; 
					  }
					  
					  if(j==recipeCols && oldRecipe.equals("") && !cell.equals(oldRecipe)){
						  oldRecipe = cell;
						  precRecipe = cell;
					  }
					  
					  if(j==recipeCols && !cell.equals(precRecipe)){
						  oldRecipe = precRecipe;
						  precRecipe = cell;
					  }
					  
					  if(j==recipeCols && !cell.equals(recipeToSearch) && !oldRecipe.equals(recipeToSearch)){
						  i-=1;
						  break;						  
					  }
					  
					  try{
						  
						  Date date = formatter.parse(cell);
					  
						  if(j==dateCol && (dateUpper.compareTo(date)<0 || dateLower.compareTo(date)>0)){
						 					  
									i-=1;
									break;						  
						  }
					  
					  }catch(Exception e){}
					  
					  if(indexes.contains(k)){
					    this.KPITable[i][j]=cell;
					    j++;
					  }
					  
					  k++;
					  
				  }
				  
				  i++;
			  }
		  } catch (IOException e) {
			  JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			  System.exit(-1);
		  }
		  
		  try {
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		  
		  if(this.machine.equals("InnerLiner")){
			  this.KPITable[0][this.numCols-3] = "Width Upper Tolerance";
		  	  this.KPITable[0][this.numCols-2] = "Width Lower Tolerance";
		  }
		  
			 this.KPITable[0][this.numCols-1] = "ProductionIndex";
		  
		  if(this.machine.equals("Quadruplex") || this.machine.equals("Duplex")){
			  
			  this.setParameterIndex(KPITable);
			  
			  if(this.machine.equals("Duplex"))
				  this.setDuplexParameterIndex(KPITable);
		  }
		  else
			  if(this.machine.equals("InnerLiner"))
				  this.setInnerLinerParameterIndex(KPITable);
		  
		  this.totParamList = null;
		  
		  if(i<this.numRecords){

			  this.numRecords = i;
			  
			  String[][] cleanKPITable = new String[this.numRecords][this.numCols];
			  
			  for(int k=0;k<this.numRecords;k++){
				  
				  for(int j=0;j<this.numCols;j++){
					  
					  cleanKPITable[k][j]=this.KPITable[k][j];
					  
				  }
				  
			  }
			  
			  this.KPITable = cleanKPITable;
			  
		  }
		    
	}
    
    public void loadKPITable(){
    	   
		BufferedReader br = null;	
		
		int recipeCols = -1;
		int prodType = -1;
		int recipeName = -1;
		
       //==== The sw finds parameters indexes / position in the original file =====//
		
		List<Integer> indexes = new LinkedList<Integer>();
		
	    indexes.add(totParamList.indexOf("DateTimeRec"));
		indexes.add(totParamList.indexOf("SAP Code"));
		indexes.add(totParamList.indexOf("Product type"));
		indexes.add(totParamList.indexOf("Recipe name"));
		
	
		switch(this.machine){
		
		  case "Quadruplex":
			  
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Marking Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Marking Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Preset from Recipe (g)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Lower Tolerance  (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Upper Tolerance  (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Preset from Recipe (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Upper Tolerance  (g)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Lower Tolerance  (g)"));
				indexes.add(totParamList.indexOf("LINE - LINE Speed Preset from Recipe (m/min)"));
				indexes.add(totParamList.indexOf("LINE - LINE Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("LINE - Windup Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Screw Speed Preset from Recipe (rpm)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Screw Pressure After Filter (Bar)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Screw Motor Torque (%)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Feeder Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("EXTRUDER 250 mm - Dancer Position Feedback (%)"));
				indexes.add(totParamList.indexOf("ProductionIndex"));
				break;
				
		  case "InnerLiner":
			  
			    indexes.add(totParamList.indexOf("Post Calender - Width Camera 1 Actual (mm)"));
				indexes.add(totParamList.indexOf("Post Calender - Windup 1 Speed Actual (m/min)"));
				indexes.add(totParamList.indexOf("Post Calender - Windup 2 Speed Actual (m/min)"));
				indexes.add(totParamList.indexOf("Calender - Line Speed Preset from Recipe (m/min)"));
				indexes.add(totParamList.indexOf("Calender - Line Speed Actual (m/min)"));
				indexes.add(totParamList.indexOf("Calender - Lower Cutter Actual (mm)"));
				indexes.add(totParamList.indexOf("Calender - Upp. Ext. Cutter Actual (mm)"));
				indexes.add(totParamList.indexOf("Calender - Bobbin Numer (--)"));
				indexes.add(totParamList.indexOf("Width Upper Tolerance"));
				indexes.add(totParamList.indexOf("Width Lower Tolerance"));
				indexes.add(totParamList.indexOf("ProductionIndex"));
				break;
				
		  case "Duplex":
			  
			    indexes.add(totParamList.indexOf("LINE - LINE Speed Preset from Recipe (m/min)"));
				indexes.add(totParamList.indexOf("LINE - LINE Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Preset from Recipe (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Measure DS (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Upper Tolerance  (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Winder Zone Lower Tolerance  (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Preset from Recipe (g)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Measure DS (g)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Upper Tolerance  (g)"));
				indexes.add(totParamList.indexOf("LINE - Weight Winder Zone Lower Tolerance  (g)"));
				indexes.add(totParamList.indexOf("LINE - Windup Speed Feedback (m/min)"));
				indexes.add(totParamList.indexOf("LINE - Width Extruder Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Width Extruder Zone Measure DS (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Marking Zone Measure OS (Or Single Stripe) (mm)"));
				indexes.add(totParamList.indexOf("LINE - Weight Marking Zone Measure DS (g)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Screw Speed Preset from Recipe (rpm)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Screw Motor Torque (%)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Screw Pressure After Filter (Bar)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Feeder Speed (m/min)"));
				indexes.add(totParamList.indexOf("EXTRUDER 200 mm - Dancer Position (%)"));	
				indexes.add(totParamList.indexOf("ProductionIndex"));
				break;
			  
		
		}

			
			Collections.sort(indexes,new Comparator<Integer>(){

				@Override
				public int compare(Integer i1, Integer i2) {
					return i1-i2;
				}
				
				
				
			});
		
		  
		  try {
			br = new BufferedReader(new FileReader(this.filepath));
		  } catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		  }
		 
		  String riga = null;
		  StringTokenizer st;
		  this.numRecords = this.countRecords(this.filepath);
		  this.numCols = indexes.size();
		  
		  //======== If an error occurred in "countRecords" or "countCols" methods, the program returns at the initial idle state ========
		  
		  if(this.numRecords == -1 || this.numCols == -1)
			  return;
			  
		  
		  this.KPITable = new String[this.numRecords][this.numCols];
		  int i=0;
		  String cell;
		  
		  try {
			while((riga = br.readLine()) != null ){
				
				  if(i==1){
					  
					  recipeCols = totParamList.indexOf("SAP Code");
						
					  prodType = totParamList.indexOf("Product type");
						
					  recipeName = totParamList.indexOf("Recipe name");
					  
					   
					  
				  }
				
				  int j=0;
				  int k=0;
				  st = new StringTokenizer(riga, ";");
				  
				  while(st.hasMoreTokens()){
					  cell = st.nextToken();		  
					  
					  if(((cell.equals("0") && (j==recipeCols || j==prodType || j==recipeName)))){
						  i-=1;
						  break; 
					  }
					  
					  if(indexes.contains(k)){
					    this.KPITable[i][j]=cell;
					    j++;
					  }
					  
					  k++;
					  
				  }
				  
				  i++;
			  }
		  } catch (IOException e) {
			  JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			  System.exit(-1);
		  }
		  
		  try {
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		  
		  if(this.machine.equals("InnerLiner")){
			  this.KPITable[0][this.numCols-3] = "Width Upper Tolerance";
		  	  this.KPITable[0][this.numCols-2] = "Width Lower Tolerance";
		  }
		  
			 this.KPITable[0][this.numCols-1] = "ProductionIndex";
		  
		  if(this.machine.equals("Quadruplex") || this.machine.equals("Duplex")){
			  
			  this.setParameterIndex(KPITable);
			  
			  if(this.machine.equals("Duplex"))
				  this.setDuplexParameterIndex(KPITable);
		  }
		  else
			  if(this.machine.equals("InnerLiner"))
				  this.setInnerLinerParameterIndex(KPITable);
		  
		  this.totParamList = null;
		  
		  if(i<this.numRecords){
			  
			  this.numRecords = i;
			  
			  String[][] cleanKPITable = new String[this.numRecords][this.numCols];
			  
			  for(int k=0;k<this.numRecords;k++){
				  
				  for(int j=0;j<this.numCols;j++){
					  
					  cleanKPITable[k][j]=this.KPITable[k][j];
					  
				  }
				  
			  }
			  
			  this.KPITable = cleanKPITable;
			  
		  }
		    
		  
		
	}
       
              
	
	public void loadSAPfile(){

		this.numSAPRecords = this.countRecords(this.SAPFilepath);
		
       //==== The sw finds parameters indexes / position in the original file =====//
		
		List<Integer> indexes = new LinkedList<Integer>();
		
		if(machine.equals("Quadruplex")){

		    indexes.add(totParamSAPList.indexOf("AVAA :Codice Corto"));
			indexes.add(totParamSAPList.indexOf("FAAO :Largh totale fascia+mini"));
			indexes.add(totParamSAPList.indexOf("FAAO :Racc bilancia continua"));
			
		}
		else{
			if(machine.equals("Duplex")){
				indexes.add(totParamSAPList.indexOf("FNAB :Codice Corto"));
				indexes.add(totParamSAPList.indexOf("FNAB :Larghezza totale assembl"));
				indexes.add(totParamSAPList.indexOf("FNAB_WEIGHT"));
				indexes.add(totParamSAPList.indexOf("AGAA :FNAB1_QTY"));	
			}
			else{
				if(machine.equals("InnerLiner")){
					indexes.add(totParamSAPList.indexOf("TargetWidth"));
					indexes.add(totParamSAPList.indexOf("ShortCode"));
				}
			}
		}
			
			Collections.sort(indexes,new Comparator<Integer>(){

				@Override
				public int compare(Integer i1, Integer i2) {
					return i1-i2;
				}
				
				
				
			});
			
			this.numSAPCols = totParamSAPList.size();
			
			this.totParamSAPList = null;
			
			if(this.numSAPRecords==-1 || this.numSAPCols==-1) return;
	    
	    BufferedReader br = null;
	    
	    try {
			br = new BufferedReader(new FileReader(this.SAPFilepath));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	    
	    this.SAPTable = new String[this.numSAPRecords][this.numSAPCols];
	    String row;
	    String[] data;
	    int i=0;
	    int j;

	    try {
			while((row = br.readLine()) != null){
				j=0;
				
				data = row.split(";");
				
				for(int k=0;k<data.length;k++){
					
					if(indexes.contains(k)){
						
						  if(data[k].equals(""))
							  data[k] = "N/A";
						
						  this.SAPTable[i][j] = data[k];
						  
						  j++;
							
						}
					
				}
				
				i++;
				
			}
						
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	    
	    try {
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		
	}
	
	
	private int countRecords(String filepath){
		
		BufferedReader br = null;;
		int numRecords = 0;
		
		try {
			br = new BufferedReader(new FileReader(filepath));
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		
		try {
			while(br.readLine()!=null){
				
		      numRecords++;		
				
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		
		try {
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "AN ERROR OCCURRED! Please, retry the analysis process", "ALERT MESSAGE", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		return numRecords;
		
	}
	
	
	private String round(double numb){
		
		 NumberFormat numForm = NumberFormat.getInstance();
	     numForm.setMinimumFractionDigits(3);
	     numForm.setMaximumFractionDigits(3);
	     numForm.setRoundingMode(RoundingMode.CEILING); // solo da JAVA 6 in poi
	     return numForm.format(numb).replace(",", ""); 
		
	}
	
	public void deleteUnusefulData(){
		
		this.SAPTable = null;
		
	}
	
	
	public Map<String, RecipePerformanceQuadruplex> getRecipesPerformanceQuadruplex(){
		
		return this.recipesPerformanceQuadruplex;
		
	}
	
	public Map<String, RecipePerformanceDuplex> getRecipesPerformanceDuplex(){
		
		return this.recipesPerformanceDuplex;
		
	}
	
	public Map<String, RecipePerformanceInnerLiner> getRecipesPerformanceInnerLiner(){
		
		return this.recipesPerformanceInnerLiner;
		
	}
	
	public int getParameterIndexWidthLine() {
		return parameterIndexWidthLine;
	}

	public int getParameterIndexWidthMarker() {
		return parameterIndexWidthMarker;
	}

	public int getParameterIndexWeightLine() {
		return parameterIndexWeightLine;
	}

	public int getParameterIndexWeightMarker() {
		return parameterIndexWeightMarker;
	}

	public int getWidthLineRecipeIndex() {
		return widthLineRecipeIndex;
	}

	public int getWeightLineRecipeIndex() {
		return weightLineRecipeIndex;
	}

	public int getWidthUpperToleranceIndex() {
		return widthUpperToleranceIndex;
	}

	public int getWidthLowerToleranceIndex() {
		return widthLowerToleranceIndex;
	}

	public int getWeightUpperToleranceIndex() {
		return weightUpperToleranceIndex;
	}

	public int getWeightLowerToleranceIndex() {
		return weightLowerToleranceIndex;
	}

	public int getNumCols() {
		return numCols;
	}

	public int getNumRecords() {
		return numRecords;
	}
	
	public int getRecipeCol() {
		return recipeCol;
	}

	public int getDateIndex(){
		
		return this.dateIndex;
		
	}
	
	public Map<String, String[]> getRecipesToLimits(){
		
		return this.recipesToLimits;
		
	}
	
	public void setMetersToCut(int metersToCut){
		
		this.metersToCut = metersToCut;
		
	}
	
	public List<String> getRecipesList(){
		
		return this.recipesList;
		
	}
	
	public int getMetersToCut(){
		
		return this.metersToCut;
		
	}
	
    public void setMachine(String machine){
    	
    	this.machine = machine;
    	
    }
    
    public void setTotParamList(List<String> totParamList){
    	
    	this.totParamList = totParamList;
    	
    }
    
    public void setTotParamSAPList(List<String> totParamSAPList){
    	
    	this.totParamSAPList = totParamSAPList;
    	
    }
    
    public String getMachine(){
    	
    	return this.machine;
    	
    }
	
    public List<String> getRecipeList(){
    	
    	return this.recipesList;
    	
    }
    
    private String getStartParameter(){
    	
    	switch(this.machine){
    	
    	case "Quadruplex":
    		return "EXTRUDER 250 mm - Screw Speed Preset from Recipe (rpm)";
    	case "Duplex":
    		return "EXTRUDER 200 mm - Screw Speed Preset from Recipe (rpm)";
    	case "InnerLiner":
    		return "Calender - Line Speed Actual (m/min)";
    		
    	default:
    		return "";
    	}
    	
    }
    
    private int getRecipeWidthForInnerLiner(String recipe){
    	
    	for(int i=0;i<this.numSAPRecords;i++){
    		if(this.SAPTable[i][this.parameterIndex("ShortCode", this.SAPTable)].equals(recipe))
    			return Integer.parseInt(this.SAPTable[i][this.parameterIndex("TargetWidth", this.SAPTable)]);
    	}
    	
    	return -1;
    	
    }
    
    private void addWidthUpperAndLowerToleranceInnerLiner(){
    	
    	List<String> tempRecipeList = new LinkedList<String>();
    	int widthLineRecipe;
    	int widthUpperTolerance=0;
    	int widthLowerTolerance=0;
    	
    	for(int i=1;i<this.numRecords;i++){
    		
    		if(!tempRecipeList.contains(this.KPITable[i][this.recipeCol])){
    			tempRecipeList.add(this.KPITable[i][this.recipeCol]);
    		}
    		
    		
    		if(tempRecipeList.size()<2 || (!this.KPITable[i][this.recipeCol].equals(tempRecipeList.get(tempRecipeList.size()-2)) && ((i-1)==0 || Integer.parseInt(this.KPITable[i][this.parameterIndex("Calender - Bobbin Numer (--)", this.KPITable)])<Integer.parseInt(this.KPITable[i-1][this.parameterIndex("Calender - Bobbin Numer (--)", this.KPITable)])))){
    			widthLineRecipe = this.getRecipeWidthForInnerLiner(this.KPITable[i][this.recipeCol]);
	            widthUpperTolerance = widthLineRecipe+3;
			    widthLowerTolerance = widthLineRecipe-3;
    		}
    		
    		if(this.KPITable[i][this.parameterIndex("Width Upper Tolerance", this.KPITable)]==null || this.KPITable[i][this.parameterIndex("Width Upper Tolerance", this.KPITable)].equals("")){
		    	this.KPITable[i][this.parameterIndex("Width Upper Tolerance", this.KPITable)] = ""+widthUpperTolerance;
		    	this.KPITable[i][this.parameterIndex("Width Lower Tolerance", this.KPITable)] = ""+widthLowerTolerance;
		    }
    			
    		
    	}
    	
    }
    
    public void cleanRecipesPerformance(String recipeToSearch){
    	
    	List<String> recipesToDelete = new LinkedList<String>();
    	
    	switch(machine){
    	
    		case "Quadruplex":
    	
    				for(String recipe: this.recipesPerformanceQuadruplex.keySet()){
    					if(!recipe.equals(recipeToSearch))
    						recipesToDelete.add(recipe);
    				}
    				
    				for(String recipe: recipesToDelete)
    					this.recipesPerformanceQuadruplex.remove(recipe);
    				
    				this.recipesPerformanceQuadruplex.get(recipeToSearch).setProductionIndex(1);
    				
    				break;
    				
    		case "Duplex":
    	    	
				for(String recipe: this.recipesPerformanceDuplex.keySet()){
					if(!recipe.equals(recipeToSearch))
						recipesToDelete.add(recipe);
				}
				
				for(String recipe: recipesToDelete)
					this.recipesPerformanceDuplex.remove(recipe);
				
				this.recipesPerformanceDuplex.get(recipeToSearch).setProductionIndex(1);
				
				break;
				
    		case "InnerLiner":
    	    	
				for(String recipe: this.recipesPerformanceInnerLiner.keySet()){

					if(!recipe.equals(recipeToSearch))
						recipesToDelete.add(recipe);
				}
				
				for(String recipe: recipesToDelete)
					this.recipesPerformanceInnerLiner.remove(recipe);
				
				this.recipesPerformanceInnerLiner.get(recipeToSearch).setProductionIndex(1);
				
				break;
    	
    	}
    	
    	   		
    	
    }
    
    public void file() throws IOException{
    	
    	Writer w = new FileWriter("ciao.csv");
    	
    	for(int i=0;i<this.numRecords;i++){
    		
    		for(int j=0;j<this.numCols;j++){
    			
    			w.write(this.KPITable[i][j]+";");
    			
    		}
    		
    		w.write("\n");
    		
    	}
    	
    	w.close();
    	
    }
            
}
