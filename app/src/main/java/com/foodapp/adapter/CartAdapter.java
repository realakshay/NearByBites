package com.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foodapp.R;
import com.foodapp.models.CartItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        super(context, 0, cartItems);
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        }

        CartItem currentItem = cartItems.get(position);

        ImageView ivItemImage = listItem.findViewById(R.id.ivItemImage);
        TextView tvItemName = listItem.findViewById(R.id.tvItemName);
        TextView tvItemPrice = listItem.findViewById(R.id.tvItemPrice);
        TextView tvItemQuantity = listItem.findViewById(R.id.tvItemQuantity);
        TextView tvItemTotal = listItem.findViewById(R.id.tvItemTotal);
        TextView tvItemRemarks = listItem.findViewById(R.id.tvItemRemarks);

        // Set the data
        tvItemName.setText(currentItem.getMenuItem().getName());
        tvItemPrice.setText("$" + String.format("%.2f", currentItem.getMenuItem().getPrice()));
        tvItemQuantity.setText("x" + currentItem.getQuantity());
        double totalPrice = currentItem.getMenuItem().getPrice() * currentItem.getQuantity();
        tvItemTotal.setText("$" + String.format("%.2f", totalPrice));
        
        // Handle remarks
        if (currentItem.getRemarks() != null && !currentItem.getRemarks().isEmpty()) {
            tvItemRemarks.setVisibility(View.VISIBLE);
            tvItemRemarks.setText("Note: " + currentItem.getRemarks());
        } else {
            tvItemRemarks.setVisibility(View.GONE);
        }

        // Load the image using Picasso
        if (currentItem.getMenuItem().getImageUrl() != null && !currentItem.getMenuItem().getImageUrl().isEmpty()) {
            Picasso.get().load(currentItem.getMenuItem().getImageUrl()).into(ivItemImage);
        }

        return listItem;
    }
}
