package com.example.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class History_order_adapter extends RecyclerView.Adapter<History_order_adapter.viewholder> {

    ArrayList<OrderInfo> arraylist;
    Context context;

    public History_order_adapter(ArrayList<OrderInfo> arraylist, Context context) {
        this.arraylist = arraylist;
        this.context = context;
    }

    @NonNull
    @Override
    public History_order_adapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_order_sample, parent, false);
            return new viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull History_order_adapter.viewholder holder, int position) {

        OrderInfo orderInfo= arraylist.get(position);
        String amountOrdered= orderInfo.getCurrencyType()+String.valueOf(orderInfo.getAmountOrdered());
        holder.AmountPaid.setText(String.valueOf(orderInfo.getAmountPaid()));
        holder.Amount.setText(amountOrdered);
        holder.Date.setText("22/10/22");


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
            TextView Date, Location, Amount, AmountPaid;

            public viewholder(@NonNull View itemView) {
                super(itemView);
                Date=itemView.findViewById(R.id.date_history);
                Location=itemView.findViewById(R.id.Location);
                Amount=itemView.findViewById(R.id.amount_history);
                AmountPaid=itemView.findViewById(R.id.amountPaid);
            }
        }
}

//    public class notificationAdaptor extends RecyclerView.Adapter<notificationAdaptor.viewholder> {
//
//        ArrayList<NotificationModel> list;
//        Context context;
//        FirebaseDatabase firebaseDatabase;
//
//        public notificationAdaptor(ArrayList<NotificationModel> list, Context context) {
//            this.list = list;
//            this.context = context;
//            firebaseDatabase = FirebaseDatabase.getInstance();
//        }
//
//        @NonNull
//        @Override
//        public notificationAdaptor.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(context).inflate(R.layout.notification_layout, parent, false);
//            return new viewholder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull viewholder holder, int position) {
//            NotificationModel model = list.get(position);
//            holder.Event.setText(model.getEvent());
//            holder.Info.setText(model.getInfo());
//            holder.Date.setText(model.getDate());
//        }
//
//        @Override
//        public int getItemCount() {
//            return list.size();
//        }
//
//        public class viewholder extends RecyclerView.ViewHolder {
//            TextView Event, Date, Info;
//
//            public viewholder(@NonNull View itemView) {
//                super(itemView);
//                Event = itemView.findViewById(R.id.noti_event_name);
//                Date = itemView.findViewById(R.id.noti_date);
//                Info = itemView.findViewById(R.id.noti_info);
//            }
//        }
//    }
//
