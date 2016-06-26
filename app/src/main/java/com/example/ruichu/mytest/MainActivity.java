package com.example.ruichu.mytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void toJsonString() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("msg", "请输入信息");
            jsonObject.put("sendTemplate", "我想订%s,%s,%s");
            jsonObject.put("handleType", 1);
            jsonObject.put("handleDeliverContent", "1");

            //这里是第一项,依次放入第二项
            JSONArray jsonArray = new JSONArray();
            JSONObject firstJsonObject=ItemtoJsonObject();
            jsonArray.put(firstJsonObject);

            jsonObject.put("list", jsonArray);
            String mstring=jsonObject.toString();
            Log.e("++++++++++", mstring);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public JSONObject ItemtoJsonObject(){
        JSONObject JsonObject = new JSONObject();
        try {
            JsonObject.put("title", "日期");
            JSONArray selectItem = new JSONArray();
            selectItem.put("今天");
            selectItem.put("明天");
            JsonObject.put("selectItem", selectItem);
            //   根据传入的参数判断是否有以下节点
            JSONObject relativeInfo = new JSONObject();
            relativeInfo.put("title", "时间");
            JSONArray relativeItems = new JSONArray();
            JSONArray relativeItems1 = new JSONArray();
            JSONArray relativeItems2 = new JSONArray();
            relativeItems1.put("12");
            relativeItems1.put("13");
            relativeItems2.put("00");
            relativeItems2.put("01");
            relativeItems.put(relativeItems1);
            relativeItems.put(relativeItems2);
            relativeInfo.put("relativeItems", relativeItems);
            JsonObject.put("relativeinfo", relativeInfo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return JsonObject;
    }
}
