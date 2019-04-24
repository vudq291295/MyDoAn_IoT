package com.dqv.smarthome.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dqv.smarthome.Adapter.ScheduleTBAdapter;
import com.dqv.smarthome.Application.MyApplication;
import com.dqv.smarthome.ConnSqlite.SQLiteHandler;
import com.dqv.smarthome.Model.ModelDay;
import com.dqv.smarthome.Model.ScheduleModel;
import com.dqv.smarthome.Model.UrlModel;
import com.dqv.smarthome.Mqtt.MqttHelper;
import com.dqv.smarthome.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddScheduleTBActivity extends AppCompatActivity {
    public static final String TAG = AddScheduleTBActivity.class.getSimpleName();

    public String url_insertSchedule = UrlModel.url_insertSchedule;
    public String url_updateSchedule = UrlModel.url_updateSchedule;

    private Toolbar toolbar;
    String currentToken = "";
    public SQLiteHandler db;
    public ScheduleModel model = new ScheduleModel();
    TextView mSelectTB,mDayChe2,mDayChe3,mDayChe4,mDayChe5,mDayChe6,mDayChe7,mDayCheC;
    List<ModelDay> lstDay = new ArrayList<>();
    Button btnAddChe,btnCancelSche;
    TimePicker timePicker;
    Switch swStatusEquip;
    EditText etnameSche;
    boolean isAdd = true;
    View.OnClickListener btnClickListner = new View.OnClickListener() {
        @TargetApi(Build.VERSION_CODES.M)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.twAddSche2:
                    lstDay.get(0).setStatus(!lstDay.get(0).isStatus());
                    if(lstDay.get(0).isStatus()){
                        mDayChe2.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else{
                        mDayChe2.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    break;
                case R.id.twAddSche3:
                    lstDay.get(1).setStatus(!lstDay.get(1).isStatus());
                    if(lstDay.get(1).isStatus()){
                        mDayChe3.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else{
                        mDayChe3.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    break;
                case R.id.twAddSche4:
                    lstDay.get(2).setStatus(!lstDay.get(2).isStatus());
                    if(lstDay.get(2).isStatus()){
                        mDayChe4.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else{
                        mDayChe4.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    break;
                case R.id.twAddSche5:
                    lstDay.get(3).setStatus(!lstDay.get(3).isStatus());
                    if(lstDay.get(3).isStatus()){
                        mDayChe5.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else{
                        mDayChe5.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    break;
                case R.id.twAddSche6:
                    lstDay.get(4).setStatus(!lstDay.get(4).isStatus());
                    if(lstDay.get(4).isStatus()){
                        mDayChe6.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else{
                        mDayChe6.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    break;
                case R.id.twAddSche7:
                    lstDay.get(5).setStatus(!lstDay.get(5).isStatus());
                    if(lstDay.get(5).isStatus()){
                        mDayChe7.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else{
                        mDayChe7.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    break;
                case R.id.twAddScheC:
                    lstDay.get(6).setStatus(!lstDay.get(6).isStatus());
                    if(lstDay.get(6).isStatus()){
                        mDayCheC.setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                    else{
                        mDayCheC.setTextColor(getResources().getColor(R.color.text_color));
                    }
                    break;
                case R.id.btnAddCheTB:
                    String day = "";
                    for(int i =0;i<lstDay.size();i++){
                        if(lstDay.get(i).isStatus()){
                            day+=lstDay.get(i).getDay()+",";
                        }
                    }
                    model.setDay(day);
                    model.setTimeStart(timePicker.getHour()+":"+timePicker.getMinute());
                    model.setName(etnameSche.getText().toString().trim());
                    if(swStatusEquip.isChecked()){
                        model.setStatusEquipment(1);
                    }
                    else{
                        model.setStatusEquipment(0);
                    }
                    model.setStatus(1);
                    if(model.getEquipmentID() <=0){
                        Toast.makeText(getApplicationContext(),"Hãy chọn thiết bị",Toast.LENGTH_LONG).show();
                    }
                    else{
                        InsertEquipApi(model);
                    }
                    break;
                case R.id.btnCancelCheTB:
                    onBackPressed();
                    break;
                default:
                    break;
            }
        }
    };
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule_tb);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        toolbar = (Toolbar) findViewById(R.id.toolbarAddScheduleTB);
        db = new SQLiteHandler(getApplicationContext());
        lstDay.add(new ModelDay("2",false));
        lstDay.add(new ModelDay("3",false));
        lstDay.add(new ModelDay("4",false));
        lstDay.add(new ModelDay("5",false));
        lstDay.add(new ModelDay("6",false));
        lstDay.add(new ModelDay("7",false));
        lstDay.add(new ModelDay("C",false));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView tw = toolbar.findViewById(R.id.app_bar_title) ;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ImageView imageView = findViewById(R.id.app_bar_home);
        imageView.setVisibility(View.GONE);
        timePicker=(TimePicker)findViewById(R.id.alarmTimePicker);
        timePicker.setIs24HourView(true);
        currentToken = MyApplication.getInstance().getPrefManager().getUser().getToken();
        mDayChe2 = (TextView)findViewById(R.id.twAddSche2);
        mDayChe3 = (TextView)findViewById(R.id.twAddSche3);
        mDayChe4 = (TextView)findViewById(R.id.twAddSche4);
        mDayChe5 = (TextView)findViewById(R.id.twAddSche5);
        mDayChe6 = (TextView)findViewById(R.id.twAddSche6);
        mDayChe7 = (TextView)findViewById(R.id.twAddSche7);
        mDayCheC = (TextView)findViewById(R.id.twAddScheC);
        btnAddChe = (Button) findViewById(R.id.btnAddCheTB);
        btnCancelSche = (Button) findViewById(R.id.btnCancelCheTB);
        etnameSche = (EditText) findViewById(R.id.nameScheTB);
        swStatusEquip = (Switch) findViewById(R.id.swStatusEquip);
        mSelectTB = (TextView) findViewById(R.id.twSelectTB);

        mDayChe2.setOnClickListener(btnClickListner);
        mDayChe3.setOnClickListener(btnClickListner);
        mDayChe4.setOnClickListener(btnClickListner);
        mDayChe5.setOnClickListener(btnClickListner);
        mDayChe6.setOnClickListener(btnClickListner);
        mDayChe7.setOnClickListener(btnClickListner);
        mDayCheC.setOnClickListener(btnClickListner);
        btnAddChe.setOnClickListener(btnClickListner);
        btnCancelSche.setOnClickListener(btnClickListner);
        if(b!=null){
            isAdd = false;
            model.setId(b.getInt("id"));
            model.setName(b.getString("name"));
            model.setTimeStart(b.getString("timeStart"));
            model.setEquipmentID(b.getInt("equipmentID"));
            model.setStatus(b.getInt("status"));
            model.setStatusEquipment(b.getInt("statusEquipment"));
            model.setDay(b.getString("day"));
            Log.d(TAG, "onCreate model.getStatusEquipment() : "+model.getStatusEquipment() );
            etnameSche.setText(model.getName());
            swStatusEquip.setChecked(model.getStatusEquipment() == 1);
            String[] time = model.getTimeStart().split(":");
            timePicker.setHour(Integer.parseInt(time[0]));
            timePicker.setMinute(Integer.parseInt(time[1]));
            mSelectTB.setText(model.getEquipmentID()+"");
            for(int i =0;i<lstDay.size();i++){
                if(model.getDay().indexOf(lstDay.get(i).getDay()) >=0){
                    lstDay.get(i).setStatus(true);
                }
                switch (i){
                    case 0:
                        if(lstDay.get(i).isStatus()){
                            mDayChe2.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            mDayChe2.setTextColor(getResources().getColor(R.color.text_color));
                        }
                        break;
                    case 1:
                        if(lstDay.get(i).isStatus()){
                            mDayChe3.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            mDayChe3.setTextColor(getResources().getColor(R.color.text_color));
                        }
                        break;
                    case 2:
                        if(lstDay.get(i).isStatus()){
                            mDayChe4.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            mDayChe4.setTextColor(getResources().getColor(R.color.text_color));
                        }
                        break;
                    case 3:
                        if(lstDay.get(i).isStatus()){
                            mDayChe5.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            mDayChe5.setTextColor(getResources().getColor(R.color.text_color));
                        }
                        break;
                    case 4:
                        if(lstDay.get(i).isStatus()){
                            mDayChe6.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            mDayChe6.setTextColor(getResources().getColor(R.color.text_color));
                        }
                        break;
                    case 5:
                        if(lstDay.get(i).isStatus()){
                            mDayChe7.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            mDayChe7.setTextColor(getResources().getColor(R.color.text_color));
                        }
                        break;
                    case 6:
                        if(lstDay.get(i).isStatus()){
                            mDayCheC.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                        else{
                            mDayCheC.setTextColor(getResources().getColor(R.color.text_color));
                        }
                        break;
                }
            }
        }


        // slect TB;
        mSelectTB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SelectEquipActivity.class);
                startActivityForResult(intent,1);
            }
        });
        Log.d(TAG, "onCreate isAdd: " + isAdd);
        if(isAdd){
            tw.setText("Thêm lịch thiết bị");
        }
        else {
            tw.setText("Sửa lịch thiết bị");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras();
                    if(b!=null){
                        model.setEquipmentID(b.getInt("equipmentID"));
                        mSelectTB.setText(b.getString("equipmentName"));
                    }
                    Log.d(TAG, "onActivityResult: "+data.getStringExtra("a"));
                    // your stuff
                }
                break;
            }
        }

    }


    public void InsertEquipApi(final ScheduleModel model){
        JSONObject postparams = new JSONObject();
        String url  = "";
        if(isAdd){
            url = url_insertSchedule;
        }
        else {
            url = url_updateSchedule;
        }
        try
        {
            if(!isAdd){
                postparams.put("id", model.getId());
            }
            postparams.put("name", model.getName());
            postparams.put("timeStart", model.getTimeStart()+":00");
            postparams.put("equipmentID", model.getEquipmentID()+"");
            postparams.put("day", model.getDay());
            postparams.put("status", model.getStatus()+"");
            postparams.put("statusEquipment", model.getStatusEquipment()+"");
        }
        catch (Exception ex){

        }

        JsonObjectRequest request = new JsonObjectRequest (Request.Method.POST, url,postparams, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse() returned: " + response);
                try {
                    String msg = response.getString("message");
                    if(!response.getBoolean("error")){
                        Intent intent = new Intent(getApplicationContext(), ScheduleTBActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),
                                msg+"", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(getApplicationContext(),
                            ex+"", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse() returned: " + ex);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
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
        };
        MyApplication.getInstance().addToRequestQueue(request,TAG);
    }
}
