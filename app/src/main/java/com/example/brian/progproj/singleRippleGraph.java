package com.example.brian.progproj;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;



public class singleRippleGraph extends AppCompatActivity  {

    float ANS = 0;
    DecoView decoView;
    private static final String TAG = "ripplesence";
    int height;
    int depth;



    Handler h;

    final int RECIEVE_MESSAGE = 1;        // Status  for Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    private ConnectedThread mConnectedThread;

    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // MAC-address of Bluetooth module, hardcoded hardware address of hco5
    private static String address = "98:D3:31:FB:1F:75";

    private SwipeRefreshLayout swipeContainer;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_ripple);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Bundle b = getIntent().getExtras();




        String str =  b.getString("ripple");
        RippleInstance thisone = new Gson().fromJson(str,RippleInstance.class);


        int height = thisone.Depth;     // 40

        final int  Range = thisone.RippleHeight;       // 50

        final int offset = Range - height;  // 10




        Log.e("depth + arduono", Range + "") ;
        Log.e("water depth", height + "") ;
        Log.e("offset", offset + "") ;

        String initial = "Tap Screen To Begin";
        SpannableString ss;
        TextView a;


        ss = new SpannableString(initial);
        ss.setSpan(new RelativeSizeSpan(2f), 0, ss.length(), 0); // set size
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#1BC2EE")), 0, ss.length(), 0);// set color
        a = (TextView) findViewById(R.id.textPercentage);
        a.setText(ss);


        decoView = (DecoView) findViewById(R.id.dynamicArcView);



        final TextView textPercentage = (TextView) findViewById(R.id.textPercentage);




        //  ripple = (Button) findViewById(R.id.ripple);                    // button LED ON
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:                                                    // if receive massage
                        //  swipeContainer.setRefreshing(false);
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);                   // create string from bytes array
                        sb.append(strIncom);                                                 // append string
                        int endOfLineIndex = sb.indexOf("\r\n");                             // determine the end-of-line
                        if (endOfLineIndex > 0) {                                            // if end-of-line,
                            String sbprint = sb.substring(0, endOfLineIndex);                // extract string
                            sb.delete(0, sb.length());// and clear


                            if(sbprint.equals("")){
                                mConnectedThread.write("ripple");


                            }


                            int arduino = Integer.parseInt(sbprint);   ///   value

                            Log.e("arduino input", arduino+ "");

                            int fract = Range - arduino;      // 40 -10 = 30
                            Log.e("range - ardlessoff", fract + "") ;
                            ///  30/ 40 == 75%

                            if (fract < 0){
                                fract = 0;
                            }

                            if (fract > Range){
                                fract = Range;
                            }


                            float fractF = (float)fract;
                            float RangeF = (float)Range;

                            Float perc = fractF/RangeF;
                            float perc1 = perc * 100;
                            Log.e("per float", perc1 + "") ;

                            int init = (int)perc1;



                            if (init > 100){
                                init = 100;
                            }

                            if(init < 0){
                                init = 0;
                            }

                            Log.e("final percent", init + "") ;



                            SeriesItem seriesItem = new SeriesItem.Builder((Color.parseColor("#1BC2EE")))
                                    .setRange(0, Range, 0)
                                    .setInset(new PointF(50f, 50f))
                                    .setLineWidth(75)
                                    .build();

                            int series1Index = decoView.addSeries(seriesItem);





                            String s,middlePercent;
                            SpannableString ss1,middleS;
                            TextView tv,tv1;




                            middlePercent = "" + init + "%";
                            middleS = new SpannableString(middlePercent);
                            middleS.setSpan(new RelativeSizeSpan(3f), 0, middlePercent.length(), 0); // set size
                            middleS.setSpan(new ForegroundColorSpan(Color.parseColor("#1BC2EE")), 0, middlePercent.length(), 0);// set color
                            tv1 = (TextView) findViewById(R.id.textView2);
                            tv1.setText(middleS);

                            if (init > 50) {


                                s = "Everything is ok!";

                                ss1 = new SpannableString(s);
                                ss1.setSpan(new RelativeSizeSpan(2f), 0, s.length(), 0); // set size
                                ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#1BC2EE")), 0, s.length(), 0);// set color
                                tv = (TextView) findViewById(R.id.textPercentage);
                                tv.setText(ss1);

                                decoView.deleteAll();

                                seriesItem = new SeriesItem.Builder((Color.parseColor("#1BC2EE")))
                                        .setRange(0, Range, 0)
                                        .setInset(new PointF(50f, 50f))
                                        .setInterpolator(new AccelerateInterpolator())

                                        .setLineWidth(75)
                                        .build();

                                series1Index = decoView.addSeries(seriesItem);
                                decoView.addEvent(new DecoEvent.Builder(fract)
                                        .setIndex(series1Index)

                                        .setDelay(1000)
                                        .build());
                            } else if (init<50 && init >20) {


                                s = "Level Low!";
                                ss1 = new SpannableString(s);
                                ss1.setSpan(new RelativeSizeSpan(2f), 0, s.length(), 0); // set size
                                ss1.setSpan(new ForegroundColorSpan(Color.parseColor("#1BC2EE")), 0, s.length(), 0);// set color
                                tv = (TextView) findViewById(R.id.textPercentage);
                                tv.setText(ss1);
                                decoView.deleteAll();

                                seriesItem = new SeriesItem.Builder((Color.parseColor("#1BC2EE")))
                                        .setRange(0, Range, 0)
                                        .setLineWidth(75)
                                        .setInset(new PointF(50f, 50f))
                                        .build();

                                series1Index = decoView.addSeries(seriesItem);
                                decoView.addEvent(new DecoEvent.Builder(fract)
                                        .setIndex(series1Index)
                                        .setDelay(1000)
                                        .build());
                            } else if (init <20 ) {


                                s = "Level is Critical";
                                ss1 = new SpannableString(s);
                                ss1.setSpan(new RelativeSizeSpan(2f), 0, s.length(), 0); // set size
                                ss1.setSpan(new ForegroundColorSpan(Color.RED), 0, s.length(), 0);// set color
                                tv = (TextView) findViewById(R.id.textPercentage);
                                tv.setText(ss1);

                                decoView.deleteAll();

                                seriesItem = new SeriesItem.Builder((Color.RED))
                                        .setRange(0, Range, 0)
                                        .setInset(new PointF(50f, 50f))
                                        .setLineWidth(75)
                                        .build();

                                series1Index = decoView.addSeries(seriesItem);
                                decoView.addEvent(new DecoEvent.Builder(fract)
                                        .setIndex(series1Index)
                                        .setDelay(1000)
                                        .build());
                            }


                        }

                        break;
                }


            }


        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();        // get Bluetooth adapter
        checkBTState();


        textPercentage.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                mConnectedThread.write("ripple");    // Send ripple via Bluetooth


            }
        });

        decoView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                mConnectedThread.write("ripple");    // Send ripple via Bluetooth

            }
        });


    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
                Log.e(TAG, "Could not create Insecure RFComm Connection", e);
            }
        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d(TAG, "...onResume - try connect...");

        // Set up a pointer to the remote node using it's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);



        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }




        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
            Toast.makeText(getBaseContext(), "connected", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "....Connection ok...");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, "...Create Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(TAG, "...In onPause()...");

        try {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if (btAdapter == null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message) {
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }




    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // Get number of bytes and message in "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();        // Send to message queue Handler
                } catch (IOException e) {
                    break;
                }
            }
        }


        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...Data to send: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Error data send: " + e.getMessage() + "...");
            }
        }


    }




}
