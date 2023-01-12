package com.example.face_detection.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.face_detection.model.all_data_model

class universal_database(val context_ : Context, val factory : SQLiteDatabase.CursorFactory?) :

    SQLiteOpenHelper(context_ , DATABASE_NAME , factory , DATABASE_VERSION){

    companion object {
        val DATABASE_NAME = "BHUMICAMP"
        const val DATABASE_VERSION = 3
        val TABLE_OF_FARMERS = "table_of_farmers_"
        val ID_COL = "id_column_"
        val NAME_ = "name_of_farmer"
        val DISTRICT_ = "district_name"
        val VILLAGE_ = "VILLAGE_NAME"
        val STATE_NAME = "STATE_NAME"
        val MOBILE_NUMBER_ = "mobile_number_"
        val ADHAR_NUMBER = "adhar_number_"
        val FARMER_PHOTO_ = "FARMER_PHOTO_"
        val CROP_TYPES_ = "CROP_TYPES"
        val FIELD_SIZE = "field_size_"
        val CROP_STAGE_ = "CROP_STAGE_"
        val CROP_HEALTH_CONDITION = "CROP_HEALTH_CONDITION"
        val PERCENTAGE_OF_CROP_IN_GROUND = "PERCENTAGE_OF_CROP_IN_GROUND"
        val SOIL_CONDITION = "SOIL_CONDITON"
        val ALL_CROP_PIC = "ALL_CROP_PCTURE_"
    }

    override fun onCreate(db : SQLiteDatabase ) {
        val query_1 = ("CREATE TABLE " + TABLE_OF_FARMERS + " (" + ID_COL + " INTEGER PRIMARY KEY, " +
                NAME_ + " TEXT, "+
                DISTRICT_ + " TEXT, "+
                VILLAGE_ + " TEXT, "+
                STATE_NAME + " TEXT, "+
                MOBILE_NUMBER_ + " TEXT, "+
                ADHAR_NUMBER + " TEXT, "+
                FARMER_PHOTO_ + " TEXT, "+
                CROP_TYPES_ + " TEXT, "+
                FIELD_SIZE + " TEXT, "+
                CROP_STAGE_ + " TEXT, "+
                CROP_HEALTH_CONDITION + " TEXT, "+
                PERCENTAGE_OF_CROP_IN_GROUND + " TEXT, "+
                SOIL_CONDITION +" TEXT, "+
                ALL_CROP_PIC +  " TEXT);")

        db.execSQL(query_1)
    }

    override fun onUpgrade(db : SQLiteDatabase , p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OF_FARMERS)
        onCreate(db)
    }


    // this unctions will save the all data of the farmers
    fun save_data(
        NAME__ : String,
        DISTRICT__ : String,
        VILLAGE__ : String,
        STATE_NAME_ : String,
        MOBILE_NUMBER__ : String,
        ADHAR_NUMBER_ : String,
        FARMER_PHOTO__ : String,
        CROP_TYPES__ : String,
        FIELD_SIZE_ : String,
        CROP_STAGE__ : String,
        CROP_HEALTH_CONDITION_ : String,
        PERCENTAGE_OF_CROP_IN_GROUND_ : String,
        SOIL_CONDITION_ : String,
        ALL_CROP_PICTUTRE_ : String
        )
    {

        var status = false
        // adding all messages to sql database
        val values = ContentValues()
        values.put(NAME_,NAME__)
        values.put(DISTRICT_,DISTRICT__)
        values.put(VILLAGE_,VILLAGE__)
        values.put(STATE_NAME,STATE_NAME_)
        values.put(MOBILE_NUMBER_,MOBILE_NUMBER__)
        values.put(ADHAR_NUMBER,ADHAR_NUMBER_)
        values.put(FARMER_PHOTO_,FARMER_PHOTO__)
        values.put(CROP_TYPES_,CROP_TYPES__)
        values.put(FIELD_SIZE,FIELD_SIZE_)
        values.put(CROP_STAGE_,CROP_STAGE__)
        values.put(CROP_HEALTH_CONDITION,CROP_HEALTH_CONDITION_)
        values.put(PERCENTAGE_OF_CROP_IN_GROUND,PERCENTAGE_OF_CROP_IN_GROUND_)
        values.put(SOIL_CONDITION,SOIL_CONDITION_)
        values.put(ALL_CROP_PIC,ALL_CROP_PICTUTRE_)

        val db = this.writableDatabase     // accessing database for writting data
        try {
            // take the index of row of saved message
           db.insert(TABLE_OF_FARMERS, null, values)
           status = true
        } catch (e: Error) {
            Log.d("","EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEError in saving the data is:${e}")
        }
        db.close()
    }



    // this will return all data of farmers
    fun get_all_data() : all_data_model {

        var store = all_data_model("","","","","","","","",
                                  "","","","","","")

        val db = this.readableDatabase
        try {
            val cursor: Cursor = db.rawQuery("SELECT * FROM ${TABLE_OF_FARMERS};", null)
            if (cursor.count > 0) {
                cursor.moveToFirst()
                do {
                     val NAME__ = cursor.getString(cursor.getColumnIndexOrThrow(NAME_))
                     val DISTRICT__ = cursor.getString(cursor.getColumnIndexOrThrow(DISTRICT_))
                     val VILLAGE__ = cursor.getString(cursor.getColumnIndexOrThrow(VILLAGE_))
                     val STATE_NAME_ = cursor.getString(cursor.getColumnIndexOrThrow(STATE_NAME))
                     val MOBILE_NUMBER__ = cursor.getString(cursor.getColumnIndexOrThrow(MOBILE_NUMBER_))
                     val ADHAR_NUMBER_ = cursor.getString(cursor.getColumnIndexOrThrow(ADHAR_NUMBER))
                     val FARMER_PHOTO__ = cursor.getString(cursor.getColumnIndexOrThrow(FARMER_PHOTO_))
                     val CROP_TYPES__ = cursor.getString(cursor.getColumnIndexOrThrow(CROP_TYPES_))
                     val FIELD_SIZE_ = cursor.getString(cursor.getColumnIndexOrThrow(FIELD_SIZE))
                     val CROP_STAGE__ = cursor.getString(cursor.getColumnIndexOrThrow(CROP_STAGE_))
                     val CROP_HEALTH_CONDITION_ = cursor.getString(cursor.getColumnIndexOrThrow(CROP_HEALTH_CONDITION))
                     val PERCENTAGE_OF_CROP_IN_GROUND_ = cursor.getString(cursor.getColumnIndexOrThrow(PERCENTAGE_OF_CROP_IN_GROUND))
                     val SOIL_CONDITION_ = cursor.getString(cursor.getColumnIndexOrThrow(SOIL_CONDITION))
                     val all_soil_picture_ = cursor.getString(cursor.getColumnIndexOrThrow(ALL_CROP_PIC))

                    store = all_data_model(NAME__   ,DISTRICT__,VILLAGE__,STATE_NAME_,MOBILE_NUMBER__,ADHAR_NUMBER_,FARMER_PHOTO__,CROP_TYPES__,
                                  FIELD_SIZE_,CROP_STAGE__, CROP_HEALTH_CONDITION_,PERCENTAGE_OF_CROP_IN_GROUND_,SOIL_CONDITION_,all_soil_picture_)

                    Log.d("", "NNNNNNNNNNNNNNNNNow EXTRACTING DATA")

                } while (cursor.moveToNext())
            }
            db.close()
        } catch(e: Exception) {
            Log.d("error in finding", ": account : ${e}")
        }

        return store
    }


    // updating all the farmers data
    fun update_persons_info(operators : String, value : String) {

        try {
            val DB = this.writableDatabase
            var sql_query = ""
//            if (operators == "mute") {
//                sql_query = "UPDATE ${TABLE_NAME_PERSON_INFO} SET ${MUTE_}='${value}' WHERE ${NUMBER_}=${contact_number};"
//                DB.execSQL(sql_query)
//            }

            DB.close()
        } catch (e: Exception) {
            Log.d("", "updating the data of faremers")
        }
    }

}