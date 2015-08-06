package application;

import java.util.Hashtable;

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
	private Hashtable<String,Rate> rateSchedules;
	private Hashtable<String,Rate> rpGroups;
	
	
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
		this.rateSchedules = new Hashtable();
		this.rpGroups = new Hashtable();
	}
	public Session(String author, String notes, String[] gHI, String lat,
			String lon, boolean north, boolean east, int timezone,
			boolean daySave, long solarStart, long solarEnd,
			Hashtable<String, Rate> rateSchedules,
			Hashtable<String, Rate> rpGroups) {
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
	public Hashtable<String, Rate> getRateSchedules() {
		return rateSchedules;
	}
	public void setRateSchedules(Hashtable<String, Rate> rateSchedules) {
		this.rateSchedules = rateSchedules;
	}
	public Hashtable<String, Rate> getRpGroups() {
		return rpGroups;
	}
	public void setRpGroups(Hashtable<String, Rate> rpGroups) {
		this.rpGroups = rpGroups;
	}
}
