package com.example.santoshmohan.speakeasy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.santoshmohan.speakeasy.R;


public class MainActivity extends Activity implements OnClickListener
{
    private TextView mText;
    private SpeechRecognizer sr;
    private static final String TAG = "Debug output: ";
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button speakButton = (Button) findViewById(R.id.mic_button);
        mText = (TextView) findViewById(R.id.Status);
        speakButton.setOnClickListener(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());
    }

    class listener implements RecognitionListener
    {
        public void onReadyForSpeech(Bundle params)
        {
            Log.d(TAG, "onReadyForSpeech");
        }
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginningOfSpeech");
        }
        public void onRmsChanged(float rmsdB)
        {
            Log.d(TAG, "onRmsChanged");
        }
        public void onBufferReceived(byte[] buffer)
        {
            Log.d(TAG, "onBufferReceived");
        }
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndofSpeech");

            Button btn = (Button)findViewById(R.id.mic_button);
            btn.setBackgroundColor(Color.rgb(85, 139, 47));
            btn.setText("TOUCH TO SPEAK COMMAND");
        }
        public void onError(int error)
        {
            Log.d(TAG,  "error " +  error);
            mText.setText("error " + error);
        }
        public void onResults(Bundle results)
        {
            String str = new String();
            Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            // For debugging purposes, we can confirm some suggestions
            for (int i = 0; i < data.size(); i++)
            {
                Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }

            mText.setText("Pick most accurate translation!");

            listView = (ListView) findViewById(R.id.TextSuggestion);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    data) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                    text.setTextColor(Color.rgb(51, 105, 30));
                    return view;
                }
            };

            listView.setAdapter(arrayAdapter);

        }
        public void onPartialResults(Bundle partialResults)
        {
            Log.d(TAG, "onPartialResults");
        }
        public void onEvent(int eventType, Bundle params)
        {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
    public void onClick(View v) {

        Button btn = (Button)findViewById(R.id.mic_button);
        btn.setBackgroundColor(Color.rgb(51,105,30));
        btn.setText("LISTENING");

        if (v.getId() == R.id.mic_button)
        {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");

            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
            sr.startListening(intent);
            Log.i("111111","11111111");
        }
    }
}