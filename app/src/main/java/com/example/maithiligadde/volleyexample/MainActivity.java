package com.example.maithiligadde.volleyexample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maithiligadde.volleyexample.R;
import com.example.maithiligadde.volleyexample.VolleyApplication;

import org.json.JSONObject;
import android.view.KeyEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.view.View.VISIBLE;

public class MainActivity extends ActionBarActivity {
    MediaPlayer mp;
    TextToSpeech ttsEN,ttsES;
    final Context mContext = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ImageView imageView = (ImageView)findViewById(R.id.imageViewf);
        ViewGroup.LayoutParams layoutParams=(ViewGroup.LayoutParams)imageView.getLayoutParams();
        layoutParams.width=500;
        imageView.setLayoutParams(layoutParams);*/
        final LinearLayout l = (LinearLayout) findViewById(R.id.lay);
        final LinearLayout l1 = (LinearLayout) findViewById(R.id.lay1);
        ImageView TranslatetoEnglish = (ImageView) findViewById(R.id.SpanishtoEngTranslation);
        ImageView TranslatetoSpanish = (ImageView) findViewById(R.id.EngtoSpanishTranslate);
        ImageView English = (ImageView) findViewById(R.id.Engpronunciation);
        ImageView Spanish=(ImageView)findViewById(R.id.SpanishPronunciation);
        final EditText t = (EditText) findViewById(R.id.editText);
        final EditText t1 = (EditText) findViewById(R.id.editText1);
        TranslatetoEnglish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                RequestQueue queue = VolleyApplication.getInstance().getRequestQueue();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "https://gateway.watsonplatform.net/machine-translation-beta/api/v1/smt/0",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                l1.setVisibility(VISIBLE);
                                InputMethodManager imm = (InputMethodManager) getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(t.getWindowToken(), 0);
                                t1.setText(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        String auth = "Basic "
                                + Base64.encodeToString(("d6928dc0-e36a-4020-934f-d279ef1b8d2e:eCUGxugYxNJ0").getBytes(),
                                Base64.NO_WRAP);
                        headers.put("Authorization", auth);
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("sid", "mt-eses-enus");
                        params.put("rt", "text");
                        params.put("txt", t.getText().toString());
                        return params;
                    }
                };

                queue.add(stringRequest);
            }
        });
        TranslatetoSpanish.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                RequestQueue queue1 = VolleyApplication.getInstance().getRequestQueue();
                StringRequest stringRequest1 = new StringRequest(Request.Method.POST,
                        "https://gateway.watsonplatform.net/machine-translation-beta/api/v1/smt/0",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                t.setText(response);

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        String auth = "Basic "
                                + Base64.encodeToString(("d6928dc0-e36a-4020-934f-d279ef1b8d2e:eCUGxugYxNJ0").getBytes(),
                                Base64.NO_WRAP);
                        headers.put("Authorization", auth);
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("sid", "mt-enus-eses");
                        params.put("rt", "text");
                        params.put("txt", t1.getText().toString());
                        return params;
                    }
                };

                queue1.add(stringRequest1);

            }
        });
        ttsEN = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ttsEN.setLanguage(Locale.ENGLISH);
            }
        });

        ttsES = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ttsES.setLanguage(Locale.ENGLISH);
            }
        });

        English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsEN.speak(t1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        Spanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsES.speak(t.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}





