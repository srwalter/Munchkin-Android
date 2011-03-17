package com.slinkman.munchkin.android.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
import com.slinkman.munchkin.android.Mover;
import com.slinkman.munchkin.android.R;
import com.slinkman.munchkin.apis.Listener;
import com.slinkman.munchkin.apis.view.FightView;

public class FightViewImpl implements FightView<View> {

	View viewHandle;
	ImageView playerUp;
	ImageView playerDown;
	ImageView monsterDown;
	ImageView monsterUp;
	
	ImageView monsterReset;
	ImageView playerReset;
	
	TextView playerChange;
	TextView playerScore;
	TextView playerTotal;
	TextView monsterChange;
	TextView monsterScore;
	TextView monsterTotal;
	TextView winText;

	Listener<Integer> monsterHandle;
	Mover move;

	@Inject
	FightViewImpl(LayoutInflater inflator, Mover inMove, final Context inContext) {
		move = inMove;
		viewHandle = inflator.inflate(R.layout.fight, null);
		Typeface tf = Typeface.createFromAsset(inContext.getAssets(),
				inContext.getString(R.string.typeface));
		// Handles to objects we'll need later
		playerUp = (ImageView) viewHandle.findViewById(R.id.fight_player_up);

		playerDown = (ImageView) viewHandle
				.findViewById(R.id.fight_player_down);

		monsterDown = (ImageView) viewHandle
				.findViewById(R.id.fight_monster_down);

		monsterUp = (ImageView) viewHandle.findViewById(R.id.fight_monster_up);

		playerChange = (TextView) viewHandle
				.findViewById(R.id.fight_player_change);
		playerChange.setTypeface(tf);

		playerScore = (TextView) viewHandle
				.findViewById(R.id.fight_player_score);
		playerScore.setTypeface(tf);

		playerTotal = (TextView) viewHandle
				.findViewById(R.id.fight_player_total);
		playerTotal.setTypeface(tf);

		monsterChange = (TextView) viewHandle
				.findViewById(R.id.fight_monster_change);
		monsterChange.setTypeface(tf);

		monsterScore = (TextView) viewHandle
				.findViewById(R.id.fight_monster_score);
		monsterScore.setTypeface(tf);

		monsterTotal = (TextView) viewHandle
				.findViewById(R.id.fight_monster_total);
		monsterTotal.setTypeface(tf);

		winText = (TextView) viewHandle.findViewById(R.id.fight_win_text);
		winText.setTypeface(tf);
		
		monsterReset = (ImageView) viewHandle.findViewById(R.id.fight_monster_reset);

		// Typeface calls for objects we'll just need now
		TextView temp = (TextView) viewHandle
				.findViewById(R.id.fight_player_title);
		temp.setTypeface(tf);

		temp = (TextView) viewHandle
				.findViewById(R.id.fight_player_change_title);
		temp.setTypeface(tf);

		temp = (TextView) viewHandle
				.findViewById(R.id.fight_monster_change_title);
		temp.setTypeface(tf);

		temp = (TextView) viewHandle.findViewById(R.id.fight_monster_title);
		temp.setTypeface(tf);

		temp = (TextView) viewHandle
				.findViewById(R.id.fight_monster_score_title);
		temp.setTypeface(tf);

		temp = (TextView) viewHandle
				.findViewById(R.id.fight_monster_total_title);
		temp.setTypeface(tf);

		temp = (TextView) viewHandle.findViewById(R.id.fight_player_title);
		temp.setTypeface(tf);

		temp = (TextView) viewHandle
				.findViewById(R.id.fight_player_score_title);
		temp.setTypeface(tf);

		temp = (TextView) viewHandle
				.findViewById(R.id.fight_player_total_title);
		temp.setTypeface(tf);
		
		temp = (TextView) viewHandle.findViewById(R.id.fight_top_title);
		temp.setTypeface(tf);
		
		LinearLayout monsterLayout = (LinearLayout) viewHandle.findViewById(R.id.fight_monster_InfoLayout);
		monsterLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new MonsterDialog(inContext, monsterHandle).show();
				
			}
		});
	}

	@Override
	public View getHandle() {
		return viewHandle;
	}

	@Override
	public void setPlayerUp(final Listener<Void> upHandle) {
		playerUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				upHandle.onAction(null);

			}
		});
	}

	@Override
	public void setPlayerDown(final Listener<Void> downHandle) {
		playerDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				downHandle.onAction(null);

			}
		});

	}

	@Override
	public void setMonsterUp(final Listener<Void> upHandle) {
		monsterUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				upHandle.onAction(null);

			}
		});

	}

	@Override
	public void setMonsterDown(final Listener<Void> downHandle) {
		monsterDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				downHandle.onAction(null);

			}
		});

	}

	@Override
	public void setBackButton(final Listener<Void> backHandle) {
		ImageView backButton = (ImageView) viewHandle
				.findViewById(R.id.fight_back_button);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				backHandle.onAction(null);
				move.moveToSummary();
			}
		});

	}

	@Override
	public void setPlayerFight(String fightScore) {
		playerScore.setText(fightScore);

	}

	@Override
	public void setPlayerModifier(String modifier) {
		playerChange.setText(modifier);

	}

	@Override
	public void setPlayerTotal(String total) {
		playerTotal.setText(total);

	}

	@Override
	public void setMonsterFight(String fightScore) {
		monsterScore.setText(fightScore);
	}

	@Override
	public void setMonsterModifier(String modifier) {
		monsterChange.setText(modifier);

	}

	@Override
	public void setMonsterTotal(String total) {
		monsterTotal.setText(total);

	}

	@Override
	public void setWinText(String winString) {
		winText.setText(winString);
	}

	@Override
	public void setMonsterHandle(Listener<Integer> monsterHandle) {
		this.monsterHandle = monsterHandle;
		
	}

	@Override
	public void setMonsterReset(final Listener<Void> resetHandle) {
		monsterReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				resetHandle.onAction(null);
				
			}
		});
		
	}

	@Override
	public void setPlayerReset(final Listener<Void> resetHandle) {
		ImageView resetImage = (ImageView) viewHandle.findViewById(R.id.fight_player_reset);
		resetImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				resetHandle.onAction(null);
				
			}
		});
		
	}

}
