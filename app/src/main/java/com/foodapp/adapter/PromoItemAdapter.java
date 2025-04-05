package com.foodapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.foodapp.R;
import com.foodapp.models.PromoItem;

import java.util.List;

public class PromoItemAdapter extends RecyclerView.Adapter<PromoItemAdapter.PromoItemViewHolder> {

    private List<PromoItem> promoItems;

    public PromoItemAdapter(List<PromoItem> promoItems) {
        this.promoItems = promoItems;
    }

    @NonNull
    @Override
    public PromoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo, parent, false);
        return new PromoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromoItemViewHolder holder, int position) {
        PromoItem promoItem = promoItems.get(position);
        holder.bind(promoItem);
    }

    @Override
    public int getItemCount() {
        return promoItems.size();
    }

    class PromoItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPromo;
        private TextView tvPromoTitle;
        private TextView tvPromoDescription;

        public PromoItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPromo = itemView.findViewById(R.id.ivPromo);
            tvPromoTitle = itemView.findViewById(R.id.tvPromoTitle);
            tvPromoDescription = itemView.findViewById(R.id.tvPromoDescription);
        }

        public void bind(PromoItem promoItem) {
            tvPromoTitle.setText(promoItem.getTitle());
            tvPromoDescription.setText(promoItem.getDescription());
            
            // Use Glide to load the image
            Glide.with(itemView.getContext())
                    .load(promoItem.getImageResourceId())
                    .into(ivPromo);
        }
    }
}
