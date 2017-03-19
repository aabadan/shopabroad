package com.alicanabadan.shopabroad;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class SelectionBoardActivityFragment extends Fragment implements VolleyClassString.OnInfoListener<String> {

    private final static String TAG = "CityInfo_HttpURLConn";
    String queryCityInfoBase = "https://iatacodes.org/api/v6/airports?api_key=3509a153-87b5-4aa0-b84b-118281316220";
    private final static String formatKey = "format";
    private final static String formatType = "json";
    private VolleyClassString volleyClassString;
    private List<City> cities;
    private DatabaseHandler db;

    public SelectionBoardActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selection_board, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button requestOrder = (Button) getActivity().findViewById(R.id.requestOrder);
        requestOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RequestItemActivity.class));
            }
        });

        Button enterTravel = (Button) getActivity().findViewById(R.id.enterTravel);
        enterTravel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectionBoardActivity.class));
            }
        });

        Button searchTravel = (Button) getActivity().findViewById(R.id.searchTravel);
        enterTravel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectionBoardActivity.class));
            }
        });

        fillCitites();
    }

    private void fillCitites() {
        db = new DatabaseHandler(getActivity());
        if(db.getCityNames().size() > 0){
            return;
        }

        if (volleyClassString == null){
            volleyClassString = new VolleyClassString(getActivity(), this);
        }
        if (!TextUtils.isEmpty(queryCityInfoBase))
            volleyClassString.makeNetworkRequests(queryCityInfoBase);
    }

    @Override
    public void onInfoAvailable(String responseString) {
        Log.d(TAG, "onInfoAvailable which: " + " : " + responseString);
        if (responseString != null) {
            ParseJsonInfo parseJsonInfo = new ParseJsonInfo();
            cities = parseJsonInfo.decodeMessage(responseString);
        }

        /**
         * CRUD Operations
         * */
        // Inserting Contacts
        Log.d("Insert: ", "Inserting ..");
       for(City c : cities){
           db.addCity(c);
       }
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
