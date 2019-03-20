package free.fun.bird.Activities;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
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
    private TextView mScoreCountView;
    private ImageView mPlayerView;
    private ImageView mTargetOneView;
    private ImageView mTargetTwoView;
    private ImageView mTargetThreeView;
    private ImageView mBackgroundView;
    private ImageView mCrashView;
    private ImageView mCloudView_0;
    private ImageView mCloudView_1;
    private ImageView mCloudView_2;
    private ImageView mCloudView_3;
    private ImageView mCloudView_4;
    private ImageView mCloudView_5;

    // Position
    private int mPlayerX;
    private int mPlayerY;
    private int mTargetOneX;
    private int mTargetOneY;
    private int mTargetTwoX;
    private int mTargetTwoY;
    private int mTargetThreeX;
    private int mTargetThreeY;
    private int mBackgroundY;
    private int mBackgroundX;

    private int mCloudX_0;
    private int mCloudY_0;
    private int mCloudX_1;
    private int mCloudY_1;
    private int mCloudX_2;
    private int mCloudY_2;
    private int mCloudX_3;
    private int mCloudY_3;
    private int mCloudX_4;
    private int mCloudY_4;
    private int mCloudX_5;
    private int mCloudY_5;

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
    private boolean mRotateFlag = false;

    private int score = 0;

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mActionFlag = true;
            mPlayerSpeed = -25;
