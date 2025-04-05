package com.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.R;
import com.foodapp.model.CartItem;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartItemViewHolder> {

    private List<CartItem> cartItems;
    private CartItemActionListener listener;
    private boolean isReadOnly = false;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public interface CartItemActionListener {
        void onItemQuantityChanged(int position, int quantity);
        void onItemRemoved(int position);
    }

    public CartAdapter(List<CartItem> cartItems, CartItemActionListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    public void setReadOnly(boolean readOnly) {
        this.isReadOnly = readOnly;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.bind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemName;
        private TextView tvItemPrice;
        private TextView tvQuantity;
        private ImageButton btnDecrease;
        private ImageButton btnIncrease;
        private ImageButton btnRemove;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }

        public void bind(final CartItem cartItem) {
            tvItemName.setText(cartItem.getMenuItem().getName());
            double totalPrice = cartItem.getMenuItem().getPrice() * cartItem.getQuantity();
            tvItemPrice.setText("$" + decimalFormat.format(totalPrice));
            tvQuantity.setText(String.valueOf(cartItem.getQuantity()));

            if (isReadOnly) {
                btnDecrease.setVisibility(View.GONE);
                btnIncrease.setVisibility(View.GONE);
                btnRemove.setVisibility(View.GONE);
            } else {
                btnDecrease.setVisibility(View.VISIBLE);
                btnIncrease.setVisibility(View.VISIBLE);
                btnRemove.setVisibility(View.VISIBLE);

                btnDecrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && listener != null) {
                            int newQuantity = cartItem.getQuantity() - 1;
                            listener.onItemQuantityChanged(position, newQuantity);
                        }
                    }
                });

                btnIncrease.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && listener != null) {
                            int newQuantity = cartItem.getQuantity() + 1;
                            listener.onItemQuantityChanged(position, newQuantity);
                        }
                    }
                });

                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && listener != null) {
                            listener.onItemRemoved(position);
                        }
                    }
                });
            }
        }
    }
}
