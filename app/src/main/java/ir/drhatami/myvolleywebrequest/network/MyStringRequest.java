package ir.drhatami.myvolleywebrequest.network;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class MyStringRequest extends StringRequest {

    private Map<String, String> Parameters;
    private Map<String, String> Headers;

    public MyStringRequest(Map<String, String> _Parameters,
                           Map<String, String> _Headers, int method, String url,
                           Response.Listener<String> listener, Response.ErrorListener errorListener, Context CTX) {
        super(method, url, listener, errorListener);
        if (CTX != null) {
            Parameters = _Parameters;
            Headers = _Headers;
        }
    }

    @Override
    protected Map<String, String> getParams() {
        return Parameters;
    }

    @Override
    public Map<String, String> getHeaders() {
        return Headers;
    }

    // parse the gzip response using a GZIPInputStream
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        if (response.headers.containsValue("gzip")) {
            StringBuilder output = new StringBuilder(); // note: better to use StringBuilder
            try {
                final GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
                final InputStreamReader reader = new InputStreamReader(gStream);
                final BufferedReader in = new BufferedReader(reader);
                String read;
                while ((read = in.readLine()) != null) {
                    output.append(read);
                }
                reader.close();
                in.close();
                gStream.close();
            } catch (IOException e) {
                return Response.error(new ParseError());
            }
            return Response.success(output.toString(), HttpHeaderParser.parseCacheHeaders(response));
        } else {
            return super.parseNetworkResponse(response);


        }
    }
}