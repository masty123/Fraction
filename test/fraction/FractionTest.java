package fraction;
import static org.junit.Assert.*;
import org.junit.*;
/**
 * JUnit 4 tests for Fraction using only finite values.
 * There is another test class for extended values.
 * 
 * TO RUN:
 * Add this to your project and run unit tests.
 */
public class FractionTest {
	private static double TOL = 1.0E-8; // tolerance for comparisons
	
	private Fraction zero;
	private Fraction one;
	private Fraction half;
	
	@Before  /* run this before each test */
	public void setUp() throws Exception {
		zero = new Fraction(0);
		one = new Fraction(1);
		half = new Fraction(1,2);
	}

	@Test(timeout=100)
	public void testFractionConstructorOneLong() {
		long [] values = { -10000001, -99, -1, 0, 1, 2, Long.MAX_VALUE };
		for(long val: values) {
			Fraction f = new Fraction(val);
			Fraction x = new Fraction(999); // test for static attributes
			assertEquals( Long.toString(val), f.toString() );
		}
	}
	
	@Test(timeout=100)
	public void testFractionConstructorTwoParams() {
		Fraction a = new Fraction(10,10);
		assertEquals("1", a.toString() );
		a = new Fraction(-20,-10);
		assertEquals("2", a.toString());
		a = new Fraction(25,-75);
		assertEquals("-1/3", a.toString());
		a = new Fraction(0, 5);
		assertTrue( zero.equals(a));
		a = new Fraction(3*999, 999);
		assertEquals( "3", a.toString());
	}
	
	/** 
	 * This test will exceed the time limit if the GCD algorithm
	 * in constructor is inefficient.  
	 * Should use Euclid's algorithm and modulo arithmetic.
	 */
	@Test(timeout=100)
	public void testGCDinConstructor() {
		long a = 1000000000L;
		long b = a*a;
		Fraction f1 = new Fraction(a, b);
		f1 = new Fraction(b,a);
		assertEquals( a, f1.doubleValue(), TOL );
		// something with no common factors
		Fraction f2 = new Fraction(a-1,b+1);
		double value = (double)(a-1)/(b+1);
		assertEquals( Long.toString(a)+"/"+Long.toString(b), value, f2.doubleValue(), TOL);
	}
	
	/** Test the doubleValue method. */
	@Test(timeout=100)
	public void testDoubleValue() {
		Fraction f =  new Fraction(1000000);
		assertEquals(1000000.0, f.doubleValue(), TOL);
		// non-integer
		f = new Fraction(7,4);
		assertEquals(1.75, f.doubleValue(), TOL);
		f = new Fraction(-5,4);
		assertEquals(-1.25, f.doubleValue(), TOL);
		// big value
		long a = 1000000000L;
		f = new Fraction(a,a+1);
		double value = ((double)a)/(a+1);
		assertEquals( value, f.doubleValue(), TOL);
	}
	
	@Test(timeout=100)
	public void testToString() {
		Fraction f = new Fraction(0);
		assertEquals("0", f.toString());
		f = new Fraction(0, 20);
		assertEquals("0", f.toString() );
		f = new Fraction(-4);
		assertEquals("-4", f.toString());
		f = new Fraction(16,-4);
		assertEquals("-4", f.toString());
		
		f = new Fraction(999999);
		assertEquals("999999", f.toString());
		long gcd = 2*3*5*99;
		f = new Fraction(gcd*4, gcd*17);
		assertEquals("4/17", f.toString());
		f = new Fraction(24*gcd, 7*gcd );
		assertEquals("24/7", f.toString());
		f = new Fraction(24*gcd, -7*gcd );
		assertEquals("-24/7", f.toString());
		f = new Fraction(-24*gcd, -7*gcd);
		assertEquals("24/7", f.toString());
	}
	
	@Test(timeout=100)
	public void testEquals() {
		assertTrue(one.equals(one));
		assertFalse(one.equals(zero));
		Fraction f = new Fraction(99, 99);
		assertTrue( one.equals(f) );
		assertTrue( f.equals(one) );
		Fraction a = new Fraction(12345, 111);
		long gcd = 999;
		Fraction b = new Fraction(12345*gcd, 111*gcd);
		assertTrue( a.equals(b) );
		b = new Fraction( -12345*gcd, -111*gcd);
		assertTrue( a.equals(b) );
		// equals() should not convert values to double and compare
		// it should compare the fraction numerators and denominators
		a = new Fraction( Long.MAX_VALUE, 2);
		b = new Fraction( Long.MAX_VALUE-1, 2);
		assertFalse( a.equals(b) );
		assertFalse( b.equals(a) );
		// test that equals checks arg type.
		assertFalse( one.equals(new Double(1)) );
	}

	@Test(timeout=100)
	public void testAdd() {
		Fraction sum = zero;
		for(int k=1; k<10; k++) {
			sum = one.add(sum);
			assertEquals(k, sum.doubleValue(), TOL);
		}
		// adding should not change the fractions
		assertEquals(1.0, one.doubleValue(), TOL);
		
		Fraction a = new Fraction(12,25);
		Fraction b = new Fraction(89,25);
		assertEquals(101.0/25.0, a.add(b).doubleValue(), TOL );
		assertEquals(12.0/25.0, a.doubleValue(), TOL); // didn't change a
		assertEquals(7.0/4.0, one.add(new Fraction(1,2)).add(new Fraction(1,4)).doubleValue(), TOL );
		// unequal denominator, differing sign
		// use Fraction.equals to compare results
		a = new Fraction(-17, 8);
		b = new Fraction(45, 7);
		assertEquals( new Fraction(-17*7+45*8, 7*8), a.add(b) );
		assertEquals( b.add(a), a.add(b) );
	}

