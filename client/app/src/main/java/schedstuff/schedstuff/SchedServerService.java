package schedstuff.schedstuff;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
    public static final String POST_EVENT_ACTION = "SchedServicePostEvent";

    public static final String EVENT_ID = "SchedServiceEventID";

    public static final String RECEIVER = "SchedResultReceiver";

    private ResultReceiver resultReceiver;

    private int resultCode;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SchedServerService() {
        super("SchedServerService");
    }


    @Override
    protected void onHandleIntent(@Nullable final Intent intent) {

        if (intent == null) {
            return;
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        resultCode = -1;
        String url = "http://192.168.1.162:8000";
        resultReceiver = intent.getParcelableExtra(RECEIVER);

        switch (intent.getStringExtra(SCHED_ACTION)) {
            case GET_EVENT_ACTION:
                String eventID = intent.getStringExtra(EVENT_ID);
                url += "/events/" + eventID;

                //Build Restful api request here
                resultCode = 1;
                break;
            case GET_EVENTS_ACTION:
                url += "/events/";

                resultCode = 0;
                break;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Bundle results = new Bundle();
                        results.putString("response", response);
                        resultReceiver.send(resultCode, results);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);

    }

}
