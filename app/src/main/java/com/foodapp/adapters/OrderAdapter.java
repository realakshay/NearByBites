package com.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.R;
import com.foodapp.models.Order;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Order> orders;
    private OrderItemListener listener;
    
    public interface OrderItemListener {
        void onOrderAgainClicked(Order order);
        void onCancelOrderClicked(Order order);
    }
    
    public OrderAdapter(Context context, List<Order> orders, OrderItemListener listener) {
        this.context = context;
        this.orders = orders;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        
        // Set restaurant name and order ID
        holder.tvRestaurantName.setText(order.getRestaurantName());
        holder.tvOrderId.setText("Order #" + order.getOrderId().substring(0, 8));
        
        // Set date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        String date = dateFormat.format(new Date(order.getTimestamp()));
        holder.tvDate.setText(date);
        
        // Set delivery address
        holder.tvAddress.setText(order.getDeliveryAddress());
        
        // Show/hide cancel button based on order status
        if (order.getStatus().equals("Processing") || order.getStatus().equals("In Progress")) {
            holder.ivCancel.setVisibility(View.VISIBLE);
        } else {
            holder.ivCancel.setVisibility(View.GONE);
        }
        
        // Set restaurant logo based on restaurant name (in a real app, this would come from an API)
        if (order.getRestaurantName().equals("McDonald's")) {
            holder.ivRestaurantLogo.setImageResource(R.drawable.ic_mcdonalds);
        } else if (order.getRestaurantName().equals("KFC")) {
            holder.ivRestaurantLogo.setImageResource(R.drawable.ic_kfc);
        } else if (order.getRestaurantName().equals("Pizza Hut")) {
            holder.ivRestaurantLogo.setImageResource(R.drawable.ic_pizza_hut);
        } else {
            holder.ivRestaurantLogo.setImageResource(R.drawable.ic_restaurant);
        }
        
        // Set click listeners
        holder.btnOrderAgain.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOrderAgainClicked(order);
            }
        });
        
        holder.ivCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancelOrderClicked(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
    
    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRestaurantLogo;
        TextView tvRestaurantName;
        TextView tvOrderId;
        TextView tvDate;
        TextView tvAddress;
        Button btnOrderAgain;
        ImageView ivCancel;
        
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            
            ivRestaurantLogo = itemView.findViewById(R.id.ivRestaurantLogo);
            tvRestaurantName = itemView.findViewById(R.id.tvRestaurantName);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnOrderAgain = itemView.findViewById(R.id.btnOrderAgain);
            ivCancel = itemView.findViewById(R.id.ivCancel);
        }
    }
}
