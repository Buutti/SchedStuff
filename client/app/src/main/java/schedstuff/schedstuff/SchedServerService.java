package schedstuff.schedstuff;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by hvilmi on 10/18/17.
 */

public class SchedServerService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public static final String SCHED_ACTION = "SchedServiceAction";
    public static final String GET_EVENTS_ACTION = "SchedServiceGetEvents";
    public static final String GET_EVENT_ACTION = "SchedServiceGetEvent";

    public static final String EVENT_ID = "SchedServiceEventID";

    public static final String RECEIVER = "SchedResultReceiver";

    private SchedResultReceiver resultReceiver;

    public SchedServerService(String name) {
        super(name);
    }

    private int resultCode;



    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        resultCode = -1;
        String url = "Osoite";
        resultReceiver = intent.getParcelableExtra(RECEIVER);

        switch (intent.getStringExtra(SCHED_ACTION)) {
            case GET_EVENT_ACTION:
                String eventID = intent.getStringExtra(EVENT_ID);

                //Build Restful api request here
                resultCode = 1;
                break;
            case GET_EVENTS_ACTION:

                resultCode = 0;
                break;
        }


        //Tähän intentin käsittely ja URL:n muokkaaminen sen mukaan

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Bundle results = new Bundle();
                        results.putString("response", response.toString());
                        resultReceiver.send(resultCode, results);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

    }

}
