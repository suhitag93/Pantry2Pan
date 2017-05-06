package com.mysampleapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.models.nosql.PantryDO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.ArrayList;


public class ListDataActivity extends AppCompatActivity {

    private AWSCredentialsProvider credentialsProvider;

    AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
    DynamoDBMapper mapper = new DynamoDBMapper(ddbClient);

    private static final String TAG = "ListDataActivity";
    ArrayList<Data> listData = new ArrayList<>();
    private ListView mListView;
    private Button addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pantry);
        mListView = (ListView) findViewById(R.id.pantryListView);
        addButton = (Button) findViewById(R.id.addButton);
        populateListView();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserPantryActivity.class);
                startActivity(intent);
            }
        });
    }

    public void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list

        class CustomAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return listData.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = getLayoutInflater().inflate(R.layout.custom_layout,null);

                //ImageView imageView=(ImageView)view.findViewById(R.id.imageView);
                TextView nameView =(TextView) view.findViewById(R.id.nameView);
                TextView typeView =(TextView) view.findViewById(R.id.typeView);
                TextView quantityView =(TextView) view.findViewById(R.id.quantityView);
                TextView expiryView =(TextView) view.findViewById(R.id.expiryView);

                nameView.setText(listData.get(i).getName());
                typeView.setText(listData.get(i).getType());
                quantityView.setText(listData.get(i).getQuantity());
                expiryView.setText(listData.get(i).getExpiry());

                //imageView.setImageResource(IMAGES[i]);
                return null;
            }
        }

        //create the list adapter and set the adapter
        CustomAdapter customadapter = new CustomAdapter();
        mListView.setAdapter(customadapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);


                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }

                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(getApplicationContext(), EditDataActivity.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("name",name);
                    editScreenIntent.putExtra("type",type);
                    editScreenIntent.putExtra("quantity",quantity);
                    editScreenIntent.putExtra("expiry",expiry);
                    startActivity(editScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
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
