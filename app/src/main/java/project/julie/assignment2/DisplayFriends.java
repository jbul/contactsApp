package project.julie.assignment2;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DisplayFriends extends AppCompatActivity {

    private FriendsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_friends);


        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Friend> friends = dbHelper.allFriends();

        RecyclerView contactView = findViewById(R.id.mRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);

        contactView.setVisibility(View.VISIBLE);
        adapter = new FriendsAdapter(this, (ArrayList<Friend>) friends);
        contactView.setAdapter(adapter);

        DividerItemDecoration itemDecor = new DividerItemDecoration(contactView.getContext(), linearLayoutManager.getOrientation());
        contactView.addItemDecoration(itemDecor);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayFriends.this, AddFriend.class);
                startActivity(intent);
            }
        });

//        dbHelper = new DatabaseHelper();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu2, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchView).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFriend(query);
                return false;
            }

            private void searchFriend(String keyword) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                List<Friend> friends = databaseHelper.search(keyword);

                adapter.setmArrayList((ArrayList<Friend>) friends);
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchFriend(s);
                return false;
            }
        });
        return true;

    }
}
