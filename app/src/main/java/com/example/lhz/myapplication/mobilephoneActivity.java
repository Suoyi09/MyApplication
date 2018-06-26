package com.example.lhz.myapplication;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class mobilephoneActivity extends Activity {
    private Button button;
    private EditText editText;
    private String NumString;

    private String address = null;
    private String response = null;

    private TextView provinceCity;
    private TextView cityCode;
    private TextView zip1;
    private TextView card1;
    private  TextView company1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobilephone);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        provinceCity = (TextView)findViewById(R.id.provinceCity) ;
        cityCode = (TextView)findViewById(R.id.citycode);
        zip1 = (TextView)findViewById(R.id.zip);
        card1 = (TextView)findViewById(R.id.card);
        company1= (TextView)findViewById(R.id.company);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        NumString = editText.getText().toString();
                        if (NumString == null) {

                            return;
                        }
                        address = "http://apis.juhe.cn/mobile/get?phone=" + NumString + "&key=0bf3a208500f1c62b3737501d3bc941d";
                        System.out.println("NUM" + NumString);
                        System.out.println("addr" + address);
                        InputStream inputStream = null;
                        try {
                            URL url = new URL(address);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setReadTimeout(8000);
                            connection.setConnectTimeout(8000);
                            inputStream = connection.getInputStream();
                            if (inputStream == null)
                                System.out.println("in null");
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                            StringBuilder builder = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                builder.append(line);
                            }
                            if (builder!=null){
                                response = builder.toString();
                            }
                            System.out.println(builder);
                            Json(response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
            }
        });
    }
    private void Json(final String response) {

        try {

            runOnUiThread(new Runnable() {
                JSONObject jsonobject = new JSONObject(response);

                JSONObject result = jsonobject.getJSONObject("result");
                // System.out.println("result"+result);
                String prvoince = result.getString("province");
                // System.out.println("result"+prvoince);
                String city = result.getString("city");
                String areacode = result.getString("areacode");
                String zip = result.getString("zip");
                String company = result.getString("company");
                String card = result.getString("card");

                public void run() {
                    provinceCity.setText(prvoince);
                    cityCode.setText(areacode);
                    zip1.setText(zip);
                    company1.setText(company);
                    card1.setText(card);
                }



            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}