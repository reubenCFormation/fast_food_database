package com.uniquename.fastfooddatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.uniquename.fastfooddatabase.JavaClasses.FastFood;
import com.uniquename.fastfooddatabase.databasehelper.DatabaseHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button mAddFastFoodBtn;
    ListView mListFastFoods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handleAddFastFoodClick();
        setFastFoodAdapter();
    }

    public void handleAddFastFoodClick(){
        mAddFastFoodBtn=findViewById(R.id.add_fast_food_btn_main);
        mAddFastFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent=new Intent(MainActivity.this, AddFastFoodActivity.class);
                startActivity(newIntent);
            }
        });
    }

    public void setFastFoodAdapter(){
        mListFastFoods=findViewById(R.id.list_fast_foods);
        ArrayList <FastFood> getAllFastFoods=getAllFastFoods();
        FastFoodAdapter newFastFoodAdapter=new FastFoodAdapter(MainActivity.this,getAllFastFoods);
        mListFastFoods.setAdapter(newFastFoodAdapter);
    }

    public ArrayList <FastFood> getAllFastFoods(){
        DatabaseHelper dbHelper=new DatabaseHelper(MainActivity.this);
        // ici j'utilise la methode getReadableDatabase() etant donnée que je ne veux pas effectuer une insertion, je veux effectuer une operation de lecture, et la methode getReadableDatabase() me permet de faire ceci
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        ArrayList <FastFood> getFastFoods=dbHelper.getAllFastFood(db);

        return getFastFoods;
    }


}