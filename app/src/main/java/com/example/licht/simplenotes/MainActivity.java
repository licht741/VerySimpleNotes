package com.example.licht.simplenotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> notes;
    ListView contentListView;
    ArrayAdapter<String> adapter;

    DatabaseConnector mConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnector = new DatabaseConnector(getApplicationContext());


        contentListView = (ListView)findViewById(R.id.contentListView);

        notes = mConnector.getAllRecords();

        adapter = new ArrayAdapter<>(this, R.layout.record_layout, notes);
        contentListView.setAdapter(adapter);




    }

    public void addNoteHandler(View view) {

        EditText source = (EditText)findViewById(R.id.newRecordEditText);
        CheckBox reverseOrderCheckBox = (CheckBox)findViewById(R.id.reverseOrderCheckBox);

        String newNote = source.getText().toString();
        //adapter.insert(newNote, adapter.getCount());

        mConnector.addRecord(newNote);
        if (reverseOrderCheckBox.isChecked())
            notes.add(notes.size() ,newNote);
        else
            notes.add(0, newNote);


        source.setText("");

        adapter.notifyDataSetChanged();
    }
}
