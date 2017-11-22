package schedstuff.schedstuff;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SchedResultReceiver.Receiver {

    EditText eventIDEntry;
    TextView eventIDTextView;
    TextView eventInfoTextView;

    SchedResultReceiver resultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventIDEntry = (EditText) findViewById(R.id.eventIDEditText);
        eventIDTextView = (TextView) findViewById(R.id.eventIDTextView);
        eventInfoTextView = (TextView) findViewById(R.id.eventInfoTextView);

        resultReceiver = new SchedResultReceiver(new Handler());
        resultReceiver.setReceiver(this);

    }

    public void refreshEvents(View view) {
        Intent mServiceIntent = new Intent(this, SchedServerService.class);
        mServiceIntent.putExtra(SchedServerService.RECEIVER, resultReceiver);
        mServiceIntent.putExtra(SchedServerService.SCHED_ACTION, SchedServerService.GET_EVENTS_ACTION);

        startService(mServiceIntent);


    }

    public void fetchEvent(View view) {
        Intent mServiceIntent = new Intent(this, SchedServerService.class);
        mServiceIntent.putExtra(SchedServerService.RECEIVER, resultReceiver);
        mServiceIntent.putExtra(SchedServerService.SCHED_ACTION, SchedServerService.GET_EVENT_ACTION);
        mServiceIntent.putExtra(SchedServerService.EVENT_ID, eventIDEntry.getText().toString());

        startService(mServiceIntent);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        //resultCode: 0 = receiving list of event IDs
        //            1 = receiving full info of event

        JSONObject resultJSON = null;
        try {
            resultJSON = new JSONObject(resultData.getString("response"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (resultCode == 0) {

        }
        else if (resultCode == 1) {
            String[] jsonKeys = {"name", "deadline", "participants", "date"};
            String eventText = "";

            for (String key : jsonKeys) {

                try {
                    eventText += resultJSON.getString(key);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
