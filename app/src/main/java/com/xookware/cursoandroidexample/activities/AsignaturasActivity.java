package com.xookware.cursoandroidexample.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xookware.cursoandroidexample.R;
import com.xookware.cursoandroidexample.database.DataBaseHandler;
import com.xookware.cursoandroidexample.fragments.InsertarFragment;
import com.xookware.cursoandroidexample.fragments.ListaFragment;

public class AsignaturasActivity extends AppCompatActivity implements InsertarFragment.OnFragmentInteractionListener, ListaFragment.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private Toolbar toolbar;
    private DataBaseHandler db;
    private BottomNavigationView navigation;
    private LinearLayout layout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();


            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new InsertarFragment();

                    break;
                case R.id.navigation_dashboard:
                    fragment = new ListaFragment();
                    break;
                case R.id.navigation_notifications:
                    Snackbar.make(layout, "Navegando entre fragments", Snackbar.LENGTH_SHORT).show();
                    return true;
            }

            transaction.replace(R.id.container, fragment);
            transaction.setTransitionStyle(R.style.AppTheme);
            transaction.commit();

            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas);
        layout = (LinearLayout) findViewById(R.id.container_g);
        db = new DataBaseHandler(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Gestión de asignaturas");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(navigation.getMenu().getItem(1).getItemId());
        navigation.performClick();

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
