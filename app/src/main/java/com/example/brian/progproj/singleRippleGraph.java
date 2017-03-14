package com.example.brian.progproj;

import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;
import menu.BlankFragment;
import menu.DetailFragment;

public class singleRippleGraph extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

float ANS =0;
    DecoView decoView;
    private static final String TAG = "ripplesence";

    Button ripple;
    // TextView txtArduino;
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

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);





        decoView = (DecoView) findViewById(R.id.dynamicArcView);

        final SeriesItem seriesItem = new SeriesItem.Builder(Color.BLUE)
                .setRange(0, 3500, 0)
                .build();

        int series1Index = decoView.addSeries(seriesItem);





        final TextView textPercentage = (TextView) findViewById(R.id.textPercentage);
        seriesItem.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float percentComplete, float currentPosition) {
                float percentFilled =((currentPosition - seriesItem.getMinValue()) / (seriesItem.getMaxValue() - seriesItem.getMinValue()));
                textPercentage.setText(String.format("%.0f%%", percentFilled * 100f));
            }

            @Override
            public void onSeriesItemDisplayProgress(float percentComplete) {

            }
        });
float answer;
        answer = ANS;
        decoView.addEvent(new DecoEvent.Builder(16.3f)
                .setIndex(series1Index)
                .setDelay(1000)
                .build());

        decoView.addEvent(new DecoEvent.Builder(30f)
                .setIndex(series1Index)
                .setDelay(1000)
                .build());

        decoView.addEvent(new DecoEvent.Builder(46f)
                .setIndex(series1Index)
                .setDelay(1000)
                .build());

        decoView.addEvent(new DecoEvent.Builder(ANS)
                .setIndex(series1Index)
                .setDelay(1000)
                .build());






















   // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
         //   this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    //drawer.setDrawerListener(toggle);
   // toggle.syncState();

  //  NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
 //   navigationView.setNavigationItemSelectedListener(this);

  //  setContentView(R.layout.activity_main2);

    ripple = (Button) findViewById(R.id.ripple);                    // button LED ON
    //btnOff = (Button) findViewById(R.id.btnOff);                   // button LED OFF
    //  humid = (Button) findViewById(R.id.humid);
    // txtArduino = (TextView) findViewById(R.id.txtArduino);        // for display the received data from the Arduino

    h = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case RECIEVE_MESSAGE:                                                    // if receive massage
                    byte[] readBuf = (byte[]) msg.obj;
                    String strIncom = new String(readBuf, 0, msg.arg1);                    // create string from bytes array
                    sb.append(strIncom);                                                // append string
                    int endOfLineIndex = sb.indexOf("\r\n");                            // determine the end-of-line
                    if (endOfLineIndex > 0) {                                            // if end-of-line,
                        String sbprint = sb.substring(0, endOfLineIndex);                // extract string
                        sb.delete(0, sb.length());// and clear
                        int foo = Integer.parseInt(sbprint);
                        float ans = (float) foo;

                        final SeriesItem seriesItem = new SeriesItem.Builder(Color.RED)
                                .setRange(0, 200, 0)
                                .build();

                        int series1Index = decoView.addSeries(seriesItem);


                        decoView.addEvent(new DecoEvent.Builder(16.3f)
                                .setIndex(series1Index)
                                .setDelay(1000)
                                .build());

                        decoView.addEvent(new DecoEvent.Builder(30f)
                                .setIndex(series1Index)
                                .setDelay(1000)
                                .build());

                        decoView.addEvent(new DecoEvent.Builder(46f)
                                .setIndex(series1Index)
                                .setDelay(1000)
                                .build());

                        decoView.addEvent(new DecoEvent.Builder(ans)
                                .setIndex(series1Index)
                                .setDelay(1000)
                                .build());

                        textPercentage.setText("D" + ans);// update TextView
                        // btnOff.setEnabled(true);
                        //  btnOn.setEnabled(true);
                        //humid.setEnabled(true);
                        ripple.setEnabled(true);
                    }
                    //Log.d(TAG, "...String:"+ sb.toString() +  "Byte:" + msg.arg1 + "...");
                    break;
            }
        }

        ;
    };

    btAdapter = BluetoothAdapter.getDefaultAdapter();        // get Bluetooth adapter
    checkBTState();

       ripple.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ripple.setEnabled(false);
                mConnectedThread.write("ripple");    // Send "1" via Bluetooth
                //Toast.makeText(getBaseContext(), "Turn on LED", Toast.LENGTH_SHORT).show();
            }
        });

      /*  btnOff.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnOff.setEnabled(false);
                mConnectedThread.write("temp");    // Send "0" via Bluetooth
                //Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
            }
        });

        humid.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                btnOff.setEnabled(false);
                mConnectedThread.write("humid");    // Send "0" via Bluetooth
                //Toast.makeText(getBaseContext(), "Turn off LED", Toast.LENGTH_SHORT).show();
            }
        });
*/

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

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

    /*try {
      btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
    } catch (IOException e) {
      errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
    }*/

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "...Connecting...");
        try {
            btSocket.connect();
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










    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        BlankFragment fragment = new BlankFragment();
        DetailFragment detailFragment = new DetailFragment();

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();


        if (id == R.id.nav_home) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        } else if (id == R.id.nav_add) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_main, detailFragment)
                    .commit();
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


private class ConnectedThread extends Thread {
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;

    public ConnectedThread(BluetoothSocket socket) {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
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