//            mSoundPlayer.playJumpSound();

        }

        onStartGame();
        return true;
    }

    private void onStartGame() {

        if (!mStartFlag) {
            mStartFlag = true;

            mGameFrameLayout.requestLayout();
            mPlayerView.requestLayout();

            mFrameHeight = mGameFrameLayout.getHeight();
            mPlayerY = (int) mPlayerView.getY();
            mPlayerX = (int) mPlayerView.getX();

            mPlayerViewSize = mPlayerView.getHeight();

            initHandler();

            setStartPositions();
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

    private void makeMeShake(View view, int from, int to) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotation", from, to);
        anim.setDuration(500);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
    }

    private void changePosition() {

        hitCheck();

        moveTargets();

        moveClouds();

        mPlayerY += mPlayerSpeed;

        mPlayerSpeed += 2;


        if (mPlayerY < 0) mPlayerY = 0;
        if (mPlayerY > mFrameHeight - mPlayerViewSize) mPlayerY = mFrameHeight - mPlayerViewSize;

        mPlayerView.setY(mPlayerY);

        mScoreCountView.setText(String.valueOf(score));
        mRotateFlag = !mRotateFlag;
    }

    private void moveClouds() {
        mCloudX_0 -= 5;

        if (mCloudX_0 + mCloudView_0.getWidth() < 0) {
            mCloudX_0 = mScreenHeight + 100;
            mCloudY_0 = (int) Math.floor(Math.random() * (mFrameHeight - mCloudView_0.getWidth()));
        }

        mCloudView_0.setX(mCloudX_0);
        mCloudView_0.setY(mCloudY_0);

        mCloudX_1 -= 10;

        if (mCloudX_1 + mCloudView_1.getWidth() < 0) {
            mCloudX_1 = mScreenHeight + 100;
            mCloudY_1 = (int) Math.floor(Math.random() * (mFrameHeight - mCloudView_1.getWidth()));
        }

        mCloudView_1.setX(mCloudX_1);
        mCloudView_1.setY(mCloudY_1);

        mCloudX_4 -= 8;

        if (mCloudX_4 + mCloudView_4.getWidth() < 0) {
            mCloudX_4 = mScreenHeight + 100;
            mCloudY_4 = (int) Math.floor(Math.random() * (mFrameHeight - mCloudView_4.getWidth()));
        }

        mCloudView_4.setX(mCloudX_4);
        mCloudView_4.setY(mCloudY_4);


    }

    private void moveTargets() {
        // Target One
        mTargetOneX -= mTargetOneSpeed;
        if (mTargetOneX + mTargetOneView.getWidth() < 0) {
            mTargetOneX = mScreenHeight + 100;
            mTargetOneY = (int) Math.floor(Math.random() * (mFrameHeight - mTargetOneView.getHeight()));
        }

        mTargetOneView.setX(mTargetOneX);
        mTargetOneView.setY(mTargetOneY);

        // Target Two
        mTargetTwoX -= mTargetTwoSpeed;
        if (mTargetTwoX + mTargetTwoView.getWidth() < 0) {
            mTargetTwoX = mScreenHeight + 1000;
            mTargetTwoY = (int) Math.floor(Math.random() * (mFrameHeight - mTargetTwoView.getWidth()));
        }

        mTargetTwoView.setX(mTargetTwoX);
        mTargetTwoView.setY(mTargetTwoY);

        // Target Three
        mTargetThreeX -= mTargetThreeSpeed;
        if (mTargetThreeX + mTargetThreeView.getWidth() < 0) {
            mTargetThreeX = mScreenHeight + 500;
            mTargetThreeY = (int) Math.floor(Math.random() * (mFrameHeight - mTargetThreeView.getWidth()));
        }

        mTargetThreeView.setX(mTargetThreeX);
        mTargetThreeView.setY(mTargetThreeY);

    }

    private void hitCheck() {
        final int targetOneCenterX = mTargetOneX + (mTargetOneView.getWidth() / 2);
        final int targetOneCenterY = mTargetOneY + (mTargetOneView.getHeight() / 2);

        if (0 <= targetOneCenterX && targetOneCenterX <= mPlayerViewSize
                && mPlayerY <= targetOneCenterY && targetOneCenterY <= mPlayerY + mPlayerViewSize) {
            mTargetOneX = -500;
            score += 10;
        }

        final int targetTwoCenterX = mTargetTwoX + (mTargetTwoView.getWidth() / 2);
        final int targetTwoCenterY = mTargetTwoY + (mTargetTwoView.getHeight() / 2);

        if (0 <= targetTwoCenterX && targetTwoCenterX <= mPlayerViewSize
                && mPlayerY <= targetTwoCenterY && targetTwoCenterY <= mPlayerY + mPlayerViewSize) {
            mTargetTwoX = -500;
            score += 20;
        }

        final int targetThreeCenterX = mTargetThreeX + (mTargetThreeView.getWidth() / 2);
        final int targetThreeCenterY = mTargetThreeY + (mTargetThreeView.getHeight() / 2);

        if (0 <= targetThreeCenterX && targetThreeCenterX <= mPlayerViewSize
                && mPlayerY <= targetThreeCenterY && targetThreeCenterY <= mPlayerY + mPlayerViewSize) {
            score -= 50;

            onCrash();

//            onGameOver();
        }
    }

    private void onCrash() {
        mCrashView.setY(mPlayerY);
        mCrashView.setX(-100);
        mCrashView.setVisibility(View.VISIBLE);
        mTimer.cancel();
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
                runOnUiThread(() -> changePosition());
            }
        }, 0, 30);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    makeMeShake(mTargetOneView, mRotateFlag ? 0 : 20, mRotateFlag ? 20 : 0);
                    makeMeShake(mTargetTwoView, mRotateFlag ? 0 : 20, mRotateFlag ? 20 : 0);
                    makeMeShake(mTargetThreeView, mRotateFlag ? 0 : 20, mRotateFlag ? 20 : 0);
                });
            }
        }, 0, 100);
    }

    /**
     * Move views to out of screen.
     */
    private void setStartPositions() {
        mTargetOneView.setX(1000);
        mTargetOneView.setY(1000);
        mTargetTwoView.setX(1000);
        mTargetTwoView.setY(1000);
        mTargetThreeView.setX(1000);
        mTargetThreeView.setY(1000);

        mPlayerSpeed = 10;
        mTargetOneSpeed = 5;
        mTargetTwoSpeed = 5;
        mTargetThreeSpeed = 5;

        mTargetOneView.setVisibility(View.VISIBLE);
        mTargetTwoView.setVisibility(View.VISIBLE);
        mTargetThreeView.setVisibility(View.VISIBLE);

        mCloudView_0.setVisibility(View.VISIBLE);
        mCloudView_1.setVisibility(View.VISIBLE);
        mCloudView_4.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        mHeartCountLayout = findViewById(R.id.heart_count);
        mGameFrameLayout = findViewById(R.id.game_frame);
        mScoreCountView = findViewById(R.id.source_count);
        mPlayerView = findViewById(R.id.player);
        mTargetOneView = findViewById(R.id.target_one);
        mTargetTwoView = findViewById(R.id.target_two);
        mTargetThreeView = findViewById(R.id.target_three);
        mCrashView = findViewById(R.id.bdsh);
        mCloudView_0 = findViewById(R.id.cloud_0);
        mCloudView_1 = findViewById(R.id.cloud_1);
        mCloudView_2 = findViewById(R.id.cloud_2);
        mCloudView_3 = findViewById(R.id.cloud_3);
        mCloudView_4 = findViewById(R.id.cloud_4);
        mCloudView_5 = findViewById(R.id.cloud_5);
//        mBackgroundView = findViewById(R.id.background_view);
    }
}
