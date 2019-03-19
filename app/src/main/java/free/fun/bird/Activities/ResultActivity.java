package free.fun.bird.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import free.fun.bird.R;

public class ResultActivity extends AppCompatActivity {

    private TextView mResultCountView;
    private Button mRestartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initViews();

        mResultCountView.setText(getIntent().getStringExtra("score"));
        mRestartButton.setOnClickListener((o) -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void initViews() {
        mResultCountView = findViewById(R.id.result_count);
        mRestartButton = findViewById(R.id.restart_button);
    }
}
