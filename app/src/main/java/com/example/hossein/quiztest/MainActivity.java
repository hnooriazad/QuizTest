
package com.example.hossein.quiztest;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RadioGroup radioGroup;
    private Button nxt;
    int pos;
    String Password;
    String UserName;
    String Role;
    Intent i;
    String Message;
    String ManagerFirstName;
    String ManagerLastName;
    String BusinessUnitName;
    String Token;

    DbHelper mDbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDbHelper = new DbHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action 1 1n", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        SQLiteDatabase mDb = null;
        try {
            mDbHelper.createDataBase();
            mDbHelper.openDataBase();
            mDbHelper.close();
            mDb = mDbHelper.getWritableDatabase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {

            String sql = "select * FROM Question ";

            //assert mDb != null;
            Cursor c = mDb.rawQuery(sql, null);
            if (c != null) {
                while (c.moveToNext()) {

					  ManagerFirstName = c.getString(6); ManagerLastName =
					  c.getString(5); BusinessUnitName = c.getString(4);


                    //Token = c.getString(3);
                    TextView Tv=(TextView)findViewById(R.id.textView4);
                    Tv.setText(c.getString(2));
                    RadioButton Rb1=(RadioButton) findViewById(R.id.radio1);
                    Rb1.setText(c.getString(3));
                    RadioButton Rb2=(RadioButton) findViewById(R.id.radio2);
                    Rb2.setText(c.getString(4));
                    RadioButton Rb3=(RadioButton) findViewById(R.id.radio3);
                    Rb3.setText(c.getString(5));
                    RadioButton Rb4=(RadioButton) findViewById(R.id.radio4);
                    Rb4.setText(c.getString(6));
                    //UserName = c.getString(2);
                   // Password = c.getString(4);
                    // ID= c.getInt(0);

                }
            }

        } catch (SQLException mSQLException) {
            throw mSQLException;
        }






        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
 radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                if (checkedId == R.id.radio1) {
                    setContentView(R.layout.final2);
                }
                else if (checkedId == R.id.radio2){
                    setContentView(R.layout.final2);
                }
                else if (checkedId == R.id.radio3) {
                    setContentView(R.layout.final1);
                }
                else{
                    setContentView(R.layout.final2);
                }
            }
        });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}





//package com.truiton.bottomnavigation;
//
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
//import android.view.MenuItem;
//import android.widget.ImageView;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                findViewById(R.id.navigation);
//
//        bottomNavigationView.setOnNavigationItemSelectedListener
//                (new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        Fragment selectedFragment = null;
//                        switch (item.getItemId()) {
//                            case R.id.action_item1:
//                                selectedFragment = ItemOneFragment.newInstance();
//                                break;
//                            case R.id.action_item2:
//                                selectedFragment = ItemTwoFragment.newInstance();
//                                break;
//                            case R.id.action_item3:
//                                selectedFragment = ItemThreeFragment.newInstance();
//                                break;
//                        }
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.frame_layout, selectedFragment);
//                        transaction.commit();
//                        return true;
//                    }
//                });
//
//        //Manually displaying the first fragment - one time only
//        ImageView Img=(ImageView)findViewById(R.id.truiton_image);
//        Img.setImageDrawable(getResources().getDrawable(R.drawable.ic_menu_camera));
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, ItemOneFragment.newInstance());
//        transaction.commit();
//
//        //Used to select an item programmatically
//        //bottomNavigationView.getMenu().getItem(2).setChecked(true);
//    }
//}