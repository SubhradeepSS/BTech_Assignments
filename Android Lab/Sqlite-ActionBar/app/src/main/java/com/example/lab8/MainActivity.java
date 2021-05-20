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
import android.widget.Toast;

import com.example.lab8.DatabaseHelper;
import com.example.lab8.Product;

public class MainActivity extends AppCompatActivity {

    EditText name,id,mrp,price;
    String name_s, id_s, mrp_s, price_s;
    Button add;
    DatabaseHelper databaseHelper;
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add);
        databaseHelper = new DatabaseHelper(this);
        product = new Product();

        add.setOnClickListener(v -> {
            name = findViewById(R.id.name);
            id = findViewById(R.id.id);
            mrp = findViewById(R.id.mrp);
            price = findViewById(R.id.price);

            name_s = name.getText().toString();
            id_s = id.getText().toString();
            mrp_s = mrp.getText().toString();
            price_s = price.getText().toString();

            if (name_s.equals("") || id_s.equals("") || mrp_s.equals("") || price_s.equals("")) {
                Toast.makeText(this, "Please Enter all Details", Toast.LENGTH_LONG).show();
            }
            else if (!databaseHelper.checkProduct(id_s)){
                product.setName(name_s);
                product.setId(id_s);
                product.setMrp(mrp_s);
                product.setPrice(price_s);

                databaseHelper.addProduct(product);

                Toast.makeText(this, "Product added successfully", Toast.LENGTH_LONG).show();

                Intent i = new Intent(this, ProductsListActivity.class);
                startActivity(i);

            } else {
                Toast.makeText(this, "Product with given ID exists", Toast.LENGTH_LONG).show();
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