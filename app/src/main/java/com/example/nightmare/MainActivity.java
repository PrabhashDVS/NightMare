<<<<<<< HEAD
package com.example.adddevices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;

    MyDatabaseHelper myDB;
    ArrayList<String> mobile_id, mobile_brand,  mobile_model, mobile_condition, mobile_price, mobile_description;
    CustomAdapter customAdapter;
=======
package com.example.orderapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Model> modelList;
    RecyclerView recyclerView;
    OrderAdapter mAdapter;
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        add_button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        mobile_id = new ArrayList<>();
        mobile_brand = new ArrayList<>();
        mobile_model = new ArrayList<>();
        mobile_condition = new ArrayList<>();
        mobile_price = new ArrayList<>();
        mobile_description = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,MainActivity.this, mobile_id, mobile_brand, mobile_model,
                mobile_condition, mobile_price, mobile_description);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            recreate();
        }
    }
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()) {
                mobile_id.add(cursor.getString(0));
                mobile_brand.add(cursor.getString(1));
                mobile_model.add(cursor.getString(2));
                mobile_condition.add(cursor.getString(3));
                mobile_price.add(cursor.getString(4));
                mobile_description.add(cursor.getString(5));
            }
            empty_imageview.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure want to delete all data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                //refresh
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
=======
        // creating an arraylist

        modelList = new ArrayList<>();
        modelList.add(new Model("Iphone 12", getString(R.string.iphone12), R.drawable.apple12 ));
        modelList.add(new Model("Iphone X", getString(R.string.iphone10), R.drawable.apple10));
        modelList.add(new Model("Xiomi mi 10 Pro", getString(R.string.mi10pro), R.drawable.mi10pro));
        modelList.add(new Model("Oppo F11", getString(R.string.oppo11), R.drawable.oppof11));
        modelList.add(new Model("NOKIA Macleran Plus", getString(R.string.nokiamac), R.drawable.nokiyamac));
        modelList.add(new Model("Iphone 7 Plus", getString(R.string.apple7), R.drawable.iphone7plus));
        modelList.add(new Model("Samsung m21", getString(R.string.samsungm21), R.drawable.m21));
        modelList.add(new Model("Samsung note10", getString(R.string.samsungno10), R.drawable.samsungnote10));
        modelList.add(new Model("Samsung galaxy S9", getString(R.string.galaxys9), R.drawable.galaxys9));

        // recyclerview
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        // adapter
        mAdapter = new OrderAdapter(this, modelList);
        recyclerView.setAdapter(mAdapter);




    }
}
>>>>>>> origin/master
