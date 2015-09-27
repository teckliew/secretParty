package lol.secretparty.supersecretparty;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.hp.linkreadersdk.EasyReadingCallback;
import com.hp.linkreadersdk.EasyReadingFragment;
import com.hp.linkreadersdk.ErrorCode;

import java.io.File;
import java.io.IOException;

public class StartScreen extends ActionBarActivity {

    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;



    private ValueCallback<Uri> mUploadMessage;
    private final static int FILECHOOSER_RESULTCODE=1;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        /*
        FrameLayout linearLayout = (FrameLayout) findViewById(R.id.linkreader_view);
        if (savedInstanceState == null) {
            EasyReadingFragment easyReadingFragment = EasyReadingFragment.initWithClientID("dpw8vigidv8guwda2zrf724y3xj9xe1o", "pc2loR19Ljham12kUhKCbD4JQDCHDBHV", new EasyReadingCallback() {
                @Override
                public void onAuthenticationSuccess(String successMessage) {
                    Log.d("Auth", successMessage);
                }

                @Override
                public void onAuthenticationError(ErrorCode errorCode) {
                    Log.d("Auth", "ErrorCode " + errorCode.name());
                }
            }, this);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(linearLayout.getId(), (Fragment)easyReadingFragment).commit();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start_screen, menu);
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




    // Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
    class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if(i==1) {
                Fragment fragment = new DemoObjectFragment();
                Bundle args = new Bundle();
                // Our object is just an integer :-P
                args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
                fragment.setArguments(args);

                return fragment;
            } else {
                EasyReadingFragment easyReadingFragment = EasyReadingFragment.initWithClientID("<Code ID>", "<Secret>", new EasyReadingCallback() {
                    @Override
                    public void onAuthenticationSuccess(String successMessage) {
                        Log.d("Auth", successMessage);
                    }

                    @Override
                    public void onAuthenticationError(ErrorCode errorCode) {
                        Log.d("Auth", "ErrorCode " + errorCode.name());
                    }
                }, getApplicationContext());
                return easyReadingFragment;

            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    // Instances of this class are fragments representing a single
// object in our collection.
    public static class DemoObjectFragment extends Fragment {
        public static final String ARG_OBJECT = "object";

        private ValueCallback<Uri> mUploadMessage;
        private final static int FILECHOOSER_RESULTCODE=1;
        private ValueCallback<Uri[]> mFilePathCallback;
        private String mCameraPhotoPath;

        @Override
        public void onActivityResult (int requestCode, int resultCode, Intent data) {
            if(requestCode != FILECHOOSER_RESULTCODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            Log.d("SSP", "Selecting file " + data.getDataString());

            Uri[] results = null;

            // Check that the response is a good one
            if(resultCode == Activity.RESULT_OK) {
                if(data == null) {
                    // If there is not data, then we may have taken a photo
                    if(mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            //Log.d("SSP","File: " + results.);

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;
            return;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.activity_web_view, container, false);
            Bundle args = getArguments();

            //Intent i = getIntent();

            //Uri u = Uri.parse(i.getDataString());

            //Log.d("SSP", "Data: " + u.getPath());

            WebView wv = (WebView) rootView.findViewById(R.id.webView);
            //wv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            wv.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading (WebView view, String url){
                    if (url.startsWith("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse(url));
                        startActivity(intent);
                    } else if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                    }
                    return true;
                }
            });
            wv.setWebChromeClient(new WebChromeClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_DIAL,
                                Uri.parse(url));
                        startActivity(intent);
                    }else if(url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                    }
                    return true;
                }


                //For Android 4.1
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "File Chooser"), StartScreen.FILECHOOSER_RESULTCODE);

                }


                public void openFileChooser(ValueCallback<Uri> uploadMsg) {

                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    Log.d("SSP", "OPening file");
                    startActivityForResult(
                            Intent.createChooser(i, "Image Browser"),
                            FILECHOOSER_RESULTCODE);
                }

                public void openFileChooser( ValueCallback uploadMsg, String acceptType ) {
                    mUploadMessage = uploadMsg;
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("*/*");
                    Log.d("SSP", "OPening fileee");
                    startActivityForResult(
                            Intent.createChooser(i, "File Browser"),
                            FILECHOOSER_RESULTCODE);
                }

                public boolean onShowFileChooser(
                        WebView webView, ValueCallback<Uri[]> filePathCallback,
                        WebChromeClient.FileChooserParams fileChooserParams) {
                    if(mFilePathCallback != null) {
                        mFilePathCallback.onReceiveValue(null);
                    }
                    mFilePathCallback = filePathCallback;
                    Log.d("SSP", "Opening file");

                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");



                    Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    contentSelectionIntent.setType("image/*");


                    Intent[] intentArray = new Intent[0];


                    Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                    chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                    startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);

                    return true;
                }


            }  );

            WebSettings webSettings = wv.getSettings();
            webSettings.setJavaScriptEnabled(true);

            wv.loadUrl("http://www.secretparty.lol/newevent");
            return rootView;
        }
    }
}
