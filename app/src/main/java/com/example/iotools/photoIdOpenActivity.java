package com.example.iotools;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.iotools.model.gridList;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;




public class photoIdOpenActivity extends AppCompatActivity {


    String currentPhotoPath;
    ImageView imageView;
    gridList gridObject;
    EditText textInput;
    View photoView;
    String id;
    String date;
    String note;
    String topic;
    String picture;
    Intent returnIntent = new Intent();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photoid);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        gridObject = args.getParcelable("noteJPGGrid");

        imageView = findViewById(R.id.photoView);
        photoView = findViewById(R.id.photoTextView);
        textInput = findViewById(R.id.textInputPhoto);

        currentPhotoPath = gridObject.getPicture();
        textInput.setText(gridObject.getNote());

        if(textInput.getText().toString().isEmpty()){
            textInput.setVisibility(View.INVISIBLE);
        }


        File imgFile = new  File(currentPhotoPath);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            imageView.setImageBitmap(myBitmap);
        }


        textInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused) {
                    keyboard.showSoftInput(textInput, 0);
                    if(photoView.VISIBLE == 0) {
                        photoView.setVisibility(view.VISIBLE);
                    }

                }
                else {
                    keyboard.hideSoftInputFromWindow(textInput.getWindowToken(), 0);
                    photoView.setVisibility(view.INVISIBLE);
                    if(textInput.getText().toString().isEmpty()){
                        textInput.setVisibility(view.INVISIBLE);
                    }
                }
            }
        });


        photoView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focused) {
                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (focused) {
                    keyboard.hideSoftInputFromWindow(photoView.getWindowToken(), 0);
                    if(photoView.VISIBLE == 1) {
                        photoView.setVisibility(view.INVISIBLE);
                        if(textInput.getText().toString().isEmpty()){
                            textInput.setVisibility(view.INVISIBLE);
                        }

                    }
                }
            }
        });



    }

    public void addTextToPic(View view)
    {
        if(textInput.getVisibility() == view.VISIBLE){

        }
        else{
            textInput.setVisibility(view.VISIBLE);
            textInput.requestFocus();
        }

    }


    public void editNoteBtn(View view){

        date =(gridObject.getCreated_date());
        note =(textInput.getText().toString());
        topic =(gridObject.getTopic());
        picture = gridObject.getPicture();
        id = gridObject.get_id();

        gridList tempGrid = new gridList();
        tempGrid.set_id(id);
        tempGrid.setCreated_date(date);
        tempGrid.setNote(note);
        tempGrid.setPicture(picture);
        tempGrid.setType(1);

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
            toastMessage = "Photo edited";
        }
        else if(id == 2){
            toastMessage = "Photo deleted";
        }

        Toast toast = Toast.makeText(getApplicationContext(),
                toastMessage,
                Toast.LENGTH_SHORT);
        toast.show();

    }



}
