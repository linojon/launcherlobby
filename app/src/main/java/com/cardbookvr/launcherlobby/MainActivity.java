package com.cardbookvr.launcherlobby;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends CardboardActivity implements CardboardView.StereoRenderer {
    private static final String TAG = "LauncherLobby";
    private CardboardOverlayView overlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CardboardView cardboardView = (CardboardView) findViewById(R.id.cardboard_view);
        cardboardView.setRestoreGLStateEnabled(false);
        cardboardView.setRenderer(this);
        setCardboardView(cardboardView);

        overlayView = (CardboardOverlayView) findViewById(R.id.overlay);
        //overlayView.setText("Hello Virtual World");
        //overlayView.show3DToast("Test text that is really long and should wrap around the view for a while so we can see in 360 degrees");
        getAppList();
    }

    @Override
    public void onNewFrame(HeadTransform headTransform) {
        final float[] angles = new float[3];
        headTransform.getEulerAngles(angles, 0);
        //NOTE: This is the messy part, and why it might be better to avoid the Cardboard SDK altogether and just use deviceOrientation in a normal split-view activity
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                overlayView.setHeadOffset(angles[1]);
            }
        });
    }

    @Override
    public void onDrawEye(Eye eye) {

    }

    @Override
    public void onFinishFrame(Viewport viewport) {

    }

    @Override
    public void onSurfaceChanged(int i, int i1) {

    }

    @Override
    public void onSurfaceCreated(EGLConfig eglConfig) {

    }

    @Override
    public void onRendererShutdown() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void getAppList() {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory("com.google.intent.category.CARDBOARD");
        mainIntent.addFlags(PackageManager.GET_INTENT_FILTERS);
        final List<ResolveInfo> pkgAppsList = getPackageManager().queryIntentActivities( mainIntent, PackageManager.GET_INTENT_FILTERS);
        for (ResolveInfo info : pkgAppsList) {
            //Log.d("getAppList", info.loadLabel(getPackageManager()).toString());
            overlayView.addShortcut( new Shortcut(info, getPackageManager()));
        }
    }
}
