package com.alicanabadan.shopabroad;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GrantListFragment extends Fragment implements OrdersRecycleViewAdapter.OnAdapterItemInteraction {

    private static final String TAG = "GrantListActFrag";
    private OrdersRecycleViewAdapter grantsRecycleViewAdapter = null;

    public GrantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grant_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayInfo();
    }

    public void displayInfo() {
        List<OrderItem> grantData = retrieveGrantData();
        grantsRecycleViewAdapter = new OrdersRecycleViewAdapter(grantData, this);
        grantsRecycleViewAdapter.notifyDataSetChanged();
        RecyclerView recyclerView= (RecyclerView)getActivity().findViewById(R.id.grantRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(grantsRecycleViewAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

    }

    public List<OrderItem> retrieveGrantData(){
        DatabaseHandler db = new DatabaseHandler(getActivity());
        return db.getOrders(true);
    }

    @Override
    public void onItemSelected(OrdersRecycleViewAdapter.OrderViewHolder holder,OrderItem orderItem) {
        showDetails(holder ,orderItem);
    }

    private void showDetails(OrdersRecycleViewAdapter.OrderViewHolder holder,OrderItem orderItem){
        Intent intent = new Intent(getActivity(), ItemDetails.class);
        intent.putExtra(OrderItem.ID, orderItem.getId());
        intent.putExtra(OrderItem.NAME, orderItem.getName());
        intent.putExtra(OrderItem.FROMPORT, orderItem.getFromPort());
        intent.putExtra(OrderItem.TOPORT, orderItem.getToPort());
        intent.putExtra(OrderItem.PRICE, orderItem.getPrice());
        intent.putExtra(OrderItem.DESCRIPTION, orderItem.getDescription());
        intent.putExtra(OrderItem.IMAGE, orderItem.getImage());
        intent.putExtra(OrderItem.IS_GRANTED, orderItem.getIsGranted());
        intent.putExtra(OrderItem.GRANTED_USER, orderItem.getGrantedUser());

        View imageView = holder.itemImageIcon;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),
                    imageView, imageView.getTransitionName());
            getActivity().startActivity(intent, options.toBundle());
        } else {
            getActivity().startActivity(intent);
        }
    }

}
