package com.alicanabadan.shopabroad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        void onItemSelected(OrderItem orderItem);
    }

    public OrdersRecycleViewAdapter(List<OrderItem> items, OnAdapterItemInteraction listener) {
        orderList = items;
        mListener = listener;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        public TextView orderDetails;

        public OrderViewHolder(View view) {
            super(view);
            orderDetails = (TextView) view.findViewById(R.id.orderDetails);
        }
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_row, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        final OrderItem orderItem = orderList.get(position);
        holder.orderDetails.setText(orderItem.getName()+"\t"+"\t" + orderItem.getPrice()+"\t"+"\t" + orderItem.getDescription());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemSelected(orderItem);
                }
            }
        };
        holder.orderDetails.setOnClickListener(listener);

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
