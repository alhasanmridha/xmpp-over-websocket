package com.example.myapplication.websocket;

import android.util.Log;

import okhttp3.*;
import okio.ByteString;

import java.util.concurrent.TimeUnit;

public class WebSocketEcho extends WebSocketListener {
    private static final String TAG = "WebSocket";
    public void run() {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(0,  TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .url("ws://157.230.36.183:5280/xmpp")
                .build();
        client.newWebSocket(request, this);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();
    }

    @Override public void onOpen(WebSocket webSocket, Response response) {
        System.out.println("WebSocket connection opened");
        webSocket.send("<open xmlns=\"urn:ietf:params:xml:ns:xmpp-framing\"\n" +
                "             to=\"ckotha.com\"\n" +
                "             version=\"1.0\" />");
//        webSocket.close(1000, "<close xmlns=\"urn:ietf:params:xml:ns:xmpp-framing\" />");
        webSocket.send("<close xmlns=\"urn:ietf:params:xml:ns:xmpp-framing\" />");
    }

    @Override public void onMessage(WebSocket webSocket, String text) {
        Log.d(TAG, "onMessage: " + text);
    }

    @Override public void onMessage(WebSocket webSocket, ByteString bytes) {
        System.out.println("MESSAGE: " + bytes.hex());
    }

    @Override public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        t.printStackTrace();
    }
    public static void main(String... args) {
        new WebSocketEcho().run();
    }
}
