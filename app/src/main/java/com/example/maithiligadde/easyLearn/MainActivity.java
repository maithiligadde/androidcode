package com.example.maithiligadde.easyLearn;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.view.View.VISIBLE;

public class MainActivity extends ActionBarActivity {
    private static final int SETTINGS_REQUEST =1 ;
    MediaPlayer mp;
    TextToSpeech ttsEN,ttsES;
    final Context mContext = this;
    private String tgtLanguage="enus";
    private String srcLanguage="eses";
    HashMap<String,String> codeToLanguage;
    HashMap<String,String> flagmap;
    EditText srcWord;
    EditText tgtWord;
    ImageView srcflag;
    ImageView tgtflag;
    int srcimageid;
    int tgtimageid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codeToLanguage=new HashMap<>();
        codeToLanguage.put("eses","Spanish");
        codeToLanguage.put("enus","English");
        codeToLanguage.put("frfr","French");
        codeToLanguage.put("ptbr","Portugese");
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        final LinearLayout l = (LinearLayout) findViewById(R.id.lay);
        final LinearLayout l1 = (LinearLayout) findViewById(R.id.lay1);
        ImageView TranslatetoEnglish = (ImageView) findViewById(R.id.SpanishtoEngTranslation);
        ImageView TranslatetoSpanish = (ImageView) findViewById(R.id.EngtoSpanishTranslate);
        final ImageView English = (ImageView) findViewById(R.id.Engpronunciation);
        srcflag = (ImageView) findViewById(R.id.imageViewf);
        tgtflag = (ImageView) findViewById(R.id.imageViewf1);
        final ImageView Spanish=(ImageView)findViewById(R.id.SpanishPronunciation);
        srcWord = (EditText) findViewById(R.id.spanishword);
        tgtWord = (EditText) findViewById(R.id.Englishword);
        srcWord.setHint(codeToLanguage.get(srcLanguage));
        tgtWord.setHint(codeToLanguage.get(tgtLanguage));
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                //tgtWord.setText(intent.getStringExtra(intent.EXTRA_TEXT));
                final String queryString=intent.getStringExtra(intent.EXTRA_TEXT);
                RequestQueue queue = VolleyApplication.getInstance().getRequestQueue();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        "https://gateway.watsonplatform.net/language-identification-beta/api/v1/txtlid/0",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                response = response.replaceAll("-", "").toLowerCase();
                                if (response.equals(srcLanguage)) {
                                    srcWord.setText(queryString);
                                    srcflag.setVisibility(View.VISIBLE);
                                    srcimageid = getResources().getIdentifier("com.example.maithiligadde.easyLearn:drawable/" +srcLanguage, null, null);
                                    srcflag.setImageResource(srcimageid);
                                } else {
                                    tgtWord.setText(queryString);
                                    tgtflag.setVisibility(View.VISIBLE);
                                    int tgtimageid = getResources().getIdentifier("com.example.maithiligadde.easyLearn:drawable/" +tgtLanguage, null, null);
                                    tgtflag.setImageResource(tgtimageid);
                                }

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
                                + Base64.encodeToString(("35b7c16c-36e8-4ceb-8f1a-fe24a951e3c6:U3mxk1FlAWqL").getBytes(),
                                Base64.NO_WRAP);
                        headers.put("Authorization", auth);
                        headers.put("Content-Type", "application/x-www-form-urlencoded");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("sid", "lid-generic");
                        params.put("rt", "text");
                        params.put("txt", queryString);
                        return params;
                    }
                };

                queue.add(stringRequest);

            }
        }

        if(intent.getStringExtra("com.parse.Data")!=null ){

            Gson gson=new Gson();
            Map<String,String> map= gson.fromJson(intent.getStringExtra("com.parse.Data"),Map.class);
            srcWord.setText(map.get("Word"));
        }
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
                                imm.hideSoftInputFromWindow(srcWord.getWindowToken(), 0);
                                tgtWord.setText(response);

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
                        params.put("sid", "mt-"+srcLanguage+"-"+tgtLanguage);
                        params.put("rt", "text");
                        params.put("txt", srcWord.getText().toString());
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
                                InputMethodManager imm = (InputMethodManager) getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(srcWord.getWindowToken(), 0);
                                srcWord.setText(response);

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
                        params.put("sid", "mt-"+tgtLanguage+"-"+srcLanguage);
                        params.put("rt", "text");
                        params.put("txt", tgtWord.getText().toString());
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
                ttsEN.speak(tgtWord.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        Spanish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsES.speak(srcWord.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
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
            showSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showSettings(){
        Intent intent=new Intent(this,SettingsActivity.class);
        startActivityForResult(intent, SETTINGS_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SETTINGS_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                System.out.println("received data "+data);
                srcLanguage=data.getStringExtra("src");
                tgtLanguage=data.getStringExtra("tgt");
                resetEditTexts();

            }
        }
    }

    private void resetEditTexts() {
        srcWord.setText(null);
        tgtWord.setText(null);
        srcWord.setHint(codeToLanguage.get(srcLanguage));
        tgtWord.setHint(codeToLanguage.get(tgtLanguage));
        srcimageid = getResources().getIdentifier("com.example.maithiligadde.easyLearn:drawable/" + srcLanguage, null, null);
        srcflag.setImageResource(srcimageid);
        tgtimageid = getResources().getIdentifier("com.example.maithiligadde.easyLearn:drawable/" + tgtLanguage, null, null);
        tgtflag.setImageResource(tgtimageid);
    }
}





