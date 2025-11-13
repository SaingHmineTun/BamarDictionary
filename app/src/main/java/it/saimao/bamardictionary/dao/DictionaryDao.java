package it.saimao.bamardictionary.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import it.saimao.bamardictionary.entities.DictionaryEntity;

@Dao
public interface DictionaryDao {
    /*
    contains    - %stripword%
    starts with - stripword%
    ends with   - %stripword
     */
    @Query("SELECT * FROM dictionary LIMIT 100")
    List<DictionaryEntity> getAllDictionary();
    @Query("SELECT * FROM dictionary WHERE stripword LIKE :stripWord LIMIT 100")
    List<DictionaryEntity> getDictionaryByStripWord(String stripWord);

    @Query("SELECT * FROM dictionary WHERE stripword = :stripWord")
    DictionaryEntity getDictionaryByStripWordExactly(String stripWord);

    @Query("SELECT * FROM dictionary WHERE _id = :id")
    DictionaryEntity getDictionaryById(int id);
}
