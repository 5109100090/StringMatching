package com.amca.android.stringmatching;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StringMatching extends Activity {

	private EditText str1;
	private EditText str2;
	private TextView textResult;
	private Button button1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_string_matching);
		
		str1 = (EditText) findViewById(R.id.str1);
		str2 = (EditText) findViewById(R.id.str2);
		textResult = (TextView) findViewById(R.id.textView1);
		button1 = (Button) findViewById(R.id.button1);

		button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	 String edResult = null, edMultiResult = null, result = null;
        		 String tfidfResult = null;
        		 String tfidfMultiResult = null;
        		
            	EditDistance ed = new EditDistance(str1.getText().toString(), str2.getText().toString());
            	edResult = (String) ("ED : " + ed.similarity());
            	edMultiResult = multi();
            	
            	TFIDF tfidf = new TFIDF();
            	tfidfResult = tfidf.count();
            	
            	multipleTFIDF mTfidf = new multipleTFIDF();
            	tfidfMultiResult = mTfidf.proces();
            	
            	result = "=== ED\n" + edResult + "\n\n=== ED multi\n" + edMultiResult + "\n\n=== TFIDF\n" + tfidfResult + "\n\n=== TFIDF multi\n" + tfidfMultiResult;
            	
            	textResult.setText(result);
            }
        });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_string_matching, menu);
		return true;
	}
	
	public String multi(){
        HashMap<String, String> doc = new HashMap<String, String>();
        doc.put("profile1", "gaming, sport, music");
        doc.put("profile2", "watching tv, music");
        doc.put("profile3", "game");
        doc.put("profile4", "sport, movie");
        doc.put("profile5", "watch movie, music");
        doc.put("profile6", "sport, read book");
        doc.put("profile7", "listening to music, reading novel");
        doc.put("profile8", "listening music, read book");
        
        String interest = "reading, watching movie, music";
        String[] interests = interest.split(", ");
        System.out.println("user interest = " + interest);
        
        String test = (String) textResult.getText().toString();
        test += "\r\n\r\nuser interest : " + interest + "\r\n";
        for (Iterator<Entry<String, String>> it = doc.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            
            String[] parts = value.toString().split(", ");
            double result = 0;
            for(int i = 0; i < interests.length; i++){
                for(int j = 0; j < parts.length; j++){
                    EditDistance ed = new EditDistance(interests[i], parts[j]);
                    result += ed.similarity();
                }
            }
            test += key + " => " + result + " (" + value + ")\r\n";
            System.out.println(key + " => " + result + " (" + value + ")");
        }
        return test;
    }

}
