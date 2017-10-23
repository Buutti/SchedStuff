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
    public static String SCHED_ACTION = "SchedServiceAction";
    public static String GET_EVENTS_ACTION = "SchedServiceGetEvents";
    public static String GET_EVENT_ACTION = "SchedServiceGetEvent";

    public static String EVENT_ID = "SchedServiceEventID";

    public static String RECEIVER = "SchedResultReceiver";

    private SchedResultReceiver resultReceiver;

    public SchedServerService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "Osoite";


        //Tähän intentin käsittely ja URL:n muokkaaminen sen mukaan

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

    }

}
