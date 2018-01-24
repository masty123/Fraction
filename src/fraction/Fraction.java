package fraction;
import java.lang.Comparable;

/**
 * A calculator that used to calculate numerator and denominator it can add, 
 * subtract, multiply, divide and even power of that number.
 * @author Theeruth Borisuth
 *
 */
public class Fraction implements Comparable<Fraction> {
	//variable 'n' so on.
	private long numerator, denominator;
	public static final Fraction ZERO = new Fraction(0)  ;

	
	/**
	 * create a new fraction with value n / d . n and d can be zero.
	 * @param numerator
	 * @param denominator
	 */
	Fraction(long numerator, long denominator){
		this.numerator = numerator;
		this.denominator = denominator;
	}
	
	/**
	 * create a new fraction with integer value; this is same as Fraction(n,1)
	 * @param numerator
	 */
	Fraction(long numerator){
		this.numerator = numerator;
		this.denominator = 1L;
	}
	
	/**
	 * create a new fraction from a String value. The String must
	 * contain a long ("123"), long/long ("12/34"), or a decimal value
	 * ("12.34"). This is similar to the BigDecimal class
	 * @param value
	 */
	Fraction( String value ){
		this.numerator = Long.parseLong(value);
		this.denominator = 1L;
	}
	
	/**
	 * return a new fraction that is sum of this fraction and f.
	 * @param f Fraction Object called f.
	 * @return added numerator and denominator.
	 */
	Fraction add(Fraction f) {
		if(this.denominator == 0 && this.numerator > 0 && f.denominator == 0 && f.numerator < 0) return new Fraction(0,0);
		else if ((this.denominator == 0 && this.numerator < 0 && f.denominator == 0 && f.numerator > 0)) return new Fraction(0,0);
		
		long num = this.numerator*f.denominator + this.denominator*f.numerator;
	  	long den = this.denominator*f.denominator;
	  	// Create the new object and return it.
	  	return 	new Fraction(num, den);		
	}
	
	/**
	 * return a new fraction that is difference of this fraction and f.
	 * @param f : fraction that you want to subtract.
	 * @return return a subtracted version.
	 */
	public Fraction subtract( Fraction f ) {
		if(this.denominator == 0 && this.numerator > 0 && f.denominator == 0 && f.numerator < 0) return new Fraction(Long.MAX_VALUE,0);
		else if ((this.denominator == 0 && this.numerator < 0 && f.denominator == 0 && f.numerator > 0)) return new Fraction(Long.MIN_VALUE,0);
		else if ( (this.denominator == 0 && this.numerator > 0 && f.denominator == 0 && f.numerator > 0) || (this.denominator == 0 && this.numerator < 0 && f.denominator == 0 && f.numerator < 0) ) return new Fraction(0,0);
		else {
			long n = this.numerator*f.denominator - f.numerator*this.denominator;
			long d = this.denominator * f.denominator;
			return new Fraction(n, d);
		}
	}
		
	/**
	 * return a new fraction that is product of this fraction and f.
	 * @param f : fraction that you want to multiply.
	 * @return return a multiplied version. 
	 */
	public Fraction multiply( Fraction f ) {
		long n = numerator * f.numerator;
		long d = denominator * f.denominator;
		return new Fraction(n, d);
	}
	
	/**
	 * return a new fraction that is this fraction divided by f.
	 * @param f : fraction that you want to divide.
	 * @return : return a divided version. 
	 */
	public Fraction divide( Fraction f ) {
		long n = numerator * f.denominator;
		long d = denominator * f.numerator;
		return new Fraction(n, d);
	}
	
	/**
	 * return a new fraction that is the negative of this Fraction. negate
	 * of Infinity is -Infinity. negate of NaN is NaN.
	 * @return
	 */
	public Fraction negate( ) {
		return new Fraction(this.numerator*-1,this.denominator);
	}
	
	/**
	 * return a new fraction that is this fraction raised to the power n. 
	 * n may be zero or negative.
	 * @param n : power of n
	 * @return fraction that has been powered.
	 */
	public Fraction pow(int n) {
		long num = (long) Math.pow(this.numerator,n);
		long denom = (long) Math.pow(this.denominator,n);
		return new Fraction(num,denom);
	}
	
