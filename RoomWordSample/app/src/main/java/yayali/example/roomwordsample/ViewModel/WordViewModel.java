package yayali.example.roomwordsample.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import yayali.example.roomwordsample.Data.Word;
import yayali.example.roomwordsample.Repositroy.WordReporsitroy;

public class WordViewModel extends AndroidViewModel {
    private WordReporsitroy mWordReporsitroy;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mWordReporsitroy = new WordReporsitroy(application);
        mAllWords = mWordReporsitroy.getmAllWords();
    }

    /**
     * 这代码写的太精辟了。
     * 原先的我，肯定会重新调用一次mWordReporsitroy.getmAllWords();
     * 但是，完全忽略了，他们完全是一回事儿，是引用，同一个引用，根本没必要写。
     * 当mWordReporsitroy中的{@link WordReporsitroy # mAllWords}发生变化时，这个自然会变化，因为是同一个！
     * @return
     */
    public LiveData<List<Word>> getmAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        mWordReporsitroy.insert(word);
    }

    public void deleteAll(){
        mWordReporsitroy.deleteAll();
    }
}
