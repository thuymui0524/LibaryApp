package com.example.btlltdd;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.btlltdd.Adapter.BannerAdapter;
import com.example.btlltdd.Adapter.BookAdapter;
import com.example.btlltdd.Adapter.ItemAdapter;
import com.example.btlltdd.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
public class homeActivity extends AppCompatActivity {

    private BookAdapter bookAdapter;
    private ImageView btnsearch;
    private EditText searchEditText;
    private ViewPager2 bannerViewPager;
    private List<Integer> bannerImages;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private ArrayList<Book> bookList;
    private DatabaseHelper databaseHelper;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ImageView homeIcon = findViewById(R.id.home_icon);
        bannerViewPager = findViewById(R.id.bannerViewPager);
        recyclerView = findViewById(R.id.bookRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        itemAdapter = new ItemAdapter(getItems());
        recyclerView.setAdapter(itemAdapter);
        // Thêm các hình ảnh banner vào List
        bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.banner);
        bannerImages.add(R.drawable.tuyensinhdhmo);
        bannerImages.add(R.drawable.image);
        bannerImages.add(R.drawable.elearning);

        // Cài đặt Adapter cho ViewPager
        BannerAdapter bannerAdapter = new BannerAdapter(bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == bannerImages.size()) {
                    currentPage = 0;
                }
                bannerViewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000);
            }
        };
//        chuc nang tim kiem
        btnsearch=findViewById(R.id.btnsearch);
        searchEditText=findViewById(R.id.searchEditText);
        databaseHelper = new DatabaseHelper(this);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBook();
            }
        });

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    searchBook();
                    return true;
                }
                return false;
            }
        });




        handler.postDelayed(runnable, 3000);

        ImageView bannerImageView = findViewById(R.id.bannerImageView);
        searchEditText = findViewById(R.id.searchEditText);

        recyclerView = findViewById(R.id.bookRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);
        bookList = databaseHelper.getAllBook();
        bookAdapter = new BookAdapter(this, bookList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(bookAdapter);

        // Thêm sách
        databaseHelper.addBook(R.drawable.img, "giao trinh triet hoc Mac-Lenin");
        databaseHelper.addBook(R.drawable.giaotrinhtinhocdaicuong_001thumbimage, "Truyện B");

        //popupmenu
        homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(homeActivity.this, homeIcon);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.action_tailieugiay) {

                            Intent intentChangePassword = new Intent(homeActivity.this, tailieugiayActivity.class);
                            startActivity(intentChangePassword);
                            return true;
                        } else if (id == R.id.action_tailieuin) {

                            Intent intentLoanHistory = new Intent(homeActivity.this, tailieuinActivity.class);
                            startActivity(intentLoanHistory);
                            return true;
                        } else if (id == R.id.action_edu_material) {

                            Intent intentLoanHistory = new Intent(homeActivity.this, hoclieuActivity.class);
                            startActivity(intentLoanHistory);
                            return true;

                        }else if (id == R.id.action_borrow) {
                            Intent intentLoanHistory = new Intent(homeActivity.this, BorrowActivity.class);
                            startActivity(intentLoanHistory);
                            return true;
                        }else if (id == R.id.action_logout) {

                            Intent intentLoanHistory = new Intent(homeActivity.this, logoutActivity.class);
                            startActivity(intentLoanHistory);
                            return true;
                        }

                        return false;
                    }

                });

                popupMenu.show();
            }
        });

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
    //phuong thuc tim kiem sach
    private void searchBook() {
        String keyword = searchEditText.getText().toString().trim();
        if (keyword.equalsIgnoreCase(keyword)) {
            List<String> searchResults = databaseHelper.searchTruyen(keyword);


            Intent intent = new Intent(homeActivity.this, SearchResultActivity.class);
            intent.putStringArrayListExtra("searchResults", (ArrayList<String>) searchResults);
            startActivity(intent);
        } else {

            Toast.makeText(homeActivity.this, "Vui lòng nhập từ khóa!", Toast.LENGTH_SHORT).show();
        }
    }

}
