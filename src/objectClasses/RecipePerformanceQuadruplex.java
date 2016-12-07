package objectClasses;

public class RecipePerformanceQuadruplex extends RecipePerformance{
	
	private String markerAvgWidth;
	
	private String targetWeight;
	private String recipeWeight;
	private String avgWeight;
	private String markerAvgWeight;
	private float deltaWeight;
	private String cpWeight;
	private String cpkWeight;
	
	private double weightSigma;
	
	private float weightUpperTolerance;
	private float weightLowerTolerance;
	
	private int recipeWeightRecords;

	public RecipePerformanceQuadruplex(String measureID, String recipeWidth, String avgWidth,
			String markerAvgWidth, float deltaWidth, String recipeWeight,
			String avgWeight, String markerAvgWeight, float deltaWeight, float widthUpperTolerance, float widthLowerTolerance, float weightUpperTolerance, float weightLowerTolerance, int recipeWidthRecords, int recipeWeightRecords) {
		super(measureID, recipeWidth, avgWidth, deltaWidth, widthUpperTolerance, widthLowerTolerance, recipeWidthRecords);
		this.markerAvgWidth = markerAvgWidth;
		this.recipeWeight = recipeWeight;
		this.avgWeight = avgWeight;
		this.markerAvgWeight = markerAvgWeight;
		this.deltaWeight = deltaWeight;
		this.recipeWeightRecords = recipeWeightRecords;
		this.weightUpperTolerance = weightUpperTolerance;
		this.weightLowerTolerance = weightLowerTolerance;
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
	
	public String toString(){
		return this.measureID+";"+this.productionIndex+";"+this.targetWidth+";"+this.recipeWidth+";"+this.avgWidth+";"+this.markerAvgWidth+";"+this.deltaWidth+";"+this.cpWidth+";"+this.cpkWidth+";"+this.targetWeight+";"+this.recipeWeight+";"+this.avgWeight+";"+this.markerAvgWeight+";"+this.deltaWeight+";"+this.cpWeight+";"+this.cpkWeight;
		
	}
		
}
