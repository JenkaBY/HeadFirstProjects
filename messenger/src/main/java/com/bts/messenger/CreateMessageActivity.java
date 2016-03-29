package com.bts.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);

    }

    public void onSendMessage(View view) {
        EditText small_text_message = (EditText) findViewById(R.id.text_message);
        String text_message = small_text_message.getText().toString();
        //Intent intent = new Intent(this, ReceiveMessageActivity.class);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //intent.putExtra(ReceiveMessageActivity.EXTRA_MESSAGE, text_message);
        intent.putExtra(Intent.EXTRA_TEXT, text_message);
        String chooserTitle = getString(R.string.chooser);
        Intent choosenIntent = Intent.createChooser(intent,chooserTitle);
        //startActivity(intent);
        startActivity(choosenIntent);
    }
    public void onResetTest(View view){
        TextView textView = (TextView) findViewById(R.id.text_message);
        textView.setText("");
    }
}
