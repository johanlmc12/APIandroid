package com.example.ucevaapp20232;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ucevaapp20232.db.DataBase;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.Document;;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.LanguageServiceSettings;
import com.google.cloud.language.v1.Sentiment;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        final EditText inputText = findViewById(R.id.inputText);
        Button analyzeButton = findViewById(R.id.analyzeButton);
        final TextView resultView = findViewById(R.id.resultView);
        Button buttonVolver = findViewById(R.id.buttonVolver);

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                analyzeSentiment(inputText.getText().toString(), resultView);
            }
        });
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void analyzeSentiment(String text, TextView resultView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    try (InputStream credentialsStream = getAssets().open("archivo.json")){
                        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);
                        LanguageServiceSettings languageServiceSettings = LanguageServiceSettings.newBuilder()
                                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                                .build();

                        try (LanguageServiceClient language = LanguageServiceClient.create(languageServiceSettings)) {
                            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
                            Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String resultText = "Score: " + sentiment.getScore()+ " Magnitud:  " + sentiment.getMagnitude();
                                    resultView.setText(resultText);

                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
