package com.foodapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.foodapp.R;
import com.foodapp.models.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    
    private final Context context;
    private List<Address> addresses;
    private Address selectedAddress;
    private final AddressClickListener listener;
    
    public AddressAdapter(Context context, List<Address> addresses, Address selectedAddress, AddressClickListener listener) {
        this.context = context;
        this.addresses = addresses;
        this.selectedAddress = selectedAddress;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addresses.get(position);
        
        // Set address label and details
        holder.tvAddressLabel.setText(address.getLabel());
        holder.tvAddressDetails.setText(address.getFormattedAddress());
        
        // Set selected state
        boolean isSelected = selectedAddress != null && 
                address.getFormattedAddress().equals(selectedAddress.getFormattedAddress());
        holder.rbSelectAddress.setChecked(isSelected);
        
        // Click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddressSelected(address);
            }
        });
        
        holder.rbSelectAddress.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddressSelected(address);
            }
        });
        
        holder.ivEditAddress.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddressEditClicked(address, position);
            }
        });
        
        holder.ivDeleteAddress.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddressDeleteClicked(address, position);
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return addresses.size();
    }
    
    public void setSelectedAddress(Address selectedAddress) {
        this.selectedAddress = selectedAddress;
        notifyDataSetChanged();
    }
    
    public void updateAddresses(List<Address> addresses, Address selectedAddress) {
        this.addresses = addresses;
        this.selectedAddress = selectedAddress;
        notifyDataSetChanged();
    }
    
    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        RadioButton rbSelectAddress;
        TextView tvAddressLabel;
        TextView tvAddressDetails;
        ImageView ivEditAddress;
        ImageView ivDeleteAddress;
        
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            rbSelectAddress = itemView.findViewById(R.id.rbSelectAddress);
            tvAddressLabel = itemView.findViewById(R.id.tvAddressLabel);
            tvAddressDetails = itemView.findViewById(R.id.tvAddressDetails);
            ivEditAddress = itemView.findViewById(R.id.ivEditAddress);
            ivDeleteAddress = itemView.findViewById(R.id.ivDeleteAddress);
        }
    }
    
    public interface AddressClickListener {
        void onAddressSelected(Address address);
        void onAddressEditClicked(Address address, int position);
        void onAddressDeleteClicked(Address address, int position);
    }
}
