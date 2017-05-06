package com.mysampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.PantryDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class UserPantryActivity extends AppCompatActivity {

    private static final String TAG = "UserPantryActivity";

    private Button btnAdd, btnViewData;
    private EditText editText,editText1,editText2,editText3;

    private AWSCredentialsProvider credentialsProvider;
    AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
    DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_add2_pantry);
        setTitle("Pantry2Pan");

        Log.i("Pantry Page", "Successfully loaded");

        editText = (EditText) findViewById(R.id.editable_item);
        editText1 = (EditText) findViewById(R.id.typeEditText);
        editText2 = (EditText) findViewById(R.id.quantityEditText);
        editText3 = (EditText) findViewById(R.id.expiryEditText);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnViewData = (Button) findViewById(R.id.btnView);

        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String newItem = editText.getText().toString();
                String newType = editText1.getText().toString();
                String newQuantity = editText2.getText().toString();
                String newExpiry = editText3.getText().toString();

                if (editText.length() != 0 && editText1.length() !=0 && editText2.length() !=0 && editText3.length() !=0) {
                    AddData(newItem,newType,newQuantity,newExpiry);
                    editText.setText("");
                    editText1.setText("");
                    editText2.setText("");
                    editText3.setText("");
                } else {
                    toastMessage("One or more missing text field!");
                }
            }
        });

        btnViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListDataActivity.class);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newItem, String newType, String newQuantity, String newExpiry) {
        Log.i(TAG, "addData: Adding " + newItem + newType + newQuantity + newExpiry +" to Database");
        addData(newItem,newType,newQuantity,newExpiry);

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public void addData(String item, String type, String quantity, String expiry) {
        PantryDO pantry = new PantryDO();
        pantry.setName(item);
        pantry.setType(type);
        pantry.setQuantity(quantity);
        pantry.setExpiry(expiry);

        mapper.save(pantry);
    }
}
