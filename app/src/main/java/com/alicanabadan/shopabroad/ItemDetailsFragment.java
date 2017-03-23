package com.alicanabadan.shopabroad;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemDetailsFragment extends Fragment {

    public ItemDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView tvName = (TextView) getActivity().findViewById(R.id.item_name);
        TextView tvFromPort = (TextView) getActivity().findViewById(R.id.from_port);
        TextView tvToPort = (TextView) getActivity().findViewById(R.id.to_port);
        TextView tvPrice = (TextView) getActivity().findViewById(R.id.item_price);
        TextView tvDescription = (TextView) getActivity().findViewById(R.id.item_description);
        ImageView ivImage = (ImageView) getActivity().findViewById(R.id.itemImage);

        Intent intent = getActivity().getIntent();
        final Bundle bundle = intent.getExtras();
        String isGranted = "";
        if (bundle != null){
            String name = (String) bundle.get(OrderItem.NAME);
            tvName.setText(name);
            String fromPort = (String) bundle.get(OrderItem.FROMPORT);
            tvFromPort.setText(fromPort);
            String toPort = (String) bundle.get(OrderItem.TOPORT);
            tvToPort.setText(toPort);
            Double price = bundle.getDouble(OrderItem.PRICE);
            tvPrice.setText(price+"");
            String description = (String) bundle.get(OrderItem.DESCRIPTION);
            tvDescription.setText(description);
            ivImage.setImageBitmap(Utils.getImage(bundle.getByteArray(OrderItem.IMAGE)));
            isGranted = (String) bundle.get(OrderItem.IS_GRANTED);
        }

        Button b = (Button) getActivity().findViewById(R.id.btnAcceptRequest);
        if("T".equals(isGranted)){
            b.setVisibility(View.GONE);
        }
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().supportFinishAfterTransition();
                DatabaseHandler db = new DatabaseHandler(getActivity());
                db.grantItem(bundle.getInt(OrderItem.ID),"username1");
                Intent i = new Intent(getActivity(), DashboardActivity.class);
                i.putExtra("TabNumber", 2);
                startActivity(i);
            }
        });

    }
}
