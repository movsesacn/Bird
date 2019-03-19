package free.fun.bird.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import free.fun.bird.R;
import free.fun.bird.SoundPlayer;

public class MainActivity extends AppCompatActivity {

    private Handler mHandler;
    private Timer mTimer;
    private SoundPlayer mSoundPlayer;

    private LinearLayout mHeartCountLayout;
    private FrameLayout mGameFrameLayout;
    private TextView mTapToStartView;
    private TextView mScoreCountView;
    private ImageView mPlayerView;
    private ImageView mTargetOneView;
    private ImageView mTargetTwoView;
    private ImageView mTargetThreeView;
    private ImageView mBackgroundView;

    // Position
    private int mPlayerY;
    private int mTargetOneX;
    private int mTargetOneY;
    private int mTargetTwoX;
    private int mTargetTwoY;
    private int mTargetThreeX;
    private int mTargetThreeY;
    private int mBackgroundY;
    private int mBackgroundX;

    // Size
    private int mFrameHeight;
    private int mPlayerViewSize;
    private int mScreenHeight;
    private int mScreenWidth;

    // Speed
    private int mPlayerSpeed;
    private int mTargetOneSpeed;
    private int mTargetTwoSpeed;
    private int mTargetThreeSpeed;
    private int mBackgroundSpeed;

    private boolean mActionFlag = false;
    private boolean mStartFlag = false;

    private int score = 0;
    int a = 5;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler();
        mTimer = new Timer();
        mSoundPlayer = new SoundPlayer(this);

        initViews();

        getScreenSize();

        setStartPositions();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mActionFlag = true;
            mPlayerSpeed = -25;
//            mSoundPlayer.playJumpSound();

        }

        onTouchScreen();
        return true;
    }

    private void onTouchScreen() {

        if (!mStartFlag) {
            mStartFlag = true;

            mGameFrameLayout.requestLayout();
            mPlayerView.requestLayout();

            mFrameHeight = mGameFrameLayout.getHeight();
            mPlayerY = (int) mPlayerView.getY();

            mPlayerViewSize = mPlayerView.getHeight();

            mTapToStartView.setVisibility(View.GONE);

            initHandler();
        }
    }

    private void getScreenSize() {
        final WindowManager manager = getWindowManager();
        final Display display = manager.getDefaultDisplay();
        final Point point = new Point();
        display.getSize(point);

        mScreenHeight = point.y;
        mScreenWidth = point.x;
    }

    private void changePosition() {

        hitCheck();

        // Target One
        mTargetOneX -= mTargetOneSpeed;
        if (mTargetOneX < 0) {
            mTargetOneX = mScreenHeight + 20;
            mTargetOneY = (int) Math.floor(Math.random() * (mFrameHeight - mTargetOneView.getWidth()));
        }

        mTargetOneView.setX(mTargetOneX);
        mTargetOneView.setY(mTargetOneY);


        // Target Two
        mTargetTwoX -= mTargetTwoSpeed;
        if (mTargetTwoX < 0) {
            mTargetTwoX = mScreenHeight + 1000;
            mTargetTwoY = (int) Math.floor(Math.random() * (mFrameHeight - mTargetTwoView.getWidth()));
        }

        mTargetTwoView.setX(mTargetTwoX);
        mTargetTwoView.setY(mTargetTwoY);


        // Target Three
        mTargetThreeX -= mTargetThreeSpeed;
        if (mTargetThreeX < 0) {
            mTargetThreeX = mScreenHeight + 500;
            mTargetThreeY = (int) Math.floor(Math.random() * (mFrameHeight - mTargetThreeView.getWidth()));
        }

        mTargetThreeView.setX(mTargetThreeX);
        mTargetThreeView.setY(mTargetThreeY);


        mPlayerY += mPlayerSpeed;

        mPlayerSpeed += 2;


        if (mPlayerY < 0) mPlayerY = 0;
        if (mPlayerY > mFrameHeight - mPlayerViewSize) mPlayerY = mFrameHeight - mPlayerViewSize;

        mPlayerView.setY(mPlayerY);

        mScoreCountView.setText(String.valueOf(score));
    }

    private void hitCheck() {
        final int targetOneCenterX = mTargetOneX + (mTargetOneView.getWidth() / 2);
        final int targetOneCenterY = mTargetOneY + (mTargetOneView.getHeight() / 2);

        if (0 <= targetOneCenterX && targetOneCenterX <= mPlayerViewSize
                && mPlayerY <= targetOneCenterY && targetOneCenterY <= mPlayerY + mPlayerViewSize) {
            mTargetOneX = -10;
            score += 10;
        }

        final int targetTwoCenterX = mTargetTwoX + (mTargetTwoView.getWidth() / 2);
        final int targetTwoCenterY = mTargetTwoY + (mTargetTwoView.getHeight() / 2);

        if (0 <= targetTwoCenterX && targetTwoCenterX <= mPlayerViewSize
                && mPlayerY <= targetTwoCenterY && targetTwoCenterY <= mPlayerY + mPlayerViewSize) {
            mTargetTwoX = -10;
            score += 20;
        }

        final int targetThreeCenterX = mTargetThreeX + (mTargetThreeView.getWidth() / 2);
        final int targetThreeCenterY = mTargetThreeY + (mTargetThreeView.getHeight() / 2);

        if (0 <= targetThreeCenterX && targetThreeCenterX <= mPlayerViewSize
                && mPlayerY <= targetThreeCenterY && targetThreeCenterY <= mPlayerY + mPlayerViewSize) {
            mTargetOneX = -10;
            mTimer.cancel();

            onGameOver();
        }
    }

    private void onGameOver() {
        final Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("score", String.valueOf(score));
        startActivity(intent);
        finish();
    }

    private void initHandler() {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(() -> changePosition());
            }
        }, 0, 15);
    }

    /**
     * Move views to out of screen.
     */
    private void setStartPositions() {
        mTargetOneView.setX(-80);
        mTargetOneView.setY(-80);
        mTargetTwoView.setX(-80);
        mTargetTwoView.setY(-80);
        mTargetThreeView.setX(-80);
        mTargetThreeView.setY(-80);


        mPlayerSpeed = 10;
        mTargetOneSpeed = 15;
        mTargetTwoSpeed = 13;
        mTargetThreeSpeed = 17;

    }

    private void initViews() {
        mHeartCountLayout = findViewById(R.id.heart_count);
        mGameFrameLayout = findViewById(R.id.game_frame);
        mTapToStartView = findViewById(R.id.tap_to_start);
        mScoreCountView = findViewById(R.id.source_count);
        mPlayerView = findViewById(R.id.player);
        mTargetOneView = findViewById(R.id.target_one);
        mTargetTwoView = findViewById(R.id.target_two);
        mTargetThreeView = findViewById(R.id.target_three);
//        mBackgroundView = findViewById(R.id.background_view);
    }
}
