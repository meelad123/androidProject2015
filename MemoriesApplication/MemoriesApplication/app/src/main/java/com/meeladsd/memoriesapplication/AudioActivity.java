package com.meeladsd.memoriesapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AudioActivity extends Dialog implements android.view.View.OnClickListener {

    Button _btnPlay,_btnStop,_btnRecord, _btnCancel, _btnDone;
    private MediaRecorder _myAudioRecorder;
    private String _outputFile = null;
    private Activity _c;
    private TextView _txtAudio;
    private Activity _ac;
    public AudioActivity(Activity ac) {
        super(ac);
        _c = ac;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        _btnPlay =(Button)findViewById(R.id.btn_play);
        _btnStop =(Button)findViewById(R.id.btn_stop);
        _btnRecord =(Button)findViewById(R.id.btn_record);
        _btnCancel =(Button)findViewById(R.id.btn_cancel);
        _btnDone =(Button)findViewById(R.id.btn_done);

        _btnStop.setEnabled(false);
        _btnPlay.setEnabled(false);
        _btnDone.setEnabled(false);
        _outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        _myAudioRecorder=new MediaRecorder();
        _myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        _myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        _myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        _myAudioRecorder.setOutputFile(_outputFile);

        _btnPlay.setOnClickListener(this);
        _btnRecord.setOnClickListener(this);
        _btnStop.setOnClickListener(this);
        _btnDone.setOnClickListener(this);
        _btnCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                File file = new File(_outputFile);
                file.delete();
                dismiss();
                break;
            case  R.id.btn_done:
                Toast.makeText(getContext(), "Saved successfully", Toast.LENGTH_LONG).show();
                _txtAudio = (TextView) _c.findViewById(R.id.txt_audio);
                _txtAudio.setText("Audio: 1");
                dismiss();
                break;
            case R.id.btn_play:
                MediaPlayer m = new MediaPlayer();

                try {
                    m.setDataSource(_outputFile);
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    m.prepare();
                }

                catch (IOException e) {
                    e.printStackTrace();
                }

                m.start();
                Toast.makeText(getContext(), "Playing audio", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_record:
                try {
                    _myAudioRecorder.prepare();
                    _myAudioRecorder.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                _btnRecord.setEnabled(false);
                _btnStop.setEnabled(true);


                Toast.makeText(getContext(), "Recording started", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_stop:
                _myAudioRecorder.stop();
                _myAudioRecorder.release();
                _myAudioRecorder = null;

                _btnStop.setEnabled(false);
                _btnPlay.setEnabled(true);
                _btnDone.setEnabled(true);
                Toast.makeText(getContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
