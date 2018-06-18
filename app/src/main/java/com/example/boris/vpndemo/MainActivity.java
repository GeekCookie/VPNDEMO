package com.example.boris.vpndemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import dns.hosts.localvpn.core.VpnManager;

public class MainActivity extends AppCompatActivity {

    //Set the given apps to access Local Vpn
    ArrayList<String> apps = new ArrayList<>();

    ArrayList<String> whiteDomain = new ArrayList<>();
    ArrayList<String> blackDomain = new ArrayList<>();
    ArrayList<String> blackIp = new ArrayList<>();
    ArrayList<String> whiteIp = new ArrayList<>();

    // You should define a unique code for VPN permission requesting from other activity request code
    public static int VPN_PERMISSION_CODE = 10001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        apps.add(BuildConfig.APPLICATION_ID);

        //for domain blacklist
        blackDomain.add("baidu.com");

        //for ip blacklist
        blackIp.add("123.125.115.110");
        //for ip range blacklist
        blackIp.add("104.31.68.191/104.31.68.200");



        findViewById(R.id.startVpn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VpnManager.startCheckService(MainActivity.this, whiteDomain, blackDomain, whiteIp, blackIp);
                //Warning: if parameter allow is false, apps must contain self package,otherwise it will have problems
                VpnManager.start(MainActivity.this, apps, null, false, VPN_PERMISSION_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // PROCESS VPN PERMISSION GRANTED HERE
        if (requestCode == VPN_PERMISSION_CODE) {
            VpnManager.onVpnDataCallback(this, apps, null, false, resultCode);
        } else {
            //others
        }

    }
}
