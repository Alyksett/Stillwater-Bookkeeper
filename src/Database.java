
import java.util.*;

public class Database {
    
    //DATABASE//
    private static Map<String, PayPeriodStorage> database = new HashMap<>();
    //DATABASE//
    

    public static void addPeriod(String date, Map<String, ArtistSales> a){
        database.get(date).addPeriod(a);
    }
  
    /**
     * THESE ARE FOR PAYPERIODSTORAGE
     * FIRST NEST, FOR A SINGLE DATE
     * 
     * @param date
     * @return
     */
  
    /*
    *Getters
    */
    public static Map<String, ArtistSales> getPeriod(String date){
        return database.get(date).getPeriod();
    }
    public static Object[] returnArtistNames(String date){
        return database.get(date).returnKeySet();
    }
    
	public static String getOldestSale(String date) {
		return database.get(date).getOldestSale();
	}

	public static String getNewestSale(String date) {
		return database.get(date).getNewestSale();
	}

	public static String getDateRange(String date) {
		return database.get(date).getDateRange();
	}
    
    public static String displayDatabase(String date){
        return database.get(date).displayAllElements();
    }
    
	public double getGrandSaleTotal(String date) {
		return database.get(date).getGrandSaleTotal();
	}

	public double getGrandTaxTotal(String date) {
		return database.get(date).getGrandTaxTotal();
	}

	public double getGrandCustomerPaymentTotal(String date) {
		return database.get(date).getGrandCustomerPaymentTotal();
	}

	public double getGrandAdminFeeTotal(String date) {
		return database.get(date).getGrandAdminFeeTotal();
	}

	public double getGrandTotalDueToArtistTotal(String date) {
		return database.get(date).getGrandTotalDueToArtistTotal();
	}
	/*
    *Setters
    */
	public void updateGrandGrossSaleTotal(String date, double a) {
		database.get(date).updateGrandGrossSaleTotal(a);
	}

	public void updateGrandSalesTaxTotal(String date, double a) {
		database.get(date).updateGrandSalesTaxTotal(a);
	}

	public void updateGrandTotalCustomerPaymentTotal(String date, double a) {
		database.get(date).updateGrandTotalCustomerPaymentTotal(a);
	}

	public void updateGrandAdminFeeTotal(String date, double a) {
		database.get(date).updateGrandAdminFeeTotal(a);
	}

	public void updateGrandTotalDueToArtistTotal(String date, double a) {
		database.get(date).updateGrandTotalDueToArtistTotal(a);
	}
    public void updateOldestSale(String date, String oldestDate) {
		database.get(date).updateOldestSale(oldestDate);
	}

	public void updateLatestSale(String date, String latestDate) {
		database.get(date).updateLatestSale(latestDate);
	}

	public void updateDateRange(String date, String range) {
        database.get(date).updateOldestSale(range);
	}
    /**
     * This is for ArtistSales!!!
     * 
     */
     /*
    *Getters!
    */
    public String getArtistName(String date, String name){
        return database.get(date).getPeriod().get(name).getArtistName();
	}
    public String artistSalesToString(String date, String name){
        return database.get(date).getPeriod().get(name).getArtistName();
    }
	public String displayAllArtistInfo(String date, String name){
        return database.get(date).getPeriod().get(name).displayAllArtistInfo();
    }
    
	public Artist getSingleSale(String date, String name, int i) {
        return database.get(date).getPeriod().get(name).getSingleSale(i);
	}
    
	public Double getSingleSaleGross(String date, String name, int i) {
        return database.get(date).getPeriod().get(name).getSingleSaleGross(i);
	}
    
	public ArrayList<Artist> getAllSales(String date, String name) {
        return database.get(date).getPeriod().get(name).getAllSales();
	}
    public double getGrossSaleTotal(String date, String name) {
        return database.get(date).getPeriod().get(name).getGrossSaleTotal();
	}
    
	public double getSalesTaxTotal(String date, String name) {
        return database.get(date).getPeriod().get(name).getSalesTaxTotal();
	}
    
	public double getTotalCustomerPaymentTotal(String date, String name) {
        return database.get(date).getPeriod().get(name).getTotalCustomerPaymentTotal();
	}
    
	public double getAdminFeeTotal(String date, String name) {
        return 	database.get(date).getPeriod().get(name).getAdminFeeTotal();
	}
    
	public double getTotalDueToArtistTotal(String date, String name) {
        return database.get(date).getPeriod().get(name).getTotalDueToArtistTotal();
	}
    
	public int getCheckNumber(String date, String name) {
        return database.get(date).getPeriod().get(name).getCheckNumber();
	}
    /*
    *Getters
    */ 
    public void addSale(String date, String name, Artist sale) {
        database.get(date).getPeriod().get(name).addSale(sale);

    }
	public void updateGrossSaleTotal(String date, String name, double a) {
        database.get(date).getPeriod().get(name).updateGrossSaleTotal(a);
	}
    
	public void updateSalesTaxTotal(String date, String name, double a) {
		database.get(date).getPeriod().get(name).updateSalesTaxTotal(a);
	}

	public void updateTotalCustomerPaymentTotal(String date, String name, double a) {
		database.get(date).getPeriod().get(name).updateTotalCustomerPaymentTotal(a);
	}

	public void updateAdminFeeTotal(String date, String name, double a) {
		database.get(date).getPeriod().get(name).updateAdminFeeTotal(a);
	}

	public void updateTotalDueToArtistTotal(String date, String name, double a) {
		database.get(date).getPeriod().get(name).updateTotalDueToArtistTotal(a);
	}

	public void updateCheckNumber(String date, String name, int num) {
		database.get(date).getPeriod().get(name).updateCheckNumber(num);
	}
    /**
     * This is all for a single sale. Artist.java
     */
    /*
    *Getters
    */
    public String getArtistName(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getArtistName();
	}
	public String getSaleDate(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getSaleDate();
	}
	public double getQuantity(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getQuantity();
	}
	public double getGrossSale(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getGrossSale();
	}
	public double getSalesTax(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getSalesTax();
	}
	public double getTotalCustomerPayment(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getTotalCustomerPayment();
	}
	public double getAdminFee(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getAdminFee();
	}
	public double getTotalDueToArtist(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getTotalDueToArtist();
	}
	public String getDescription(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getDescription();
	}
	public String getCategory(String date, String name, int i) {
		return database.get(date).getPeriod().get(name).getSingleSale(i).getCategory();
	}
    public String saleToString(String date, String name, int i){
        return database.get(date).getPeriod().get(name).getSingleSale(i).toString();
    }


    /*
    *Setters!
    */
    public void clearSingleSale(String date, String name, int i){
        database.get(date).getPeriod().get(name).getSingleSale(i).clear();
    }
    public void updateAristName(String date, String name, int i, String a) {
		database.get(date).getPeriod().get(name).getSingleSale(i).updateAristName(a);
	}
	public void updateSaleDate(String date, String name, int i, String a) {
		database.get(date).getPeriod().get(name).getSingleSale(i).updateSaleDate(a);
	}
	public void updateQuantity(String date, String name, int i, int a) {
		database.get(date).getPeriod().get(name).getSingleSale(i).updateQuantity(i);
	}
	public void updateGrossSale(String date, String name, int i, double a) {
		database.get(date).getPeriod().get(name).getSingleSale(i).updateGrossSale(a);
	}
	public void updateSalesTax(String date, String name, int i, double a) {
		database.get(date).getPeriod().get(name).getSingleSale(i).updateSalesTax(a);
	}
}
