package com.tricks.coupons;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.NetworkInterface;
import java.net.URL;


public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener{

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String URL ="http://www.flipkartdiscounts.net";
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString(); //getiing app name for later use

        //Method for adding navigation drawer items
        addDrawerItems();
        //Finalizing Drawer
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        //Checking if internet connection is available or not
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();

        if(activeNetwork!=null && activeNetwork.isConnected()){
            startWebView(URL);
        }
        else {
            // notify user you are not online
            Toast.makeText(this, "Check your Internet Connection!!!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.no_internet);

        }


    }


    //Making webView
    public void startWebView(String url){

        webView = (WebView)findViewById(R.id.webView);
        final ProgressDialog pd = ProgressDialog.show(MainActivity.this, "", "Loading...", true);


        //settings to make app better
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        //without this Every page will be shown in browser
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

            //Show PRogress dialog
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                pd.show();
            }

            //Dismiss progress dialog
            @Override
            public void onPageFinished(WebView view, String url) {
                if (pd.isShowing())
                    pd.dismiss();

            }


        });
        webView.loadUrl(url);

    }

    private void addDrawerItems() {
        String[] osArray = {"Home", "About Us", "Policy", "Switch Theme","Report a Bug", "Exit"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);


        //Adding listenrs to navigation drawer items
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        webView.loadUrl(URL);

                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                    case 6:
                        finish();
                        System.exit(0);
                        break;
                    default:
                }

            }


        });

}


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 3 dot menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

       try {

           int id = item.getItemId();


           if (id == R.id.refresh) {
               webView.reload();
           }

           // Activate the navigation drawer toggle
           if (mDrawerToggle.onOptionsItemSelected(item)) {
               return true;
           }

           return super.onOptionsItemSelected(item);
       }
       //App Closing after pressing refresh option
       catch (Exception e){
           Toast.makeText(this, "No Internet Connection Detected", Toast.LENGTH_LONG).show();
             return false;
        }

        }



     @Override
        // Detect when the back button is pressed
     public void onBackPressed() {
        if(webView.canGoBack()) {
        webView.goBack();
        } else {
        // Let the system handle the back button
        super.onBackPressed();
        }
     }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void exitButton(View v){
        finish();
        System.exit(0);
    }
}
