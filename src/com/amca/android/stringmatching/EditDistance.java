package com.amca.android.stringmatching;

public class EditDistance {
	
	private static String str1, str2;
	private static int length1, length2;
	
	public EditDistance(String s1, String s2){
		str1 = s1;
		str2 = s2;
		length1 = s1.length();
		length2 = s2.length();
	}
	
	public double similarity(){
        int d = process();
        double x = 1 - ( (float) d / (Math.max(length1, length2)) );
        return x;
    }
    
    public int process(){
        int m[][] = new int[length1 + 1][length2 + 1];
        
        for(int i = 0; i < length1 + 1; i++){
            for(int j = 0; j < length2 + 1; j++){
                if(i == 0){
                    m[i][j] = j;
                }else if(j == 0){
                    m[i][j] = i;
                }else{
                    if(str1.charAt(i - 1) == str2.charAt(j - 1)){
                        m[i][j] = m[i-1][j-1];
                    }else{
                        m[i][j] = 1 + Math.min(
                                    Math.min( m[i][j-1], m[i-1][j-1] ),
                                    m[i-1][j]
                                );
                    }
                }
            }
        }
        
        return m[length1][length2];
    }
}
