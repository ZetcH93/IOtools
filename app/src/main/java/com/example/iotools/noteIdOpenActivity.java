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




public class noteIdOpenActivity extends AppCompatActivity {

    public TextView showDateTime;
    public TextInputEditText editText, topicInput;
    gridList gridObject;
    String id;
    String date;
    String note;
    String topic;
    String picture;
    Intent returnIntent = new Intent();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noteid);


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        gridObject = args.getParcelable("noteGrid");

        showDateTime = findViewById(R.id.dateText);
        editText = findViewById(R.id.noteTextInput);
        topicInput = findViewById(R.id.topicTextInput);

        showDateTime.setText(gridObject.getCreated_date());
        editText.setText(gridObject.getNote());
        topicInput.setText(gridObject.getTopic());


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

        showDateTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused)
                    keyboard.showSoftInput(showDateTime, 0);
                else
                    keyboard.hideSoftInputFromWindow(showDateTime.getWindowToken(), 0);
            }
        });

    }

    public void editNoteBtn(View view){
        date =(showDateTime.getText().toString());
        note =(editText.getText().toString());
        topic =(topicInput.getText().toString());
        picture = gridObject.getPicture();
        id = gridObject.get_id();

        gridList tempGrid = new gridList();
        tempGrid.setTopic(topic);
        tempGrid.set_id(id);
        tempGrid.setCreated_date(date);
        tempGrid.setNote(note);
        tempGrid.setPicture(picture);
        tempGrid.setType(0);

        Bundle gridBundle = new Bundle();
        gridBundle.putParcelable("editGrid", tempGrid);
        returnIntent.putExtra("BUNDLE", gridBundle);

        setResult(RESULT_OK, returnIntent);
        showToastMessage(1);
        finish();
    }

    public void delete(View view){

        gridList tempGrid = new gridList();
        Bundle gridBundle = new Bundle();
        tempGrid.set_id( id = gridObject.get_id());

        gridBundle.putParcelable("deleteGrid", tempGrid);
        returnIntent.putExtra("BUNDLE", gridBundle);

        setResult(RESULT_FIRST_USER, returnIntent);
        showToastMessage(2);
        finish();
    }

    public void showToastMessage(int id){
        String toastMessage = null;

        if(id == 1){
            toastMessage = "Note edited";
        }
        else if(id == 2){
            toastMessage = "Note deleted";
        }

        Toast toast = Toast.makeText(getApplicationContext(),
                toastMessage,
                Toast.LENGTH_SHORT);
        toast.show();

    }

}
