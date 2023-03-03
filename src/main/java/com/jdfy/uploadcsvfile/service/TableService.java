package com.jdfy.uploadcsvfile.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.jdfy.uploadcsvfile.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class TableService {
    private static final Logger logger = LoggerFactory.getLogger(TableService.class);

    @Autowired
    AmazonDynamoDB amazonDynamoDB;

    public String deleteTable() throws InterruptedException {

        logger.info("Deleting table %s...\n", Constants.TABLE_NAME);
        DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
        try {
            Table table = dynamoDB.getTable(Constants.TABLE_NAME);
            table.delete();
            table.waitForDelete();
            logger.info(Constants.TABLE_NAME +" was successfully deleted!");
            return Constants.TABLE_NAME +" was successfully deleted!";
        }
        catch (ResourceNotFoundException e){
            System.err.println("Resource Not Found, Please connect with admin "+e.getErrorMessage());
            return Constants.TABLE_NAME +" not Found, Please connect with admin";
        }
        catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            return Constants.TABLE_NAME +" not deleted"+ e.getErrorMessage();
        }
    }

    public String createTable(){

        CreateTableRequest request = new CreateTableRequest();
        /* Setting Table Name */
        request.setTableName(Constants.TABLE_NAME);

        /* Create & Set a list of AttributeDefinition */
        List<AttributeDefinition> attributeDefinitions = Arrays.asList(
                new AttributeDefinition[] {
                        new AttributeDefinition("customerId", ScalarAttributeType.S),
                        new AttributeDefinition("contractId", ScalarAttributeType.S) });
        request.setAttributeDefinitions(attributeDefinitions);

        /* Create & Set a list of KeySchemaElement */
        List<KeySchemaElement> keySchema = Arrays.asList(
                new KeySchemaElement[] {
                        new KeySchemaElement("customerId", KeyType.HASH),
                        new KeySchemaElement("contractId", KeyType.RANGE)});
        request.setKeySchema(keySchema);

        /* Setting Provisioned Throughput */
        request.setProvisionedThroughput(new ProvisionedThroughput(new Long(1), new Long(1)));

        try {
            /* Send Create Table Request */
            CreateTableResult result = amazonDynamoDB.createTable(request);
            logger.info("Status : " +  result.getSdkHttpMetadata().getHttpStatusCode());
            logger.info("Table Name : " +  result.getTableDescription().getTableName());
            return Constants.TABLE_NAME+" successfully created!!";
        } catch (AmazonServiceException e) {
            logger.error(e.getErrorMessage());
            return Constants.TABLE_NAME+" creation failed "+e.getErrorMessage();
        }
    }
}
