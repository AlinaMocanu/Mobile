package com.example.proiect.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect.R;
import com.example.proiect.databinding.FragmentDashboardBinding;
import com.example.proiect.domain.Dish;
import com.example.proiect.repository.DishRepository;

import java.util.ArrayList;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private static FragmentDashboardBinding binding;
    private static RecyclerViewAdapter adapter;
    private static Context context;
    private static ArrayList<String> dishNames = new ArrayList<String>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        context = getContext();
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        DishRepository.readListener();

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        return root;
    }

    // calling on create option menu
    // layout to inflate our menu file.
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<String> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (String item : dishNames) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    public void onItemClick(View view, int position) {
        Toast.makeText(getContext(), "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void readData(Map<String, Dish> dishes) {
       dishNames.clear();
        for (Map.Entry<String, Dish> entry : dishes.entrySet()) {
            Map singleDish = (Map) entry.getValue();
            dishNames.add((String)singleDish.get("dishName"));
        }


        if (binding != null) {
            RecyclerView recyclerView = binding.recyclerView;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new RecyclerViewAdapter(context, dishNames);
            // adapter.setClickListener(context::onItemClick);
            recyclerView.setAdapter(adapter);
        }
    }
}