package com.uniquename.fastfooddatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uniquename.fastfooddatabase.databasehelper.DatabaseHelper;

public class AddFastFoodActivity extends AppCompatActivity {

    EditText mAddFastFoodTitle;
    EditText mAddFastFoodDescription;

    Button mAddFastFoodBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fast_food);
        handleAddFastFoodClick();
    }

    public void handleAddFastFoodClick(){
        mAddFastFoodBtn=findViewById(R.id.add_new_fast_food_btn);
        mAddFastFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddFastFastFood();
            }
        });
    }

    public void handleAddFastFastFood(){
        mAddFastFoodTitle=findViewById(R.id.add_fast_food_title);
        mAddFastFoodDescription=findViewById(R.id.add_fast_food_description);
        if(isFieldsFilled(mAddFastFoodTitle,mAddFastFoodDescription)){
            DatabaseHelper dbHelper=new DatabaseHelper(AddFastFoodActivity.this);
            // ici je vais acceder a une "writable database a l'aide de mon dbHelper pour pouvoir effectuer une insertion dans ma base de données. Ceci est possible grace a la methode getWritableDatabase de mon dbHelper"
            SQLiteDatabase db=dbHelper.getWritableDatabase();
            // ici j'effectue l'insertion avec mon dbHelper en precisant mon argument db qui va pouvoir reelement effectuer l'insertion.
            dbHelper.insertFastFoodRestaurant(db,mAddFastFoodTitle.getText().toString(),mAddFastFoodDescription.getText().toString());

            Toast.makeText(AddFastFoodActivity.this,"Fast Food Bien Ajouté !",Toast.LENGTH_SHORT).show();
        }

        else{
            Toast.makeText(AddFastFoodActivity.this,"Vous avez dex champs vide!",Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isFieldsFilled(EditText mAddFastFoodTitle,EditText mAddFastFoodDescription){
        if(mAddFastFoodTitle.getText().toString().isEmpty() || mAddFastFoodDescription.getText().toString().isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }
}