package pe.edu.ulima.eventosulima;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import layout.CardFragment;
import layout.General;
import layout.Industrial;
import layout.Sistemas;

public class Navigation extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Obtener referencia al action bar

        actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_slideshow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.flaContenido, new General());
        transaction.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);

                FragmentManager fm = getSupportFragmentManager();

                FragmentTransaction transaction = fm.beginTransaction();
                if(item.getItemId() == R.id.fragmentgeneral){
                    transaction.replace(R.id.flaContenido, new General());
                }else if(item.getItemId() == R.id.fragmentindustrial){
                transaction.replace(R.id.flaContenido, new Industrial());
                }else if(item.getItemId() == R.id.fragmentsistemas){
                transaction.replace(R.id.flaContenido, new Sistemas());
                }

                transaction.commit();
                drawerLayout.closeDrawers();
                return true;
            }
        });





    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.menu1:
                Intent intent = new Intent(this, CalendarioActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "Accion 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu2:
                Toast.makeText(this, "Accion 2", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


}
