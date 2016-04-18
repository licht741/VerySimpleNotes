package com.example.licht.simplenotes;

import android.content.Intent;
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
        contentListView = (ListView)findViewById(R.id.contentListView);

        mConnector = new DatabaseConnector(getApplicationContext());

        notes = mConnector.getAllRecords();
        adapter = new ArrayAdapter<>(this, R.layout.record_layout, notes);
        contentListView.setAdapter(adapter);
    }

    public void addNoteHandler(View view) {

        EditText source = (EditText)findViewById(R.id.newRecordEditText);
        CheckBox reverseOrderCheckBox = (CheckBox)findViewById(R.id.reverseOrderCheckBox);

        String newNote = source.getText().toString();

        boolean isValidNote = checkNote(newNote);
        if (!isValidNote)
            return;

        boolean additionToEnd = reverseOrderCheckBox.isChecked();

        if (additionToEnd)
            notes.add(notes.size() ,newNote);
        else
            notes.add(0, newNote);
        mConnector.addRecord(newNote);

        source.setText("");
        adapter.notifyDataSetChanged();
    }

    public void shareHandler(View view) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.notes_head))
                .append("\n");

        for (String note: notes)
            stringBuilder.append(note)
                    .append("\n");

        String result = stringBuilder.toString();

        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_TEXT, result);


        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    private boolean checkNote(String note) {
        return !note.equals("");
    }
}
