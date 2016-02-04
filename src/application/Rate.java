package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rate {
	private String rate;
	private String color;
	private ObservableList<Price> prices;
	private ObservableList<Feedin> feedins;
	private ObservableList<Demand> demands;
	private boolean singlePrice;
	private boolean singleFeedin;
	private boolean singleDemand;
	
	public Rate() {
		this.rate = "";
		this.color = "";
		this.prices = FXCollections.observableArrayList();
		this.feedins = FXCollections.observableArrayList();
		this.demands = FXCollections.observableArrayList();
		singlePrice = true;
		singleFeedin = true;
		singleDemand = true;
	}
	
	public Rate(String rate, String price, String feedin, String demand,
			String color, boolean singlePrice, boolean singleFeedin, boolean singleDemand) {
		this.rate = rate;
		this.color = color;
		this.prices = FXCollections.observableArrayList();
		Price p = new Price(price, "0");
		prices.add(p);
		this.feedins = FXCollections.observableArrayList();
		Feedin f = new Feedin(feedin, "0");
		feedins.add(f);
		this.demands = FXCollections.observableArrayList();
		Demand d = new Demand(demand, "0");
		demands.add(d);
		this.singlePrice = true;
		this.singleFeedin = true;
		this.singleDemand = true;
	}
	public boolean isSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(boolean singlePrice) {
		this.singlePrice = singlePrice;
	}

	public boolean isSingleFeedin() {
		return singleFeedin;
	}

	public void setSingleFeedin(boolean singleFeedin) {
		this.singleFeedin = singleFeedin;
	}

	public boolean isSingleDemand() {
		return singleDemand;
	}

	public void setSingleDemand(boolean singleDemand) {
		this.singleDemand = singleDemand;
	}

	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getPrice() {
		return prices.get(0).getValue();
	}
	public Price getPrice(int index){
		return prices.get(index);
	}
	public ObservableList<Price> getPrices(){
		return prices;
	}
	public void setPrice(String price) {
		if(prices.size() <= 0){
			Price p = new Price(price, "0");
			prices.add(p);
		}else{
			prices.set(0, new Price(price, "0"));
		}
	}
	public void setPrice(String price, String threshold) {
		for(int x = 0; x < prices.size(); x++){
			if(prices.get(x).getThreshold().equals(threshold)){
				prices.get(x).setValue(price);;
			}
		}
	}
	public void setPriceThreshold(String price, String threshold) {
		for(int x = 0; x < prices.size(); x++){
			if(prices.get(x).getValue().equals(price)){
				prices.get(x).setThreshold(threshold);
			}
		}
	}
	public void setPrice(int index, Price price){
		prices.set(index, price);
	}
	
	public String getFeedin() {
		return feedins.get(0).getValue();
	}
	public Feedin getFeedin(int index){
		return feedins.get(index);
	}
	public ObservableList<Feedin> getFeedins(){
		return feedins;
	}
	public void setFeedin(String feedin) {
		if(feedins.size() <= 0){
			Feedin p = new Feedin(feedin, "0");
			feedins.add(p);
		}else{
			feedins.set(0, new Feedin(feedin, "0"));
		}
	}
	public void setFeedin(String feedin, String threshold) {
		for(int x = 0; x < feedins.size(); x++){
			if(feedins.get(x).getThreshold().equals(threshold)){
				feedins.get(x).setValue(feedin);;
			}
		}
	}
	public void setFeedinThreshold(String feedin, String threshold) {
		for(int x = 0; x < feedins.size(); x++){
			if(feedins.get(x).getValue().equals(feedin)){
				feedins.get(x).setThreshold(threshold);
			}
		}
	}
	public void setFeedin(int index, Feedin feedin){
		feedins.set(index, feedin);
	}
	public String getDemand() {
		return demands.get(0).getValue();
	}
	public Demand getDemand(int index){
		return demands.get(index);
	}
	public ObservableList<Demand> getDemands(){
		return demands;
	}
	public void setDemand(String demand) {
		if(demands.size() <= 0){
			Demand p = new Demand(demand, "0");
			demands.add(p);
		}else{
			demands.set(0, new Demand(demand, "0"));
		}
	}
	public void setDemand(String demand, String threshold) {
		for(int x = 0; x < feedins.size(); x++){
			if(demands.get(x).getThreshold().equals(threshold)){
				demands.get(x).setValue(demand);;
			}
		}
	}
	public void setDemandThreshold(String demand, String threshold) {
		for(int x = 0; x < demands.size(); x++){
			if(demands.get(x).getValue().equals(demand)){
				demands.get(x).setThreshold(threshold);
			}
		}
	}
	public void setDemand(int index, Feedin demand){
		feedins.set(index, demand);
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
