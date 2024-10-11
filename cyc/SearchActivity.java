package net.micode.notes;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    private EditText searchInput; // 用于用户输入搜索关键词的 EditText
    private ListView searchResultsListView; // 显示搜索结果的 ListView
    private SimpleCursorAdapter adapter; // 适配器，用于将数据库查询结果绑定到 ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search); // 设置 Activity 的布局文件

        // 获取界面元素
        searchInput = findViewById(R.id.search_input);
        searchResultsListView = findViewById(R.id.search_results_list_view);
        Button searchButton = findViewById(R.id.search_button); // 搜索按钮

        // 设置适配器，绑定数据库查询结果到 ListView
        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, // 每个列表项的布局
                null, // 初始无数据
                new String[]{NoteColumns.CONTENT}, // 要显示的字段
                new int[]{android.R.id.text1}, // 布局中的 TextView ID
                0);
        searchResultsListView.setAdapter(adapter); // 设置适配器

        // 设置搜索按钮的点击事件
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString(); // 获取用户输入的查询词
            if (query.isEmpty()) {
                loadAllNotes(); // 如果查询词为空，则加载所有笔记
            } else {
                searchNotes(query); // 否则执行搜索
            }
        });

        // 监听输入框文本变化
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    loadAllNotes(); // 清空输入框时加载所有笔记
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // 设置 ListView 项目的点击事件
        searchResultsListView.setOnItemClickListener((parent, view, position, id) -> {
            // 点击事件处理，打开选中的笔记
            Toast.makeText(this, "打开笔记 ID: " + id, Toast.LENGTH_SHORT).show(); // 显示 Toast 提示
        });
    }

    // 根据用户输入的查询词搜索笔记
    private void searchNotes(String query) {
        String selection = NoteColumns.CONTENT + " LIKE ?"; // 设置查询条件
        String[] selectionArgs = new String[]{"%" + query + "%"}; // 使用模糊查询
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://" + Notes.AUTHORITY + "/notes"), // 查询 URI
                null, selection, selectionArgs, null); // 执行查询

        if (cursor != null) {
            adapter.changeCursor(cursor); // 更新适配器以显示查询结果
        } else {
            Toast.makeText(this, "未找到匹配的笔记", Toast.LENGTH_SHORT).show(); // 查询结果为空时提示用户
        }
    }

    // 加载所有笔记
    private void loadAllNotes() {
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://" + Notes.AUTHORITY + "/notes"), // 查询 URI
                null, null, null, null); // 查询所有笔记

        adapter.changeCursor(cursor); // 显示所有笔记
    }
}
