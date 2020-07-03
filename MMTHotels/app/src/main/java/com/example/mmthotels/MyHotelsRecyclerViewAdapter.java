package com.example.mmthotels;

import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mmthotels.dummy.DummyContent.DummyItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import static java.lang.StrictMath.min;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyHotelsRecyclerViewAdapter extends RecyclerView.Adapter<MyHotelsRecyclerViewAdapter.ViewHolder> {

    private final List<HotelsClass> mValues;

    public MyHotelsRecyclerViewAdapter(List<HotelsClass> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.hName.setText("Name : "+(mValues.get(position).getName()));
        holder.hPrice.setText("Price : "+(mValues.get(position).getPrice()));
        String loc=mValues.get(position).getLocation();
        holder.hLocation.setText("Location : "+loc);
        //holder.hLocation.setText(loc.substring(0,min(loc.length(), 20)) );
        if((mValues.get(position).isFavourite())){
            holder.hfavourite.setBackgroundResource(R.drawable.ic_favorite_red_24);
        }
        else{
            holder.hfavourite.setBackgroundResource(R.drawable.ic_favorite_dark_24);
        }
        Picasso.get().load(mValues.get(position).getImageurl()).into(holder.hImage);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView hName;
        public final TextView hPrice;
        public final TextView hLocation;
        public final Button hfavourite;
        public final ImageView hImage;
        public HotelsClass mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            hName = (TextView) view.findViewById(R.id.textView);
            hPrice = (TextView) view.findViewById(R.id.textView2);
            hLocation= (TextView) view.findViewById(R.id.textView3);
            hfavourite=(Button) view.findViewById(R.id.button);
            hImage=(ImageView) view.findViewById(R.id.imageView1);

            hfavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    HotelsClass hcur= (HotelsClass) MainActivity.HotelsList.get(position);
                    hcur.setFavourite(!hcur.isFavourite());
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" +hName.getText() + "'";
        }
    }
}