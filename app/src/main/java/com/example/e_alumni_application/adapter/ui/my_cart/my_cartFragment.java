package com.example.e_alumni_application.adapter.ui.my_cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_alumni_application.R;
import com.example.e_alumni_application.activites.PlacedOrderActivity;
import com.example.e_alumni_application.adapter.MyCartAdapter;
import com.example.e_alumni_application.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class my_cartFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount;
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    Button buyNow;
    int totalBill;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);

       db = FirebaseFirestore.getInstance();
       auth = FirebaseAuth.getInstance();
       recyclerView = root.findViewById(R.id.recyclerview);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       buyNow = root.findViewById(R.id.buy_now);

       overTotalAmount = root.findViewById(R.id.textView4);


       cartModelList = new ArrayList<>();
       cartAdapter = new MyCartAdapter(getActivity(),cartModelList);
       recyclerView.setAdapter(cartAdapter);

       db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
               .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot> task) {

                       if (task.isSuccessful()){
                           for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                               String documentID = documentSnapshot.getId();

                               MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);

                               cartModel.setDocumentID(documentID);
                               cartModelList.add(cartModel);
                               cartAdapter.notifyDataSetChanged();
                           }

                           calculateTotalAmount(cartModelList);
                       }

                   }
               });

       buyNow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
               intent.putExtra("itemlist", (Serializable) cartModelList);
               startActivity(intent);

           }
       });


        return root;
    }

    private void calculateTotalAmount(List<MyCartModel> cartModelList) {

        double totalAmount = 0.0;
        for (MyCartModel myCartModel : cartModelList){

            totalAmount += myCartModel.getTotalPrice();
        }

        overTotalAmount.setText("Total Amount : RM " + totalAmount);
    }

}
