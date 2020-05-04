package com.example.piratica;

import android.R.layout;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

public class savedScans extends AppCompatActivity {
private TextView info;
private EditText delete;
private Button deleteAll;
private ListView lists;
ArrayList<storeScan> scans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_scans);
        deleteAll = findViewById(R.id.deleteButton);
        info = findViewById(R.id.information);
        delete = findViewById(R.id.deleteid);
        lists = findViewById(R.id.list);

        final ArrayList<storeScan> list = readArray();
        Log.e("Read: ", "Read scans");
        if(list!=null){
            storeScan[] storeScans = new storeScan[list.size()];//store arraylist as an array
            storeScans = list.toArray(storeScans);
            lists.setAdapter(new ArrayAdapter<storeScan>(savedScans.this, layout.simple_list_item_1, storeScans));//display the data in listview
        }
        else {
            info.setText("No saved scans");
        }

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = delete.getText().toString();
                storeScan scan = null;
                if(!id.isEmpty()){
                    if(list!=null){
                        Iterator<storeScan> iter = list.iterator();
                        while(iter.hasNext()){
                            Log.e("Size: ", String.valueOf(list.size()));
                            storeScan scanner = iter.next();
                            String uid = String.valueOf(scanner.getId());
                            if(id.equals(uid)){//If id input matches an id from list delete it
                                Log.e("Delete: ", String.valueOf(scanner.getId()));
                                iter.remove();
                                Log.e("Size: ", String.valueOf(list.size()));
                                saveArray(list);
                                break;
                            }else {}
                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"No Saved scans",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"A Input cannot be empty",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public ArrayList<storeScan> readArray(){//Read saved scans from shared preferences
        ArrayList<storeScan> list = null;
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("share preferences", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("scans",null);
            Type type = new TypeToken<ArrayList<storeScan>>(){}.getType();
            list = gson.fromJson(json, type);

        }catch (Exception e){Log.e("Readarray: ", "Unable to read");}
        return list;
    }

    public void saveArray(ArrayList<storeScan> list){//save scan in shared preferences after being updated
        try{
            SharedPreferences sharedPreferences = getSharedPreferences("share preferences", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(list);
            editor.putString("scans", json);
            editor.apply();
            Toast.makeText(getApplicationContext(),"Scan Deleted, Refresh page",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Unable to Delete",Toast.LENGTH_SHORT).show();
        }

    }


}
