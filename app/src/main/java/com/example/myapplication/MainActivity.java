package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.websocket.WebSocketEcho;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "WebSocket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetExample example = new GetExample();
        String url = "ws://157.230.36.183:5280/xmpp";
        example.execute(url);
    }
    public static class GetExample extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            new WebSocketEcho().run();
            return "Nothing happened";
        }

        @Override
        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: " + s);
        }
    }

}