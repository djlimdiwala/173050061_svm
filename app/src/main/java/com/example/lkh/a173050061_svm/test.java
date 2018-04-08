package com.example.lkh.a173050061_svm;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import umich.cse.yctung.androidlibsvm.LibSVM;

/**
 * Created by DJL on 4/6/2018.
 */

public class test extends Fragment {


    private String path;
    private EditText options;
    private Button log;
    LibSVM svm;
    private TextView cou;
    private TextView tot;
    private TextView accu;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_layout, container, false);

        path = getActivity().getApplicationContext().getExternalFilesDir(null).toString();
//        options = (EditText) view.findViewById(R.id.options);

        cou = (TextView) view.findViewById(R.id.count_value);
        tot = (TextView) view.findViewById(R.id.total_value);
        accu = (TextView)view.findViewById(R.id.accu_value);

        log = (Button) view.findViewById(R.id.button_test);

        log.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String model_file = path + File.separator + "model.txt ";
                String path_test = path + File.separator + "test.csv";
                String output = path + File.separator + "output.txt";
                String original = path + File.separator + "original.txt";


                svm = new LibSVM();

                try {
                    path_test = convert_file_format(path_test, original);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                svm.predict(path_test + " " + model_file + output + " ");

                try {
                    float accuracy = find_accuracy(original, output);
//                    Log.e("accuracy",Integer.toString(accuracy));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        return view;
    }



    public String convert_file_format(String csv_path, String original_path) throws FileNotFoundException {



        String output_line;

        String path_full = path + File.separator + "test.txt";
        FileOutputStream stream = new FileOutputStream(path_full);
        FileOutputStream stream1 = new FileOutputStream(original_path);
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
                        output_line = "1\n";
                        stream1.write(output_line.getBytes());

                    }
                    else
                    {
                        output_line = "-1 1:" + tokens[3] + " 2:" + tokens[4] + " 3:" + tokens[5] + "\n";
                        stream.write(output_line.getBytes());
                        output_line = "-1\n";
                        stream1.write(output_line.getBytes());

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


    public float find_accuracy (String original_path, String output_path) throws IOException {
        float accuracy = 0;
        float total = 0;
        float count = 0;

        BufferedReader br1 = new BufferedReader(new FileReader(output_path));
        BufferedReader br2 = new BufferedReader(new FileReader(original_path));
        String line1;
        String line2;

        while ((line1 = br1.readLine()) != null) {
            line2 = br2.readLine();
            if (line1.equals(line2))
            {
                count++;
            }
            total++;
        }

        accuracy = (count / total) * 100;
        cou.setText(Float.toString(count));
        tot.setText(Float.toString(total));
        accu.setText(Float.toString(accuracy));


        return accuracy;
    }




}
