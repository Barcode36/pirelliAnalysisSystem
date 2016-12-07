package objectClasses;

public class RecipePerformanceInnerLiner extends RecipePerformance{

	public RecipePerformanceInnerLiner(String measureID, String recipeWidth, String avgWidth, float deltaWidth, float widthUpperTolerance, float widthLowerTolerance, int recipeWidthRecords){
		super(measureID, recipeWidth, avgWidth, deltaWidth, widthUpperTolerance, widthLowerTolerance, recipeWidthRecords);		
	}
		
	public String toString(){
		
		return this.measureID+";"+this.productionIndex+";"+this.targetWidth+";"+this.avgWidth+";"+this.deltaWidth+";"+this.cpWidth+";"+this.cpkWidth;
		
	}
		

}
