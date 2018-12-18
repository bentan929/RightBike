package rightbike.com.example.bentan.rightbike;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class Feedback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        WebView webView=(WebView)findViewById(R.id.webview);
        webView.loadUrl("https://goo.gl/forms/Rzv49xPNsyBbE2xd2");
    }
}
