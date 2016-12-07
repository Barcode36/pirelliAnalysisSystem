package objectClasses;

public class RecipePerformanceDuplex extends RecipePerformance{
	
	private String markerAvgWidth;
	
	private String avgWidthDS;
	private String markerAvgWidthDS;
	private float deltaWidthDS;
	private String cpWidthDS;
	private String cpkWidthDS;
	
	private String targetWeight;
	private String recipeWeight;
	private String avgWeight;
	private String markerAvgWeight;
	private float deltaWeight;
	private String cpWeight;
	private String cpkWeight;
	
	private String avgWeightDS;
	private String markerAvgWeightDS;
	private float deltaWeightDS;
	private String cpWeightDS;
	private String cpkWeightDS;
	
	private double weightSigma;
	private double widthSigmaDS;
	private double weightSigmaDS;
	
	private float weightUpperTolerance;
	private float weightLowerTolerance;
	
	private int recipeWidthRecordsDS;
	private int recipeWeightRecords;
	private int recipeWeightRecordsDS;

	public RecipePerformanceDuplex(String measureID, String recipeWidth, String avgWidth,
			String markerAvgWidth, float deltaWidth, String avgWidthDS,
			String markerAvgWidthDS, float deltaWidthDS, String recipeWeight,
			String avgWeight, String markerAvgWeight, float deltaWeight, String avgWeightDS, String markerAvgWeightDS, float deltaWeightDS, float widthUpperTolerance, float widthLowerTolerance, float weightUpperTolerance, float weightLowerTolerance, int recipeWidthRecords, int recipeWeightRecords, int recipeWidthRecordsDS, int recipeWeightRecordsDS) {
		super(measureID, recipeWidth, avgWidth, deltaWidth, widthUpperTolerance, widthLowerTolerance, recipeWidthRecords);
		this.markerAvgWidth = markerAvgWidth;
		this.avgWidthDS = avgWidthDS;
		this.markerAvgWidthDS = markerAvgWidthDS;
		this.deltaWidthDS = deltaWidthDS;
		this.recipeWeight = recipeWeight;
		this.avgWeight = avgWeight;
		this.markerAvgWeight = markerAvgWeight;
		this.deltaWeight = deltaWeight;
		this.avgWeightDS = avgWeightDS;
		this.markerAvgWeightDS = markerAvgWeightDS;
		this.deltaWeightDS = deltaWeightDS;
		this.recipeWidthRecordsDS = recipeWidthRecordsDS;
		this.recipeWeightRecords = recipeWeightRecords;
		this.recipeWeightRecordsDS = recipeWeightRecordsDS;
		this.weightUpperTolerance = weightUpperTolerance;
		this.weightLowerTolerance = weightLowerTolerance;
	}
	

	public int getRecipeWidthRecordsDS() {
		return recipeWidthRecordsDS;
	}


	public int getRecipeWeightRecordsDS() {
		return recipeWeightRecordsDS;
	}


	public void setRecipeWidthRecordsDS(int recipeWidthRecordsDS) {
		this.recipeWidthRecordsDS = recipeWidthRecordsDS;
	}


	public void setRecipeWeightRecordsDS(int recipeWeightRecordsDS) {
		this.recipeWeightRecordsDS = recipeWeightRecordsDS;
	}


	public String getMarkerAvgWidth() {
		return markerAvgWidth;
	}

	public void setMarkerAvgWidth(String markerAvgWidth) {
		this.markerAvgWidth = markerAvgWidth;
	}

	public String getTargetWeight() {
		return targetWeight;
	}

	public void setTargetWeight(String targetWeight) {
		this.targetWeight = targetWeight;
	}

	public String getRecipeWeight() {
		return recipeWeight;
	}

	public void setRecipeWeight(String recipeWeight) {
		this.recipeWeight = recipeWeight;
	}

	public String getAvgWeight() {
		return avgWeight;
	}

	public void setAvgWeight(String avgWeight) {
		this.avgWeight = avgWeight;
	}

	public String getMarkerAvgWeight() {
		return markerAvgWeight;
	}

	public void setMarkerAvgWeight(String markerAvgWeight) {
		this.markerAvgWeight = markerAvgWeight;
	}

	public float getDeltaWeight() {
		return deltaWeight;
	}

	public void setDeltaWeight(float deltaWeight) {
		this.deltaWeight = deltaWeight;
	}

	public String getCpWeight() {
		return cpWeight;
	}

