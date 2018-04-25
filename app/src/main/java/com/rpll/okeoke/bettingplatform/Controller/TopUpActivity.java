package com.rpll.okeoke.bettingplatform.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rpll.okeoke.bettingplatform.Model.User;
import com.rpll.okeoke.bettingplatform.R;

public class TopUpActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView textView;
    FirebaseDatabase database;
    int point;
    int amount;
    Button btnSubmit;
    User user;
    EditText editAmount;
    DatabaseReference myRef2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);
        database = FirebaseDatabase.getInstance();
        myRef2 = database.getReference();
        auth = FirebaseAuth.getInstance();
        final String email = auth.getCurrentUser().getEmail();
        String encodedEmail = User.encodeUserEmail(email);
        DatabaseReference myRef = database.getReference("Users").child(encodedEmail);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String fullname = dataSnapshot.child("fullname").getValue(String.class);
                String username = dataSnapshot.child("username").getValue(String.class);
                String password = dataSnapshot.child("password").getValue(String.class);
                point = dataSnapshot.child("point").getValue(int.class);
                textView = (TextView) findViewById(R.id.txtProfile);
                textView.setText("Email: " + email + "\n" + "Your Point: " + point + "\n");
                user = new User(username, fullname, password, point);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("sout", "Failed to read value.", error.toException());
            }
        });
        editAmount = (EditText) findViewById(R.id.amount);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("BCA");
        adapter.add("Indomaret");
        adapter.add("Mandiri");
        spinner.setAdapter(adapter);
    }

    public void buttonPopup(View view) {
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.topupactivity);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.popup_topup, null);
        if (editAmount.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Enter Amount.", Toast.LENGTH_SHORT).show();
        }
        else{
            amount = Integer.parseInt(editAmount.getText().toString());
            TextView amountText = (TextView)popup.findViewById(R.id.poptoptxt2);
            amountText.setText("Rp. " + amount +" ,-");
            boolean focusable = true;
            final PopupWindow popupWindow = new PopupWindow(popup,660,900,focusable);
            popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            Button submitTopup = popup.findViewById(R.id.poptopbtn);
            submitTopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                    String encodedEmail = User.encodeUserEmail(auth.getCurrentUser().getEmail());
                    user.setPoint(user.getPoint() + amount);
                    myRef2.child("Users").child(encodedEmail).setValue(user, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                Toast.makeText(getApplicationContext(), "Top Up Failed..", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Top Up Successfull.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });
                    popupWindow.dismiss();
                    startActivity(intent);
                    finish();
                }
            });
            }
        }

}
