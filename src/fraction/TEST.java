package fraction;
public class TEST implements Comparable<TEST> {
	private long numerator;
	private long denominator;
	public static final TEST ZERO = new TEST(0);


	TEST(long numerator, long denominator){
		this.numerator = numerator;
		this.denominator = denominator;
	}

	TEST(long numerator){
		this.numerator = numerator;
		this.denominator = 1L;
	}

	TEST(String in){
		this.numerator = Long.parseLong(in);
		this.denominator = 1L;
	}

	public TEST add(TEST f){
		if (this.toString().equals(f.toString()) 
				&& this.toString().equals("+infinity")){
			return new TEST(Long.MAX_VALUE,0);
		}else if (this.toString().equals(f.toString()) 
				&& this.toString().equals("-infinity")){
			return new TEST(Long.MIN_VALUE,0);
		}
		else {
			long numeratorF = f.numerator;
			long denominatorF = f.denominator;
			long newNumerator = numeratorF*this.denominator 
					+ this.numerator*denominatorF;
			long newDenominator = this.denominator*denominatorF;	
			return new TEST(newNumerator,newDenominator);
		}
	}
	
	public TEST subtract(TEST f){
		if (f.toString().equals("-infinity") 
				&& this.toString().equals("+infinity")){
			return new TEST(Long.MAX_VALUE,0);
		}
		else if (f.toString().equals("+infinity") 
				&& this.toString().equals("-infinity")){
			return new TEST(Long.MIN_VALUE,0);
		}
		else if (f.toString().equals(this.toString())){
			return new TEST(0,0);
		}
		else {
			long numeratorF = f.numerator;
			long denominatorF = f.denominator;
			long newNumerator = numeratorF*this.denominator 
				- this.numerator*denominatorF;
			long newDenominator = this.denominator*denominatorF;	
			return new TEST(newNumerator,newDenominator);
		}
	};

	@Override
	public int compareTo(TEST o) {
		if (this.toString().equals("NaN")){
			if (o.toString().equals("NaN")){
				return 0;
			}
			else 
				return 1; 
		}
		else if (this.toString().equals("+infinity")){
			if (o.toString().equals("NaN") || o.toString().equals("-infinity")){
				return -1;
			}
			else if (o.toString().equals("+infinity")){
				return 0;			
			}
			else 
				return 1;
		}
		else if (this.toString().equals("-infinity")){
			if (o.toString().equals("-infinity")){
				return 0;
			}
			else
				return 1;
		}
		else
			if (this.doubleValue() > o.doubleValue()){
				return 1;
			}
			else if (this.doubleValue() < o.doubleValue()){
				return -1;
			}
			else
				return 0;
	}

	public TEST divide(TEST f){
		long numeratorF = f.numerator;
		long denominatorF = f.denominator;
		long newNumerator = this.numerator*denominatorF;
		long newDenominator = this.denominator*numeratorF;
		return new TEST(newNumerator,newDenominator);
	}

	public double doubleValue(){
		if(this.denominator != 0)
			return this.numerator/this.denominator;
		else
			System.out.println("Uable to be Double");
			return Double.MAX_VALUE;
	}

	public boolean equals(Object ob){
		TEST castedObj = null;
		if (ob != null){
			if (ob instanceof TEST){
				castedObj = (TEST) ob;
			}
			else
				return false;
		}
		else
			return false;
		return this.toString().equals(castedObj.toString());
	}

	public boolean isInfinite(){
		return this.numerator != 0 && this.denominator == 0;
	}

	public boolean isNaN(){
		return this.numerator == 0 && this.denominator == 0;
	}

	public boolean isInfinite(TEST f){
		return f.numerator != 0 && f.denominator == 0;
	}

	public boolean isNaN(TEST f){
		return f.numerator == 0 && f.denominator == 0;
	}

	public TEST multi(TEST f){
		return new TEST(this.numerator*f.numerator
				,this.denominator*f.denominator);
	}

	public TEST negate(){
		return new TEST(this.numerator*-1,this.denominator);
	}

	public TEST pow(int n){
		Double numerator = new Double(Math.pow(this.numerator,n));
		Double denominator = new Double( Math.pow(this.denominator,n));
		return new TEST( numerator.longValue() , denominator.longValue() );
	}

	public int signum(){
		if(Double.parseDouble( this.toString() ) > 0){
			return 1;
		}
		else if (Double.parseDouble( this.toString() ) < 0){
			return 1;
		}
		else
			return 0;	
	}

	public String toString(){
		if (this.numerator == 0 && this.denominator != 0){
			return "0";
		}
		else if (this.denominator == 0){
			if (this.numerator > 0){
				return "+infinity";
			}
			else if (this.numerator < 0){
				return "-infinity";
			}
			else
				return "NaN";
		}
		else {
			long gcd = GCD(this.numerator,this.denominator);
			long newNumerator = this.numerator/gcd;
			long newDenominator = this.denominator/gcd;
			return newNumerator+"/"+newDenominator ;
		}

	}

	public static long GCD(long a, long b){
		long t;
		while(b != 0)
		{
			t = b;
			b = a%b;
			a = t;
		}
		return a;
	}

	public static void main(String[] a){
		TEST five = new TEST(5);
		TEST f = new TEST(1,0);
		TEST zero = TEST.ZERO;
		System.out.println(f.isInfinite());
		System.out.println(f.multi(zero));
		System.out.println(five.divide(f));
	}





}
