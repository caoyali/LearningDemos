package yayali.example.roomwordsample.Data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Word.TABLE_NAME)
public class Word {

    public static final String TABLE_NAME = "word_table";
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(String mWord) {
        this.mWord = mWord;
    }

    public String getWord() {
        return mWord;
    }
}
