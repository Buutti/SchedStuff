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

import org.json.JSONArray;
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

        JSONObject resultJSON;
        JSONArray resultJSONArray;


        if (resultCode == 0) {

            try {
                resultJSONArray = new JSONArray(resultData.getString("response"));
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }

            StringBuilder eventIDList = new StringBuilder();
            for (int i = 0; i < resultJSONArray.length(); i++) {
                try {
                    JSONObject event = resultJSONArray.getJSONObject(i);
                    int eventID = event.getInt("identifier");
                    eventIDList.append(Integer.toString(eventID));
                    if (i < resultJSONArray.length() -1) {
                        eventIDList.append(", ");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            eventIDTextView.setText(eventIDList);


        }
        else if (resultCode == 1) {

            try {
                resultJSON = new JSONObject(resultData.getString("response"));
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }


            String[] jsonKeys = {"name", "deadline", "date", "length", "organizer"};
            StringBuilder eventText = new StringBuilder();

            for (String key : jsonKeys) {

                try {
                    eventText.append("\n").append(key).append(resultJSON.getString(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            eventInfoTextView.setText(eventText);
        }
    }

    public void createEvent(View view) {
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }
}
