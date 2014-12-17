package com.example.rbates.rxplayground;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.searchapi.SearchResult;
import com.example.searchapi.TagSearch;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.android.observables.ViewObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "RxPlayground";

    @Inject
    TagSearch tagSearch;

    @InjectView(R.id.list)
    ListView listView;

    @InjectView(R.id.edit_text)
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        ((RxPlaygroundApplication) getApplication()).getApplicationGraph().inject(this);
        ViewObservable.text(editText)
                .debounce(800, TimeUnit.MILLISECONDS, Schedulers.io())
                .map(e -> e.text.toString())
                .filter(s -> s.length() > 3)
                .map(s ->{Log.d(TAG, "Search term is " + s);return s;})
                .map(this::search)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayResults, this::logError);

    }

    private void displayResults(List<SearchResult> r) {
        listView.setAdapter(new SearchResultAdapter(this, r));
    }

    private void logError(Throwable throwable){
        Log.e(TAG, "Error in search", throwable);
    }

    private List<SearchResult> search(String s) {
        return tagSearch.search(s);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class SearchResultAdapter extends ArrayAdapter<SearchResult> {

        public SearchResultAdapter(Context context, List<SearchResult> r) {
            super(context, android.R.layout.simple_list_item_1, android.R.id.text1, r);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = super.getView(position, convertView, parent);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).getName());
            return convertView;
        }
    }
}
