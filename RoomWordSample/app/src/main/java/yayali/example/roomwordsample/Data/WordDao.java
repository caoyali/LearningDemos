package yayali.example.roomwordsample.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert(Word word);

    @Query("DELETE FROM " + Word.TABLE_NAME)
    void deleteAll();

    @Query("SELECT * from " + Word.TABLE_NAME + " ORDER BY word ASC")
    LiveData<List<Word>> getAllWords();
}
