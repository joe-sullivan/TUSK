package com.seniordesign.ultimatescorecard;

import com.seniordesign.ultimatescorecard.view.viewgroup.FlyOutContainer;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BasketballActivity extends Activity{
	FlyOutContainer root;
	MainActivity mainActivity;
	TextView homeTextView, awayTextView;
	Button p1Button, p2Button, p3Button, p4Button, p5Button;
	private boolean possession = false;				//boolean for tracking possession (false:home, true:away)
	private GetTeamInformation gpi = new GetTeamInformation();
	
	//what the program should do when this page is created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.root = (FlyOutContainer)this.getLayoutInflater().inflate(R.layout.activity_basketball, null);
		setContentView(root);
		
		String home = getIntent().getExtras().getString("Home");
		String away = getIntent().getExtras().getString("Away");
		
		homeTextView = (TextView)findViewById(R.id.homeTextView);
		if(home != null){
			homeTextView.setText(gpi.abbreviation(home));
		}
		awayTextView = (TextView)findViewById(R.id.awayTextView);
		if(away != null){
			awayTextView.setText(gpi.abbreviation(away));
		}
		
		p1Button = (Button)findViewById(R.id.homeP1Button);
		p1Button.setText(gpi.player1(home));
		
		p2Button = (Button)findViewById(R.id.homeP2Button);
		p2Button.setText(gpi.player2(home));
		
		p3Button = (Button)findViewById(R.id.homeP3Button);
		p3Button.setText(gpi.player3(home));
		
		p4Button = (Button)findViewById(R.id.homeP4Button);
		p4Button.setText(gpi.player4(home));
		
		p5Button = (Button)findViewById(R.id.homeP5Button);
		p5Button.setText(gpi.player5(home));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//when xml:onclick (opening the slide out menu)
	public void toggleMenu(View v){
		this.root.toggleMenu();
	}

	//when xml:onclick calls this method (for interactive map)
	public boolean interaction(View v) {
		final ImageView view = (ImageView) findViewById(R.id.basketballCourt);			//calling the components for xml view
		final ImageView mask = (ImageView) findViewById(R.id.basketballCourtMask);
		final Bitmap bitmap = ((BitmapDrawable) mask.getDrawable()).getBitmap();
		final TextView homeScoreText = (TextView) findViewById(R.id.homeScoreTextView);
		final TextView awayScoreText = (TextView) findViewById(R.id.awayScoreTextView);
		view.setOnTouchListener(new OnTouchListener(){									//setting an on touch listener					
			@Override
			public boolean onTouch(View v, MotionEvent event) {							//what to do on touch
				if(event.getAction() == MotionEvent.ACTION_DOWN){						//so that the code executes once only
					int pixel = bitmap.getPixel((int)event.getX(), (int)event.getY());	//getting pixel color value from touched point
					int redValue = Color.red(pixel);									//getting the RGB values from the pixel
					int blueValue = Color.blue(pixel);
					int greenValue = Color.green(pixel);
					if(redValue > blueValue && redValue > greenValue){					//if red, shot is from 3
						if(possession){
							changeScore(homeScoreText, 3);
						}
						else {
							changeScore(awayScoreText, 3);
						}
					}
					else if (blueValue > redValue && blueValue > greenValue){			//if blue, shot is 2 points (in the paint)
						if(possession){
							changeScore(homeScoreText, 2);
						}
						else {
							changeScore(awayScoreText, 2);
						}
					}
					else if (greenValue > blueValue && greenValue > redValue){			//if green, shot is 2 points (outside the paint)
						if(possession){
							changeScore(homeScoreText, 2);
						}
						else {
							changeScore(awayScoreText, 2);
						}
					}
				}
				return false;
			}
		});
		possession = !possession;														//change possessions
		return false;
	}
	
	//private method to change the score
	private void changeScore(TextView scoreText, int pointAdd){							//given text view and point to be added
		int score = Integer.parseInt(scoreText.getText().toString());					//read and change to integer the text box
		score += pointAdd;																//add the points
		if(score < 10){
			scoreText.setText("00" + Integer.toString(score));
		}
		else if(score < 100){								
			scoreText.setText("0" + Integer.toString(score));							//need a zero in front if less than 100
		}
		else{
			scoreText.setText(Integer.toString(score));									//anyways just put the score back into text box
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			Builder alert = new Builder(this);
			alert.setTitle("Return to Team Selection");
			alert.setMessage("Are you sure?");
			
			alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					onBackPressed();
					finish();
				}
			});
			alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
										
				}
			});
			
			alert.show();
			return true;
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