	public void setCpWeight(String cpWeight) {
		this.cpWeight = cpWeight;
	}

	public String getCpkWeight() {
		return cpkWeight;
	}

	public void setCpkWeight(String cpkWeight) {
		this.cpkWeight = cpkWeight;
	}

	public double getWeightSigma() {
		return weightSigma;
	}

	public void setWeightSigma(double weightSigma) {
		this.weightSigma = weightSigma;
	}

	public float getWeightUpperTolerance() {
		return weightUpperTolerance;
	}

	public void setWeightUpperTolerance(float weightUpperTolerance) {
		this.weightUpperTolerance = weightUpperTolerance;
	}

	public float getWeightLowerTolerance() {
		return weightLowerTolerance;
	}

	public void setWeightLowerTolerance(float weightLowerTolerance) {
		this.weightLowerTolerance = weightLowerTolerance;
	}

	public int getRecipeWeightRecords() {
		return recipeWeightRecords;
	}

	public void setRecipeWeightRecords(int recipeWeightRecords) {
		this.recipeWeightRecords = recipeWeightRecords;
	}
		
	public String getAvgWidthDS() {
		return avgWidthDS;
	}

	public String getMarkerAvgWidthDS() {
		return markerAvgWidthDS;
	}

	public float getDeltaWidthDS() {
		return deltaWidthDS;
	}

	public String getCpWidthDS() {
		return cpWidthDS;
	}

	public String getCpkWidthDS() {
		return cpkWidthDS;
	}

	public String getAvgWeightDS() {
		return avgWeightDS;
	}

	public String getMarkerAvgWeightDS() {
		return markerAvgWeightDS;
	}

	public float getDeltaWeightDS() {
		return deltaWeightDS;
	}

	public String getCpWeightDS() {
		return cpWeightDS;
	}

	public String getCpkWeightDS() {
		return cpkWeightDS;
	}

	public double getWidthSigmaDS() {
		return widthSigmaDS;
	}

	public double getWeightSigmaDS() {
		return weightSigmaDS;
	}

	public void setAvgWidthDS(String avgWidthDS) {
		this.avgWidthDS = avgWidthDS;
	}

	public void setMarkerAvgWidthDS(String markerAvgWidthDS) {
		this.markerAvgWidthDS = markerAvgWidthDS;
	}

	public void setDeltaWidthDS(float deltaWidthDS) {
		this.deltaWidthDS = deltaWidthDS;
	}

	public void setCpWidthDS(String cpWidthDS) {
		this.cpWidthDS = cpWidthDS;
	}

	public void setCpkWidthDS(String cpkWidthDS) {
		this.cpkWidthDS = cpkWidthDS;
	}

	public void setAvgWeightDS(String avgWeightDS) {
		this.avgWeightDS = avgWeightDS;
	}

	public void setMarkerAvgWeightDS(String markerAvgWeightDS) {
		this.markerAvgWeightDS = markerAvgWeightDS;
	}

	public void setDeltaWeightDS(float deltaWeightDS) {
		this.deltaWeightDS = deltaWeightDS;
	}

	public void setCpWeightDS(String cpWeightDS) {
		this.cpWeightDS = cpWeightDS;
	}

	public void setCpkWeightDS(String cpkWeightDS) {
		this.cpkWeightDS = cpkWeightDS;
	}

	public void setWidthSigmaDS(double widthSigmaDS) {
		this.widthSigmaDS = widthSigmaDS;
	}

	public void setWeightSigmaDS(double weightSigmaDS) {
		this.weightSigmaDS = weightSigmaDS;
	}

	public String toString(){
		
		return this.measureID+";"+this.productionIndex+";"+this.targetWidth+";"+this.recipeWidth+";"+this.avgWidth+";"+this.avgWidthDS+";"+this.markerAvgWidth+";"+this.markerAvgWidthDS+";"+this.deltaWidth+";"+this.deltaWidthDS+";"+this.cpWidth+";"+this.cpWidthDS+";"+this.cpkWidth+";"+this.cpkWidthDS+";"+this.targetWeight+";"+this.recipeWeight+";"+this.avgWeight+";"+this.avgWeightDS+";"+this.markerAvgWeight+";"+this.markerAvgWeightDS+";"+this.deltaWeight+";"+this.deltaWeightDS+";"+this.cpWeight+";"+this.cpWeightDS+";"+this.cpkWeight+";"+this.cpkWeightDS;
		
	}

}
