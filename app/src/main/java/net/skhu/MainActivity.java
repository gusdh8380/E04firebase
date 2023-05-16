package net.skhu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //public void button_clicked(View view) {
    //  Intent intent;
    //  switch (view.getId()) {
    //      case R.id.button1: intent = new Intent(this, Firebase1Activity.class);
    //          startActivity(intent);
    //          break;
    //      case R.id.button2: intent = new Intent(this, MemoList1Activity.class);
    //          startActivity(intent);
    //          break;
    //          }
    //   }

    public void button_clicked(View view) {
        Intent intent = new Intent(this, Firebase1Activity.class);
        startActivity(intent);
    }
    public void button_clicked1(View view) {
        Intent intent = new Intent(this, MemoListActivity.class);
        startActivity(intent);
    }
}