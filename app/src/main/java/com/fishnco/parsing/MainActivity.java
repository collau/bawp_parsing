package com.fishnco.parsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue;
    String url = "https://www.google.com";
    String apiUrl = "https://jsonplaceholder.typicode.com/todos";
    String jsonObjUrl = "https://jsonplaceholder.typicode.com/todos/1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = findViewById(R.id.textView);

        queue = Volley.newRequestQueue(this);

//        getJSONArrayRequest();

//        getString(queue);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, jsonObjUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    textView.setText(response.getString("title"));
                    Log.d("JSONObject", response.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("MainActivity", "Error retrieving JSONObject");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "onCreate JSONObject: Failed");
            }
        });
        queue.add(jsonObjectRequest);

    }

    private void getJSONArrayRequest() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, apiUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("JSON", response.toString());

                try {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Log.d("JSONObject", "onCreate: " + jsonObject.getString("title"));
                    }
                } catch (JSONException e) {
                    Log.e("MainActivity", "Unable to parse JSON");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "onCreate: Failed");

            }
        });

        queue.add(jsonArrayRequest);
    }

    private void getString(RequestQueue queue) {
        // Create request for String
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            // Display the contents of our url
            @Override
            public void onResponse(String response) {
                Log.d("Main", "onCreate: " + response.substring(0, 500));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Main", "Failed to get info");

            }
        });

        // Add the request to the request queue
        queue.add(stringRequest);
    }
}