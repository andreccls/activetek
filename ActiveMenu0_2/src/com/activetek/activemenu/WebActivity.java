package com.activetek.activemenu;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class WebActivity extends AbstractActivityGroup{
	
	private Receiver rec;
	private TextView text;
	private int count;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		WebView web=(WebView) findViewById(R.id.webview);
		count=getIntent().getExtras().getInt("count");
		text=(TextView) findViewById(R.id.TextView01);
		text.setText("Su Orden está siendo preparada");
		rec=Receiver.getInstance();
		rec.setOwner(this);
		web.getSettings().setJavaScriptEnabled(true);
		Log.d("Web", getIntent().getExtras().getString("URL"));
		web.loadUrl(getIntent().getExtras().getString("URL"));
		web.setWebViewClient(new HelloWebViewClient());
	}
	
	
	@Override
	public void notifier(String message) {
		// TODO Auto-generated method stub
		if(message.equals("SERVED"));
			text.setText("Su Orden ya está Lista");
	}
	public class HelloWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;
		}
	}
	@Override
	public int count() {
		// TODO Auto-generated method stub
		return count;
	}
}

