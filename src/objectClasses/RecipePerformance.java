package objectClasses;

public abstract class RecipePerformance {
	
	protected String measureID;
	protected int productionIndex;
	
	protected String targetWidth;
	protected String recipeWidth;
	protected String avgWidth;
	protected float deltaWidth;
	protected String cpWidth;
	protected String cpkWidth;
		
	protected double widthSigma;
	
	protected float widthUpperTolerance;
	protected float widthLowerTolerance;
	
	protected int recipeWidthRecords;

	public RecipePerformance(String measureID, String recipeWidth, String avgWidth,
			float deltaWidth, float widthUpperTolerance, float widthLowerTolerance, int recipeWidthRecords) {
		super();
		this.measureID = measureID;
		this.recipeWidth = recipeWidth;
		this.avgWidth = avgWidth;
		this.deltaWidth = deltaWidth;
		this.recipeWidthRecords = recipeWidthRecords;
		this.widthUpperTolerance = widthUpperTolerance;
		this.widthLowerTolerance = widthLowerTolerance;
	}
	
	public String getMeasureID() {
		return measureID;
	}

	public void setMeasureID(String measureID) {
		this.measureID = measureID;
	}

	public int getProductionIndex() {
		return productionIndex;
	}

	public void setProductionIndex(int productionIndex) {
		this.productionIndex = productionIndex;
	}

	public String getTargetWidth() {
		return targetWidth;
	}

	public void setTargetWidth(String targetWidth) {
		this.targetWidth = targetWidth;
	}

	public String getRecipeWidth() {
		return recipeWidth;
	}

	public void setRecipeWidth(String recipeWidth) {
		this.recipeWidth = recipeWidth;
	}

	public String getAvgWidth() {
		return avgWidth;
	}

	public void setAvgWidth(String avgWidth) {
		this.avgWidth = avgWidth;
	}

	public float getDeltaWidth() {
		return deltaWidth;
	}

	public void setDeltaWidth(float deltaWidth) {
		this.deltaWidth = deltaWidth;
	}

	public String getCpWidth() {
		return cpWidth;
	}

	public void setCpWidth(String cpWidth) {
		this.cpWidth = cpWidth;
	}

	public String getCpkWidth() {
		return cpkWidth;
	}

	public void setCpkWidth(String cpkWidth) {
		this.cpkWidth = cpkWidth;
	}

	public double getWidthSigma() {
		return widthSigma;
	}

	public void setWidthSigma(double widthSigma) {
		this.widthSigma = widthSigma;
	}
	
	public float getWidthUpperTolerance() {
		return widthUpperTolerance;
	}

	public void setWidthUpperTolerance(float widthUpperTolerance) {
		this.widthUpperTolerance = widthUpperTolerance;
	}

	public float getWidthLowerTolerance() {
		return widthLowerTolerance;
	}

	public void setWidthLowerTolerance(float widthLowerTolerance) {
		this.widthLowerTolerance = widthLowerTolerance;
	}

	public int getRecipeWidthRecords() {
		return recipeWidthRecords;
	}

	public void setRecipeWidthRecords(int recipeWidthRecords) {
		this.recipeWidthRecords = recipeWidthRecords;
	}
	
	public String toString(){
		
		return this.measureID+";"+this.productionIndex+";"+this.targetWidth+";"+this.recipeWidth+";"+this.avgWidth+";"+this.deltaWidth+";"+this.cpWidth+";"+this.cpkWidth;
		
	}
		
}
