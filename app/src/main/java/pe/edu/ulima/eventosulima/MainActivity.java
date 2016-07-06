package pe.edu.ulima.eventosulima;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

import layout.CardFragment;
import layout.General;
import layout.Industrial;
import layout.Sistemas;

public class MainActivity extends AppCompatActivity {
    Firebase myFirebaseRef = new Firebase("https://eventosulima-b8cfe.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new General());
        adapter.addFragment(new Industrial());
        adapter.addFragment(new Sistemas());
        adapter.addFragment(new CardFragment());

        viewPager.setAdapter(adapter);
    }

    public static class Adapter extends FragmentPagerAdapter{
        private List<Fragment> mFragments = new ArrayList<>();
        //PagerAdapter va a tener un arreglo de miembros obviamente va a tener un arreglo de miembros mFragments.getPosition()
        //private int LENGHT = 10;
        public Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
            //return mFragments.get(la imagen);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        public void addFragment(Fragment fragment){
            mFragments.add(fragment);
        }
    }
}
