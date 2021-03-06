package kale.commonadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kale.adapter.AdapterItem;
import kale.adapter.abs.CommonAdapter;
import kale.adapter.recycler.CommonRcvAdapter;
import kale.commonadapter.item.ButtonItem;
import kale.commonadapter.item.ImageItem;
import kale.commonadapter.item.TextItem;
import kale.commonadapter.model.TestModel;


public class MainActivity extends ActionBarActivity {

    private final String TAG = getClass().getSimpleName();

    private ListView listView;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<TestModel> data = loadData();
        getSupportActionBar().setTitle("ListView的效果");

        listView = (ListView) findViewById(R.id.listView);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        addDataToListView(data);
        addDataToRecyclerView(data);

        Button showListViewBtn = (Button) findViewById(R.id.showListView_button);

        showListViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("ListView的效果");
                listView.setVisibility(View.VISIBLE);
                ((CommonAdapter<TestModel>) listView.getAdapter()).updateData(loadData());
                recyclerView.setVisibility(View.GONE);
            }
        });

        Button showRecyclerViewBtn = (Button) findViewById(R.id.showRecyclerView_button);
        showRecyclerViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setTitle("RecyclerView的效果");
                recyclerView.setVisibility(View.VISIBLE);
               // ((CommonRcvAdapter<TestModel>) recyclerView.getAdapter()).updateData(loadData());
                listView.setVisibility(View.GONE);
            }
        });
    }
    
    private void addDataToListView(List<TestModel> data) {
        listView.setAdapter(new CommonAdapter<TestModel>(data) {

            @NonNull
            @Override
            protected AdapterItem<TestModel> initItemView(Object type) {
                return initItem(type);
            }
        });
    }
    
    private void addDataToRecyclerView(List<TestModel> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new CommonRcvAdapter<TestModel>(data) {

            @NonNull
            @Override
            protected AdapterItem<TestModel> initItemView(Object type) {
                Log.d(TAG, "type = " + type);
                
                return initItem(type);
            }
        });
    }

    private AdapterItem<TestModel> initItem(Object type) {
        switch ((String)type) {
            case "text":
                return new TextItem();
            case "button":
                return new ButtonItem();
            case "image":
                return new ImageItem();
            default:
                return new TextItem();
        }
    }


    /**
     * 模拟加载数据的操作
     */
    private List<TestModel> loadData() {
        List<TestModel> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int type = (int) (Math.random() * 3);
            //Log.d(TAG, "type = " + type);
            TestModel model = new TestModel();
            switch (type) {
                case 0:
                    model.type = "text";
                    model.content = "第一种布局";
                    break;
                case 1:
                    model.type = "button";
                    model.content = "第二种布局";
                    break;
                case 2:
                    model.type = "image";
                    model.content = String.valueOf(R.drawable.kale);
                    break;
                default:
            }
            list.add(model);
        }
        return list;
    }
}
