package com.alicanabadan.shopabroad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alican on 3/19/2017.
 */

public class OrdersRecycleViewAdapter extends RecyclerView.Adapter<OrdersRecycleViewAdapter.OrderViewHolder> {

    private List<OrderItem> orderList = new ArrayList<OrderItem>();
    private final OnAdapterItemInteraction mListener;

    public interface OnAdapterItemInteraction {
        void onItemSelected(OrderViewHolder holder,OrderItem orderItem);
    }

    public OrdersRecycleViewAdapter(List<OrderItem> items, OnAdapterItemInteraction listener) {
        orderList = items;
        mListener = listener;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImageIcon;
        public TextView orderDetails;

        public OrderViewHolder(View view) {
            super(view);
            itemImageIcon = (ImageView) view.findViewById(R.id.itemImageIcon);
            orderDetails = (TextView) view.findViewById(R.id.orderDetails);
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_cardview, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, int position) {
        final OrderItem orderItem = orderList.get(position);
        holder.itemImageIcon.setImageBitmap(Utils.getImage(orderItem.getImage()));
        holder.orderDetails.setText("Name:"+"\t" + orderItem.getName()+"\n" + "Trip:"+"\t" + orderItem.getFromPort()+"\t"+"\t"+"->" +orderItem.getToPort()+"\n" +
                "Price:"+"\t" + orderItem.getPrice());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemSelected(holder, orderItem);
                }
            }
        };

        holder.orderDetails.setOnClickListener(listener);
        holder.itemImageIcon.setOnClickListener(listener);

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(OrderItem orderItem);
    }

    private OnItemClickListener onItemClickListener;
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
