package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class PastOrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_orders);

        ArrayList<OrderInfo> arrayList=new ArrayList<>();
        RecyclerView recyclerview=findViewById(R.id.recycler_history);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        OrderInfo orderinfo=new OrderInfo("USD",100,7756);
        arrayList.add(orderinfo);
        History_order_adapter adapter=new History_order_adapter(arrayList,this);
        recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}