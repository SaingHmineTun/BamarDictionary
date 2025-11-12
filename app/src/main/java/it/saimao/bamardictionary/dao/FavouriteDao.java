package it.saimao.bamardictionary.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import it.saimao.bamardictionary.entities.FavoriteEntity;

@Dao
public interface FavouriteDao {
    @Insert
    void insert(FavoriteEntity favoriteEntity);

    @Update
    void update(FavoriteEntity favoriteEntity);

    @Delete
    void delete(FavoriteEntity favoriteEntity);

    @Query("SELECT * FROM favorite")
    List<FavoriteEntity> getAll();

    @Query("SELECT * FROM favorite WHERE id = :id")
    FavoriteEntity getById(int id);

}
