package yayali.example.roomwordsample.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static volatile WordRoomDatabase INSTANCE;

    //貌似这个方法只能在主线程中执行
    public static WordRoomDatabase getDataBase(Context context){
        if (null == INSTANCE){
            synchronized (WordRoomDatabase.class){
                if (null == INSTANCE){
                    //创建一个新的单例，要单例。是这么要求的
                    INSTANCE = Room.databaseBuilder(context, WordRoomDatabase.class, "word_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WordDao wordDao();
}
