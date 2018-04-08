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
import android.widget.TextView;

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
    private TextView svm_type;
    private TextView kernel;
    private TextView gamma;
    private TextView sv;
    private TextView bsv;
    private TextView usv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.train_layout, container, false);

        svm_type = (TextView) view.findViewById(R.id.type_value);
        kernel = (TextView) view.findViewById(R.id.kernel_value);
        gamma = (TextView) view.findViewById(R.id.gamma_value);
        sv = (TextView) view.findViewById(R.id.sv_value);
        bsv = (TextView) view.findViewById(R.id.bsv_value);
        usv = (TextView) view.findViewById(R.id.usv_value);

        svm_type.setText("-");
        kernel.setText("-");
        gamma.setText("-");
        sv.setText("-");
        bsv.setText("-");
        usv.setText("-");

        path = getActivity().getApplicationContext().getExternalFilesDir(null).toString();
        options = (EditText) view.findViewById(R.id.options);

        log = (Button) view.findViewById(R.id.train_button);


        log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String train_options = options.getText().toString() + " ";
                String path_train = path + File.separator + "train.csv";
                String path_model = path + File.separator + "model.txt";


                svm = new LibSVM();

                try {
                    path_train = convert_file_format(path_train);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                svm.train(train_options + path_train + " " + path_model + " ");


                try {
                    BufferedReader br = new BufferedReader(new FileReader(path_model));
                    String line;
//                    line = br.readLine();
//                    String[] spl = line.split("\\s+");
//                    Log.e("svm type",spl[1]);
//                    svm_type.setText(spl[1]);
//                    line = br.readLine();
//                    spl = line.split(" ");
//                    Log.e("kernel type",spl[1]);
//                    kernel.setText(spl[1]);
//                    line = br.readLine();
//                    spl = line.split(" ");
//                    Log.e("gamma",spl[1]);
//                    gamma.setText(spl[1]);
//                    line = br.readLine();
//                    line = br.readLine();
//                    spl = line.split(" ");
//                    Log.e("Support vectors",spl[1]);
//                    sv.setText(spl[1]);
//                    line = br.readLine();
//                    line = br.readLine();
//                    line = br.readLine();
//                    spl = line.split(" ");
//                    Log.e("Bounded Support vectors",spl[1]);
//                    Log.e("UnboundedSupportvectors",spl[2]);
//                    bsv.setText(spl[1]);
//                    usv.setText(spl[2]);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }


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
                    output_line = "1 1:" + tokens[3] + " 2:" + tokens[4] + " 3:" + tokens[5] + "\n";
                    stream.write(output_line.getBytes());

                }
                else
                {
                    output_line = "-1 1:" + tokens[3] + " 2:" + tokens[4] + " 3:" + tokens[5] + "\n";
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
