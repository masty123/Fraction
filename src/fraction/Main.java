package fraction;

public class Main {
	public static void main(String[] args) {
		Fraction a = new Fraction(1, 3);
		Fraction b = new Fraction(17);
		Fraction c = new Fraction(2).add(new Fraction(1,5));
		Fraction answer = a.multiply(b.divide(c));
		System.out.println(answer.toString());
	}
}
