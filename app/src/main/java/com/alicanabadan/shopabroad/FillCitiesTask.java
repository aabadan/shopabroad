package com.alicanabadan.shopabroad;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.mobile.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.CityDO;

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

/**
 * Created by Alican on 5/1/2017.
 */

public class FillCitiesTask extends AsyncTask<String, Integer, String> {

    private final static String LOG_TAG = Application.class.getSimpleName();
    private CityDO citiesDO;

    private Context mContext;

    public FillCitiesTask (Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(String... params) {
        readExcelFile(mContext);
        return null;
    }

    private void readExcelFile(Context context) {

        List<CityDO> cityDataList = null;
        try{
            String filename = "city.xls";
            // Creating Input Stream
            File file = new File(context.getExternalFilesDir(null), filename);
            FileInputStream myInput = new FileInputStream(file);

            // Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            /** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();
            List<String> rowData = new ArrayList<String>();
            cityDataList = new ArrayList<CityDO>();
            while(rowIter.hasNext()){
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                if(rowData.size() > 0) {
                    cityDataList.add(setCityObject(rowData));
                    rowData = new ArrayList<String>();
                }
                while(cellIter.hasNext()){
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    rowData.add(myCell.toString());
                    //Log.d(LOG_TAG, "Cell Value: " +  myCell.toString());
                    //Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Log.d(LOG_TAG, "total data size in the list: " +  cityDataList.size());

        insertCityData(cityDataList);

        return;
    }


    public void insertCityData(List<CityDO> citiesList) {
        // Fetch the default configured DynamoDB ObjectMapper
        final DynamoDBMapper dynamoDBMapper = AWSMobileClient.defaultMobileClient().getDynamoDBMapper();

        // The userId has to be set to user's Cognito Identity Id for private / protected tables.
        // User's Cognito Identity Id can be fetched by using:
        // AWSMobileClient.defaultMobileClient().getIdentityManager().getCachedUserID()

        AmazonClientException lastException = null;
        int i=0;
        try {
            for(CityDO citiesDO :citiesList) {
                dynamoDBMapper.save(citiesDO);
                i++;
            }
        } catch (final AmazonClientException ex) {
            ex.printStackTrace();
            Log.e(LOG_TAG, "Failed saving item : " + ex.getMessage(), ex);
            lastException = ex;
        }

        Log.d(LOG_TAG, "total data size inserted to DynamoDB: " +  i);
    }

    public CityDO setCityObject(List<String> rowData){
        citiesDO = new CityDO(); // Initialize the Cities Object
        citiesDO.setCODE(rowData.get(0));
        citiesDO.setNAME(rowData.get(1));
        citiesDO.setCOUNTRY(rowData.get(2));
        citiesDO.setSHORTNAMETR(rowData.get(3));
        citiesDO.setSHORTNAMEEN(rowData.get(4));
        citiesDO.setSHORTNAMEDE(rowData.get(5));
        citiesDO.setSHORTNAMEFR(rowData.get(6));
        citiesDO.setSHORTNAMERU(rowData.get(7));
        citiesDO.setSHORTNAMENL(rowData.get(8));
        citiesDO.setSHORTNAMEDA(rowData.get(9));
        citiesDO.setSHORTNAMEIT(rowData.get(10));
        citiesDO.setSHORTNAMEHE(rowData.get(11));
        citiesDO.setSHORTNAMESV(rowData.get(12));

        return citiesDO;
    }
}
