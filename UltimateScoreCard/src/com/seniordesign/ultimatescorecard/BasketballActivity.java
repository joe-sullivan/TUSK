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
	FlyOutContainer _root;
	MainActivity _mainActivity;
	TextView _homeTextView, _awayTextView;
	Button _p1Button, _p2Button, _p3Button, _p4Button, _p5Button;
	private boolean _possession = false;								//boolean for tracking possession (false:home, true:away)
	private GetTeamInformation _gpi = new GetTeamInformation();			//see the class to understand it
	
	//what the program should do when this page is created
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this._root = (FlyOutContainer)this.getLayoutInflater().inflate(R.layout.activity_basketball, null);
		setContentView(_root);
		
		String home = getIntent().getExtras().getString("Home");			//getting the data we had passed in with the codeword
		String away = getIntent().getExtras().getString("Away");
		
		_homeTextView = (TextView)findViewById(R.id.homeTextView);			//saving our home and away name text view in a variable
		if(home != null){													//if we passed a string from previous activity, have the name for the teams appear
			_homeTextView.setText(_gpi.abbreviation(home));					//if we did not, then use the generic ones
		}
		_awayTextView = (TextView)findViewById(R.id.awayTextView);
		if(away != null){
			_awayTextView.setText(_gpi.abbreviation(away));
		}
		
		_p1Button = (Button)findViewById(R.id.homeP1Button);			//saving a reference for our buttons to a member variable
		_p1Button.setText(_gpi.player1(home));							//setting the buttons on our slide-out menu with name of players
		
		_p2Button = (Button)findViewById(R.id.homeP2Button);
		_p2Button.setText(_gpi.player2(home));
		
		_p3Button = (Button)findViewById(R.id.homeP3Button);
		_p3Button.setText(_gpi.player3(home));
		
		_p4Button = (Button)findViewById(R.id.homeP4Button);
		_p4Button.setText(_gpi.player4(home));
		
		_p5Button = (Button)findViewById(R.id.homeP5Button);
		_p5Button.setText(_gpi.player5(home));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//when xml:onclick (opening the slide out menu)
	public void toggleMenu(View v){
		this._root.toggleMenu();
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
						if(_possession){
							changeScore(homeScoreText, 3);								//home team scored
						}
						else {
							changeScore(awayScoreText, 3);								//away team scored
						}
					}
					else if (blueValue > redValue && blueValue > greenValue){			//if blue, shot is 2 points (in the paint)
						if(_possession){
							changeScore(homeScoreText, 2);
						}
						else {
							changeScore(awayScoreText, 2);
						}
					}
					else if (greenValue > blueValue && greenValue > redValue){			//if green, shot is 2 points (outside the paint)
						if(_possession){
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
		_possession = !_possession;														//change possessions
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
	
	//again handling the back button being pressed, no more explaining needed
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
