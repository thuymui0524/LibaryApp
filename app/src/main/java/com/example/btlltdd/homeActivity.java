package com.example.btlltdd;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;
public class homeActivity extends AppCompatActivity {
    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private List<String> bookList;
    private EditText searchEditText;
    private ViewPager2 bannerViewPager;
    private List<Integer> bannerImages;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bannerViewPager = findViewById(R.id.bannerViewPager);
        recyclerView = findViewById(R.id.bookRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Khởi tạo Adapter và truyền dữ liệu vào
        itemAdapter = new ItemAdapter(getItems());
        recyclerView.setAdapter(itemAdapter);
        // Thêm các hình ảnh banner vào List
        bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.banner);
        bannerImages.add(R.drawable.tuyensinhdhmo);
        bannerImages.add(R.drawable.image);

        // Cài đặt Adapter cho ViewPager
        BannerAdapter bannerAdapter = new BannerAdapter(bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);
        //tao handler
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == bannerImages.size()) {
                    currentPage = 0;
                }
                bannerViewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000); // Trượt sau mỗi 3 giây
            }
        };
        handler.postDelayed(runnable, 3000);
        // Khởi tạo các thành phần
        ImageView bannerImageView = findViewById(R.id.bannerImageView);
        searchEditText = findViewById(R.id.searchEditText);
        bookRecyclerView = findViewById(R.id.bookRecyclerView);

        recyclerView = findViewById(R.id.bookRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Hiển thị 2 cột

        bookList = new ArrayList<>();
        bookList.add(String.valueOf(new Book("Đồng hồ",R.drawable.banner)));
        bookList.add(String.valueOf(new Book("Đếm chim", R.drawable.giaotrinhtinhocdaicuong_001thumbimage)));
        bookList.add(String.valueOf(new Book("Thanh sắc núi rừng", R.drawable.giaotrinhtinhocdaicuong_001thumbimage)));
        bookList.add(String.valueOf(new Book("Đồng hồ",R.drawable.banner)));
        // Thêm các sách khác

        bookAdapter = new BookAdapter(this, bookList);
        recyclerView.setAdapter(bookAdapter);
        // Tạo danh sách sách
//        bookList = new ArrayList<>();
//        bookList.add("Book 1");
//        bookList.add("Book 2");
//        bookList.add("Book 3");
//
//        // Cấu hình RecyclerView
//        bookAdapter = new BookAdapter(this, bookList);
//        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        bookRecyclerView.setAdapter(bookAdapter);
//
//        // Xử lý tìm kiếm khi nhập dữ liệu
//        searchEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                filterBooks(charSequence.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {}
//        });
//
//        // Xử lý các sự kiện của thanh điều hướng
//        findViewById(R.id.homeButton).setOnClickListener(v -> Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show());
//        findViewById(R.id.categoriesButton).setOnClickListener(v -> Toast.makeText(this, "Categories", Toast.LENGTH_SHORT).show());
//        findViewById(R.id.favoritesButton).setOnClickListener(v -> Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show());
//        findViewById(R.id.profileButton).setOnClickListener(v -> Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show());
    }

    // Hàm lọc sách
    private void filterBooks(String text) {
        List<String> filteredList = new ArrayList<>();
        for (String book : bookList) {
            if (book.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(book);
            }
        }
        bookAdapter = new BookAdapter(this, filteredList);
        bookRecyclerView.setAdapter(bookAdapter);
    }
    private List<String> getItems() {
        List<String> items = new ArrayList<>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");
        items.add("Item 5");
        return items;
    }

}
