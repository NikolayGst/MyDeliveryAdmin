package ru.mydelivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import ru.mydelivery.Adapter.MyAdapter;
import ru.mydelivery.Pojo.Order;
import ru.mydelivery.Provider.VolleyRequest;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MyAdapter adapter;
    private  List<Order> orderList;
    private VolleyRequest volleyRequest;
    private  RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Context context = MainActivity.this;
    com.getbase.floatingactionbutton.FloatingActionButton fab_refresh;
    com.getbase.floatingactionbutton.FloatingActionButton fab_add;
    com.getbase.floatingactionbutton.FloatingActionsMenu fab_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        volleyRequest = new VolleyRequest();
        orderList = new ArrayList<>();
        adapter = new MyAdapter(context, orderList);
        recyclerView.setAdapter(adapter);
        volleyRequest.getOrder(context, orderList, adapter);
        fab_refresh = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.refresh);
        fab_add = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.add);
        fab_menu = (FloatingActionsMenu) findViewById(R.id.menu_floating);
        fab_refresh.setOnClickListener(this);
        fab_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                fab_menu.collapse();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh:
                fab_menu.collapse();
                volleyRequest.getOrder(context, orderList,adapter);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
