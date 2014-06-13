package com.thebluealliance.androidclient.background;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.thebluealliance.androidclient.Constants;
import com.thebluealliance.androidclient.R;
import com.thebluealliance.androidclient.activities.RefreshableHostActivity;
import com.thebluealliance.androidclient.adapters.ListViewAdapter;
import com.thebluealliance.androidclient.datafeed.DataManager;
import com.thebluealliance.androidclient.datatypes.APIResponse;
import com.thebluealliance.androidclient.datatypes.ListItem;
import com.thebluealliance.androidclient.datatypes.TeamListElement;
import com.thebluealliance.androidclient.models.SimpleTeam;

import java.util.ArrayList;

/**
 * File created by phil on 4/20/14.
 */
public class PopulateTeamList extends AsyncTask<Integer, String, APIResponse.CODE> {

    private Fragment fragment;

    private RefreshableHostActivity activity;
    private ArrayList<ListItem> teamItems;
    private ListViewAdapter adapter;

    public PopulateTeamList(Fragment fragment) {
        this.fragment = fragment;
        activity = (RefreshableHostActivity) fragment.getActivity();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        teamItems = new ArrayList<>();
    }

    @Override
    protected APIResponse.CODE doInBackground(Integer... params) {
        int start = params[0];
        int end = params[1];
        Log.d("doInBackground", "is cancelled? " + isCancelled());
        APIResponse<ArrayList<SimpleTeam>> response = new APIResponse<>(null, APIResponse.CODE.NODATA);
        if (!isCancelled()) {
            try {
                response = DataManager.getSimpleTeamsInRange(activity, start, end);
                ArrayList<SimpleTeam> teams = response.getData();
                for (SimpleTeam team : teams) {
                    if (isCancelled()) {
                        break;
                    }
                    TeamListElement e = team.render();
                    teamItems.add(e);
                }
            } catch (Exception e) {
                Log.w(Constants.LOG_TAG, "unable to load team list");
            }
        }
        return response.getCode();
    }


    @Override
    protected void onPostExecute(APIResponse.CODE code) {
        super.onPostExecute(code);

        View view = fragment.getView();
        if (activity != null && view != null) {
            adapter = new ListViewAdapter(activity, teamItems);
            TextView noDataText = (TextView) view.findViewById(R.id.no_data);

            // If there's no teams in the adapter or if we can't download info
            // off the web, display a message.
            if (code == APIResponse.CODE.NODATA || adapter.values.isEmpty())
            {
                noDataText.setText(R.string.no_team_list);
                noDataText.setVisibility(View.VISIBLE);
            }
            else
            {
                ListView eventList = (ListView) view.findViewById(R.id.list);
                eventList.setAdapter(adapter);
            }

            if (code == APIResponse.CODE.OFFLINECACHE) {
                activity.showWarningMessage(fragment.getString(R.string.warning_using_cached_data));
            }
            view.findViewById(R.id.progress).setVisibility(View.GONE);
        }
    }
}
