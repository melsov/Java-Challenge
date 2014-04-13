package silly;

public class RandomNameGenerator {

	public static String[] firstNames = new String[] {
		"GrilledCheese",
		"Peppermint",
		"Quiche",
		"Raspberry"
	};
	
	public static String[] lastNames = new String[] {
		"Johnson",
		"Samuelson",
		"Paulson",
		"Raspberry"
	};
	
	public static String GetName() {
		int firstIndex = (int) (Math.random() * firstNames.length);
		String result = firstNames[firstIndex];
		int lastIndex =  (int) (Math.random() * lastNames.length);
		result += " " + lastNames[lastIndex];
		return result;
	}
	
	
	
}
