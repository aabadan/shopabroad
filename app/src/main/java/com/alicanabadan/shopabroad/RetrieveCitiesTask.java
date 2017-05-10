package com.alicanabadan.shopabroad;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedScanList;
import com.amazonaws.models.nosql.CityDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Alican on 5/1/2017.
 */

public class RetrieveCitiesTask extends AsyncTask<String, Integer, String> {

    private final static String LOG_TAG = Application.class.getSimpleName();
    public CityDO cityDO;
    /** The DynamoDB object mapper for accessing DynamoDB. */
    private final DynamoDBMapper mapper;
    public ArrayList<String> names;


    public RetrieveCitiesTask(Context context){
        mapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();
    }

    @Override
    protected String doInBackground(String... params) {
        fetchItems();
        return null;
    }

    private void fetchItems() {

        ScanResult result = null;
        BasicAWSCredentials credentials = new BasicAWSCredentials("AKIAJ6E4JIHHIPTYT5GA","wk84EkzrU2eayLNiNMPXPEWOMubEk2jn/4Vj83fq");
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentials);
        names = new ArrayList<String>();
        do{
            ScanRequest req = new ScanRequest();
            req.setTableName("shopabroad-mobilehub-776800601-city");

            if(result != null){
                req.setExclusiveStartKey(result.getLastEvaluatedKey());
            }

            result = ddbClient.scan(req);

            List<Map<String, AttributeValue>> rows = result.getItems();

            for(Map<String, AttributeValue> map : rows){
                try{
                    AttributeValue v = map.get("NAME");
                    AttributeValue v2 = map.get("COUNTRY");
                    String name = v.getS();
                    names.add(name);
                } catch (NumberFormatException e){
                    System.out.println(e.getMessage());
                }
            }
        } while(result.getLastEvaluatedKey() != null);

        System.out.println("Result size: " + names.size());
    }

    /*@Override
    public boolean executeOperation() {
        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        results = mapper.scan(CityDO.class, scanExpression);
        if (results != null) {
            resultsIterator = results.iterator();
            if (resultsIterator.hasNext()) {
                return true;
            }
        }
        return false;
    }*/
}
