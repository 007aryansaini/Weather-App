package com.example.taapmaan;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText editText;
      Button search;
      TextView country , sunrise , sunset , pressure , humidity , temperature , city , wind  , centi , feri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        search=findViewById(R.id.search);
       country=findViewById(R.id.country);
       sunrise=findViewById(R.id.sunrise);
       sunset=findViewById(R.id.textView11);
       pressure=findViewById(R.id.textView12);
       humidity=findViewById(R.id.textView15);
       temperature=findViewById(R.id.temperature);
       city=findViewById(R.id.textView2);
       wind=findViewById(R.id.textView7);
       editText=findViewById(R.id.editTextTextPersonName2);
       centi=findViewById(R.id.centigrade);
       feri=findViewById(R.id.farenheit);
       search.setOnClickListener(view -> setContent());

    }
    public void setContent(){
        String text=editText.getText().toString();
        String url="http://api.openweathermap.org/data/2.5/weather?q="+text+"&appid=6ca567f2c51b0b663de3354131e992a2&units=imperial";
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject sys_object=response.getJSONObject("sys");
                String temp= sys_object.getString("country");
                country.setText(temp);
                String fsrise=sys_object.getString("sunrise");
                sunrise.setText(fsrise);
                String fset=sys_object.getString("sunset");
                sunset.setText(fset);
                JSONObject main_object=response.getJSONObject("main");
                String fpressure=main_object.getString("pressure");
                 pressure.setText(fpressure+" hPa");
                 String fhumidity=main_object.getString("humidity");
                 humidity.setText(fhumidity +"%");
                 String Ftemp=main_object.getString("temp");
                 double temp_int =Double.parseDouble(Ftemp);
                 double centigrade= (temp_int - 32 )* 0.5555;
                 centigrade=Math.round(centigrade);
                 temperature.setText(centigrade +"");

//
                 String fcity=response.getString("name");
                 city.setText(fcity);
                JSONObject wind_speed= response.getJSONObject("wind");
                     String fwind=wind_speed.getString("speed");
                     wind.setText(fwind +" kmph");
                double finalCentigrade = centigrade;
                feri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        temperature.setText(Ftemp+"");
                    }
                });
                centi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        temperature.setText(finalCentigrade+"");
                    }
                });





            } catch (JSONException e) {
                e.printStackTrace();
            }

        }, error -> Toast.makeText(MainActivity.this,"City not included in the list... ",Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);

    }
}