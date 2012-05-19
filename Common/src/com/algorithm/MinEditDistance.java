package com.algorithm;

public class MinEditDistance {
    public double distance(String target,String source){
    	int n = target.length();
    	int m = source.length();
    	double[][] dis = new double[n+1][m+1];
    	for(int i=0;i<m+1;i++){
    		dis[0][i]=i;
    	}
    	for(int i=0;i<n+1;i++){
    		dis[i][0]=i;
    	}
    	for(int i=1;i<n+1;i++){
    		for(int j=1;j<m+1;j++){
    			dis[i][j]=min(dis[i-1][j]+insertCost(),dis[i-1][j-1]+subCost(target.charAt(i-1),source.charAt(j-1)),dis[i][j-1]+delCost());
    		}
    	}
    	
    	return dis[n][m];
    }
    
    private double min(double a,double b,double c){
    	double tmp = -1;
    	return (tmp = (a<=b?a:b))<=c?tmp:c;
    }
    
    private double insertCost(){
    	return 1.0;
    }
    
    private double subCost(char a,char b){
    	if(a==b){
    		return 0.0;
    	}
    	return 2.0;
    }
    
    private double delCost(){
    	return 1.0;
    }
    
    public static void main(String[] args){
    	MinEditDistance d = new MinEditDistance();
    	System.out.println(d.distance("intention", "execution"));
    }
}
