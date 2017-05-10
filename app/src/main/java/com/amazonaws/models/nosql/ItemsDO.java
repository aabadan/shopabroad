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

@DynamoDBTable(tableName = "shopabroad-mobilehub-776800601-items")

public class ItemsDO {
    private String _userCreated;
    private Double _id;
    private String _createDate;
    private String _description;
    private String _fromPort;
    private String _grantDate;
    private Boolean _isGranted;
    private String _name;
    private Double _price;
    private String _toPort;
    private String _userGranted;

    @DynamoDBHashKey(attributeName = "user_created")
    @DynamoDBAttribute(attributeName = "user_created")
    public String getUserCreated() {
        return _userCreated;
    }

    public void setUserCreated(final String _userCreated) {
        this._userCreated = _userCreated;
    }
    @DynamoDBRangeKey(attributeName = "id")
    @DynamoDBAttribute(attributeName = "id")
    public Double getId() {
        return _id;
    }

    public void setId(final Double _id) {
        this._id = _id;
    }
    @DynamoDBAttribute(attributeName = "create_date")
    public String getCreateDate() {
        return _createDate;
    }

    public void setCreateDate(final String _createDate) {
        this._createDate = _createDate;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return _description;
    }

    public void setDescription(final String _description) {
        this._description = _description;
    }
    @DynamoDBIndexRangeKey(attributeName = "from_port", globalSecondaryIndexName = "user_granted-from_port")
    public String getFromPort() {
        return _fromPort;
    }

    public void setFromPort(final String _fromPort) {
        this._fromPort = _fromPort;
    }
    @DynamoDBAttribute(attributeName = "grant_date")
    public String getGrantDate() {
        return _grantDate;
    }

    public void setGrantDate(final String _grantDate) {
        this._grantDate = _grantDate;
    }
    @DynamoDBAttribute(attributeName = "is_granted")
    public Boolean getIsGranted() {
        return _isGranted;
    }

    public void setIsGranted(final Boolean _isGranted) {
        this._isGranted = _isGranted;
    }
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "price")
    public Double getPrice() {
        return _price;
    }

    public void setPrice(final Double _price) {
        this._price = _price;
    }
    @DynamoDBAttribute(attributeName = "to_port")
    public String getToPort() {
        return _toPort;
    }

    public void setToPort(final String _toPort) {
        this._toPort = _toPort;
    }
    @DynamoDBIndexHashKey(attributeName = "user_granted", globalSecondaryIndexName = "user_granted-from_port")
    public String getUserGranted() {
        return _userGranted;
    }

    public void setUserGranted(final String _userGranted) {
        this._userGranted = _userGranted;
    }

}
