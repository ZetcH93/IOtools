package com.example.iotools;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import com.example.iotools.model.gridList;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class addPhotoActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 1;


    String currentPhotoPath;
    ImageView imageView;
    String timeStamp;
    String imageFileName;

    EditText textInput;
    View photoView;
    Uri photoURI;

    ArrayList<gridList> gridArray;
    String id;
    String date;
    String note;
    String topic;
    String picture;
    Intent returnIntent = new Intent();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_photo);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        gridArray = (ArrayList<gridList>) args.getSerializable("ARRAYLIST");

        imageView = findViewById(R.id.photoView);
        photoView = findViewById(R.id.photoTextView);
        textInput = findViewById(R.id.textInputPhoto);
        dispatchTakePictureIntent();

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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                 photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

                    imageView.setImageURI(photoURI);
        }
        else if(resultCode == RESULT_CANCELED){
            finish();
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void addNotetoGrid(View view){
        date = java.text.DateFormat.getDateTimeInstance().format(new Date());
        note =(textInput.getText().toString());
        topic ="0";
        picture = currentPhotoPath;
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
        tempGrid.setType(1);

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
            toastMessage = "Photo created";
        }


        Toast toast = Toast.makeText(getApplicationContext(),
                toastMessage,
                Toast.LENGTH_SHORT);
        toast.show();

    }

}