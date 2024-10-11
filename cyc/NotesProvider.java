
package net.micode.notes.data;


import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import net.micode.notes.R;
import net.micode.notes.data.Notes.DataColumns;
import net.micode.notes.data.Notes.NoteColumns;
import net.micode.notes.data.NotesDatabaseHelper.TABLE;
@Override
public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                    String sortOrder) {
    Cursor c = null;
    SQLiteDatabase db = mHelper.getReadableDatabase();
    String id = null;
    switch (mMatcher.match(uri)) {
        case URI_NOTES:
            c = db.query(NoteColumns.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            break;
        default:
            throw new IllegalArgumentException("Unknown URI " + uri);
    }
    if (c != null) {
        c.setNotificationUri(getContext().getContentResolver(), uri);
    }
    return c;
}
