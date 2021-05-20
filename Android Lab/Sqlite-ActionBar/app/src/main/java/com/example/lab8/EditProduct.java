package com.example.lab8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditProduct extends AppCompatActivity {

    EditText name,id,mrp,price;
    String name_s, id_s, mrp_s, price_s;
    Button edit;
    DatabaseHelper databaseHelper;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        edit = findViewById(R.id.edit);
        databaseHelper = new DatabaseHelper(this);

        edit.setOnClickListener(v -> {
            name = findViewById(R.id.edit_name);
            id = findViewById(R.id.edit_id);
            mrp = findViewById(R.id.edit_mrp);
            price = findViewById(R.id.edit_price);

            name_s = name.getText().toString();
            id_s = id.getText().toString();
            mrp_s = mrp.getText().toString();
            price_s = price.getText().toString();

            if (databaseHelper.checkProduct(id_s)){
               databaseHelper.updateProduct(id_s, name_s, mrp_s, price_s);
               Toast.makeText(this, "Updated Product Successfully", Toast.LENGTH_LONG).show();
               Intent i = new Intent(this, ProductsListActivity.class);
               startActivity(i);
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