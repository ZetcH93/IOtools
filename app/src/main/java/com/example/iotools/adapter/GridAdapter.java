package com.example.iotools.adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.iotools.R;
import com.example.iotools.model.gridList;
import com.example.iotools.MainActivity;
import java.io.File;
import java.util.List;

public class GridAdapter extends ArrayAdapter {

    private static final int TYPE_NOTE = 0;
    private static final int TYPE_JPG = 1;

    private final Context context;
    private List<gridList> values;
    private MainActivity mainActivity;
    private gridList gridViewItem;





    public GridAdapter(Context appContext, List objects, MainActivity listAct) {
        super(appContext, -1 ,objects);
        context = appContext;
        values = objects;
        mainActivity = listAct;

    }

    private void setImgBtn(String imgPath,  ImageButton v){
        File imgFile = new  File(imgPath);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            v.setImageBitmap(myBitmap); }
    }



    @Override
    public View getView (final int position, View view, ViewGroup parent) {

        gridViewItem = values.get(position);
        int viewType = getItemViewType(position);


        switch (viewType) {
            case TYPE_NOTE: {
                final ViewHolderNote holderNote;
                if (view == null){
                    view = LayoutInflater.from(getContext()).inflate(R.layout.row_data, parent, false);
                    holderNote = new ViewHolderNote(view);
                    view.setTag(holderNote);
                    holderNote.mStoreImageBtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            int pos = holderNote.viewHolderpos;
                            gridViewItem = values.get(pos);
                            mainActivity.currentPosition = pos;
                            mainActivity.noteIdOpen(view);
                            notifyDataSetChanged();
                        }
                    });
                }else{
                    holderNote = (ViewHolderNote) view.getTag();
                }
                if(gridViewItem != null){
                    holderNote.mStoreNameTxt.setText(gridViewItem.getTopic());
                    holderNote.viewHolderpos = position;
                    break;
                }
            }

            case TYPE_JPG: {
                final ViewHolderJPG holderJPG;
                if (view == null){
                    view = LayoutInflater.from(getContext()).inflate(R.layout.row_data_jpg, parent, false);
                    holderJPG = new ViewHolderJPG(view);
                    view.setTag(holderJPG);
                    holderJPG.mStoreImageBtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            int pos = holderJPG.viewHolderpos;
                            gridViewItem = values.get(pos);
                            mainActivity.currentPosition = pos;
                            mainActivity.photoIdOpen(view);
                            notifyDataSetChanged();
                        }
                    });
                }else{
                    holderJPG = (ViewHolderJPG) view.getTag();

                }
                if(gridViewItem != null){
                    setImgBtn(gridViewItem.getPicture(), holderJPG.mStoreImageBtn);
                    holderJPG.viewHolderpos = position;
                    break;
                }
            }
        }

        return view;
    }


    static class ViewHolderNote{
        private TextView mStoreNameTxt;
        private ImageButton mStoreImageBtn;
        private int viewHolderpos;

        ViewHolderNote(View view){
            mStoreNameTxt = view.findViewById(R.id.gridText);
            mStoreImageBtn = view.findViewById(R.id.imageBtnNote);

        }

    }

    static class ViewHolderJPG{
        private ImageButton mStoreImageBtn;
        private int viewHolderpos;

        ViewHolderJPG(View view){
            mStoreImageBtn = view.findViewById(R.id.imageButtonJPG);
        }

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }



    @Override
    public int getItemViewType(int position) {
        if((values.get(position).getType()) == 0){
            return TYPE_NOTE;
        }
        else{
            return TYPE_JPG;
        }
    }

}

