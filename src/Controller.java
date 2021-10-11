import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.google.gson.Gson;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.poi.ss.usermodel.*;

public class Controller {
	static double[] totalArray = new double[5];
	static double grossTotal = 0;
	static double salesTaxTotal = 0;
	static double totalCustPay = 0;
	static double adminFeeTotal = 0;
	static double totalDueToArtist = 0;
	static String dateRange;
	static String latestSale;
	static String oldestSale;

	static ArrayList<Object> rawSheetData = new ArrayList<Object>();
	static ArrayList<Artist> artistArray = new ArrayList<Artist>();

	public static Map<String, ArtistSales> artistSalesMap = new HashMap<>();
	public static Map<String, PayPeriodStorage> mainDatabase = new HashMap<>();

	static ArrayList<String> allDates = new ArrayList<String>();
	static ArrayList<Object> allNames = new ArrayList<Object>();

	// main method
	/**
	 * Main method. Opens file, then creates a new GUI
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		openFile();
		GUI gui = new GUI();
		gui.setVisible(true);
	}

	/**
	 * Method that calls rawSheetData() and createArtistArray(). After these it clears rawSheetData.
	 * <p> called from ImportFile.
	 * 
	 * @see ImportFile
	 * @param book
	 */
	public static void breakWorkbook(XSSFWorkbook book) {
		createRawSheetData(book);
		createArtistArray();
		Controller.rawSheetData.clear();
	}

	/**
	 * Loops through rawSheetData and constructs a new temp artist from the data,
	 * loads it into artistArray, then iterates again.
	 * <p>
	 * Calculates date range, latest and oldest sale, then updates payPeriodDates. 
	 * <p>
	 * Called from breakWorkbook
	 * 
	 */
	private static void createArtistArray() {
		// loading local variables with specific elements from the large data array.
		// then using these vars to create new artist
		// and add it to an arraylist of artists.
		
		for (int i = 0; i < rawSheetData.size(); i += 26) {
			DecimalFormat format = new DecimalFormat("#.##");
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
			// reset the local variables
			Date saleDate = null;
			String category = "BROKEN Category";
			String artistName = "BROKEN Artist Name";
			double quantity = 0;
			double grossSale = 0.0;
			double salesTax = 0.0;
			String description = "BROKEN Description";
			// define the vars with the appropriate raw sheet index
			saleDate = (Date) rawSheetData.get(i);
			String realDate = dateFormat.format(saleDate);

			category = (String) rawSheetData.get(i + 3);
			artistName = (String) rawSheetData.get(i + 4);
			quantity = (double) rawSheetData.get(i + 5);
			grossSale = (double) rawSheetData.get(i + 9);
			format.format(grossSale);
			salesTax = (double) rawSheetData.get(i + 12);
			format.format(salesTax);
			description = (String) rawSheetData.get(i + 16);
			// construct new artist
			Artist tempArtist = new Artist(artistName, realDate, quantity, grossSale, salesTax, description, category);
			// add to arrayList
			artistArray.add(tempArtist);
		}

		int max = artistArray.size() - 1;

		dateRange = ((artistArray.get(max).getSaleDate()) + " - " + (artistArray.get(0).getSaleDate()));
		latestSale = (artistArray.get(0).getSaleDate().replace("/", "-"));
		oldestSale = (artistArray.get(max).getSaleDate().replace("/", "-"));

		

		GUI.appendConsoleText("Excel File imported.\n");
		// Sorting artistArray
		Collections.sort(artistArray, new SortArtists());
	}

