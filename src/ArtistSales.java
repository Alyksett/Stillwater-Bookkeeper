
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArtistSales {

	@JsonProperty ArrayList<Artist> salesArray = new ArrayList<Artist>();
	private String artistName;
	private double totalGross;
	private double totalSalesTax;
	private double totalAdminFee;
	private double totalCustPay;
	private double totalArtistPay;
	private int checkNumber;
	static String date;
	//constructors


	public ArtistSales() {
		//this.salesArray = null;
		this.artistName = null;
		this.totalGross = 0;
		this.totalSalesTax = 0;
		this.totalAdminFee = 0;
		this.totalCustPay = 0;
		this.totalArtistPay = 0;
		this.checkNumber = 0;
		ArtistSales.date = null;
	}

	/**
	 * Constructor<p>
	 * 
	 * This is for a single artist's sales in a given month.<p>
	 * This class contains an arraylist of Sales(Artist) that represents individual sales <p><p>
	 * 
	 * 
	 * 
	 * 
	 * @param totalGross2
	 * @param totalSalesTax2
	 * @param totalAdminFee2
	 * @param totalCustPay2
	 * @param totalArtistPay2
	 * @param checkNumber2
	 * @param date2
	 */
	public ArtistSales(
		//@JsonProperty("salesArray") ArrayList<Artist> salesArray2, 
		@JsonProperty("totalGross") double totalGross2,
		@JsonProperty("totalSalesTax") double totalSalesTax2,
		@JsonProperty("totalAdminFee")double totalAdminFee2,
		@JsonProperty("totalCustPay")double totalCustPay2,
		@JsonProperty("totalArtistPay")double totalArtistPay2,
		@JsonProperty("checkNumber")int checkNumber2,
		@JsonProperty("date") Date date2
		) {
		//this.salesArray = salesArray2;
		this.totalGross = totalGross2;
		this.totalSalesTax = totalSalesTax2;
		this.totalAdminFee = totalAdminFee2;
		this.totalCustPay = totalCustPay2;
		this.totalArtistPay = totalArtistPay2;
		this.checkNumber = checkNumber2;
		//setName();
	}



	// getters and setters for the arraylist

	public String getArtistName(){
		return artistName;
	}
	
	public void addSale(Artist sale) {
		salesArray.add(sale);

	}

	public Artist getSingleSale(int i) {
		return salesArray.get(i);
	}

	public Double getSingleSaleGross(int i) {
		return salesArray.get(i).getGrossSale();
	}

	public ArrayList<Artist> getAllSales() {
		return salesArray;
	}

	public String toString(String name) {
		String text;
		text = "Artist Name: " + name + "\n" + "Total Gross Sales: " + getGrossSaleTotal() + "\n" + "Total Sales Tax: "
				+ getSalesTaxTotal() + "\n" + "Total Customer Payment: " + getTotalCustomerPaymentTotal() + "\n"
				+ "Total Admin Fee: " + getAdminFeeTotal() + "\n" + "Total Due to Artist: " + getTotalDueToArtistTotal()
				+ "\n" + "Check Number: " + getCheckNumber() + "\n" + "------------------------------" + "\n";
		return text;
	}

	public String displayAllArtistInfo(){
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i<salesArray.size(); i++){
			builder.append(salesArray.get(i).toString() + "----------------------------------\n");
		}
		String text = builder.toString();
		return text;
	}

	// getters and setters for totals
	public double getGrossSaleTotal() {
		return totalGross;
	}

	public double getSalesTaxTotal() {
		return totalSalesTax;
	}

	public double getTotalCustomerPaymentTotal() {
		return totalCustPay;
	}

	public double getAdminFeeTotal() {
		return totalAdminFee;
	}

	public double getTotalDueToArtistTotal() {
		return totalArtistPay;
	}

	public int getCheckNumber() {
		return checkNumber;
	}

	public void updateGrossSaleTotal(double a) {
		totalGross = a;
	}

	public void updateSalesTaxTotal(double a) {
		totalSalesTax = a;
	}

	public void updateTotalCustomerPaymentTotal(double a) {
		totalCustPay = a;
	}

	public void updateAdminFeeTotal(double a) {
		totalAdminFee = a;
	}

	public void updateTotalDueToArtistTotal(double a) {
		totalArtistPay = a;
	}

	public void updateCheckNumber(int num) {
		checkNumber = num;
	}
}
