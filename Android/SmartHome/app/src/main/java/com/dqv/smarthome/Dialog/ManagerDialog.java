package com.dqv.smarthome.Dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dqv.smarthome.Activity.MainActivity;
import com.dqv.smarthome.Activity.RoomActivity;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Model.UserModel;
import com.dqv.smarthome.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ManagerDialog extends DialogFragment {
    public static final String TAG = ManagerDialog.class.getSimpleName();
    public String urlGetAddRoom = UrlModel.url_insertRoomn;
    public String urlUpdateRoom = UrlModel.url_updateRoomn;

    Button btnUpdate;
    Button btnDelete;
    EditText etTen;
    EditText etKenh;
    TextView twTittle;
    Intent intent;
    public String currentToken ="";

//    static Context curentContext;
//    static Class<?>  updateContext;
//    static Class<?>  deleteContext;

    //Được dùng khi khởi tạo dialog mục đích nhận giá trị
    public static ManagerDialog newInstance(RoomModel data) {
        ManagerDialog dialog = new ManagerDialog();
        Bundle args = new Bundle();
        args.putString("name", data.getName());
        args.putInt("chanel", data.getChanel());
        args.putInt("id", data.getId());
        dialog.setArguments(args);

//        curentContext = _curentContext;
//        updateContext = _updateContext;
//        deleteContext = _deleteContext;
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_manager, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // lấy giá trị tự bundle
        String name = getArguments().getString("name", "");
        int chanel = getArguments().getInt("chanel", -1);
        final int id = getArguments().getInt("id", -1);
        intent =  new Intent(getContext(), RoomActivity.class);
        twTittle = (TextView) view.findViewById(R.id.dialogTittle);
        btnDelete = (Button) view.findViewById(R.id.btnDeleteDialog);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdateDialog);
        etTen = (EditText) view.findViewById(R.id.etSuaNamePhong);
        etKenh = (EditText) view.findViewById(R.id.etSuaKenhPhong);
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
        Log.d(TAG, "onViewCreated chanel: "+id);
        if(id>0){
            twTittle.setText("Sửa phòng");
        }
        else {
            twTittle.setText("Thêm mới phòng");
        }

        etTen.setText(name);
        etKenh.setText(chanel+"", EditText.BufferType.EDITABLE);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id>0){
                    RoomModel model = new RoomModel();
                    String name = etTen.getText().toString().trim();
                    String chanel = etKenh.getText().toString().trim();
                    if(name == null || name.equals("")){
                        Toast.makeText(getContext(),
                                "Không được để trống tên", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(chanel == null || chanel.equals("")) {
                            Toast.makeText(getContext(),
                                    "Không được để trống kênh", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Log.d(TAG, "onClick: "+name);
                            RoomModel obj = new RoomModel();
                            obj.setName(name);
                            obj.setChanel(Integer.parseInt(chanel));
                            obj.setId(id);
                            UpdateRoomApi(obj);
                        }

                    }
                }
                else {
                    RoomModel model = new RoomModel();
                    String name = etTen.getText().toString().trim();
                    String chanel = etKenh.getText().toString().trim();
                    if(name == null || name.equals("")){
                        Toast.makeText(getContext(),
                                "Không được để trống tên", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(chanel == null || chanel.equals("")) {
                            Toast.makeText(getContext(),
                                    "Không được để trống kênh", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Log.d(TAG, "onClick: "+name);
                            RoomModel obj = new RoomModel();
                            obj.setName(name);
                            obj.setChanel(Integer.parseInt(chanel));
                            InsertRoomApi(obj);
                        }

                    }
                }
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }

    public void InsertRoomApi(final RoomModel model){
        JSONObject postparams = new JSONObject();
        try
        {
            postparams.put("name", model.getName());
            postparams.put("chanel", model.getChanel());
        }
        catch (Exception ex){

        }

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, urlGetAddRoom,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(getContext(),RoomActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else {
                        Toast.makeText(getContext(),
                                msg+"", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getContext(),
                            ex+"", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse() returned: " + ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),
                        error+"", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onErrorResponse() returned: " + error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", "Bearer "+currentToken);
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", model.getName());
                params.put("chanel", model.getChanel()+"");
                return params;
            }
            @Override
            protected Map<String, String> getPostParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", model.getName());
                params.put("chanel", model.getChanel()+"");
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }

    public void UpdateRoomApi(final RoomModel model){
        JSONObject postparams = new JSONObject();
        try
        {
            postparams.put("name", model.getName());
            postparams.put("chanel", model.getChanel());
            postparams.put("id",model.getId());
        }
        catch (Exception ex){

        }

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, urlUpdateRoom,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(getContext(),RoomActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    else {
                        Toast.makeText(getContext(),
                                msg+"", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getContext(),
                            ex+"", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse() returned: " + ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),
                        error+"", Toast.LENGTH_LONG).show();
                Log.d(TAG, "onErrorResponse() returned: " + error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String,String> params = new HashMap<String,String>();
                params.put("Authorization", "Bearer "+currentToken);
                params.put("Content-Type", "application/json; charset=UTF-8");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", model.getName());
                params.put("chanel", model.getChanel()+"");
                return params;
            }
            @Override
            protected Map<String, String> getPostParams() throws AuthFailureError {
                super.getParams();
                Map<String,String> params = new HashMap<String,String>();
                params.put("name", model.getName());
                params.put("chanel", model.getChanel()+"");
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }

}
