package com.alicanabadan.shopabroad;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class RequestItemActivityFragment extends Fragment {

    private DatabaseHandler db;
    private EditText itemName;
    private AutoCompleteTextView fromSelection;
    private AutoCompleteTextView toSelection;
    private EditText itemPrice;
    private EditText itemDescription;
    private Button addItem;

    public RequestItemActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_item, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        itemName = (EditText) getActivity().findViewById(R.id.item_name);
        fromSelection = (AutoCompleteTextView) getActivity().findViewById(R.id.fromAutoComplete);
        toSelection = (AutoCompleteTextView) getActivity().findViewById(R.id.toAutoComplete);
        itemPrice = (EditText) getActivity().findViewById(R.id.item_price);
        itemDescription = (EditText) getActivity().findViewById(R.id.item_description);
        addItem = (Button) getActivity().findViewById(R.id.add_item);

        db = new DatabaseHandler(getActivity());
        String[] countries = db.getCityNames().toArray(new String[0]);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, countries);
        fromSelection.setAdapter(adapter);
        toSelection.setAdapter(adapter);

        addItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                saveItemRequest();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Order Request Status");
                alertDialogBuilder
                        .setMessage(itemName.getText().toString() + "added successfully")
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

            
        });
    }

    public void saveItemRequest(){
        if(allFieldsFilled()){
            OrderItem orderItem = new OrderItem();
            orderItem.setName(itemName.getText().toString());
            orderItem.setFromPort(fromSelection.getText().toString());
            orderItem.setToPort(toSelection.getText().toString());
            orderItem.setPrice(Double.parseDouble(itemPrice.getText().toString()));
            orderItem.setDescription(itemDescription.getText().toString());

            db.addItem(orderItem);
        }
    }

    public boolean allFieldsFilled(){
        if(itemName.getText().toString().trim().equals("") || fromSelection.getText().toString().trim().equals("") ||
                toSelection.getText().toString().trim().equals("") || itemPrice.getText().toString().trim().equals("") ||
                itemDescription.getText().toString().trim().equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
