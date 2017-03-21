package com.alicanabadan.shopabroad;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderListFragment extends Fragment implements OrdersRecycleViewAdapter.OnAdapterItemInteraction{

    private static final String TAG = "OrderListActFrag";
    private OrdersRecycleViewAdapter ordersRecycleViewAdapter = null;

    public OrderListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayInfo();
    }

    public void displayInfo() {
        List<OrderItem> orderData = retrieveOrderData();
        ordersRecycleViewAdapter = new OrdersRecycleViewAdapter(orderData, this);
        ordersRecycleViewAdapter.notifyDataSetChanged();
        RecyclerView recyclerView= (RecyclerView)getActivity().findViewById(R.id.orderRecyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(ordersRecycleViewAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        /*quakeRecyclerViewAdapter.setOnItemClickListener(new QuakeRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Quake quake) {
                //onclick show details
                Location loc = new Location("quakeGPS");
                loc.setLatitude(Double.parseDouble(quake.getLatitude()));
                loc.setLongitude(Double.parseDouble(quake.getLongitude()));
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Earthquake Details");
                alertDialogBuilder
                        .setMessage(loc.toString()+"\t"+"\t"+quake.getLink())
                        .setCancelable(false)
                        .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });
                //create the dialog and show
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });*/
    }

    @Override
    public void onItemSelected(OrderItem orderItem) {

    }

    public List<OrderItem> retrieveOrderData(){
        DatabaseHandler db = new DatabaseHandler(getActivity());
        return db.getOrders();
    }
}
