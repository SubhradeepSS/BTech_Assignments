package com.example.lab8;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ProductsRecyclerAdapter extends RecyclerView.Adapter<ProductsRecyclerAdapter.ProductViewHolder> {

    private final List<Product> listProducts;

    public ProductsRecyclerAdapter(List<Product> listProducts) {
        this.listProducts = listProducts;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_recycler, parent, false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.tvId.setText(listProducts.get(position).getId());
        holder.tvName.setText(listProducts.get(position).getName());
        holder.tvMrp.setText(String.valueOf(listProducts.get(position).getMrp()));
        holder.tvPrice.setText(String.valueOf(listProducts.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        Log.v(ProductsRecyclerAdapter.class.getSimpleName(),""+ listProducts.size());
        return listProducts.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        public TextView tvId;
        public TextView tvName;
        public TextView tvMrp;
        public TextView tvPrice;

        public ProductViewHolder(View view) {
            super(view);
            tvId = view.findViewById(R.id.aId);
            tvName =  view.findViewById(R.id.aName);
            tvMrp =  view.findViewById(R.id.aMrp);
            tvPrice = view.findViewById(R.id.aPrice);
        }
    }


}
