package com.scorecard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.app.Activity;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class SoccerActivity extends ActionBarActivity {

    ExpandableListAdapter listAdapterA;
    ExpandableListAdapter listAdapterB;
    ExpandableListView expListViewA;
    ExpandableListView expListViewB;
    List<String> listDataHeaderA;
    List<String> listDataHeaderB;
    HashMap<String, List<String>> listDataChildA;
    HashMap<String, List<String>> listDataChildB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer);

        // get the listview
        expListViewA = (ExpandableListView) findViewById(R.id.exListViewA);
        expListViewB = (ExpandableListView) findViewById(R.id.exListViewB);

        // preparing list data
        prepareListData();
        listAdapterA = new ExpandableListAdapter(this, listDataHeaderA, listDataChildA);
        listAdapterB = new ExpandableListAdapter(this, listDataHeaderB, listDataChildB);

        // setting list adapter
        expListViewA.setAdapter(listAdapterA);
        expListViewB.setAdapter(listAdapterB);

        // Listview Group expanded listener
        expListViewA.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT)
                .show();*/
            }
        });
        expListViewB.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        // Listview Group collasped listener
        expListViewA.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT)
                .show();*/
            }
        });
        expListViewB.setOnGroupCollapseListener(new OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // Listview on child click listeners
        expListViewA.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parentA, View v,
                                        int groupPositionA, int childPositionA, long id) {
                Toast.makeText(getApplicationContext(), listDataHeaderA.get(groupPositionA) + " : " +
                        listDataChildA.get(listDataHeaderA.get(groupPositionA)).get(childPositionA), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        expListViewB.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parentB, View v,
                                        int groupPositionB, int childPositionB, long id) {
                Toast.makeText(getApplicationContext(), listDataHeaderB.get(groupPositionB) + " : " +
                        listDataChildB.get(listDataHeaderB.get(groupPositionB)).get(childPositionB), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeaderA = new ArrayList<String>();
        listDataHeaderB = new ArrayList<String>();
        listDataChildA = new HashMap<String, List<String>>();
        listDataChildB = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeaderA.add("Home Team");
        listDataHeaderB.add("Away Team");

        // Adding child data
        List<String> TeamHome = new ArrayList<String>();
        TeamHome.add("The Sharks");
        TeamHome.add("The Huskies");
        TeamHome.add("Dark Knights");
        TeamHome.add("Angry Men");

        listDataChildA.put(listDataHeaderA.get(0), TeamHome); // Header, Child A data
        listDataChildB.put(listDataHeaderB.get(0), TeamHome); // Header, Child B data
    }
    public void gotoGame(View v){
        Intent i = new Intent(getApplicationContext(),com.scorecard.SoccerGameActivity.class);
        startActivity(i);
    }
    public void gotoAddTeam(View w){
        Intent j = new Intent(getApplicationContext(),AddTeamActivity.class);
        startActivity(j);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.soccer, menu);
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
