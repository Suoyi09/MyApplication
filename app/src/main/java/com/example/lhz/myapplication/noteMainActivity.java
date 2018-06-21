package com.example.lhz.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NoteMainActivity extends ListActivity {
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;

    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;

    private NoteDbAdapter mDbHelper;
    private Cursor mDiaryCursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);
        mDbHelper = new NoteDbAdapter(this);
        mDbHelper.open();
        renderListView();

    }

    private void renderListView() {
        mDiaryCursor = mDbHelper.getAllNotes();
        startManagingCursor(mDiaryCursor);
        String[] from = new String[] { NoteDbAdapter.KEY_TITLE,
                NoteDbAdapter.KEY_CREATED };
        int[] to = new int[] { R.id.text1, R.id.created };
        SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
                R.layout.activity_note_list, mDiaryCursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createDiary();
                return true;
            case DELETE_ID:
                mDbHelper.deleteDiary(getListView().getSelectedItemId());
                renderListView();
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void createDiary() {
        Intent i = new Intent(this, NoteEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Cursor c = mDiaryCursor;
        c.moveToPosition(position);
        Intent i = new Intent(this, NoteEditActivity.class);
        i.putExtra(NoteDbAdapter.KEY_ROWID, id);
        i.putExtra(NoteDbAdapter.KEY_TITLE, c.getString(c
                .getColumnIndexOrThrow(NoteDbAdapter.KEY_TITLE)));
        i.putExtra(NoteDbAdapter.KEY_BODY, c.getString(c
                .getColumnIndexOrThrow(NoteDbAdapter.KEY_BODY)));
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        renderListView();
    }
}
