package net.webersoftwaresolutions.readwebasync;

// Janet Weber    10/9/2017
// Android App Dev III Exercise
// http://www.vogella.com/tutorials/AndroidBackgroundProcessing/article.html#asynctask
//
// This project ReadWebAsync implements the second activity from above tutorial.
// This project uses a webview instead of a text view.  Basically, this code uses okhttp
// to download html from a url into a String (in background) and when download is complete,
// the String (NOT the url) is loaded into a webview for display.

// async task via the execute() method - which calls doInBackground and onPostExecute
// methods.  doInBackground() method contains the coding instruction which should be
// performed in a background thread. This method runs automatically in a separate thread.
// onPostExecute() method synchronizes itself again with the user interface thread and
// allows it to be updated. This method is called by the framework once the doInBackground
// method finishes.


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
//import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.myWebView);
        Button loadButton = (Button) findViewById(R.id.loadWebButton);

        // when loadButton is clicked - start the download
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // declare/init a task.  Then execute it, passing the url (as a string)
                //  of the site to download from (html will be downloaded)
                DownloadWebPageTask task = new DownloadWebPageTask();
                task.execute("http://www.vogella.com/index.html");
                //task.execute("http://jweber.mccdgm.net/dgm275/portfolioPHP/index.php");
            }
        });
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            // we use the OkHttp library from https://github.com/square/okhttp (had to put
            //    dependencies in gradle.build file for app
            // See: http://www.vogella.com/tutorials/JavaLibrary-OkHttp/article.html
            OkHttpClient client = new OkHttpClient();
            Request request =
                    new Request.Builder()
                            .url(urls[0])
                            .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                try {
                    // myResult is a string of html code (downloaded from the url)
                    String myResult = response.body().string();
                    return myResult;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Log.i("NO DOWNLOAD", "Download failed");
            return "Download failed";
        }

        // Now present the data.  Use loadData to load html.
        //  Note: loadUrl would load a url.
        @Override
        protected void onPostExecute(String result) {
            webView.loadData(result, "text/html","UTF-8");
        }
    }
}
