package com.amazonaws.models.nosql;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "shopabroad-mobilehub-776800601-city")

public class CityDO {
    private String _cODE;
    private String _cOUNTRY;
    private String _nAME;
    private String _sHORTNAMEDA;
    private String _sHORTNAMEDE;
    private String _sHORTNAMEEN;
    private String _sHORTNAMEFR;
    private String _sHORTNAMEHE;
    private String _sHORTNAMEIT;
    private String _sHORTNAMENL;
    private String _sHORTNAMERU;
    private String _sHORTNAMESV;
    private String _sHORTNAMETR;

    @DynamoDBHashKey(attributeName = "CODE")
    @DynamoDBAttribute(attributeName = "CODE")
    public String getCODE() {
        return _cODE;
    }

    public void setCODE(final String _cODE) {
        this._cODE = _cODE;
    }
    @DynamoDBAttribute(attributeName = "COUNTRY")
    public String getCOUNTRY() {
        return _cOUNTRY;
    }

    public void setCOUNTRY(final String _cOUNTRY) {
        this._cOUNTRY = _cOUNTRY;
    }
    @DynamoDBAttribute(attributeName = "NAME")
    public String getNAME() {
        return _nAME;
    }

    public void setNAME(final String _nAME) {
        this._nAME = _nAME;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_DA")
    public String getSHORTNAMEDA() {
        return _sHORTNAMEDA;
    }

    public void setSHORTNAMEDA(final String _sHORTNAMEDA) {
        this._sHORTNAMEDA = _sHORTNAMEDA;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_DE")
    public String getSHORTNAMEDE() {
        return _sHORTNAMEDE;
    }

    public void setSHORTNAMEDE(final String _sHORTNAMEDE) {
        this._sHORTNAMEDE = _sHORTNAMEDE;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_EN")
    public String getSHORTNAMEEN() {
        return _sHORTNAMEEN;
    }

    public void setSHORTNAMEEN(final String _sHORTNAMEEN) {
        this._sHORTNAMEEN = _sHORTNAMEEN;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_FR")
    public String getSHORTNAMEFR() {
        return _sHORTNAMEFR;
    }

    public void setSHORTNAMEFR(final String _sHORTNAMEFR) {
        this._sHORTNAMEFR = _sHORTNAMEFR;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_HE")
    public String getSHORTNAMEHE() {
        return _sHORTNAMEHE;
    }

    public void setSHORTNAMEHE(final String _sHORTNAMEHE) {
        this._sHORTNAMEHE = _sHORTNAMEHE;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_IT")
    public String getSHORTNAMEIT() {
        return _sHORTNAMEIT;
    }

    public void setSHORTNAMEIT(final String _sHORTNAMEIT) {
        this._sHORTNAMEIT = _sHORTNAMEIT;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_NL")
    public String getSHORTNAMENL() {
        return _sHORTNAMENL;
    }

    public void setSHORTNAMENL(final String _sHORTNAMENL) {
        this._sHORTNAMENL = _sHORTNAMENL;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_RU")
    public String getSHORTNAMERU() {
        return _sHORTNAMERU;
    }

    public void setSHORTNAMERU(final String _sHORTNAMERU) {
        this._sHORTNAMERU = _sHORTNAMERU;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_SV")
    public String getSHORTNAMESV() {
        return _sHORTNAMESV;
    }

    public void setSHORTNAMESV(final String _sHORTNAMESV) {
        this._sHORTNAMESV = _sHORTNAMESV;
    }
    @DynamoDBAttribute(attributeName = "SHORT_NAME_TR")
    public String getSHORTNAMETR() {
        return _sHORTNAMETR;
    }

    public void setSHORTNAMETR(final String _sHORTNAMETR) {
        this._sHORTNAMETR = _sHORTNAMETR;
    }

}
