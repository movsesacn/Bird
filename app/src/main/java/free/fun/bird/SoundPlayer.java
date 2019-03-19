package free.fun.bird;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class SoundPlayer {

    private final int SOUND_POOL_MAX = 2;

    private SoundPool mSoundPool;
    private AudioAttributes mAudioAttributes;
    private int mJumpSound;
    private int mOverSound;

    public SoundPlayer(Context context) {
        mAudioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(mAudioAttributes)
                .setMaxStreams(SOUND_POOL_MAX)
                .build();

        mJumpSound = mSoundPool.load(context, R.raw.jump, 1);
        mOverSound = mSoundPool.load(context, R.raw.jump, 1);
    }

    public void playJumpSound() {
        mSoundPool.play(mJumpSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playOverSound() {

    }
}
