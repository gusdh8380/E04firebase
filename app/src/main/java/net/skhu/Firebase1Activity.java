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
    //FirevaseActivity 클래스에 ValueEventListener 상속해서 구현
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase1);
        textView = findViewById(R.id.textView);

        DatabaseReference item01 = FirebaseDatabase.getInstance().getReference("item01");
        item01.addValueEventListener(this);
        //Button button = findViewById(R.id.btnSave); -> 버튼의 id 찾아서 버튼객체에 연결
        // View.OnClickListener listener2 = new View.OnClickListener() { -> clicked리스너 객체 생성
        //  @Override
        //  public void onClick(View view) { -> onclicked 메소드 재정의
        //      EditText editText = findViewById(R.id.editText); -> editText id 찾아서 연결
        //      String s = editText.getText().toString(); -> 문자열 변수 s에 연결
        //      item01.setValue(s); -> item01에 문자열 변수 s를 set 하기
        //        }
        // }; button.setOnClickListener(listener2);
        // 위 코드를 개선한 코드
        EditText editText = findViewById(R.id.editText);    //editText의 id 연결
        Button button = findViewById(R.id.btnSave);     // 버튼id 찾아서 버튼 객체에 연결
        button.setOnClickListener((view) -> item01.setValue(editText.getText().toString()));
    }   //  @Override
    //  public void onClick(View view) { -> onclicked 메소드 재정의
    //      EditText editText = findViewById(R.id.editText); -> editText id 찾아서 연결
    //      String s = editText.getText().toString(); -> 문자열 변수 s에 연결
    //      item01.setValue(s); -> item01에 문자열 변수 s를 set 하기
    //        } -> 이 코드를 Lamba expression 문법으로 익명 내부 클래스 구현

    @Override   //ValueEventListener의 메소드 재정의
    public void onDataChange(DataSnapshot dataSnapshot) {
        String value = dataSnapshot.getValue(String.class);
        textView.setText(value);
        Log.d("내태그", "받은 데이터: " + value);
    }

    @Override   //ValueEventListener의 메소드 재정의
    public void onCancelled(DatabaseError error) {

        Log.e("내태그", "서버 에러: ", error.toException());
    }
}