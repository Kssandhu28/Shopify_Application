package com.example.shopifyapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.shopifyapplication.Domain.SliderItems;
import com.example.shopifyapplication.R;

import java.time.Instant;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewholder> {
  private ArrayList<SliderItems> sliderItems;
  private ViewPager2 viewPager2;
  private Handler handler = new Handler() {
      @Override
      public void publish(LogRecord logRecord) {

      }

      @Override
      public void flush() {

      }

      @Override
      public void close() throws SecurityException {

      }
  };

  public SliderAdapter(ArrayList<SliderItems> sliderItems, ViewPager2 viewPager2) {
   this.sliderItems = sliderItems;
  this.viewPager2 = viewPager2;
  }

  @NonNull
  @Override
 public SliderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  return new SliderViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false));
  }

  @Override
 public void onBindViewHolder(@NonNull SliderViewholder holder, int position) {
   holder.setImage(sliderItems.get(position));
  if (position == sliderItems.size() - 2) {
      handler.notify(); // delay for 3 seconds
  }
  }

  @Override
  public int getItemCount() {
   return sliderItems.size();
 }

  public static class SliderViewholder extends RecyclerView.ViewHolder {
  private ImageView imageView;

  public SliderViewholder(@NonNull View itemView) {
 super(itemView);
 imageView = itemView.findViewById(R.id.imageSlide); // Example view ID
  }

      void setImage(SliderItems sliderItems) {
          Glide.with(itemView.getContext())
                  .load(sliderItems.getUrl())
                  .centerCrop()
                  .into(imageView);
      }

  }
}
