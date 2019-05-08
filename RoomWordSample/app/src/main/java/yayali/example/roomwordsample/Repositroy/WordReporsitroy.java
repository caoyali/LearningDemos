package yayali.example.roomwordsample.Repositroy;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import yayali.example.roomwordsample.Data.Word;
import yayali.example.roomwordsample.Data.WordDao;
import yayali.example.roomwordsample.Data.WordRoomDatabase;

/**
 * 一个数据仓库，专门处理数据。但是这个数据怎么来的，其余部分完全不care！！
 * 如：在这个仓库类中，你可以选择从网络中下载数据，或者从数据库中获取数据，完全根据当前所在的环境判断！
 * 这些都是你自己处理的！！你就是个纯粹的数据创造者！
 */
public class WordReporsitroy {
    private WordDao mWordDao;

    //这个很可能是某个外部类，要从你这里拿的数据！
    private LiveData<List<Word>> mAllWords;

    public WordReporsitroy(Context context) {

        //初始化最初的数据，如 你操作数据库必须用到的mWordDao
        WordRoomDatabase wdb = WordRoomDatabase.getDataBase(context);
        //wordDao这个方法，自有框架帮你实现。这个不用管了。
        mWordDao = wdb.wordDao();
        //初始化另外一个数据
        //其实当执行完这一步的时候，就很明显的改了mAllWords的值。那些观察这个值的观察者们，就会自动触发对应的方法！
        //并且这里的被观察对象，不难看出，从始至终，都是Room在维护的。
        mAllWords = mWordDao.getAllWords();
    }

    /**
     * 让外部要注册的观察者得到这个关键的对象。这样他们才好利用它进行注册，从而实现观察！
     * @return
     */
    public LiveData<List<Word>> getmAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll(){
        new deleteAllAsynTask(mWordDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Word, Void, Void>{

        private WordDao mAsyncTaskDao;

        public insertAsyncTask(WordDao mWordDao) {
            this.mAsyncTaskDao = mWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }

    private static class deleteAllAsynTask extends AsyncTask{
        private WordDao mAsyncTaskDao;

        public deleteAllAsynTask(WordDao mWordDao) {
            this.mAsyncTaskDao = mWordDao;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
