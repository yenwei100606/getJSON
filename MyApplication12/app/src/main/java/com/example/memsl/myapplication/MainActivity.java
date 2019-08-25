package com.example.memsl.myapplication;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private Context context;
    private Spinner sp,sp2;

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;

    List<String> country_list = new ArrayList<String>();
    //List<String> list = new ArrayList<String>();
    List<List<String>> total_list = new ArrayList<List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        text = (TextView) findViewById(R.id.test);
        //sp=(Spinner)findViewById(R.id.country);
        //sp2=(Spinner)findViewById(R.id.country2);
        String data = getjson("taiwan_city.json");





        try {
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++)
            {
                List<String> list = new ArrayList<String>();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String country = jsonObject.getString("name");
                country_list.add(country);
                Log.d("result","縣市"+country_list);
                JSONArray array = jsonObject.getJSONArray("districts");
                for(int j=0;j<array.length();j++)
                {

                    JSONObject obj = array.getJSONObject(j);
                    String city = obj.getString("name");
                    list.add(city);
                    //Log.d("fuck","鄉鎮"+list);
                }
                Log.d("fuck","鄉鎮"+list);
                total_list.add(list);
                //Log.d("fuck","鄉鎮總數1"+total_list);

            }

            Log.d("fuck","鄉鎮總數2"+total_list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, country_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp = (Spinner) findViewById(R.id.country);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(selectListener);

        //因為下拉選單第一個為茶類，所以先載入茶類群組進第二個下拉選單
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, total_list.get(0));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2 = (Spinner) findViewById(R.id.country2);
        sp2.setAdapter(adapter2);


    }
    private String[][] type2 = new String[][]{{},{"中正區"},{"大同區","中山區","中山區","松山區","大安區","萬華區","信義區","士林區","北投區","內湖區","南港區","文山區"},{""},{},{},{},{}};
    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position,long id){
            //讀取第一個下拉選單是選擇第幾個
            int pos = sp.getSelectedItemPosition();
            //重新產生新的Adapter，用的是二維陣列type2[pos]
            adapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, total_list.get(pos));
            //載入第二個下拉選單Spinner
            sp2.setAdapter(adapter2);

        }

        public void onNothingSelected(AdapterView<?> arg0){

        }

    };

    public  String getjson(String filename)
    {
        StringBuilder stringBuilder = new StringBuilder();
        try{
            AssetManager assetManager = getAssets();
            InputStream inputStream = null;

            inputStream = assetManager.open(filename);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    /*public ArrayList getCountry(String data)
    {
        ArrayList list = new ArrayList();
        String country="";
        try {
            //JSONObject jsonObject = new JSONObject(data);
            //country = jsonObject.getString("name");
            JSONArray array = new JSONArray(data);
            for(int i=0;i<array.length();i++)
            {
                JSONObject jsonObject =array.getJSONObject(i);
                country = jsonObject.getString("name");
                list.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }*/


}
