
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
import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity extends AppCompatActivity {
    private EditText searchInput;
    private ListView searchResultsListView;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search); // 在布局文件中定义

        searchInput = findViewById(R.id.search_input);
        searchResultsListView = findViewById(R.id.search_results_list_view);
        Button searchButton = findViewById(R.id.search_button);

        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{NoteColumns.CONTENT}, // 假设CONTENT是笔记的内容字段
                new int[]{android.R.id.text1},
                0);
        searchResultsListView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchInput.getText().toString();
                searchNotes(query);
            }
        });

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
    }

    private void searchNotes(String query) {
        String selection = NoteColumns.CONTENT + " LIKE ?"; // 假设根据笔记内容搜索
        String[] selectionArgs = new String[]{"%" + query + "%"}; // 模糊查询

        Cursor cursor = getContentResolver().query(
                Uri.parse("content://" + Notes.AUTHORITY + "/notes"), // 查询笔记的URI
                null, selection, selectionArgs, null);

        adapter.changeCursor(cursor); // 更新适配器以显示结果
    }

    private void loadAllNotes() {
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://" + Notes.AUTHORITY + "/notes"),
                null, null, null, null);

        adapter.changeCursor(cursor); // 显示所有笔记
    }
}
