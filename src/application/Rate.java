package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rate {
	private String rate;
	private String color;
	private String price;
	private String feedin;
	private String demand;
	private ObservableList<Price> prices;
	private ObservableList<Feedin> feedins;
	private ObservableList<Demand> demands;
	private boolean singlePrice;
	private boolean singleFeedin;
	private boolean singleDemand;
	public static int RATE = 0, COLOR = 1, PRICE = 2, 
			FEEDIN = 3, DEMAND = 4, SINGLE_PRICE = 5, 
			SINGLE_FEEDIN = 6, SINGLE_DEMAND = 7;

	public Rate() {
		this.rate = "";
		this.price = "";
		this.feedin = "";
		this.demand = "";
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
		this.price = price;
		this.feedin = feedin;
		this.demand = demand;
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
	
	public Rate(String rate, ObservableList<Price> prices, ObservableList<Feedin> feedin,
			ObservableList<Demand> demands, String color, boolean singlePrice, boolean singleFeedin, boolean singleDemand){
		
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
		if(singlePrice){
			return prices.get(0).getValue();
		}else{
			return "Tiered..." + prices.get(0).getValue();
		}
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
		if(index >= prices.size()){
			prices.add(price);
		}else{
			prices.set(index, price);
		}
	}
	
	public void addPrice(Price p){
		prices.add(p);
	}

	public String getFeedin() {
		if(singleFeedin){
			return feedins.get(0).getValue();
		}else{
			return "Tiered..." + feedins.get(0).getValue();
		}
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
		if(index >= feedins.size()){
			feedins.add(feedin);
		}else{
			feedins.set(index, feedin);
		}
	}
	
	public void addFeedin(Feedin f){
		feedins.add(f);
	}
	public String getDemand() {
		if(singleDemand){
			return demands.get(0).getValue();
		}else{
			return "Tiered..." + demands.get(0).getValue();
		}
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
	public void setDemand(int index, Demand demand){
		if(index >= demands.size()){
			demands.add(demand);
		}else{
			demands.set(index, demand);
		}
	}
	public void addDemand(Demand d){
		demands.add(d);
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public void setPrices(ObservableList<Price> prices) {
		this.prices = prices;
	}

	public void setFeedins(ObservableList<Feedin> feedins) {
		this.feedins = feedins;
	}

	public void setDemands(ObservableList<Demand> demands) {
		this.demands = demands;
	}
	
	public String toString(){
		String ret = rate + "," + color + "," + price + "," 
				   + feedin + "," + demand + "," 
				   + (singlePrice ? 1 : 0) + "," 
				   + (singleFeedin ? 1 : 0) + "," 
				   + (singleDemand ? 1 : 0) + "\n";
		for(int x = 0; x < prices.size(); x++){
			ret += prices.get(x).toString() + "\t";
		}
		ret = ret.substring(0,ret.length()-1);
		ret += "\n";
		for(int x = 0; x < feedins.size(); x++){
			ret += feedins.get(x).toString() + "\t";
		}
		ret = ret.substring(0,ret.length()-1);
		ret += "\n";
		for(int x = 0; x < demands.size(); x++){
			ret += demands.get(x).toString() + "\t";
		}
		ret = ret.substring(0,ret.length()-1);
		return ret;
	}
}
