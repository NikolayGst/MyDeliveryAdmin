package ru.mydelivery.Provider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.mydelivery.Adapter.MyAdapter;
import ru.mydelivery.AddActivity;
import ru.mydelivery.MainActivity;
import ru.mydelivery.Pojo.Order;
import ru.mydelivery.Utils.AppConfig;

public class VolleyRequest {
    private static final String TAG = VolleyRequest.class.getSimpleName();
    private ProgressDialog pDialog;
    MainActivity mainActivity = new MainActivity();

    public void getOrder(final Context context, final List<Order> orderList, final MyAdapter adapter) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Пожалуйста, подождите...");
        pDialog.setCancelable(false);
        showpDialog();
        JsonArrayRequest req = new JsonArrayRequest(AppConfig.jobs,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        orderList.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonOrder = (JSONObject) response.get(i);
                                Order order = new Order();
                                order.setJobsId(jsonOrder.getInt("jobs_id"));
                                JSONObject user = jsonOrder.getJSONObject("user");
                                order.setName(user.getString("fio"));
                                order.setTelephone(user.getString("telephone"));
                                order.setPickFrom(user.getString("pick_from"));
                                order.setAddress(user.getString("address"));
                                order.setDescription(user.getString("description"));
                                order.setPrice(user.getDouble("price"));
                                order.setNote(user.getString("note"));
                                order.setDate(user.getString("date"));
                                order.setUserId(user.getInt("user_id"));
                                order.setStatus(user.getInt("status"));
                                order.setStatusText(user.getString("status_text"));
                                orderList.add(order);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context,
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                        adapter.notifyDataSetChanged();
                        hidepDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }

    public void deleteUser(final Context context, final int jobsId) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Удаление ...");
        pDialog.setCancelable(false);
        showpDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.delete, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hidepDialog();
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    String msg = jObj.getString("msg");
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: " + msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Json error: " + e.getMessage());
                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(context, "Невозможность подключения к серверу, проверьте подключение к интернету!", Toast.LENGTH_LONG).show();
                hidepDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("jobs_id", String.valueOf(jobsId));
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

    public void addJob(final Context context, final String fio, final String telephone, final String pickFrom, final String address,
                       final String desc, final String price, final String note, final String date, final String userId) {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Добавление задания ...");
        pDialog.setCancelable(false);
        showpDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.add, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                hidepDialog();
                Log.d(TAG, "onResponse: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        String msg = jObj.getString("msg");
                        Toast.makeText(context,
                                msg, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context,
                                MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        context.startActivity(intent);
                        ((AddActivity) context).finish();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(context,
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAG, "Json error: " + e.getMessage());
                    Toast.makeText(context, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(context, "Невозможность подключения к серверу, проверьте подключение к интернету!", Toast.LENGTH_LONG).show();
                hidepDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fio",fio);
                params.put("telephone",telephone);
                params.put("from",pickFrom);
                params.put("address",address);
                params.put("description",desc);
                params.put("price",price);
                params.put("note",note);
                params.put("date",date);
                params.put("user_id",userId);
                return params;
            }

        };

        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