	/**
	 * 
	 * Takes the workbook passed from ImportFile, then loops through and adds
	 * all the information to an array, rawSheetData.
	 * <p>
	 * This is the location where the sheet is retrieved(0 or 1). Do not forget 
	 * to change this when outside of dev mode
	 * <p>
	 * Called from breakWorkbook.
	 * @see ImportFile
	 * @param book
	 */
	private static void createRawSheetData(XSSFWorkbook book) {
		// importing workbook from ImportFile class and getting sheet from it

		//! REMEMBER TO CHANGE THIS!!!!!//
		//! REMEMBER TO CHANGE THIS!!!!!//


		XSSFSheet sheet = book.getSheetAt(0);


		//! REMEMBER TO CHANGE THIS!!!!!//
		//! REMEMBER TO CHANGE THIS!!!!!//

		/*
		 * This is for getting the information from the raw sheet and putting it into an
		 * ArrayList, rawSheetData
		 */

		int rows = sheet.getLastRowNum();
		int cols = sheet.getRow(0).getLastCellNum();
		Cell cell;
		int temp = -1;// this is used to track the first cell in each row, as it is a date and needs
						// to be treated with love
		for (int r = 1; r <= rows; r++) {
			XSSFRow row = sheet.getRow(r);
			for (int c = 0; c < cols; c++) {
				temp++;
				cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				// getting date
				if (temp % 26 == 0) {
					rawSheetData.add(cell.getDateCellValue());
				}
				// if structure to get appropriate cell inputs
				else {
					if (cell.getCellType().equals(CellType.STRING)) {
						rawSheetData.add(cell.getStringCellValue());
					} else if (cell.getCellType().equals(CellType.NUMERIC)) {
						rawSheetData.add(cell.getNumericCellValue());
					} else if (cell.getCellType().equals(CellType.BLANK)) {
						rawSheetData.add(null);
					}
				}
			}
		}
	}

	/**
	 * Appends the GUI console all of the information in a given pay perion. This is
	 * Called from GUI.
	 * 
	 * @see PayPeriodStorage
	 * @see ArtistSales
	 * @see Artist
	 */
	public static void displayInput() {
			GUI.appendConsoleText(">=======================================<\n");
			Map.Entry<String,PayPeriodStorage> entry = mainDatabase.entrySet().iterator().next();
			String key = entry.getKey();
			GUI.appendConsoleText(mainDatabase.get(key).displayAllElements() + "\n"); 
	}

	/**
	 * This exports a workbook. Called from GUI.
	 * 
	 * @throws IOException
	 * @see GenerateWorkbook
	 */
	public static void exportBigWorkbook() throws IOException {
		new GenerateWorkbook(mainDatabase, GUI.getDate());
	}

	/**
	 * This creates a small workbook with only the totals
	 * @throws IOException
	 * @deprecated
	 */
	public static void exportSmallWorkbook() throws IOException {
		new CreateSmallWorkbook();
	}
	/**
	 * This calculates the totals for each artist in the database. Uses a nested for loop.
	 * Clean code.
	 * 
	 * @param dateKey
	 */
	public static void calculateTotalsForArtist(String dateKey){
		double totalGross = 0;
		double totalSalesTax = 0;
		double totalAdminFee = 0;
		double totalCustPay = 0;
		double totalArtistPay = 0;
		
		for(ArtistSales sales: mainDatabase.get(dateKey).getPeriod().values()){
			for(Artist sale : sales.getAllSales()){
				totalGross += sale.getGrossSale();
				mainDatabase.get(dateKey).getPeriod().get(sale.getArtistName()).updateGrossSaleTotal(totalGross);
				totalSalesTax += sale.getSalesTax();
				mainDatabase.get(dateKey).getPeriod().get(sale.getArtistName()).updateSalesTaxTotal(totalSalesTax);
				totalAdminFee += sale.getAdminFee();
				mainDatabase.get(dateKey).getPeriod().get(sale.getArtistName()).updateAdminFeeTotal(totalAdminFee);
				totalCustPay += sale.getTotalCustomerPayment();
				mainDatabase.get(dateKey).getPeriod().get(sale.getArtistName()).updateTotalCustomerPaymentTotal(totalCustPay);
				totalArtistPay += sale.getTotalDueToArtist();
				mainDatabase.get(dateKey).getPeriod().get(sale.getArtistName()).updateTotalDueToArtistTotal(totalArtistPay);
			}
			mainDatabase.get(dateKey).updateGrandGrossSaleTotal(mainDatabase.get(dateKey).getGrandSaleTotal() + totalGross);
			mainDatabase.get(dateKey).updateGrandSalesTaxTotal(mainDatabase.get(dateKey).getGrandTaxTotal() + totalSalesTax);
			mainDatabase.get(dateKey).updateGrandAdminFeeTotal(mainDatabase.get(dateKey).getGrandAdminFeeTotal() + totalAdminFee);
			mainDatabase.get(dateKey).updateGrandTotalCustomerPaymentTotal(mainDatabase.get(dateKey).getGrandCustomerPaymentTotal() + totalCustPay);
			mainDatabase.get(dateKey).updateGrandTotalDueToArtistTotal(mainDatabase.get(dateKey).getGrandTotalDueToArtistTotal() + totalArtistPay);

			totalGross = 0;
			totalSalesTax = 0;
			totalAdminFee = 0;
			totalCustPay = 0;
			totalArtistPay = 0;
		}
	}

