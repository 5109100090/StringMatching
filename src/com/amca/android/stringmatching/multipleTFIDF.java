package com.amca.android.stringmatching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class multipleTFIDF {
	private String interest = "reading, watching movie, music";
    private String[] interests;
    private Integer numOfProfiles;
    private HashMap profiles = new HashMap();
    private HashMap doc = new HashMap();
    private HashMap tfValue = new HashMap();
    private HashMap<String, Integer> idfValue = new HashMap();
    private List<String> terms = new ArrayList<String>();
    
    public multipleTFIDF(){
        profiles.put("profile1", "gaming, sport, music");
        profiles.put("profile2", "watching tv, music");
        profiles.put("profile3", "game");
        profiles.put("profile4", "sport, movie");
        profiles.put("profile5", "watch movie, music");
        profiles.put("profile6", "sport, read book");
        profiles.put("profile7", "listening to music, reading novel");
        profiles.put("profile8", "listening music, read book");
        profiles.put("profileX", interest);
        interests = interest.split(", ");
        numOfProfiles = profiles.size();
        
        counting();
        //proces();
        
        //printIdf();
        //printList();
    }
    
    // kemunculan term pada tiap document
    private void counting(){
        for (Iterator it = profiles.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            
            String[] parts = value.split(", ");
            HashMap<String, Integer> hm = new HashMap();
            for(int i = 0; i < parts.length; i++){
                
                // to count idf later
                terms.add(parts[i]);
                
                if(hm.containsKey(parts[i])){
                    int v = (int) hm.get(parts[i]);
                    hm.put(parts[i], v + 1);
                }else{
                    hm.put(parts[i], 1);
                }
            }
            doc.put(key, hm);
        }
        countingIdf();
    }
    
    private void countingIdf(){
        String term;
        for(int i = 0; i < terms.size(); i++){
            term = terms.get(i);
            idfValue.put(term, 0);
            for(int j = i + 1; j < terms.size(); j++){
                if(term.equals(terms.get(j))){
                    int v = (int) idfValue.get(term);
                    idfValue.put(term, v + 1);
                    terms.remove(j);
                }
            }
        }
    }
    
    public String proces(){
    	String res = null;
        double tf, idf;
        int count = 0;
         for (Iterator it = profiles.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String keyProfile = entry.getKey();
            String value = entry.getValue();
            String[] parts = value.split(", ");
            
            HashMap<String, Integer> term = (HashMap) doc.get(keyProfile);
            for(int i = 0; i < parts.length; i++){
                count++;
                String currentTerm = parts[i];
                int termFrequency = (int) term.get( currentTerm );
                tf = tfCalculate(termFrequency, parts.length);
                idf = idfCalculate( idfValue.get(currentTerm) );
                
                System.out.println("#"+(count)+" "+keyProfile + " => " + currentTerm + " :");
                System.out.println("tf:" + tf + " idf:" + idf + " tfidf:" + (tf * idf));
                res += "#"+(count)+" "+keyProfile + " => " + currentTerm + " :\n";
                res += "tf:" + tf + " idf:" + idf + " tfidf:" + (tf * idf) + "\n";
                /*
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }*/
            }
            System.out.println();
            res += "\n";
        }
         return res;
    }
    
    private double tfCalculate(Number numOfOccurencess, Number totalTermsInDocument){
        double f = numOfOccurencess.doubleValue() / totalTermsInDocument.doubleValue();
        return f;
        //return Math.pow(f,2);
    }
    
    private double idfCalculate(Number totalTermsInDocument){
        return Math.log10(numOfProfiles / (double) (1.0 + totalTermsInDocument.floatValue()));
    }
    
    private void printIdf(){
        for (Iterator itProfile = idfValue.entrySet().iterator(); itProfile.hasNext();) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) itProfile.next();
            String keyProfile = entry.getKey();
            Integer valueProfile = entry.getValue();
            System.out.println(keyProfile + " => " + valueProfile);
        }
    }
    
    private void printList(){
	Iterator<String> iterator = terms.iterator();
	while (iterator.hasNext()) {
		System.out.println(iterator.next());
	}
    }
}
