package com.gibom.ex41_myleaderboard;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends BaseGameActivity {

	private SignInButton btnSignIn;
	private Button btnShowLeaderboard;
	private Button btnShowAchievement;
	private EditText editPoint;
	private EditText editTime;
	private int nStep = 1;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

	
		btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
		btnSignIn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// google login
				beginUserInitiatedSignIn();
			}
		});
		
		editPoint = (EditText) findViewById(R.id.edit_point);
		Button btnPoint = (Button) findViewById(R.id.btn_point);
		btnPoint.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (isSignedIn())
				{
					// 최고 점수 등록
					Games.Leaderboards.submitScore(
							getApiClient(),
							getString(R.string.leaderboard_point),
							Integer.parseInt(editPoint.getText().toString()));
				}
			}
		});
		
		editTime = (EditText) findViewById(R.id.edit_time);
		Button btnTime = (Button) findViewById(R.id.btn_time);
		btnTime.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (isSignedIn())
				{
					// 소요 시간 등록
					Games.Leaderboards.submitScore(
							getApiClient(),
							getString(R.string.leaderboard_time),
							Integer.parseInt(editTime.getText().toString()));
				}
			}
		});
		
		Button btnAchievement1 = (Button) findViewById(R.id.btn_achievement1);
		btnAchievement1.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 업적1 달성
				if (getApiClient().isConnected())
				{
					Games.Achievements.unlock(
							getApiClient(),
							getString(R.string.achievement_once));
				}
			}
		});
		
		Button btnAchievement2 = (Button) findViewById(R.id.btn_achievement2);
		btnAchievement2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 업적2 달성
				if (getApiClient().isConnected())
				{
					Games.Achievements.increment(
							getApiClient(),
							getString(R.string.achievement_progress),
							nStep);
					nStep += 1;
				}
			}
		});
		
		btnShowLeaderboard = (Button) findViewById(R.id.btn_show_leaderboard);
		btnShowLeaderboard.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 리더보드 확인
//				if (isSignedIn())
//				{
//					startActivityForResult(
//							Games.Leaderboards.getLeaderboardIntent(
//									getApiClient(),
//									getString(R.string.leaderboard_point)),
//							1001);
//				}
				if (isSignedIn())
				{
					startActivityForResult(
							Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()),
							1001);
				}

			}
		});
		
		btnShowAchievement = (Button) findViewById(R.id.btn_show_achievement);
		btnShowAchievement.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// 업적 확인
				startActivityForResult(
						Games.Achievements.getAchievementsIntent(getApiClient()),
						1002);
			}
		});

	
	}

	@Override
	public void onSignInFailed() {
		
	}

	@Override
	public void onSignInSucceeded() {

		//btnSignIn.setVisibility(View.GONE);
		btnShowAchievement.setEnabled(true);
		btnShowLeaderboard.setEnabled(true);		
	}


}