	/**
	 * This loads artistSalesMap into the database. Called from ImportFile
	 * <p>Clears artistSalesMap
	 * @see ImportFile
	 */
	public static void loadMap() {
		for (int i = 0; i < artistArray.size(); i++) {
			artistSalesMap.computeIfAbsent(artistArray.get(i).getArtistName(), arg -> new ArtistSales()).addSale(artistArray.get(i));
		}
		addArtistToDatabase(artistSalesMap);
		artistSalesMap.clear();
	}

	/**
	 * Adds a new PERIOD to the database. Called from loadMap.<p>
	 * Updates the oldest and latest sale as well as the date range.
	 * 
	 * @param artistSalesMap
	 * 
	 */
	public static void addArtistToDatabase(Map<String, ArtistSales> artistSalesMap) {
		
		mainDatabase.computeIfAbsent(oldestSale, arg -> new PayPeriodStorage()).addPeriod(artistSalesMap);
		artistSalesMap.clear();
		allDates.add(oldestSale);
		mainDatabase.get(oldestSale).updateOldestSale(oldestSale);
		mainDatabase.get(oldestSale).updateLatestSale(latestSale);
		mainDatabase.get(oldestSale).updateDateRange(dateRange);
		GUI.addDate(oldestSale);
		calculateTotalsForArtist(oldestSale);
	}

	/**
	 * Returns an Object[] with the keyset of all dates. Typically called from GUI
	 * @return All dates
	 */
	public static Object[] getDates() {
		return mainDatabase.keySet().toArray();
	}
	/**
	 * Returns an Object[] with a single name from a given date. Typically called from GUI
	 * @param date
	 * @return Single artist name
	 */
	public static Object[] initArtistNames(){
		Object[] dates = getDates();
		return mainDatabase.get(dates[0]).getPeriod().keySet().toArray();
	}
	/**
	 * Returns an Object[] of all the artist names from a given date. Typically caled from GUI1
	 * 
	 * @param date
	 * @return Artist names
	 */
	public static Object[] getArtistNames(String date) {
		return mainDatabase.get(date).getPeriod().keySet().toArray();
	}

	/**
	 * Returns a String of an Artist's total for a given payPeriod.
	 * 
	 * @return String of artist info
	 * 
	 */
	public static String retrieveArtistTotal(String date, String name) {
		String text = mainDatabase.get(date).getPeriod().get(name).toString(name);
		return text;
	}
	/**
	 * This updates the artist's check number specified in the params. 
	 * 
	 * @param date
	 * @param name
	 * @param number
	 */
	public static void updateArtistCheckNo(String date, String name, int number) {
		mainDatabase.get(date).getPeriod().get(name).updateCheckNumber(number);
	}
	/**
	 * This serializes the database to a JSON file.
	 * <p> named "dataSave" 
	 * 
	 * 
	 */
	public static void saveFile() throws IOException{
        Gson gson1 = new Gson();
        try(Writer writer = new FileWriter("dataSave.json")){
            gson1.toJson(mainDatabase, writer);
        }
    }
	/**
	 * Deserializes the dataSave JSON file. 
	 */
	public static void openFile() throws IOException{
		ObjectMapper  mapper = new ObjectMapper();
		TypeReference<HashMap<String, PayPeriodStorage>> typeRef = new TypeReference<>() {};
		HashMap<String, PayPeriodStorage> temp = null;

		try(FileReader reader = new FileReader("dataSave.json")){
			temp = mapper.readValue(reader, typeRef);
			mainDatabase.putAll(temp);
		}
	}
}
