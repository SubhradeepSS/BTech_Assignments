package com.example.lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShowActivity extends AppCompatActivity {

    EditText show_id;
    Button show;
    TextView show_name, show_price, show_mrp;
    DatabaseHelper databaseHelper;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        show = findViewById(R.id.show);
        databaseHelper = new DatabaseHelper(this);

        show.setOnClickListener(v -> {
            show_id = findViewById(R.id.show_id);
            show_name = findViewById(R.id.show_name);
            show_mrp = findViewById(R.id.show_mrp);
            show_price = findViewById(R.id.show_price);

            if(databaseHelper.checkProduct(show_id.getText().toString())) {
                product  = databaseHelper.retProduct(show_id.getText().toString());
                show_name.setText(product.getName());
                show_mrp.setText(product.getMrp());
                show_price.setText(product.getPrice());
            }
            else {
                Toast.makeText(this, "Product with given ID does not exist", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_all: {
                Intent i = new Intent(this, ProductsListActivity.class);
                startActivity(i);
                break;
            }

            case R.id.show_one: {
                Intent i = new Intent(this, ShowActivity.class);
                startActivity(i);
                break;
            }

            case R.id.edit_one: {
                Intent intent = new Intent(this, EditProduct.class);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}