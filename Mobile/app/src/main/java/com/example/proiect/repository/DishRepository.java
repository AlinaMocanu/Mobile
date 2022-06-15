package com.example.proiect.repository;

import static android.content.ContentValues.TAG;

import static com.example.proiect.ui.dashboard.DashboardFragment.readData;

import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect.domain.Dish;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DishRepository {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    public static void saveDish(Dish dish, byte[] data) {
        mDatabase.child("dishes").child(dish.getDishName()).setValue(dish);
        storageRef.child(dish.getDishName()).putBytes(data);
    }


    public static void readListener() {

        ValueEventListener dishListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                readData((Map<String, Dish>)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("dishes").addValueEventListener(dishListener);
    }
}
