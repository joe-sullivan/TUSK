package com.scorecard;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddTeamActivity extends ActionBarActivity {

    EditText _teamName, _coachName, _player1, _player2, _player3, _player4, _player5, _player6, _player7,
            _player8, _player9, _player10, _player11, _sub1, _sub2, _sub3, _sub4, _sub5, _sub6, _sub7;

    List<Team> Teams = new ArrayList<Team>();
    ListView teamListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);

        _teamName = (EditText) findViewById(R.id.teamNametxt);
        _coachName = (EditText) findViewById(R.id.coachNametxt);
        _player1 = (EditText) findViewById(R.id.player1txt);
        _player2 = (EditText) findViewById(R.id.player2txt);
        _player3 = (EditText) findViewById(R.id.player3txt);
        _player4 = (EditText) findViewById(R.id.player4txt);
        _player5 = (EditText) findViewById(R.id.player5txt);
        _player6 = (EditText) findViewById(R.id.player6txt);
        _player7 = (EditText) findViewById(R.id.player7txt);
        _player8 = (EditText) findViewById(R.id.player8txt);
        _player9 = (EditText) findViewById(R.id.player9txt);
        _player10 = (EditText) findViewById(R.id.player10txt);
        _player11 = (EditText) findViewById(R.id.player11txt);
        _sub1 = (EditText) findViewById(R.id.sub1txt);
        _sub2 = (EditText) findViewById(R.id.sub2txt);
        _sub3 = (EditText) findViewById(R.id.sub3txt);
        _sub4 = (EditText) findViewById(R.id.sub4txt);
        _sub5 = (EditText) findViewById(R.id.sub5txt);
        _sub6 = (EditText) findViewById(R.id.sub6txt);
        _sub7 = (EditText) findViewById(R.id.sub7txt);

        teamListView = (ListView) findViewById(R.id.listview);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("team creator");
        tabSpec1.setContent(R.id.tabTeamCreator);
        tabSpec1.setIndicator("Team Creator");
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("team list");
        tabSpec2.setContent(R.id.tabTeamList);
        tabSpec2.setIndicator("Team List");
        tabHost.addTab(tabSpec2);

        final Button ContinueGameButton = (Button) findViewById(R.id.button_ContinueGame);
        ContinueGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),com.scorecard.SoccerActivity.class);
                startActivity(i);
            }
        });

        final Button DoneAddTeamButton = (Button) findViewById(R.id.button_AddTeam);
        DoneAddTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkTeam();
                addTeam(_teamName.getText().toString(),_coachName.getText().toString(),_player1.getText().toString(),
                        _player2.getText().toString(),_player3.getText().toString(),_player4.getText().toString(),
                        _player5.getText().toString(),_player6.getText().toString(),_player7.getText().toString(),
                        _player8.getText().toString(),_player9.getText().toString(),_player10.getText().toString(),
                        _player11.getText().toString(),_sub1.getText().toString(),_sub2.getText().toString(),
                        _sub3.getText().toString(),_sub4.getText().toString(),_sub5.getText().toString(),
                        _sub6.getText().toString(),_sub7.getText().toString());
                populateList();
                Toast.makeText(getApplicationContext(),"Your team '" +_teamName.getText().toString() +"' has been Created",Toast.LENGTH_SHORT).show();
                set_TeamEmpty();
            }
        });

        _teamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                DoneAddTeamButton.setEnabled(!_teamName.getText().toString().trim().isEmpty());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void set_TeamEmpty(){
        _teamName.setText("");
        _coachName.setText("");
        _player1.setText("");
        _player2.setText("");
        _player3.setText("");
        _player4.setText("");
        _player5.setText("");
        _player6.setText("");
        _player7.setText("");
        _player8.setText("");
        _player9.setText("");
        _player10.setText("");
        _player11.setText("");
        _sub1.setText("");
        _sub2.setText("");
        _sub3.setText("");
        _sub4.setText("");
        _sub5.setText("");
        _sub6.setText("");
        _sub7.setText("");
    }

    private void checkTeam(){
        if (_coachName.getText().toString().trim().isEmpty()){
            _coachName.setText("the coach");
        }
        if (_player1.getText().toString().trim().isEmpty()){
            _player1.setText("goalkeeper");
        }
        if (_player2.getText().toString().trim().isEmpty()){
            _player2.setText("defense 1");
        }
        if (_player3.getText().toString().trim().isEmpty()){
            _player3.setText("defense 2");
        }
        if (_player4.getText().toString().trim().isEmpty()){
            _player4.setText("defense 3");
        }
        if (_player5.getText().toString().trim().isEmpty()){
            _player5.setText("defense 4");
        }
        if (_player6.getText().toString().trim().isEmpty()){
            _player6.setText("midfielder 1");
        }
        if (_player7.getText().toString().trim().isEmpty()){
            _player7.setText("midfielder 2");
        }
        if (_player8.getText().toString().trim().isEmpty()){
            _player8.setText("midfielder 3");
        }
        if (_player9.getText().toString().trim().isEmpty()){
            _player9.setText("attacker 1");
        }
        if (_player10.getText().toString().trim().isEmpty()){
            _player10.setText("attacker 2");
        }
        if (_player11.getText().toString().trim().isEmpty()){
            _player11.setText("attacker 3");
        }
        if (_sub1.getText().toString().trim().isEmpty()){
            _sub1.setText("bench 1");
        }
        if (_sub2.getText().toString().trim().isEmpty()){
            _sub2.setText("bench 2");
        }
        if (_sub3.getText().toString().trim().isEmpty()){
            _sub3.setText("bench 3");
        }
        if (_sub4.getText().toString().trim().isEmpty()){
            _sub4.setText("bench 4");
        }
        if (_sub5.getText().toString().trim().isEmpty()){
            _sub5.setText("bench 5");
        }
        if (_sub6.getText().toString().trim().isEmpty()){
            _sub6.setText("bench 6");
        }
        if (_sub7.getText().toString().trim().isEmpty()){
            _sub7.setText("bench 7");
        }
    }

    private void populateList(){
        ArrayAdapter<Team> adapter = new TeamListAdapter();
        teamListView.setAdapter(adapter);
    }

    private void addTeam (String tName, String tCoach, String tPlay1, String tPlay2, String tPlay3, String tPlay4, String tPlay5,
                          String tPlay6, String tPlay7, String tPlay8, String tPlay9, String tPlay10, String tPlay11, String tSub1,
                          String tSub2, String tSub3, String tSub4, String tSub5, String tSub6, String tSub7){

        Teams.add(new Team(tName, tCoach, tPlay1, tPlay2, tPlay3, tPlay4, tPlay5, tPlay6, tPlay7, tPlay8, tPlay9, tPlay10, tPlay11,
                tSub1, tSub2, tSub3, tSub4, tSub5, tSub6, tSub7));
    }

    private class TeamListAdapter extends ArrayAdapter<Team> {

        public TeamListAdapter(){
            super(AddTeamActivity.this, R.layout.listview_item, Teams);
        }
        @Override
        public View getView(int position, View view, ViewGroup parent){

            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Team currentTeam = Teams.get(position);

            TextView TeamName = (TextView) view.findViewById(R.id.team_Name);
            TeamName.setText(currentTeam.get_tName());

            TextView TeamCoach = (TextView) view.findViewById(R.id.team_Coach);
            TeamCoach.setText(currentTeam.get_tCoach());

            TextView Player_1 = (TextView) view.findViewById(R.id.teamP1);
            Player_1.setText(currentTeam.get_tPlay1());

            TextView Player_2 = (TextView) view.findViewById(R.id.teamP2);
            Player_2.setText(currentTeam.get_tPlay2());

            TextView Player_3 = (TextView) view.findViewById(R.id.teamP3);
            Player_3.setText(currentTeam.get_tPlay3());

            TextView Player_4 = (TextView) view.findViewById(R.id.teamP4);
            Player_4.setText(currentTeam.get_tPlay4());

            TextView Player_5 = (TextView) view.findViewById(R.id.teamP5);
            Player_5.setText(currentTeam.get_tPlay5());

            TextView Player_6 = (TextView) view.findViewById(R.id.teamP6);
            Player_6.setText(currentTeam.get_tPlay6());

            TextView Player_7 = (TextView) view.findViewById(R.id.teamP7);
            Player_7.setText(currentTeam.get_tPlay7());

            TextView Player_8 = (TextView) view.findViewById(R.id.teamP8);
            Player_8.setText(currentTeam.get_tPlay8());

            TextView Player_9 = (TextView) view.findViewById(R.id.teamP9);
            Player_9.setText(currentTeam.get_tPlay9());

            TextView Player_10 = (TextView) view.findViewById(R.id.teamP10);
            Player_10.setText(currentTeam.get_tPlay10());

            TextView Player_11 = (TextView) view.findViewById(R.id.teamP11);
            Player_11.setText(currentTeam.get_tPlay11());

            TextView Sub_1 = (TextView) view.findViewById(R.id.teamSub1);
            Sub_1.setText(currentTeam.get_tSub1());

            TextView Sub_2 = (TextView) view.findViewById(R.id.teamSub2);
            Sub_2.setText(currentTeam.get_tSub2());

            TextView Sub_3 = (TextView) view.findViewById(R.id.teamSub3);
            Sub_3.setText(currentTeam.get_tSub3());

            TextView Sub_4 = (TextView) view.findViewById(R.id.teamSub4);
            Sub_4.setText(currentTeam.get_tSub4());

            TextView Sub_5 = (TextView) view.findViewById(R.id.teamSub5);
            Sub_5.setText(currentTeam.get_tSub5());

            TextView Sub_6 = (TextView) view.findViewById(R.id.teamSub6);
            Sub_6.setText(currentTeam.get_tSub6());

            TextView Sub_7 = (TextView) view.findViewById(R.id.teamSub7);
            Sub_7.setText(currentTeam.get_tSub7());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
