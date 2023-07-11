package com.example.currencyconverter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.example.currencyconverter.Retrofit.RetrofitBuilder;
import com.example.currencyconverter.Retrofit.RetrofitInterface;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;

    Button placeOrder;
    Button convertButton;
    Button pastOrders;
    EditText orderAmount;
    EditText amountInINR;
    Spinner selectCurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        if(auth.getCurrentUser()==null){
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
        }

        placeOrder=findViewById(R.id.placeOrder);
        convertButton=findViewById(R.id.convertbutton);
        orderAmount=findViewById(R.id.etAmount);
        amountInINR=findViewById(R.id.resultInINR);
        selectCurrency=findViewById(R.id.chooseCountry);
        pastOrders=findViewById(R.id.yourOrders);

        String[] dropDownList = {"USD", "INR","EUR","NZD"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropDownList);
       selectCurrency.setAdapter(adapter);

       convertButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               RetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(RetrofitInterface.class);
               Call<JsonObject> call = retrofitInterface.getExchangeCurrency(selectCurrency.getSelectedItem().toString());
               call.enqueue(new Callback<JsonObject>() {
                   @Override
                   public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                       JsonObject res = response.body();
                       JsonObject rates = res.getAsJsonObject("rates");
                       double currency = Double.valueOf(orderAmount.getText().toString());
                       double multiplier = Double.valueOf(rates.get("INR").toString());
                       double result = currency * multiplier;
                       amountInINR.setText(String.valueOf(result));
                   }

                   @Override
                   public void onFailure(Call<JsonObject> call, Throwable t) {

                   }
               });
           }
       });

       placeOrder.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String currencyType = selectCurrency.getSelectedItem().toString();
               double amountOrdered = Double.valueOf(orderAmount.getText().toString());
               double amountPaid = Double.valueOf(amountInINR.getText().toString());


               System.out.println("AMount ordered : "+amountOrdered);
               String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
               DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
               DatabaseReference orderRef = rootRef.child("Users' Order list").child(uid).child("Orders");

               OrderInfo orderInfo = new OrderInfo(currencyType,amountOrdered,amountPaid);

//               orderRef.addValueEventListener(new ValueEventListener() {
//                   @Override
//                   public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                       OrderInfo order= snapshot.getValue(OrderInfo.class);
//
//                       if(order == null){
//                           // Create list
//                           order = new OrderInfo();
//                       }
//                       // Add new user
//                       order.add(orderInfo);
//                       orderRef.getRef().setValue(order);
//                   }
//
//                   @Override
//                   public void onCancelled(@NonNull DatabaseError error) {
//
//                   }
//               });
               orderRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
               orderRef.push(). setValue(orderInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(HomeActivity.this, "Order successful", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
//               orderRef.setValue(orderInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
//                   @Override
//                   public void onComplete(@NonNull Task<Void> task) {
//                       if (task.isSuccessful()) {
//                           Toast.makeText(HomeActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
//                       } else {
//                           Toast.makeText(HomeActivity.this, "Error in creating user", Toast.LENGTH_SHORT).show();
//                       }
//                   }
//               });
           }
       });

        pastOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,PastOrdersActivity.class));
            }
        });



    }
}