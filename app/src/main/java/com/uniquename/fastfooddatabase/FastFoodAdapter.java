package com.uniquename.fastfooddatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.uniquename.fastfooddatabase.JavaClasses.FastFood;

import java.util.ArrayList;

public class FastFoodAdapter extends BaseAdapter {
   private Context context;
   private ArrayList <FastFood> fastFoods;

   TextView mGetTextTitle;
   TextView mGetDescriptionText;


public FastFoodAdapter(Context context, ArrayList <FastFood> fastFoods){
       this.context=context;
       this.fastFoods=fastFoods;
   }

   public int getCount(){
        return this.fastFoods.size();
   }

   public FastFood getItem(int position){
        return this.fastFoods.get(position);
   }

   public long getItemId(int position){
        return position;
   }

   public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(R.layout.list_fast_foods,parent,false);
        }
        FastFood getFastFood=this.fastFoods.get(position);
        mGetTextTitle=convertView.findViewById(R.id.fast_food_title_text);
        mGetDescriptionText=convertView.findViewById(R.id.fast_food_description_text);
        mGetTextTitle.setText(getFastFood.getTitle());
        mGetDescriptionText.setText(getFastFood.getDescription());

        return convertView;


   }
}
