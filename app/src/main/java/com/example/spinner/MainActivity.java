package com.example.spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;



import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private OkHttpClient client;
    private Request request;
    String text;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         this.text = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        client = new OkHttpClient();
        String url = "https://learn.operatoroverload.com/rental/"+ this.text;
        request = new Request.Builder().url(url).build();

        Thread t =new Thread(){
            @Override
            public void run(){
                super.run();
                try
                {
                    final Response response = client.newCall(request).execute();
                    final String response_string = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView v = findViewById(R.id.datatext);
                            v.setText(response_string);
                        }
                    });
                }catch(IOException e){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView v = findViewById(R.id.datatext);
                            v.setText("error");
                        }
                    });
                }
            }
        };
        t.start();

    }
}
