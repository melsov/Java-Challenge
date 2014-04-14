package silly;

public class D {
	public static void print(String s) {
		System.out.println(s);
	}
	public static void Assert(boolean condition, String s) {
		assert(condition) : s;
	}
}
