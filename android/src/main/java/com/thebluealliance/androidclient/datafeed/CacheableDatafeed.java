package com.thebluealliance.androidclient.datafeed;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thebluealliance.androidclient.models.Award;
import com.thebluealliance.androidclient.models.District;
import com.thebluealliance.androidclient.models.Event;
import com.thebluealliance.androidclient.models.Match;
import com.thebluealliance.androidclient.models.Media;
import com.thebluealliance.androidclient.models.Team;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class CacheableDatafeed implements APIv2 {

    private APIv2 mRetrofitAPI;
    private APICache mAPICache;

    // TODO add callback to retrofit results to store new data in db

    @Inject
    public CacheableDatafeed(
      @Named("retrofit") APIv2 retrofitAPI,
      @Named("cache") APICache apiCache) {
        mRetrofitAPI = retrofitAPI;
        mAPICache = apiCache;
    }

    @Override
    public Observable<List<Team>> fetchTeamPage(int pageNum) {
        return null;
    }

    @Override
    public Observable<Team> fetchTeam(String teamKey) {
        return mAPICache.fetchTeam(teamKey).concatWith(
          mRetrofitAPI.fetchTeam(teamKey));
    }

    @Override
    public Observable<List<Event>> fetchTeamEvents(String teamKey, int year
    ) {
        return mAPICache.fetchTeamEvents(teamKey, year).concatWith(
          mRetrofitAPI.fetchTeamEvents(teamKey, year));
    }

    @Override
    public Observable<List<Award>> fetchTeamAtEventAwards(String teamKey, String eventKey
    ) {
        return null;
    }

    @Override
    public Observable<List<Match>> fetchTeamAtEventMatches(String teamKey, String eventKey
    ) {
        return null;
    }

    @Override
    public Observable<List<Integer>> fetchTeamYearsParticipated(String teamKey) {
        return null;
    }

    @Override
    public Observable<List<Media>> fetchTeamMediaInYear(String teamKey, int year) {
        return mAPICache.fetchTeamMediaInYear(teamKey, year).concatWith(
          mRetrofitAPI.fetchTeamMediaInYear(teamKey, year));
    }

    @Override
    public Observable<List<Event>> fetchTeamEventHistory(String teamKey) {
        return null;
    }

    @Override
    public Observable<List<Award>> fetchTeamEventAwards(String teamKey) {
        return null;
    }

    @Override
    public Observable<List<Event>> fetchEventsInYear(int year) {
        return null;
    }

    @Override
    public Observable<Event> fetchEvent(String eventKey) {
        return mAPICache.fetchEvent(eventKey).concatWith(
          mRetrofitAPI.fetchEvent(eventKey));
    }

    @Override
    public Observable<List<Team>> fetchEventTeams(String eventKey) {
        return mAPICache.fetchEventTeams(eventKey).concatWith(
          mRetrofitAPI.fetchEventTeams(eventKey));
    }

    @Override
    public Observable<JsonArray> fetchEventRankings(String eventKey) {
        return mAPICache.fetchEventRankings(eventKey).concatWith(
          mRetrofitAPI.fetchEventRankings(eventKey));
    }

    @Override
    public Observable<List<Match>> fetchEventMatches(String eventKey) {
        return mAPICache.fetchEventMatches(eventKey).concatWith(
          mRetrofitAPI.fetchEventMatches(eventKey));
    }

    @Override
    public Observable<JsonObject> fetchEventStats(String eventKey) {
        return mAPICache.fetchEventStats(eventKey).concatWith(
          mRetrofitAPI.fetchEventStats(eventKey));
    }

    @Override
    public Observable<List<Award>> fetchEventAwards(String eventKey) {
        return mAPICache.fetchEventAwards(eventKey).concatWith(
          mRetrofitAPI.fetchEventAwards(eventKey));
    }

    @Override
    public Observable<JsonObject> fetchEventDistrictPoints(String eventKey) {
        return mAPICache.fetchEventDistrictPoints(eventKey).concatWith(
          mRetrofitAPI.fetchEventDistrictPoints(eventKey));
    }

    @Override
    public Observable<List<District>> fetchDistrictList(int year) {
        return null;
    }

    @Override
    public Observable<List<Event>> fetchDistrictEvents(String districtShort, int year) {
        return null;
    }

    @Override
    public Observable<JsonArray> fetchDistrictRankings(String districtShort, int year) {
        return null;
    }

    @Override
    public Observable<Match> fetchMatch(String matchKey) {
        return null;
    }
}