package ir.drhatami.myvolleywebrequest.network;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.util.HashMap;
import java.util.Map;

import ir.drhatami.myvolleywebrequest.Application;

public class MainNetwork {
    private static final String BASE_URL = "";

    public static void testRequest(Listener<String> listener, ErrorListener errorListener) {
        try {

            Map<String, String> params = new HashMap<>();
            Map<String, String> headers = new HashMap<>();

            params.put("fnname", "Quiz_Get_Rules");
            headers.put("Accept-Encoding", "gzip");

            MyStringRequest req = new MyStringRequest(params, headers,
                    Method.POST, BASE_URL, listener, errorListener, Application.getInstance());

            req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 7, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Application.getInstance().addToRequestQueue(req);

        } catch (Exception ignored) {
        }
    }

}