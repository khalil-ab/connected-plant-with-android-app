package com.example.connected_plant;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class iot_info_pg extends AppCompatActivity {

    TextView nom_plante,temperature,humidite_air,humidite_sol;
    private Button analyser,localiser;
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iot_info_pg);

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl("http://192.168.43.77:8000/index.html");

        //nom_plante = (TextView)findViewById(R.id.txt_name_plant);
        temperature = (TextView)findViewById(R.id.txt_temp);
        humidite_air = (TextView)findViewById(R.id.txt_hum_air);
        humidite_sol = (TextView)findViewById(R.id.txt_hum_sol);

       String clientId = MqttClient.generateClientId();
       final MqttAndroidClient client =
                new MqttAndroidClient(iot_info_pg.this, "tcp://192.168.43.77:1883",
                       clientId);

        afficher_resultats(client);

        analyser = findViewById(R.id.anlys);
        analyser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String topic = "foo/bar";
                String payload = "the payload";
                byte[] encodedPayload = new byte[0];
                try
                {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                }
                catch (UnsupportedEncodingException | MqttException e)
                {
                    e.printStackTrace();
                }

                ProgressDialog progress;
                progress = new ProgressDialog(iot_info_pg.this);
                progress.setTitle("Please Wait!!");
                progress.setMessage("Wait!!");
                progress.setCancelable(true);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                try {
                    IMqttToken token = client.connect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            //Toast.makeText(iot_info_pg.this, "connected", Toast.LENGTH_SHORT).show();

                            String topic = "plant_name";
                            int qos = 1;
                            try {
                                IMqttToken subToken = client.subscribe(topic, qos);
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            Toast.makeText(iot_info_pg.this, "not connected", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {}

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception
                    {
                        if(topic.equals("plant_name")){
                        //Toast.makeText(stream_ai.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();
                        open_second_pg(new String(message.getPayload()));}
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {}
                });


            }
        });


        localiser = findViewById(R.id.localisation);
        localiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String topic = "foo/bar2";
                String payload = "the payload2";
                byte[] encodedPayload = new byte[0];
                try
                {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                }
                catch (UnsupportedEncodingException | MqttException e)
                {
                    e.printStackTrace();
                }

                ProgressDialog progress;
                progress = new ProgressDialog(iot_info_pg.this);
                progress.setTitle("Please Wait!!");
                progress.setMessage("Wait!!");
                progress.setCancelable(true);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                try {
                    IMqttToken token = client.connect();
                    token.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            //Toast.makeText(iot_info_pg.this, "connected", Toast.LENGTH_SHORT).show();

                            String topic = "loc";
                            int qos = 1;
                            try {
                                IMqttToken subToken = client.subscribe(topic, qos);
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                            Toast.makeText(iot_info_pg.this, "not connected", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {}

                    @Override
                    public void messageArrived(String topic, MqttMessage message) throws Exception
                    {
                        if(topic.equals("loc")){
                                       //Toast.makeText(stream_ai.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();
                            open_third_pg(new String(message.getPayload()));}
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {}
                });
            }
        });
    }

    public void open_second_pg(String txt)
    {
        String text=txt;
        Intent intent = new Intent(this, info_pg.class);
        intent.putExtra("plant_name",text);
        startActivity(intent);
    }

    public void open_third_pg(String txt)
    {
        String text=txt;
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("loc",text);
        startActivity(intent);
    }

    public void afficher_resultats(final MqttAndroidClient client )
    {
        try
        {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken)
                {
                    Toast.makeText(iot_info_pg.this, "connected", Toast.LENGTH_SHORT).show();
                    String topic = "resultas";
                    int qos = 1;
                    try
                    {
                        IMqttToken subToken = client.subscribe(topic, qos);
                    }
                    catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(iot_info_pg.this, "not connected", Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (MqttException e) { e.printStackTrace(); }

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {}

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception
            {
                if(topic.equals("resultas")){
               //Toast.makeText(iot_info_pg.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();

                String s[] = new String(message.getPayload()).split("/");
                temperature.setText(s[0]);
                humidite_air.setText(s[1]);
                humidite_sol.setText(s[2]);}
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {}
        });
    }


}
