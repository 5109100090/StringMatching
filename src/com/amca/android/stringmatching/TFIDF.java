package com.amca.android.stringmatching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TFIDF {
	private String interest = "reading, watching movie, music";
    private String[] interests;
    private Integer numOfProfiles;
    private HashMap profiles = new HashMap();
    private HashMap doc = new HashMap();
    private HashMap<String, Double> tfValue = new HashMap();
    private HashMap<String, Double> idfValue = new HashMap();
    private List<String> terms = new ArrayList<String>();
    
    public TFIDF(){
        profiles.put("profile1", "gaming, sport, music");
        profiles.put("profile2", "watching tv, music");
        profiles.put("profile3", "game");
        profiles.put("profile4", "sport, movie");
        profiles.put("profile5", "watch movie, music");
        profiles.put("profile6", "sport, read book");
        profiles.put("profile7", "listening to music, reading novel");
        profiles.put("profile8", "listening music, read book");
        //profiles.put("profileX", interest);
        interests = interest.split(", ");
        numOfProfiles = profiles.size();
        
        countTf();
        countIdf();
        process();
        /*
        print(tfValue);
        System.out.println("\nidf\n");
        print(idfValue);
        */
    }
    
    public String count(){
    	String res = null;
        for(int i = 0; i < interests.length; i++){
            double tfResult = (double) tfValue.get(interests[i]);
            double idfResult = (double) idfValue.get(interests[i]);
            double result = (double) tfResult * idfResult;
            System.out.println(interests[i] + " => " + tfResult + "*" + idfResult + " = " + result);
            res += interests[i] + " => " + tfResult + "*" + idfResult + " = " + result + "\n";
        }
        return res;
    }
    
    private void countTf(){
        String[] parts = interest.split(", ");
        for(int i = 0; i < parts.length; i++){
            if(!tfValue.containsKey(parts[i])){
                tfValue.put(parts[i], 1.0);
            }
            
            for(int j = i + 1; j < parts.length; j++){    
                if(parts[i].equals(parts[j])){
                    double v = tfValue.get(parts[j]);
                    tfValue.put(parts[j], v + 1.0);
                }
            }
        }
    }
    
    private void countIdf(){
        for (Iterator it = profiles.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String key = entry.getKey();
            String[] value = entry.getValue().split(", ");

            for(int i = 0; i < interests.length; i++){
                if(!idfValue.containsKey(interests[i])){
                    idfValue.put(interests[i], 0.0);
                }
                for(int j = 0; j < value.length; j++){
                    if(interests[i].equals(value[j])){
                        double v = idfValue.get(value[j]);
                        idfValue.put(value[j], v + 1.0);
                    }
                }
            }
        }
    }
    
    public void process(){
        for(int i = 0; i < interests.length; i++){
            double tf = (double) tfValue.get(interests[i]) / interests.length;
            double idf = Math.log10(profiles.size() / (double) (1.0 + idfValue.get(interests[i])));
            
            tfValue.put(interests[i], tf);
            idfValue.put(interests[i], idf);
        }
    }
    
    private void print(HashMap obj){
        for (Iterator itProfile = obj.entrySet().iterator(); itProfile.hasNext();) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) itProfile.next();
            String keyProfile = entry.getKey();
            Integer valueProfile = entry.getValue();
            System.out.println(keyProfile + " => " + valueProfile);
        }
    }
}
