 import java.text.DecimalFormat;



public class Artist {
	private String artistName;
	private String saleDate;
	private double quantity;
	private double grossSale;
	private double salesTax;
	private double totalCustomerPayment;
	private double adminFee;
	private double totalDueToArtist;
	private String description;
	private String category;
	
	

	//accessors
	public String getArtistName() {
		return artistName;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public double getQuantity() {
		return quantity;
	}
	public double getGrossSale() {
		return grossSale;
	}
	public double getSalesTax() {
		return salesTax;
	}
	public double getTotalCustomerPayment() {
		return totalCustomerPayment;
	}
	public double getAdminFee() {
		return adminFee;
	}
	public double getTotalDueToArtist() {
		return totalDueToArtist;
	}
	public String getDescription() {
		return description;
	}
	public String getCategory() {
		return category;
	}
	//mutators
	public void updateAristName(String a) {
		artistName = a;
	}
	public void updateSaleDate(String a) {
		saleDate = a;
	}
	public void updateQuantity(double quantity2) {
		quantity = quantity2;
	}
	public void updateGrossSale(double a) {
		grossSale = a;
	}
	public void updateSalesTax(double a) {
		salesTax = a;
	}
	public void updateTotalCustomerPayment(double gross, double tax) {
		//DecimalFormat format = new DecimalFormat("#.##");
		double temp = (gross+tax);
		//format.format(temp);
		totalCustomerPayment = temp;
	}
	public void updateAdminFee(double grossSale) {
		DecimalFormat f = new DecimalFormat("#.##");
		double temp = grossSale*(0.05);
		f.format(temp);
		adminFee = (temp);
		
	}	
	public void updateTotalDueToArtist(double gross) {
		DecimalFormat f = new DecimalFormat("#.##");
		double temp = getTotalCustomerPayment()-getAdminFee();
		f.format(temp);
		totalDueToArtist = (temp);
		
	}
	public void updateDescription(String a) {
		description = a;
	}
	public void updateCategory(String a) {
		category = a;
	}
	//constructors
	//default constructor
	public Artist() {
		updateAristName(null);
		updateSaleDate(null);
		updateQuantity(0);
		updateGrossSale(0);
		updateSalesTax(0);
		updateTotalCustomerPayment(0,0);
		updateAdminFee(0);
		updateTotalDueToArtist(0);
		updateDescription(null);	
	}
	//initializing constructor
	public Artist(String name, String date, double quantity2, 
				  double gross, double tax,  
				   String desc, String category) 
	{
		
		updateAristName(name);
		updateSaleDate(date);
		updateQuantity(quantity2);
		updateGrossSale(gross);
		updateSalesTax(tax);
		updateTotalCustomerPayment(gross, tax);
		updateAdminFee(gross);
		updateTotalDueToArtist(gross);
		updateDescription(desc);	
		updateCategory(category);

	}
	public void clear(){
		updateAristName(null);
		updateSaleDate(null);
		updateQuantity(0);
		updateGrossSale(0);
		updateSalesTax(0);
		updateTotalCustomerPayment(0,0);
		updateAdminFee(0);
		updateTotalDueToArtist(0);
		updateDescription(null);	
	}	
	
	
	//Override toString()
	@Override
	public String toString() {
		return
			"Artist name: " + getArtistName() + "\n" +
			"Item Sold: " + getCategory() + "\n" +
			"Description: " + getDescription() + "\n" +
			"Sale Date: " + getSaleDate() + "\n" +
			"Gross Sale: " + getGrossSale() + "\n" +
			"Sales Tax: " + getSalesTax() + "\n";
	}
}