	/**
	 * return value of this fraction as a double. May be inaccurate.
	 * @return numerator and denominator.
	 */
	public double doubleValue() { 
		if(this.denominator != 0){
			double n = numerator;	// convert to double
		    double d = denominator;	
		    return (n / d);
		}
		else return Double.MAX_VALUE;
	}
	/**
	 * 
	 * Compare the value of 2 fractions.
	 * @return boolean of compareTo method.
	 */

	public boolean equals(Object obj){
		if (obj.getClass() != this.getClass() || obj == null){
			return false;
		}
		Fraction f = (Fraction) obj;
		return this.toString().equals(f.toString());
	}
	
	/**
	 * return true if this fraction is Not a Number (NaN). Internally,
	 * NaN is represented as numerator = denominator = 0.
	 * @return
	 */
	public boolean isNaN() {
		return this.numerator == 0 && this.denominator == 0;

	}
	/**
	 * return true if this fraction is positive or negative infinity.
	 * @return
	 */
	public boolean isInfinite() {
		return this.numerator != 0 && this.denominator == 0;
	}
	public static boolean isNaN(Fraction f){
		return f.isNaN();
	}
	
	public static boolean isInfinite(Fraction f){
		return f.isInfinite();
	}
	/**
	 * This method is used to find the GCD of numerator and denominator.
	 * @param n : numerator that you wanted to do GCD.
	 * @param d : denominator that you wanted to do GCD.
	 * @return GCD
	 */
	public static long gcd(long n, long d){
		long t;
		while(d != 0){
			t = d;
			d = n%d;
			n = t;
		}
		return n ;
	}
	/**
	 * Return +1 if this fraction is greater than zero (including
	 * +Infinity), 0 if fraction is 0 or NaN, -1 if this fraction is less than
	 * zero (including -Infinity).
	 * @return
	 */
	public int signum( ) {
		if(Double.parseDouble( this.toString() ) > 0){
			return 1;
		}
		else if (Double.parseDouble( this.toString() ) < 0){
			return 1;
		}
		else
			return 0;	
	}
	
	/**
	 * compare this fraction to f. The return value should be:
	 * a.compareTo(b) < 0 if a is less than b
	 * a.compareTo(b) = 0 if a has same value as b
	 * a.compareTo(b) > 0 if a is greater than b
	 * a.compareTo(Fraction.NaN) < 0 for any a != NaN
	 * 
	 */
	public int compareTo(Fraction f) {
	  if(this.numerator > 0 && this.denominator == 0){
		  if (f.numerator == 0 && f.denominator == 0) return -1;
		  else if (f.numerator > 0 && f.denominator == 0) return 0;
		  else if (f.numerator < 0 && f.denominator == 0) return 1;
		  else return 1;
	  }
	  else if (this.numerator == 0 && this.denominator == 0){
		  if(f.numerator == 0 && f.denominator == 0) return 0;
		  else if (f.numerator > 0 && f.denominator == 0) return 1;
		  else if (f.numerator < 0 && f.denominator == 0) return 1;
		  else return 1;
	  }
	  else if (this.numerator < 0 && this.denominator == 0){
		  if(f.numerator == 0 && f.denominator == 0) return -1;
		  else if (f.numerator > 0 && f.denominator == 0) return -1;
		  else if (f.numerator < 0 && f.denominator == 0) return -1;
		  else return -1;
	  }
	  else if (this.subtract(f).doubleValue() > 0) return 1;
	  else if (this.subtract(f).doubleValue() < 0) return -1;
	  else return 0;
	  
	}
	/**
	 * return a String representation of the fraction, with no spaces.
	 */
	public String toString( ) {
		if(this.denominator == 0){
			if(this.numerator > 0){
				return "Infinity";
			}else if (this.numerator < 0){
				return "-Infinity" ;
			}else return "NaN";		
		}
		else {
			
			long temp = gcd(this.numerator,this.denominator);
			long nTemp = this.numerator/temp;
			long dTemp = this.denominator/temp;
			if (dTemp < 0){
				nTemp = nTemp*(-1);
				dTemp = dTemp*(-1);
			}
			if (dTemp == 1L){
				return nTemp+"";
			}
			return String.format("%d/%d",nTemp,dTemp);
		}	
	}
	
}
