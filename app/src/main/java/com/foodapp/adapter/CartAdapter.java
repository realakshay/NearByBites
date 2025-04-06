package com.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.R;
import com.foodapp.models.CartItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<CartItem> cartItems;
    private boolean isReadOnly = false;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem currentItem = cartItems.get(position);

        holder.tvItemName.setText(currentItem.getMenuItem().getName());
        holder.tvItemPrice.setText("$" + String.format("%.2f", currentItem.getMenuItem().getPrice()));
        holder.tvItemQuantity.setText("x" + currentItem.getQuantity());
        double totalPrice = currentItem.getMenuItem().getPrice() * currentItem.getQuantity();
        holder.tvItemTotal.setText("$" + String.format("%.2f", totalPrice));

        if (currentItem.getRemarks() != null && !currentItem.getRemarks().isEmpty()) {
            holder.tvItemRemarks.setVisibility(View.VISIBLE);
            holder.tvItemRemarks.setText("Note: " + currentItem.getRemarks());
        } else {
            holder.tvItemRemarks.setVisibility(View.GONE);
        }

        if (currentItem.getMenuItem().getImageUrl() != null && !currentItem.getMenuItem().getImageUrl().isEmpty()) {
            Picasso.get().load(currentItem.getMenuItem().getImageUrl()).into(holder.ivItemImage);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemImage;
        TextView tvItemName, tvItemPrice, tvItemQuantity, tvItemTotal, tvItemRemarks;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvItemQuantity = itemView.findViewById(R.id.tvQuantity);
            tvItemTotal = itemView.findViewById(R.id.tvItemTotal);
            tvItemRemarks = itemView.findViewById(R.id.tvItemRemarks);
        }
    }
}
