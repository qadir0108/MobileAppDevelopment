package com.example.a25_alertdialogs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

@SuppressLint("ValidFragment")
public class MyListDialog extends DialogFragment {

    String[] colors;
    public MyListDialog(String[] _colors) {
        colors = _colors;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick a Color")
                .setItems(colors, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        String selectedColor = colors[which];
                        MyDialog myDialog = new MyDialog("You selected color: " + selectedColor);
                        myDialog.show(getFragmentManager(), "TAG");

                    }
                });
        return builder.create();
    }
}
