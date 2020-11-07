package com.kostya_zinoviev.vksearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText inputId;
    Button btnOk;
    RecyclerView recyclerView;
    List<MyModel> myModelList;
    Adaapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputId = findViewById(R.id.inputId);
        btnOk = findViewById(R.id.btnOk);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myModelList = new ArrayList<MyModel>();
        myModelList.add(new MyModel( 34848,"Kostya","Zinoview"));
        adapter = new Adaapter(myModelList);
        recyclerView.setAdapter(adapter);
        btnOk.setOnClickListener(new View.OnClickListener() {
            String text;
            @Override
            public void onClick(View v) {
                String inputIdText = inputId.getText().toString();
                Uri uri  = Uri.parse("https://api.vk.com/method/users.get?user_ids=" + inputIdText + "&v=5.21&access_token=a02ac696a02ac696a02ac696c2a0597b40aa02aa02ac696ff65711cdd04156edb6f8b2c");
                URL url = null;
                try {
                     url = new URL(uri.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                MyAsyncTask myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute(url);
            }
        });
    }

    private String VkOk(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasNext = scanner.hasNext();
            if (hasNext){
                return scanner.next();
            }else{
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }
    class MyAsyncTask extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                 response = VkOk(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            String name = null;
            String secondName = null;
            int id = 0;
            JSONObject resultObject = null;
            try {
                JSONObject object = new JSONObject(response);
                JSONArray array = object.getJSONArray("response");
                resultObject  = array.getJSONObject(0);

                name = resultObject.getString("first_name");
                secondName = resultObject.getString("last_name");
                id = Integer.parseInt(resultObject.getString("id"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            myModelList.add(new MyModel(id,name,secondName));
            adapter.updateRecyclerView(myModelList.size() - 1);
        }
    }
}
