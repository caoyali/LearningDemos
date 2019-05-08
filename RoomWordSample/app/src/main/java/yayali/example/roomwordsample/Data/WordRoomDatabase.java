package yayali.example.roomwordsample.Data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {

    private static volatile WordRoomDatabase INSTANCE;

    //数据库的创建是可以在主线程中做的。
    //但是但是，它的增删查改，必须在子线程里面完成
    public static WordRoomDatabase getDataBase(Context context){
        if (null == INSTANCE){
            synchronized (WordRoomDatabase.class){
                if (null == INSTANCE){
                    //创建一个新的单例，要单例。是这么要求的
                    INSTANCE = Room.databaseBuilder(context, WordRoomDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract WordDao wordDao();

    //在数据库创建完之后先插入一些默认数据
    private static RoomDatabase.Callback sRoomDatabaseCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        WordDao wordDao;

        public PopulateDbAsync(WordRoomDatabase db) {
            this.wordDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAll();
            Word word = new Word("Hello");
            wordDao.insert(word);
            word = new Word("world");
            wordDao.insert(word);
            return null;
        }
    }
}
