
package com.uniquename.fastfooddatabase.databasehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.uniquename.fastfooddatabase.JavaClasses.FastFood;
import com.uniquename.fastfooddatabase.JavaClasses.FastFoodMeals;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DatabaseName="Fast Food";
    private static final int DatabaseVersion = 1;
    private final String FAST_FOOD_TABLE="restaurants_fast_food";

    private final String FAST_FOOD_MEALS_LIST_TABLE="fast_food_meals_list";


    // je transmets les informations neccesaires chaque fois que j'instancie cette classe
    public DatabaseHelper(Context context){
        super(context,DatabaseName,null,DatabaseVersion);
    }

    /*
       Ici, la premiere fois que je vais instancier ma classe DatabaseHelper nous allons faire appel a la methode onCreate qui va se charger de creer nos deux tables
     */
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ FAST_FOOD_TABLE +" (id INTEGER PRIMARY KEY AUTOINCREMENT,title VARCHAR(255),description TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ FAST_FOOD_MEALS_LIST_TABLE +" (id INTEGER PRIMARY KEY AUTOINCREMENT,meal_title VARCHAR(255),meal_price INTEGER, fast_food_restaurants_id INTEGER, FOREIGN KEY (fast_food_restaurants_id) REFERENCES "+FAST_FOOD_TABLE + " (id))");
    }

    /*
        La methode onUpgrade sera appalé chaque fois que je vais voulour changer la version de ma base de données qui est actuellement representé par la properité prive DatabaseVersion. Si je veux changer de version, nous allons declencher la methode onUpgrade
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP table if exists restaurants_fast_food");
        db.execSQL("DROP table if exists fast_food_meals_list");
    }

    public void insertFastFoodRestaurant(SQLiteDatabase db, String title,String description){
        /*
            Ici avec mon instance de la classe contentValues je vais pouvoir inserer des données dans ma base de données, mon instance de contentValues va me creer un objet ou je vais pouvoir stocker des valeurs differents et ensuite je vais pouvoir utiliser cette objet pour l'insertion dans ma base de données.  Attention, le premier argument doit correspondre au vrai nom de notre champ dans notre base de données. Sinon l'insertion ne pourra pas aboutir
         */
        ContentValues values=new ContentValues();
        values.put("title",title);
        values.put("description",description);
        /*
            Ici je vais reelement inserer mes données que j'aurai construit dans mon objet values en precisant la table dans laquelle je souhaite inserer bien sur et mon objet values qui va mapper les champs et les inserer
         */
        db.insert(FAST_FOOD_TABLE,null,values);
        db.close();

    }

    public void insertMealToFastFood(SQLiteDatabase db, String mealTitle, int mealPrice,int fastFoodId){
        // meme chose que dans la fonction insertFastFoodRestaurant mais avec des données differentes
        ContentValues values=new ContentValues();
        values.put("meal_title",mealTitle);
        values.put("meal_price",mealPrice);
        values.put("fast_food_restaurants_id",fastFoodId);
        db.insert(FAST_FOOD_MEALS_LIST_TABLE,null,values);
        db.close();
    }
    public ArrayList <FastFood> getAllFastFood(SQLiteDatabase db){
        ArrayList <FastFood> fastFoods=new ArrayList<>();
        /*
           Ici je vais creer un objet cursor qui va effectuer ma requette et qui va premiere rangée de ma requette qui conteindra mes colonnes id, title,description
         */
        Cursor cursor=db.rawQuery("SELECT * FROM " +FAST_FOOD_TABLE,null);
        /*
            Ici je vais pouvoir recuperer la position  de mes trois differentes colonnes (id, title, index) pour une rangéé en particuliere qui est mon cursor
         */
        int idIndex=cursor.getColumnIndex("id");
        int titleIndex=cursor.getColumnIndex("title");
        int descriptionIndex=cursor.getColumnIndex("description");
        // ici j'incremente mon cursor (ma rangée) et tant que il me reste des rangéds defini je continue a boucler
        while(cursor.moveToNext()){
            // ici je vais reellement recuperer mes valeurs associé pour chaque colonne en utilisant mon cursor. A savoir que grace a la methode moveToNext, les valeurs de mon cursor vont s'incrementer
            int id=cursor.getInt(idIndex);
            String title=cursor.getString(titleIndex);
            String description=cursor.getString(descriptionIndex);
            // ici je vais instancier mon objet fast food
            FastFood newFastFood=new FastFood(id,title,description);
            // enfin je vais dynamiquement alimenter mon ArrayList avec mes intances
            fastFoods.add(newFastFood);
        }
        return fastFoods;

    }

    public ArrayList <FastFoodMeals> getFastFoodMealsForFastFood(SQLiteDatabase db,int fastFoodId){
        ArrayList <FastFoodMeals> fastFoodMeals=new ArrayList<>();
        /*
            Ici je vais preparer ma requette pour me proteger d'injection sql. Dans un 2eme temps je vais preciser la valeu ractuelle en utilisant mon tableau que j'ai crée et en lui alimentnat avec la valeur de mon parametre. Etant donnée que ici je souhaite recuperer tous les repas pour un restaurant fast food en particulier
         */
        String[] selectionArgs={String.valueOf(fastFoodId)};
        Cursor cursor=db.rawQuery("SELECT * FROM "+ FAST_FOOD_MEALS_LIST_TABLE+ " WHERE fast_food_restaurants_id = ?",selectionArgs);

        int idIndex=cursor.getColumnIndex("id");
        int mealTitleIndex=cursor.getColumnIndex("meal_title");
        int mealPriceIndex=cursor.getColumnIndex("meal_price");
        int fastFoodIdIndex=cursor.getColumnIndex("fast_food_restaurants_id");
        while(cursor.moveToNext()){
            int id=cursor.getInt(idIndex);
            String mealTitle=cursor.getString(mealTitleIndex);
            int mealPrice=cursor.getInt(mealPriceIndex);
            int fastFood=cursor.getInt(fastFoodIdIndex);

            FastFoodMeals newFastFoodMeal=new FastFoodMeals(id,mealTitle,mealPrice,fastFood);
            fastFoodMeals.add(newFastFoodMeal);
        }

        return fastFoodMeals;
    }

    public FastFood findFastFood(SQLiteDatabase db,int fastFoodId){
        String[] selectionArgs={String.valueOf(fastFoodId)};
        Cursor cursor=db.rawQuery("SELECT * FROM "+ FAST_FOOD_TABLE+ " WHERE id = ?",selectionArgs);
        // si nous avons bien un resultat
        if(cursor.moveToFirst()){
            int restaurantId=cursor.getColumnIndex("id");
            int titleIndex=cursor.getColumnIndex("title");
            int descriptionIndex=cursor.getColumnIndex("description");

            int id=cursor.getInt(restaurantId);
            String title=cursor.getString(titleIndex);
            String description=cursor.getString(descriptionIndex);
            return new FastFood(id,title,description);


        }
        return null;

    }


}