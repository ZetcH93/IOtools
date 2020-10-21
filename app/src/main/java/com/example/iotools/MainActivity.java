package com.example.iotools;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.iotools.adapter.GridAdapter;
import com.example.iotools.database.DataBaseHelper;
import com.example.iotools.model.gridList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    GridView gridView;
    public List<gridList> gridList = new ArrayList<>();
    public int currentPosition = 0;
    DataBaseHelper databasehelper;
    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gridView = findViewById(R.id.noteLayout);
        gridView.setAdapter((ArrayAdapter)getAdapter());


        databasehelper = new DataBaseHelper(this);
        if (gridList.isEmpty() == true) {
            initiateGridWithdb();
        }


    }

    public void addNote (View v) {
        Intent intent = new Intent(getApplicationContext(), addNoteActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST",(Serializable)gridList);
        intent.putExtra("BUNDLE",args);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == RESULT_OK){

                Bundle result = data.getBundleExtra("BUNDLE");

                gridList tempGrid = result.getParcelable("NEWgrid");
                int newIndex = (Integer.parseInt(tempGrid.get_id()));
                gridList.add(newIndex, tempGrid);
                databasehelper.addData(tempGrid.get_id(),tempGrid.getPicture(),
                        tempGrid.getCreated_date(),tempGrid.getNote(), tempGrid.getTopic(), String.valueOf(tempGrid.getType()));

                gridView.setAdapter((ArrayAdapter)getAdapter());

            }
        }
        if (requestCode == 2) {
            if(resultCode == RESULT_OK){

                Bundle result = data.getBundleExtra("BUNDLE");
                gridList tempGrid = result.getParcelable("editGrid");
                int index = (Integer.parseInt(tempGrid.get_id()));

                for(int i = 0; i < gridList.size(); i++){
                    if(Integer.parseInt((gridList.get(i).get_id())) == index){
                        gridList.set(i, tempGrid);

                        databasehelper.updateDate(tempGrid.getCreated_date(), String.valueOf(i));
                        databasehelper.updateNote(tempGrid.getNote(), String.valueOf(i));
                        databasehelper.updateTopic(tempGrid.getTopic(), String.valueOf(i));
                    }
                }

                gridView.setAdapter((ArrayAdapter)getAdapter());
            }
            else if(resultCode == RESULT_FIRST_USER){

                Bundle result = data.getBundleExtra("BUNDLE");
                gridList tempGrid = result.getParcelable("deleteGrid");


                int removeObject = Integer.parseInt(tempGrid.get_id());

                for(int i = 0; i < gridList.size(); i++){
                    if(Integer.parseInt((gridList.get(i).get_id())) == removeObject){
                        gridList.remove(i);
                        databasehelper.deleteID(String.valueOf(i),tempGrid.getTopic());
                    }
                }
                gridView.setAdapter((ArrayAdapter)getAdapter());
            }
        }


    }

    public void noteIdOpen (View v) {
        Intent intent = new Intent(getApplicationContext(), noteIdOpenActivity.class);
        Bundle noteToEdit = new Bundle();

        gridList objectToChange = gridList.get(currentPosition);
        noteToEdit.putParcelable("noteGrid", objectToChange);
        intent.putExtra("BUNDLE",noteToEdit);

        startActivityForResult(intent, 2);

    }


    public void photoIdOpen (View v) {
        Intent intent = new Intent(getApplicationContext(), photoIdOpenActivity.class);
        Bundle noteToEdit = new Bundle();

        gridList objectToChange = gridList.get(currentPosition);
        noteToEdit.putParcelable("noteJPGGrid", objectToChange);
        intent.putExtra("BUNDLE",noteToEdit);

        startActivityForResult(intent, 2);

    }

    public void addPhoto(View v) {
        Intent intent = new Intent(getApplicationContext(), addPhotoActivity.class);
        Bundle args = new Bundle();

        args.putSerializable("ARRAYLIST",(Serializable)gridList);
        intent.putExtra("BUNDLE",args);
        startActivityForResult(intent, 1);
    }

    public Adapter getAdapter() {
        return new GridAdapter(getApplicationContext(), gridList, this);
    }



    public void initiateGridWithdb(){
        int i = 0;


        Cursor data = databasehelper.getData();

            if(data.getColumnCount()>0)
                while(data.moveToNext()){
                    gridList tempGrid = new gridList();
                    tempGrid.set_id(data.getString(1));
                    tempGrid.setPicture(data.getString(2));
                    tempGrid.setCreated_date(data.getString(3));
                    tempGrid.setNote(data.getString(4));
                    tempGrid.setTopic(data.getString(5));
                    tempGrid.setType(Integer.parseInt(data.getString(6)));

                    gridList.add(i, tempGrid);
                    i++;
                }

            data.close();

        }


        public void createNewContactDialog(View view){
          dialogBuilder = new AlertDialog.Builder(this);
          final View choicePopupView = getLayoutInflater().inflate(R.layout.popupdeletedb, null);
          Button yesBtn = choicePopupView.findViewById(R.id.delDByes);
          Button noBtn = choicePopupView.findViewById(R.id.delDBno);

          dialogBuilder.setView(choicePopupView);
          dialog = dialogBuilder.create();
          dialog.show();

          yesBtn.setOnClickListener(new View.OnClickListener(){
              public void onClick(View v){

                  databasehelper.close();
                  databasehelper.resetDB();
                  showToastMessage(1);
                  gridList.clear();
                  gridView.setAdapter((ArrayAdapter)getAdapter());
                  dialog.dismiss();
              }
            });

            noBtn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    dialog.dismiss();
                }
            });
        }


    public void showToastMessage(int id){
        String toastMessage = null;

        if(id == 1){
            toastMessage = "Database reset";
        }

        Toast toast = Toast.makeText(getApplicationContext(),
                toastMessage,
                Toast.LENGTH_SHORT);
        toast.show();

    }

}
