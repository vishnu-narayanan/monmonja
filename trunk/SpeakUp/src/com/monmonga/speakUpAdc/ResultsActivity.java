package com.monmonga.speakUpAdc;

/*
Copyright (C) 2009 Momonja, http://www.almondmendoza.com/

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

*/

import java.util.ArrayList;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.monmonga.speakUpAdc.R;
import com.monmonga.speakUpAdc.db.WordsProvider;

public class ResultsActivity extends Activity implements View.OnClickListener {
	private TextView txtCorrectNumbers;
	private TextView txtWrongNumbers;
	private ListView listWordsAnswer;
	private Button btnResultBack;
	
	private class WordsAndAnswer {
		private String word;
		private ArrayList<String> answer;
		
		public String getWord(){
			return word;
		}
		
		public String getAnswer(){
			return answer.toString();
		}
		public void setWord(String mWord){
			word = mWord;
		}
		public void setAnswer(ArrayList<String> mAnswer){
			answer = mAnswer;
		}
	}
	
	private class WordsAndAnswerAdapter extends ArrayAdapter<WordsAndAnswer>{
		private ArrayList<WordsAndAnswer> items;

		public WordsAndAnswerAdapter(WordsAndAnswer[] accounts) {
	            super(ResultsActivity.this, 0, accounts);
	    }
		@Override
        public View getView(int position, View convertView, ViewGroup parent) {
			WordsAndAnswer word = getItem(position);
			View view;
            if (convertView != null) {
                view = convertView;
            } else {
                view = getLayoutInflater().inflate(R.layout.results_list, parent, false);
            }
            
            TextView t = (TextView) view.findViewById(R.id.resultListWordFrom);
            t.setText(word.getWord());
            
            TextView tAnswer = (TextView) view.findViewById(R.id.resultListAnswer);
            tAnswer.setText(word.getAnswer());
			return view;
		}
       
	}
	
	/** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.results);
        
        this.txtCorrectNumbers = (TextView) this.findViewById(R.id.txtCorrectNumbers);
        this.txtWrongNumbers = (TextView) this.findViewById(R.id.txtWrongNumbers);
        this.listWordsAnswer = (ListView) this.findViewById(R.id.listWordsAnswer);
        
        WordsAndAnswer[] wordsAndAnswers = new WordsAndAnswer[WordsProvider.LIMIT];
        btnResultBack = (Button) findViewById(R.id.btnResultBack);
        btnResultBack.setOnClickListener(this);
        btnResultBack.setBackgroundColor(color.background_light);
        
        txtCorrectNumbers.setText(String.valueOf(10));
		txtWrongNumbers.setText(String.valueOf(5));
		
        Bundle extras = getIntent().getExtras(); 
        if(extras !=null)  {
        	
        }
        int correctAnswers = 0;
		int wrongAnswers = 0;
		for (int i = 0; i < WordsProvider.LIMIT;i++) {
			ArrayList<String> answer =  extras.getStringArrayList("answer" + i);
			
			WordsAndAnswer wordAndAnwer = new WordsAndAnswer();
			String word =  extras.getString("word" + i);
			
			wordAndAnwer.setAnswer(answer);
			wordAndAnwer.setWord(word);
			wordsAndAnswers[i] = wordAndAnwer;
			
			if(answer.contains(word))
				correctAnswers++;
			else
				wrongAnswers++;	
		}
		
		WordsAndAnswerAdapter adapter = new WordsAndAnswerAdapter(wordsAndAnswers);
		this.listWordsAnswer.setAdapter(adapter);
		listWordsAnswer.setFocusable(true);
		listWordsAnswer.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		txtCorrectNumbers.setText(String.valueOf(correctAnswers));
		txtWrongNumbers.setText(String.valueOf(wrongAnswers));
		
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v == btnResultBack){
			finish();
		}
	}

}
