package it.saimao.bamardictionary;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import it.saimao.bamardictionary.dao.DictionaryDao;
import it.saimao.bamardictionary.dao.FavouriteDao;
import it.saimao.bamardictionary.entities.DictionaryEntity;
import it.saimao.bamardictionary.entities.FavoriteEntity;

@Database(entities = {DictionaryEntity.class, FavoriteEntity.class}, version = 1)
public abstract class DictionaryDatabase extends RoomDatabase {
    public abstract DictionaryDao dictionaryDao();

    public abstract FavouriteDao favoriteDao();

    private static DictionaryDatabase instance;

    public static DictionaryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DictionaryDatabase.class, "myanmar_dictionary")
                    .createFromAsset("myanmar_dictionary.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


}
