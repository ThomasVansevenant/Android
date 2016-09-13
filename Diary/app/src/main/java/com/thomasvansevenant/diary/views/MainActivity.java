package com.thomasvansevenant.diary.views;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.thomasvansevenant.diary.R;
import com.thomasvansevenant.diary.persistence.MyDB;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.provider.BaseColumns._ID;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.CONTENT_URI;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.DB_TABLE_NAME;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.DIARY_CONTEXT;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.DIARY_ID;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.DIARY_RECORDDATE;
import static com.thomasvansevenant.diary.persistence.Constants.DiaryEntry.DIARY_TITLE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private MyDB myDB;
    private static final int DIARY_LOADER = 0;
    private static final String[] DIARY_COLUMNS =
            {
                    DB_TABLE_NAME + "." + _ID,
                    DIARY_ID,
                    DIARY_TITLE,
                    DIARY_CONTEXT,
                    DIARY_RECORDDATE
            };

    @Bind(R.id.listview_diary)
    ListView listView;

    private SimpleCursorAdapter mDiaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        addToDatabase();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        getLoaderManager().initLoader(DIARY_LOADER, null, this);

        myDB = new MyDB(this);

        //SimpleCursorAdapter*
        mDiaryAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item_diary,
                null,
                //these column name
                new String[]{
                        DIARY_TITLE,
                        DIARY_RECORDDATE},
                new int[]{
                        R.id.list_item_title_textview,
                        R.id.list_item_date_textview
                }, 0
        );

        listView.setAdapter(mDiaryAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("SI", id);
                startActivity(intent);
            }
        });
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
        if (id == R.id.action_reset_db) {

            myDB.deleteAllRecords();
            finish();
            startActivity(getIntent());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Sort order:  Ascending, by date.
        String sortOrder = DIARY_RECORDDATE + " ASC";

        return new CursorLoader(this,
                CONTENT_URI,
                DIARY_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //after data fetched change cursor in adapter
        mDiaryAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDiaryAdapter.swapCursor(null);
    }

    @OnClick(R.id.to_insert_activity)
    void goToInsertActivity() {
        Intent intent = new Intent(this, InsertDiaryActivity.class);
        startActivity(intent);
    }

    /**
     * *SimpleCursorAdapter
     * Parameters
     * context: The context where the ListView associated with this SimpleListItemFactory is running
     * layout:  resource identifier of a layout file that defines the views for this list item. The layout file should include at least those named views defined in "to"
     * c:   The database cursor. Can be null if the cursor is not available yet.
     * from:    A list of column names representing the data to bind to the UI. Can be null if the cursor is not available yet.
     * to:  The views that should display column in the "from" parameter. These should all be TextViews. The first N views in this list are given the values of the first N columns in the from parameter. Can be null if the cursor is not available yet.
     * flags:   Flags used to determine the behavior of the adapter, as per CursorAdapter(Context, Cursor, int).
     */

}
