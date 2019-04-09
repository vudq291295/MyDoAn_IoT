package com.dqv.smarthome.Dialog;

import android.content.Intent;
import android.media.audiofx.DynamicsProcessing;
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
import com.dqv.smarthome.Model.EquipmentModel;
import com.dqv.smarthome.Model.RoomModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DialogEquipment extends DialogFragment {
    public static final String TAG = DialogEquipment.class.getSimpleName();
    public String urlGetAddEquip = UrlModel.url_insertEquipment;
    public String urlUpdateEquip = UrlModel.url_updateEquipment;

    Button btnUpdate;
    Button btnDelete;
    EditText etTen;
    EditText etKenh;
    TextView twTittle;
    TextView twCurrentPhong;

    Intent intent;
    public String currentToken ="";

//    static Context curentContext;
//    static Class<?>  updateContext;
//    static Class<?>  deleteContext;

    //Được dùng khi khởi tạo dialog mục đích nhận giá trị
    public static DialogEquipment newInstance(EquipmentModel data) {
        DialogEquipment dialog = new DialogEquipment();
        Bundle args = new Bundle();
        args.putString("name", data.getName());
        args.putString("nameRoom", data.getNameRoom());
        args.putInt("portOutput", data.getPortOutput());
        args.putInt("roomId", data.getRoomId());
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
        return inflater.inflate(R.layout.dialog_equipment, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // lấy giá trị tự bundle
        String name = getArguments().getString("name", "");
        String nameRoom = getArguments().getString("nameRoom", "");
        int chanel = getArguments().getInt("chanel", -1);
        final int id = getArguments().getInt("id", -1);
        final int portOutput = getArguments().getInt("portOutput", -1);
        final int roomId = getArguments().getInt("roomId", -1);
        intent =  new Intent(getContext(), MainActivity.class);
        twTittle = (TextView) view.findViewById(R.id.dialogTittleEquip);
        twCurrentPhong = (TextView) view.findViewById(R.id.currentPhongEquip);
        btnDelete = (Button) view.findViewById(R.id.btnDeleteDialogEquip);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdateDialogEquip);
        etTen = (EditText) view.findViewById(R.id.etSuaNameEquip);
        etKenh = (EditText) view.findViewById(R.id.etSuaKenhEquip);
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
        twCurrentPhong.setText(nameRoom);
        Log.d(TAG, "onViewCreated chanel: "+id);
        if(id>0){
            twTittle.setText("Sửa thiết bị");
        }
        else {
            twTittle.setText("Thêm thiết bị");
        }

        etTen.setText(name);
        etKenh.setText(portOutput+"", EditText.BufferType.EDITABLE);
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
                            EquipmentModel obj = new EquipmentModel();
                            obj.setName(name);
                            obj.setPortOutput(Integer.parseInt(chanel));
                            obj.setRoomId(roomId);
                            obj.setId(id);
                            UpdateEquipApi(obj);
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
                            EquipmentModel obj = new EquipmentModel();
                            obj.setName(name);
                            obj.setPortOutput(Integer.parseInt(chanel));
                            obj.setRoomId(roomId);
                            InsertEquipApi(obj);
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

    public void InsertEquipApi(final EquipmentModel model){
        JSONObject postparams = new JSONObject();
        Log.d(TAG, "InsertEquipApi name: "+model.getName());
        Log.d(TAG, "InsertEquipApi name: "+model.getPortOutput());
        Log.d(TAG, "InsertEquipApi name: "+model.getRoomId());

        try
        {
            postparams.put("name", model.getName());
            postparams.put("portOutput", model.getPortOutput());
            postparams.put("roomId", model.getRoomId());

        }
        catch (Exception ex){

        }

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, urlGetAddEquip,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(getContext(), MainActivity.class);
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

    public void UpdateEquipApi(final EquipmentModel model){
        JSONObject postparams = new JSONObject();
        try
        {
            postparams.put("name", model.getName());
            postparams.put("portOutput", model.getPortOutput());
            postparams.put("roomId", model.getRoomId());
            postparams.put("id",model.getId());
        }
        catch (Exception ex){

        }

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, urlUpdateEquip,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(getContext(),MainActivity.class);
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
