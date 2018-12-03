package com.example.tockn.githubclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView title, contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.Title);
        contents = findViewById(R.id.Contents);

        GitHubAPIStore task = new GitHubAPIStore(title, contents);
        task.execute("GetPublicGists");
    }
}
