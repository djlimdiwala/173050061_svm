package com.example.lkh.a173050061_svm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import umich.cse.yctung.androidlibsvm.LibSVM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by DJL on 4/6/2018.
 */

public class train extends Fragment {

    private String path;
    private EditText options;
    private Button log;
    LibSVM svm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_layout, container, false);

        path = getActivity().getApplicationContext().getExternalFilesDir(null).toString();
        options = (EditText) view.findViewById(R.id.options);

        log = (Button) view.findViewById(R.id.train_button);


        log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String train_options = options.getText().toString() + " ";
                String path_train = path + File.separator + "train.csv";
                String path_model = path + File.separator + "model.txt ";


                svm = new LibSVM();

                try {
                    path_train = convert_file_format(path_train);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                svm.train(train_options + path_train + " " + path_model);
            }
        });


        return view;
    }

public String convert_file_format(String csv_path) throws FileNotFoundException {



    String output_line;

    String path_full = path + File.separator + "train.txt";
    FileOutputStream stream = new FileOutputStream(path_full);
//    stream.write(str.getBytes());

    try (BufferedReader br = new BufferedReader(new FileReader(csv_path))) {
        String line;
        line = br.readLine();
        line = br.readLine();
        while ((line = br.readLine()) != null) {
            String[] tokens = line.split(",");
            if (tokens.length == 7)
            {
                if (tokens[6].equals("Stationary"))
            {
                output_line = "1 1:" + tokens[1] + " 2:" + tokens[2] + " 3:" + tokens[3] + " 4:" + tokens[4] + " 5:" + tokens[5] + "\n";
                stream.write(output_line.getBytes());

            }
            else
            {
                output_line = "-1 1:" + tokens[1] + " 2:" + tokens[2] + " 3:" + tokens[3] + " 4:" + tokens[4] + " 5:" + tokens[5] + "\n";
                stream.write(output_line.getBytes());

            }
            }
            else
            {
                line = br.readLine();

            }
        }
        stream.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }

    return path_full;
}
}
