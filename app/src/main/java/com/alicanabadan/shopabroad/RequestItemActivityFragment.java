package com.alicanabadan.shopabroad;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.ItemsDO;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class RequestItemActivityFragment extends Fragment {

    private static final String LOG_TAG = RequestItemActivityFragment.class.getSimpleName();

    private DatabaseHandler db;
    private EditText itemName;
    private AutoCompleteTextView fromSelection;
    private AutoCompleteTextView toSelection;
    private EditText itemPrice;
    private EditText itemDescription;
    private Button addItem;
    private ImageView itemImage;
    private static int PICK_IMAGE_REQUEST = 1;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;


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

        itemImage = (ImageView) getActivity().findViewById(R.id.itemImage);
        itemImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                loadImagefromGallery(view);
            }
        });
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
                if(!allFieldsFilled()){
                    return;
                }
                saveItemRequest();
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                alertDialogBuilder.setTitle("Order Request Status");
                alertDialogBuilder
                        .setMessage(itemName.getText().toString() + " added successfully")
                        .setCancelable(false)
                        .setNegativeButton("Ok",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                                startActivity(new Intent(getActivity(), DashboardActivity.class));
                            }
                        });
                //create the dialog and show
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
    }

    /*public void saveItemRequest(){
            OrderItem orderItem = new OrderItem();
            orderItem.setName(itemName.getText().toString());
            orderItem.setFromPort(fromSelection.getText().toString());
            orderItem.setToPort(toSelection.getText().toString());
            orderItem.setPrice(Double.parseDouble(itemPrice.getText().toString()));
            orderItem.setDescription(itemDescription.getText().toString());
            orderItem.setImage(Utils.getImageBytes(((BitmapDrawable)itemImage.getDrawable()).getBitmap()));
            db.addItem(orderItem);
    }*/

    public boolean allFieldsFilled(){
        if(itemName.getText().toString().trim().equals("") || fromSelection.getText().toString().trim().equals("") ||
                toSelection.getText().toString().trim().equals("") || itemPrice.getText().toString().trim().equals("") ||
                itemDescription.getText().toString().trim().equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void loadImagefromGallery(View view) {
        int hasReadStoragePermission = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                //ActivityCompat.sh("Permission Needed", "Rationale", Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        , REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                ImageView imageView = (ImageView) getActivity().findViewById(R.id.itemImage);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "Image selection Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void saveItemRequest() {
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper dynamoDBMapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
        final ItemsDO itemsDO = new ItemsDO(); // Initialize the Notes Object

        itemsDO.setId(1.0);
        itemsDO.setUserCreated("alican");
        itemsDO.setCreateDate("");
        itemsDO.setName(itemName.getText().toString());
        itemsDO.setFromPort(fromSelection.getText().toString());
        itemsDO.setToPort(toSelection.getText().toString());
        itemsDO.setPrice(Double.parseDouble(itemPrice.getText().toString()));
        itemsDO.setDescription(itemDescription.getText().toString());
        itemsDO.setUserCreated("");
        itemsDO.setGrantDate("");
        itemsDO.setIsGranted(false);
        //itemsDO.setImage(Utils.getImageBytes(((BitmapDrawable)itemImage.getDrawable()).getBitmap()));
        AmazonClientException lastException = null;

        try {
            dynamoDBMapper.save(itemsDO);
        } catch (final AmazonClientException ex) {
            Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
            lastException = ex;
        }
    }
}
