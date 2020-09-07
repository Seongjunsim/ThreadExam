package com.android.threadexam;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button btn_start;
    ProgressBar pg_download;
    TextView tv_text;
    DownloadTask downloadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = (Button)findViewById(R.id.btn_start);
        pg_download = (ProgressBar)findViewById(R.id.pg_download);
        tv_text = (TextView)findViewById(R.id.tv_loading);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download(view);
            }
        });

    }

    public void download(View view) {
        downloadTask = new DownloadTask();
        downloadTask.execute();
    }

    class DownloadTask extends AsyncTask<Void, Integer, Void> {
        @Override
        protected Void doInBackground(Void... voids){
            for(int i=0; i<=100; i++){
                try{
                    Thread.sleep(100);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                final int percent = i;

                publishProgress(percent);
                if(isCancelled()){
                    break;
                }
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Integer... values){
            tv_text.setText(values[0]+"$");
            pg_download.setProgress(values[0]);
        }

        @Override
        protected void onCancelled(Void aVoid){
            Toast.makeText(MainActivity.this, "취소됨",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void aVoid){
            Toast.makeText(MainActivity.this, "완료됨",Toast.LENGTH_LONG).show();
        }
    }

    public void stop(View view){
        if(downloadTask!=null && !downloadTask.isCancelled()){
            downloadTask.cancel(true);
        }
    }
}