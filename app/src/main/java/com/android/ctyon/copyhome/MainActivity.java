package com.android.ctyon.copyhome;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity  implements RightFragment.OnFragmentInteractionListener {
    private  static  String TAG = "MainActivity";
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.test_fragment);
        Button btnSwitchFragment = (Button) findViewById(R.id.btn_switch_Fragments);
        fragmentManager = getSupportFragmentManager();
        btnSwitchFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentTransaction = fragmentManager.beginTransaction();
                Fragment curFragment = fragmentManager.findFragmentById(R.id.right_fragment);
                if(null != curFragment && curFragment instanceof RightFragment){
                    SecondFragment secondFragment = new SecondFragment();
                    fragmentTransaction.replace(R.id.right_fragment, secondFragment, "secondFragment");
                    fragmentTransaction.addToBackStack("secondFragment");
                }else if(null != curFragment && curFragment instanceof SecondFragment){
                    RightFragment rightFragment = new RightFragment();
                    fragmentTransaction.replace(R.id.right_fragment, rightFragment, "firstFragment");
                    fragmentTransaction.addToBackStack("firstFragment");

                }else{
                    RightFragment rightFragment = new RightFragment();
                    fragmentTransaction.add(R.id.right_fragment, rightFragment);
                }


                //SecondFragment secondFragment = new SecondFragment();



                fragmentTransaction.commit();
            }
        });
        //RightFragment rightFragment = new RightFragment();
        //fragmentTransaction.add(R.id.right_fragment, rightFragment, con)
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "uri: " + uri, Toast.LENGTH_SHORT).show();
    }
}