	@Test(timeout=100)
	public void testSubtract() {
		Fraction f = zero.subtract(one);
		assertEquals(-1.0, f.doubleValue(), TOL);
		// zero and one don't change
		assertEquals("0", zero.toString() );
		assertEquals("1", one.toString() );
		
		Fraction a = new Fraction(5,4);
		Fraction b = new Fraction(5,8);
		Fraction r = a.subtract(b).subtract(b);
		assertEquals( zero, r);
		// some trivial cases
		assertEquals( zero, one.subtract(one) );
		assertEquals( one, one.subtract(zero) );
		assertEquals( one, zero.subtract( one.negate() ) );
		assertEquals( 1.0, one.doubleValue(), TOL ); //don't change one
	}

	@Test(timeout=100)
	public void testMultiply() {
		Fraction f = one.multiply(one);
		assertEquals("1", f.toString() );
		f = zero.multiply(one);
		assertEquals("0", f.toString() );
		assertEquals("0", zero.toString() );
		Fraction a = new Fraction(99, 4);
		Fraction b = new Fraction(2, 11);
		Fraction c = new Fraction(4,  9);
		assertEquals( 9.0/2.0, a.multiply(b).doubleValue(), TOL);
		assertEquals( "2", a.multiply(b).multiply(c).toString() );
		// a didn't change
		assertEquals( "99/4", a.toString());
		// test negative values
		a = new Fraction(99, -4);
		b = new Fraction( 4, 11);
		c = new Fraction(-2, 9);
		assertEquals( -9.0, a.multiply(b).doubleValue(), TOL );
		assertEquals( "2", a.multiply(b).multiply(c).toString() );
		assertEquals( zero, a.multiply(zero) );
	}

	@Test(timeout=100)
	public void testDivide() {
		Fraction f = one.divide(one);
		assertEquals("1", f.toString() );
		f = new Fraction(99999).divide(new Fraction(99999) );
		assertEquals("1", f.toString() );
		Fraction a = new Fraction(99, 2);
		Fraction b = new Fraction(11, 4);
		assertEquals( 18.0, a.divide(b).doubleValue(), TOL);
		// a didn't change
		assertEquals( "99/2", a.toString());
		// test using negative values
		a = new Fraction(99, -4);
		b = new Fraction(-11, 4);
		Fraction c = new Fraction(-9, -2);
		assertEquals( 9.0, a.divide(b).doubleValue(), TOL );
		assertEquals( "2", a.divide(b).divide(c).toString() );
	}

	@Test(timeout=100)
	public void testNegate() {
		assertEquals("-1", one.negate().toString() );
		// one didn't change
		assertEquals( "1", one.toString() );
		assertEquals( 0.0, zero.negate().doubleValue(), TOL );
		// some non-trivial values
		Fraction a = new Fraction(-123);
		assertEquals( "123", a.negate().toString() );
		assertEquals( "-123", a.negate().negate().toString() );
	}
	
	/** fraction should implement Comparable<Fraction> */
	@Test(timeout=100)
	public void testImplementsComparable() {
		assertTrue( one instanceof java.lang.Comparable );
		assertTrue( one.compareTo(zero) > 0 );
		assertTrue( zero.compareTo(one) < 0 );
		assertTrue( one.compareTo(one) == 0 );

		Fraction a = new Fraction( 12, 5);
		Fraction b = new Fraction( 11, 5);
		assertTrue( a.compareTo(b) > 0 );
		assertTrue( b.compareTo(a) < 0 );
		assertTrue( a.compareTo(a) == 0 );
		a = a.negate();  // a = -12/5
		assertEquals("a.negate() doesn't work", -2.4, a.doubleValue(), TOL);
		assertTrue( a.compareTo(zero) < 0);
		assertTrue( a.compareTo(b) < 0 );
		assertTrue( b.compareTo(a) > 0 );
		b = b.negate();  // b = -11/5
		assertEquals("b.negate() doesn't work", -2.2, b.doubleValue(), TOL);
		assertTrue( b.compareTo(zero) < 0 );
		assertTrue( a.compareTo(b) < 0 );
		assertTrue( b.compareTo(a) > 0 );

	}
	
	/** CompareTo with the big numbers, to check for accidental wrap-around.
	 *  If you fail these tests, be more careful about comparing values.
	 */
	@Test(timeout=100)
	public void testCompareBigValues() {
		// this is harder. Must be careful of numeric overflow
		// and loss of precision
		Fraction a = new Fraction( Long.MAX_VALUE/2 - 1, 2);
		Fraction b = new Fraction( Long.MAX_VALUE/2 - 2, 2);
		assertTrue( a.compareTo(b) > 0 );
		assertTrue( b.compareTo(a) < 0 );
		assertTrue( a.compareTo(a) == 0 );
	}
}
