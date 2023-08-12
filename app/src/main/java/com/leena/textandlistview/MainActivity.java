package com.leena.textandlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView alphabetView = findViewById(R.id.alphabetListView);

        String[] alphabets = getResources().getStringArray(R.array.alphabets);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                alphabets);

        alphabetView.setAdapter(adapter);

        TextView sentenceTextView = findViewById(R.id.sentenceTextView);

        alphabetView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedText = alphabetView.getItemAtPosition(i).toString();
                if (selectedText.contains("Backspace")) {
                    String currentText = sentenceTextView.getText().toString();
                    if (!currentText.isEmpty()) {
                        currentText = currentText.substring(0, currentText.length() - 1);
                        sentenceTextView.setText(currentText);
                    }
                } else if (selectedText.equals("Space")) {
                    sentenceTextView.append(" ");
                } else if (selectedText.contains("Enter")) {
                        String currentText = sentenceTextView.getText().toString();
                        textToSpeech.speak(currentText, TextToSpeech.QUEUE_FLUSH, null, null);

                    } else
                sentenceTextView.append(selectedText);
            }
        });

        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = textToSpeech.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {


                }
            }
        });
    }
}