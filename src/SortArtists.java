
import java.util.Comparator;


public class SortArtists  extends Artist implements Comparator<Artist>{
	
	@Override
	public int compare(Artist o1, Artist o2) {
		// TODO Auto-generated method stub
		return o1.getArtistName().compareTo(o2.getArtistName());
	}

}
