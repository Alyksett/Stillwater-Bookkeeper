import java.util.HashMap;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonProperty;

public class PayPeriodStorage {
	private double grandGross;
	private double grandSalesTax;
	private double grandAdminFee;
	private double grandCustPay;
	private double grandArtistPay;

	private String oldestSale;
	private String newestSale;
	private String dateRange;

	@JsonProperty Map<String, ArtistSales> groupedArtists = new HashMap<>();

	//constructors
	//default
	public PayPeriodStorage(){
		

		this.grandGross = 0;
		this.grandSalesTax = 0;
		this.grandAdminFee = 0;
		this.grandCustPay = 0;
		this.grandArtistPay = 0;
		this.oldestSale =  null;
		this.newestSale = null;
		this.dateRange = null;
	}
	public PayPeriodStorage(
		@JsonProperty("grandGross") double GG, 
		@JsonProperty("grandSalesTax") double GST,
		@JsonProperty("grandAdminFee") double GAF, 
		@JsonProperty("grandCustPay") double GCP,
		@JsonProperty("grandArtistPay") double GAP,
		@JsonProperty("oldestSale") String oldest,
		@JsonProperty("newestSale") String newest,
		@JsonProperty("dateRange") String range){

		this.grandGross = GG;
		this.grandSalesTax = GST;
		this.grandAdminFee = GAF;
		this.grandCustPay = GCP;
		this.grandArtistPay = GAP;
		this.oldestSale =  oldest;
		this.newestSale = newest;
		this.dateRange = range;
	}

	public void addPeriod(Map<String, ArtistSales> groupedArtists2) {
		groupedArtists.putAll(groupedArtists2);
	}

	public Map<String, ArtistSales> getPeriod() {
		return groupedArtists;
	}

	public Object[] returnKeySet() {
		return groupedArtists.keySet().toArray();
	}

	public String getOldestSale() {
		return oldestSale;
	}

	public String getNewestSale() {
		return newestSale;
	}

	public String getDateRange() {
		return dateRange;
	}

	public String displayAllElements(){
		String text;
		StringBuilder builder = new StringBuilder();
		for(String key : groupedArtists.keySet()) {
			builder.append(groupedArtists.get(key).displayAllArtistInfo() + "===================="+ "\n");
		}
		text = builder.toString();
		return text;
	}

	// getters and thesetters for totals

	public void updateOldestSale(String date) {
		oldestSale = date;
	}

	public void updateLatestSale(String date) {
		newestSale = date;
	}

	public void updateDateRange(String date) {
		dateRange = date;
	}

	public double getGrandSaleTotal() {
		return grandGross;
	}

	public double getGrandTaxTotal() {
		return grandSalesTax;
	}

	public double getGrandCustomerPaymentTotal() {
		return grandCustPay;
	}

	public double getGrandAdminFeeTotal() {
		return grandAdminFee;
	}

	public double getGrandTotalDueToArtistTotal() {
		return grandArtistPay;
	}
	//setters
	public void updateGrandGrossSaleTotal(double a) {
		grandGross = a;
	}

	public void updateGrandSalesTaxTotal(double a) {
		grandSalesTax = a;
	}

	public void updateGrandTotalCustomerPaymentTotal(double a) {
		grandCustPay = a;
	}

	public void updateGrandAdminFeeTotal(double a) {
		grandAdminFee = a;
	}

	public void updateGrandTotalDueToArtistTotal(double a) {
		grandArtistPay = a;
	}

}
