package maria.alc4.alc4phase1challenge;

import android.content.Intent;
import android.os.Bundle;

 import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    CurrentNetworkState networkState;
    CoordinatorLayout coodLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button btn_aboutALC= findViewById(R.id.about_alc);
        btn_aboutALC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checkConnected=networkState.getCurrentConnection();
                if(checkConnected) {
                    Intent intent_aboutALC = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent_aboutALC);
                }else{
                    Snackbar snackbar = Snackbar.make(coodLayout,"Oops!! No internet connection. Please try again!",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        });
        Button my_profile=findViewById(R.id.myprofile);
        coodLayout=findViewById(R.id.layout_root);
        networkState=new CurrentNetworkState(MainActivity.this);

        my_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myProfileIntent=new Intent(MainActivity.this,MyProfileActivity.class);
                startActivity(myProfileIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
