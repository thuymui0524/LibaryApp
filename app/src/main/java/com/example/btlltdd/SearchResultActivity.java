package com.example.btlltdd;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    private ListView resultListView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        resultListView = findViewById(R.id.resultListView);

        // Nhận dữ liệu tìm kiếm từ Intent
        Intent intent = getIntent();
        ArrayList<String> searchResults = intent.getStringArrayListExtra("searchResults");

        if (searchResults != null && !searchResults.isEmpty()) {
            // Hiển thị dữ liệu tìm kiếm lên ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
            resultListView.setAdapter(adapter);
        } else {
            Toast.makeText(this, "Không tìm thấy kết quả nào!", Toast.LENGTH_SHORT).show();
        }
    }
}
