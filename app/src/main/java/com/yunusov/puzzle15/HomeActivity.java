package com.yunusov.puzzle15;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView linkToAuthor = (TextView) findViewById(R.id.linkToAuthor);
        linkToAuthor.setText(Html.fromHtml("<html>Created by <a href='#'>Abdurakhmon Yunusov</a></html>"));

        UpdateChecker appUpdateChecker = new UpdateChecker(this);  //pass the activity in constructor
        appUpdateChecker.checkForUpdate(true); //mannual check false here
    }
    public void play(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void about(View view){
        startActivity(new Intent(this, AboutActivity.class));
    }



    public void exit(View view) {
        finish();
    }

    public void forwardToLink(View view) {
        String value = "<html>Visit my CV and Portfolio on <a href=\"http://www.yunusov.pl\">yunusov.pl</a></html>";

        TextView linkToAuthor = (TextView) findViewById(R.id.linkToAuthor);

        linkToAuthor.setText(Html.fromHtml(value));
        linkToAuthor.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
