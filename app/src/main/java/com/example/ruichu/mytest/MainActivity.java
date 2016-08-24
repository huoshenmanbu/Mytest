package com.example.ruichu.mytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/** txt{
    "msg": "请你输入时间人数",
		"sendTemplate":"我想订%s,%s,%s", //发送时的文案模版，客户端按照顺序将每一项%s替换掉
		//下面两个字段，不需要的不用关注
		"handleType",1, //处理类型，可省。默认没有处理逻辑。1、表示当前的快捷输入不发送消息，而通过调用QuickInputToCall接口传递数据；2、表示当前的快捷输入发送消息，且通过调用QuickInputToCall接口传递数据
		"handleDeliverContent":"xxxx",handleType为1或者2时，将此字段传递给QuickInputToCall接口

    "list": [
        {   //这里是第一项
            "title": "日期",
            "selectItems": [
                "今天",
                "明天"
            ],
						//有关联关系时，有下面这个节点，没有关联关系时，没有这个节点
            "relativeInfo": {
                "title": "时间",
                "relativeItems": [
                    [
                        "12:00",
                        "13:00"
                    ],
                    [
                        "00:00",
                        "01:00"
                    ]
                ]
            }
        },
				//这里是另外一项
        {
            "title": "人数",
            "selectItems": [
                "1人",
                "2人"
            ]
        }
    ]
	}*/
public class MainActivity extends AppCompatActivity {
//   解析的变量
    private static final String KEY_MSG = "msg";
    private static final String KEY_SEND_TEMP = "sendTemplate";
    private static final String KEY_LIST = "list";

    private static final String KEY_TITLE = "title";
    private static final String KEY_RELATIVE_INFO = "relative_info";
    private static final String KEY_SELECT_ITEMS = "selectItems";
    private static final String KEY_RELATIVE_ITEMS = "relativeItems";

    private RelativeLayout mRelativeLayout;
    private Button mButton;
    private Button testButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    public void init(){
        mEditText=(EditText)findViewById(R.id.test_edittext);
    }
    private void fromJson(JSONObject jsonObject) {
        String msg = jsonObject.optString(KEY_MSG);
        String sendTemp = jsonObject.optString(KEY_SEND_TEMP);

        JSONArray listArray = jsonObject.optJSONArray(KEY_LIST);
        int n = listArray.length();
        for (int i = 0; i < n; i++) {
            parseListItem(listArray.optJSONObject(i));
        }
    }

    private void parseListItem(JSONObject jsonObject) {
        String title = jsonObject.optString(KEY_TITLE);

        JSONArray jsonArray = jsonObject.optJSONArray(KEY_SELECT_ITEMS);
        String item1 = jsonArray.optString(0);
        String item2 = jsonArray.optString(1);


        JSONObject obj = jsonObject.optJSONObject(KEY_RELATIVE_INFO);
        if (obj != null) {
            // 有关联关系的情况
            String title_rel = obj.optString(KEY_TITLE);

            JSONArray relativeItemsObj = obj.optJSONArray(KEY_RELATIVE_ITEMS);

            JSONArray r1 = relativeItemsObj.optJSONArray(0);
            String a = r1.optString(0);//"12:00"
            String b = r1.optString(1);//"13:00"

            JSONArray r2 = relativeItemsObj.optJSONArray(1);
            String c = r2.optString(0);//00:00
            String d = r2.optString(1);//01:00
        }

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
