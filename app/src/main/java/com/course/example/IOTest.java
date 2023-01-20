/*
 * This is an example of writing and reading text IO to a file.
 * Press Go button to write text to the file. 
 * Press Enter key after last entry or Press Go with empty EditText widget.
 * The written file is found in /data/data/com.course.example/files/IOTest.txt
 * Run this example at API23. There is a regression in APIs 24 and 25.
 */
package com.course.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.View.OnClickListener;
import android.util.Log;
import java.io.*;

public class IOTest extends Activity implements OnClickListener {

	private final String file = "IOTest.txt";
	private String line;
	private TextView text;
	private Button button;
	private EditText edit;
	private OutputStreamWriter out;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text = (TextView) findViewById(R.id.TextView01);
		button = (Button) findViewById(R.id.Button01);
		edit = (EditText) findViewById(R.id.EditText01);

		button.setOnClickListener(this);
		
		//open output stream
		try {
			out = new OutputStreamWriter(openFileOutput(file, MODE_PRIVATE)); // also try MODE_APPEND
		} catch (IOException e) {}

	}

	public void onClick(View v) {
		doThatIO();
	}
	
	@Override
	public boolean onKeyUp(int keycode, KeyEvent event){
		super.onKeyUp(keycode, event);
		if (keycode == KeyEvent.KEYCODE_ENTER) {
			doThatIO();
			return true;
		}
		return true;
	}
      
	public void doThatIO() {
		try {
			line = edit.getText().toString().trim();
			if (!line.equals("")) { // empty string ends loop
				out.write(line + " \n"); 
				edit.setText("");
				return;
			};
		      	
			//close output stream
			out.close();
			
			//open stream for reading from file
			InputStream in = openFileInput(file);
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader reader = new BufferedReader(isr);
			String str = null;

			int count = 0;
			while ((str = reader.readLine()) != null) {
				count++; // count number of records read
				text.append(str + "\n");
			}
			// toast how many records read
			Toast.makeText(this,
					Integer.valueOf(count) + " records read",
					Toast.LENGTH_LONG).show();
			
			//close input stream
			reader.close();

		} catch (IOException e) {
			Log.e("IOTest", e.getMessage());
		}
	}

}