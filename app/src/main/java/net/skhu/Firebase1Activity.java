package net.skhu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Firebase1Activity extends AppCompatActivity implements ValueEventListener {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase1);
        textView = findViewById(R.id.textView);
        DatabaseReference item01 = FirebaseDatabase.getInstance().getReference("item01");
        item01.addValueEventListener(this);
        EditText editText = findViewById(R.id.editText);
        Button button = findViewById(R.id.btnSave);
        button.setOnClickListener((view) -> item01.setValue(editText.getText().toString()));
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String value = dataSnapshot.getValue(String.class);
        textView.setText(value);
        Log.d("내태그", "받은 데이터: " + value);
    }

    @Override
    public void onCancelled(DatabaseError error) {
        Log.e("내태그", "서버 에러: ", error.toException());
    }
}