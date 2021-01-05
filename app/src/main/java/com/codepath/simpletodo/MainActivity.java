package com.codepath.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
//import android.os.FileUtils;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    //Giving member variables for the functions in the .xml
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //R - resource

        //These are now referred to here in the function
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        //items = new ArrayList<>();
        loadItems();
        //items.add("Buy groceries");
        //items.add("Run 2 miles");
        //items.add("Submit project");

        ItemsAdapter.OnLongClickListener OnLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void OnItemLongClicked(int position) {
                //delete the item
                items.remove(position);
                //notifies that the item was removed from the model
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item removed",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };
       itemsAdapter = new ItemsAdapter(items, OnLongClickListener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toString - returns string
                String todoItem = etItem.getText().toString();
                //Adds item to list
                items.add(todoItem);
                //To notify adapter that somethings being added (added at last position)
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                //Adding toast - a short pop up that gives feedback about an operation performed
                Toast.makeText(getApplicationContext(),"Item added",Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile() {
            return new File(getFilesDir(), "data.txt");
    }

    //will read each line in the data file
    private void loadItems() {

        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error in reading lines",e);
            items = new ArrayList<>();
        }
    }
    //will write the items and save
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity","Error in writing lines",e);
        }
    }
}