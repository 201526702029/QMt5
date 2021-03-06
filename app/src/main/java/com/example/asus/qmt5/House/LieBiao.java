package com.example.asus.qmt5.House;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.asus.qmt5.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LieBiao extends AppCompatActivity {
    private ListView sousuokuang = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lie_biao);

        final ImageView ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
        final EditText etSearch = (EditText) findViewById(R.id.etSearch);
        ivDeleteText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivDeleteText.setVisibility(View.GONE);
                } else {
                    ivDeleteText.setVisibility(View.VISIBLE);
                }
            }
        });



        sousuokuang = (ListView) findViewById(R.id.sousuokuang);
        final int[] imageld = new int[]{
                R.drawable.img1121, R.drawable.img1122, R.drawable.img1133, R.drawable.img1144,
                R.drawable.img1155, R.drawable.img1166, R.drawable.img1177, R.drawable.img1121,
        };

        final String[] message = new String[]{
                "苹果青年酒店   南昌县天祥大道188号（奥体天虹商场后面200米，原苹果KTV）",
                "情侣巢情侣酒店   南昌县尤氨路长胜大市场旁（距地铁太子殿站4出口200米）",
                "致青春主题酒店    南昌县高新区紫阳大道988号奥体天虹商场背面15栋十三楼1345A室",
                "维也纳智好酒店    青云谱区解放西路426号（上海路与解放西路交叉口东南角）",
                "东区智慧酒店    南昌县紫阳大道988号（瑶湖天虹商场后，近奥体中心地铁站C出口",
                "南昌柠檬heart酒店公寓    南昌县紫阳大道新立方公寓七楼714房（维珍天使酒店楼上）",
                "尚客优连锁酒店    南昌县高新区紫阳大道985号小时代15栋（瑶湖天虹商场后，奥体中心4号出口）",
                "小时代酒店公寓    南昌县瑶湖紫阳大道988号华宸东区国际15栋11楼1142室（酷加天虹楼上）"};

        final String[] title1 = new String[]{"苹果青年酒店", "情侣巢情侣酒店", "致青春主题酒店",
                "维也纳智好酒店", "东区智慧酒店", "南昌柠檬heart酒店公寓", "尚客优连锁酒店", "小时代酒店公寓"};
        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("imageld", R.drawable.img1121);
        map1.put("title1", title1[0]);
        listItems.add(map1);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("imageld", R.drawable.img1122);
        map2.put("title1", title1[1]);
        listItems.add(map2);
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("imageld", R.drawable.img1133);
        map3.put("title1", title1[2]);
        listItems.add(map3);
        Map<String, Object> map4 = new HashMap<String, Object>();
        map4.put("imageld", R.drawable.img1144);
        map4.put("title1", title1[3]);
        listItems.add(map4);
        Map<String, Object> map5 = new HashMap<String, Object>();
        map5.put("imageld", R.drawable.img1155);
        map5.put("title1", title1[4]);
        listItems.add(map5);
        Map<String, Object> map6 = new HashMap<String, Object>();
        map6.put("imageld", R.drawable.img1166);
        map6.put("title1", title1[5]);
        listItems.add(map6);
        Map<String, Object> map7 = new HashMap<String, Object>();
        map7.put("imageld", R.drawable.img1177);
        map7.put("title1", title1[6]);
        listItems.add(map7);
        Map<String, Object> map8 = new HashMap<String, Object>();
        map8.put("imageld", R.drawable.img1188);
        map8.put("title1", title1[7]);
        listItems.add(map8);
        sousuokuang.setAdapter(new SimpleAdapter(this, listItems, R.layout.activity_items,
                new String[]{"imageld", "title1"}, new int[]{R.id.jiudianttu, R.id.jiudianming}));
        sousuokuang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Bundle bundle = new Bundle();
                bundle.putInt("imageld", imageld[arg2]);
                bundle.putString("message", message[arg2]);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(LieBiao.this, JiuDian.class);
                Log.i("message", message[arg2]);
                startActivity(intent);
            }
        });

    }
}
