
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
public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    SQLiteDatabase db = mHelper.getReadableDatabase(); // 获取可读数据库实例
    Cursor cursor; // 声明 Cursor 用于保存查询结果

    switch (mMatcher.match(uri)) { // 根据 URI 进行匹配
        // 其他case省略...

        case URI_NOTES: // 处理笔记查询
            cursor = db.query(NoteColumns.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            break;

        default:
            throw new IllegalArgumentException("Unknown URI " + uri); // 未知 URI 时抛出异常
    }
    
    cursor.setNotificationUri(getContext().getContentResolver(), uri); // 设置通知 URI，便于更新数据
    return cursor; // 返回查询结果
}
