package application;

import java.util.Hashtable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Session {
	private String author;
	private String notes;
	private String[] GHI;
	private String lat;
	private String lon;
	private boolean north;
	private boolean east;
	private int timezone;
	private boolean daySave;
	private long solarStart;
	private long solarEnd;
	private ObservableList<RateSchedule> rateSchedules;
	private ObservableList<RatepayerGroup> rpGroups;
	private Grid grid;
	
	
	public Session() {
		this.author = "";
		this.notes = "";
		GHI = new String[12];
		this.lat = "";
		this.lon = "";
		this.north = true;
		this.east = false;
		this.timezone = 0;
		this.daySave = true;
		this.solarStart = System.currentTimeMillis();
		this.solarEnd = System.currentTimeMillis();
		this.rateSchedules = FXCollections.observableArrayList();
		this.rpGroups = FXCollections.observableArrayList();
		this.grid = new Grid();
	}
	public Session(String author, String notes, String[] gHI, String lat,
			String lon, boolean north, boolean east, int timezone,
			boolean daySave, long solarStart, long solarEnd,
			ObservableList<RateSchedule> rateSchedules,
			ObservableList<RatepayerGroup> rpGroups, Grid grid) {
		this.author = author;
		this.notes = notes;
		GHI = gHI;
		this.lat = lat;
		this.lon = lon;
		this.north = north;
		this.east = east;
		this.timezone = timezone;
		this.daySave = daySave;
		this.solarStart = solarStart;
		this.solarEnd = solarEnd;
		this.rateSchedules = rateSchedules;
		this.rpGroups = rpGroups;
		this.grid = grid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String[] getGHI() {
		return GHI;
	}
	public void setGHI(String[] gHI) {
		GHI = gHI;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public boolean isNorth() {
		return north;
	}
	public void setNorth(boolean north) {
		this.north = north;
	}
	public boolean isEast() {
		return east;
	}
	public void setEast(boolean east) {
		this.east = east;
	}
	public int getTimezone() {
		return timezone;
	}
	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}
	public boolean isDaySave() {
		return daySave;
	}
	public void setDaySave(boolean daySave) {
		this.daySave = daySave;
	}
	public long getSolarStart() {
		return solarStart;
	}
	public void setSolarStart(long solarStart) {
		this.solarStart = solarStart;
	}
	public long getSolarEnd() {
		return solarEnd;
	}
	public void setSolarEnd(long solarEnd) {
		this.solarEnd = solarEnd;
	}
	public ObservableList<RateSchedule> getRateSchedules() {
		return rateSchedules;
	}
	public void setRateSchedules(ObservableList<RateSchedule> rateSchedules) {
		this.rateSchedules = rateSchedules;
	}
	public ObservableList<RatepayerGroup> getRpGroups() {
		return rpGroups;
	}
	public void setRpGroups(ObservableList<RatepayerGroup> rpGroups) {
		this.rpGroups = rpGroups;
	}
	public Grid getGrid() {
		return grid;
	}
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

}
