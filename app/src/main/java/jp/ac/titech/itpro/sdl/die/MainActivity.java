package jp.ac.titech.itpro.sdl.die;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, Runnable {
    private final static String TAG = MainActivity.class.getSimpleName();

    private final static long DELAY = 50;

    private GLSurfaceView glView;
    private SimpleRenderer renderer;

    private Cube cube;
    private Pyramid pyramid;

    private SeekBar seekBarX;
    private SeekBar seekBarY;
    private SeekBar seekBarZ;
    private ToggleButton buttonX;
    private ToggleButton buttonY;
    private ToggleButton buttonZ;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_main);

        glView = findViewById(R.id.gl_view);
        seekBarX = findViewById(R.id.seekbar_x);
        seekBarY = findViewById(R.id.seekbar_y);
        seekBarZ = findViewById(R.id.seekbar_z);
        seekBarX.setMax(360);
        seekBarY.setMax(360);
        seekBarZ.setMax(360);
        seekBarX.setOnSeekBarChangeListener(this);
        seekBarY.setOnSeekBarChangeListener(this);
        seekBarZ.setOnSeekBarChangeListener(this);

        buttonX = findViewById(R.id.seekbar_x_button);
        buttonY = findViewById(R.id.seekbar_y_button);
        buttonZ = findViewById(R.id.seekbar_z_button);

        handler = new Handler();
        renderer = new SimpleRenderer();
        cube = new Cube();
        pyramid = new Pyramid();
        renderer.setObj(cube);
        glView.setRenderer(renderer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        glView.onResume();
        handler.postDelayed(this, DELAY);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        glView.onPause();
        handler.removeCallbacks(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");
        switch (item.getItemId()) {
        case R.id.menu_cube:
            renderer.setObj(cube);
            break;
        case R.id.menu_pyramid:
            renderer.setObj(pyramid);
            break;
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
        case R.id.seekbar_x:
            renderer.rotateObjX(progress);
            break;
        case R.id.seekbar_y:
            renderer.rotateObjY(progress);
            break;
        case R.id.seekbar_z:
            renderer.rotateObjZ(progress);
            break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void run() {
        if (buttonX.isChecked()) {
            if (seekBarX.getProgress() >= 360) {
                seekBarX.setProgress(0);
            } else {
                seekBarX.incrementProgressBy(1);
            }
        }
        if (buttonY.isChecked()) {
            if (seekBarY.getProgress() >= 360) {
                seekBarY.setProgress(0);
            } else {
                seekBarY.incrementProgressBy(1);
            }
        }
        if (buttonZ.isChecked()) {
            if (seekBarZ.getProgress() >= 360) {
                seekBarZ.setProgress(0);
            } else {
                seekBarZ.incrementProgressBy(1);
            }
        }
        handler.postDelayed(this, DELAY);
    }
}
