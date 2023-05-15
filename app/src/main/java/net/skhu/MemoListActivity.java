package net.skhu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.List;

public class MemoListActivity extends AppCompatActivity {
    MemoAdapter memoAdapter;
    ArrayList<Memo> arrayList;
    ActivityResultLauncher<Intent> activityResultLauncher;
    DatabaseReference item02;
    ValueEventListener firebaseListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            GenericTypeIndicator<ArrayList<Memo>> typeIndicator;
            typeIndicator = new GenericTypeIndicator<ArrayList<Memo>>() {
            };
            ArrayList<Memo> temp = dataSnapshot.getValue(typeIndicator);
            if (temp != null) {
                arrayList.clear();
                arrayList.addAll(temp);
                memoAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError error) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);
        arrayList = new ArrayList<Memo>();
        memoAdapter = new MemoAdapter(this, arrayList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(memoAdapter);
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    Memo memo = (Memo) intent.getSerializableExtra("MEMO");
                    Integer index = (Integer) intent.getSerializableExtra("index");
                    if (index == null) arrayList.add(memo);
                    else arrayList.set(index, memo);
                    item02.setValue(arrayList);
                    memoAdapter.notifyDataSetChanged();
                }
            }
        });
        this.item02 = FirebaseDatabase.getInstance().getReference("item02");
        this.item02.addValueEventListener(firebaseListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_memo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_create) {
            Intent intent = new Intent(this, MemoActivity.class);
            activityResultLauncher.launch(intent);
        } else if (id == R.id.action_remove) {
            removeMemos();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeMemos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.doYouWantToDelete);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {
                ListIterator<Memo> iterator = arrayList.listIterator();
                while (iterator.hasNext()) if (iterator.next().isChecked()) iterator.remove();
                item02.setValue(arrayList);
                //memoAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton(R.string.no, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void onMemoClicked(int index) {
        Intent intent = new Intent(this, MemoActivity.class);
        Memo memo = arrayList.get(index);
        intent.putExtra("MEMO", memo);
        intent.putExtra("index", index);
        activityResultLauncher.launch(intent);
    }
}