package com.example.iotools;

import com.example.iotools.model.gridList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Date;



public class addNoteActivity extends AppCompatActivity {

    public String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
    public TextView showDateTime;
    public TextInputEditText editText, topicInput;
    ArrayList<gridList> gridArray;
    String id;
    String date;
    String note;
    String topic;
    String picture;
    Intent returnIntent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);



        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        gridArray = (ArrayList<gridList>) args.getSerializable("ARRAYLIST");

        showDateTime = findViewById(R.id.dateText);
        showDateTime.setText(currentDateTimeString);
        editText = findViewById(R.id.noteTextInput);
        topicInput = findViewById(R.id.topicTextInput);




        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused)
                    keyboard.showSoftInput(editText, 0);
                else
                    keyboard.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        });

        topicInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused)
                    keyboard.showSoftInput(topicInput, 0);
                else
                    keyboard.hideSoftInputFromWindow(topicInput.getWindowToken(), 0);
            }
        });

    }

    public void addNotetoGrid(View view){
        date =(showDateTime.getText().toString());
        note =(editText.getText().toString());
        topic =(topicInput.getText().toString());
        picture =("0");
        if(gridArray.isEmpty() == true){
            id = "0";
        }
        else{
            id = (String.valueOf(gridArray.size()));
        }

        gridList tempGrid = new gridList();
        tempGrid.setTopic(topic);
        tempGrid.set_id(id);
        tempGrid.setCreated_date(date);
        tempGrid.setNote(note);
        tempGrid.setPicture(picture);
        tempGrid.setType(0);

        Bundle gridBundle = new Bundle();
        gridBundle.putParcelable("NEWgrid", tempGrid);
        returnIntent.putExtra("BUNDLE", gridBundle);

        setResult(RESULT_OK, returnIntent);
        showToastMessage(1);
        finish();
    }

    public void showToastMessage(int id){
        String toastMessage = null;

        if(id == 1){
            toastMessage = "Note created";
        }

        Toast toast = Toast.makeText(getApplicationContext(),
                toastMessage,
                Toast.LENGTH_SHORT);
        toast.show();

    }

}
