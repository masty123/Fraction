package fraction;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Tests of Fraction class using extended numbers.
 * Extended values are Infinity, Negative Infinity, and NaN.
 * This test code doesn't depend on student-defined constants.
 */
public class ExtendedValuesTest {
	// Some values used in these tests.  Create objects in setUp().
	Fraction POS_INFINITY;
	Fraction NEG_INFINITY;
	Fraction NaN;
	Fraction zero;
	Fraction one;
	Fraction two;
	
	@Before
	public void setUp() throws Exception {
		POS_INFINITY = new Fraction(1,0);
		NEG_INFINITY = new Fraction(-1,0);
		NaN = new Fraction(0,0);
		zero = new Fraction(0);
		one = new Fraction(4,4);
		two = new Fraction(4,2);
	}
	
	/** test that equals works with extended numbers */
	@Test(timeout=100)
	public void testEquals() {
		/* f = +Infinity */
		Fraction f = new Fraction(7,0);
		assertTrue( f.equals(POS_INFINITY) );
		assertFalse( f.equals(zero) );
		assertFalse( f.equals(NEG_INFINITY) );
		assertFalse( f.equals(NaN) );
		/* f = -Infinity */
		f = new Fraction(-3,0);
		assertFalse( f.equals(POS_INFINITY) );
		assertFalse( f.equals(zero) );
		assertTrue( f.equals(NEG_INFINITY) );
		assertFalse( f.equals(NaN) );
		/* f = zero (0/20) */
		f = new Fraction(0,20);
		assertFalse( f.equals(POS_INFINITY) );
		assertTrue( f.equals(zero) );
		assertFalse( f.equals(NEG_INFINITY) );
		assertFalse( f.equals(NaN) );
		/* f = NaN */
		f = new Fraction(0,0);
		assertFalse( f.equals(POS_INFINITY) );
		assertFalse( f.equals(zero) );
		assertFalse( f.equals(NEG_INFINITY) );
//Not clear if NaN really equals NaN
//		assertTrue( f.equals(NAN) );
		/* should not equal any finite value. */
		f = new Fraction(1,1);
		assertFalse( f.equals(POS_INFINITY) );
		assertFalse( f.equals(zero) );
		assertFalse( f.equals(NEG_INFINITY) );
		assertFalse( f.equals(NaN) );
	}
	
	@Test(timeout=100)
	public void testIsInfinite() {
		assertTrue("isInfinite(Infinity)", Fraction.isInfinite(POS_INFINITY));
		assertTrue("isInfinite(-Infinity)", Fraction.isInfinite(NEG_INFINITY));
		assertTrue("Infininity.isInfinite()", POS_INFINITY.isInfinite());
		assertTrue("-Infinity.isInfinite()", NEG_INFINITY.isInfinite());
		// Not Infinite
		assertFalse("isInfinite(zero)", Fraction.isInfinite(zero));
		assertFalse("zero.isInfinite()", zero.isInfinite());
		assertFalse("isInfinite(one)", Fraction.isInfinite(one));
		assertFalse("one.isInfinite()", one.isInfinite());
		assertFalse("isInfinite(NaN)", Fraction.isInfinite(NaN));
		assertFalse("NaN.isInfinite()", NaN.isInfinite());
	}
	
	
	@Test(timeout=100)
	public void testIsNaN() {
		// is NaN
		assertTrue("NaN.isNaN()", NaN.isNaN());
		assertTrue("isNaN()", Fraction.isNaN(NaN));
		// Not NaN
		assertFalse("isNaN(Infinity)", Fraction.isNaN(POS_INFINITY));
		assertFalse("isNaN(-Infinity)", Fraction.isNaN(NEG_INFINITY));
		assertFalse("Infininity.isNaN()", POS_INFINITY.isNaN());
		assertFalse("-Infinity.isNaN()", NEG_INFINITY.isNaN());
		// Not NaN
		assertFalse("isNaN(zero)", Fraction.isNaN(zero));
		assertFalse("zero.isNaN()", zero.isNaN());
		assertFalse("Fraction.isNaN(one)", Fraction.isNaN(one));
		assertFalse("one.isNaN()", one.isNaN());
	}

	/** Test addition of extended values. */
	@Test(timeout=100)
	public void testAddInfinity() {
		assertEquals( POS_INFINITY, one.add(POS_INFINITY) );
		assertEquals( POS_INFINITY, POS_INFINITY.add(one) );
		assertEquals( NEG_INFINITY, one.add(NEG_INFINITY) );
		assertEquals( POS_INFINITY, zero.add(POS_INFINITY) );
		assertEquals( POS_INFINITY, POS_INFINITY.add(zero) );
	}

	/** Test addition involving NaN. */
	@Test(timeout=100)
	public void testAddNaN()  {
		assertTrue( one.add(NaN).isNaN() );
		assertTrue( POS_INFINITY.add(NEG_INFINITY).isNaN() );
		assertTrue( POS_INFINITY.add(NaN).isNaN() );
		assertTrue( NaN.add(NEG_INFINITY).isNaN() );
		// verify that isNaN works reasonably
		assertFalse( two.isNaN() );
		assertFalse( POS_INFINITY.isNaN() );
	}

	/** Test multiplication of extended values. */
	@Test(timeout=100)
	public void testMultiplyInfinity() {
		assertEquals( POS_INFINITY, two.multiply(POS_INFINITY) );
		assertEquals( POS_INFINITY, POS_INFINITY.multiply(two) );
		assertEquals( NEG_INFINITY, two.multiply(NEG_INFINITY) );
		
		
		Fraction minustwo = new Fraction(-2);
		assertEquals( POS_INFINITY, minustwo.multiply(NEG_INFINITY) );
		assertEquals( NEG_INFINITY, POS_INFINITY.multiply(minustwo) );
		assertEquals( NEG_INFINITY, minustwo.multiply(POS_INFINITY) );
		assertEquals( POS_INFINITY, minustwo.multiply(NEG_INFINITY) );
	}

	/** Test multiplication involving NaN. */
	@Test(timeout=100)
	public void testMultiplyNaN() {
		assertTrue( two.multiply(NaN).isNaN() );
		assertTrue( NaN.multiply(two).isNaN() );
		assertTrue( NaN.multiply(zero).isNaN() );
		assertTrue( zero.multiply(POS_INFINITY).isNaN() );
	}
	

	/** Test compareTo using Infinity */
	@Test(timeout=100)
	public void testExtendedCompareTo() {
		Fraction max = new Fraction(Long.MAX_VALUE);
		assertTrue( POS_INFINITY.compareTo(NEG_INFINITY) > 0 );
		assertTrue( POS_INFINITY.compareTo(zero) > 0 );
		assertTrue( POS_INFINITY.compareTo(one) > 0 );
		assertTrue( POS_INFINITY.compareTo(max) > 0 );
		assertTrue( NEG_INFINITY.compareTo(POS_INFINITY) < 0 );
		assertTrue( NEG_INFINITY.compareTo(zero) < 0 );
		assertTrue( NEG_INFINITY.compareTo(one) < 0 );
		assertTrue( NEG_INFINITY.compareTo(max) < 0 );
		assertTrue( NEG_INFINITY.compareTo(new Fraction(Long.MIN_VALUE)) < 0 );
	}
	

	/** Test negation of NaN and Infinity. Depends on compareTo. */
	@Test(timeout=100)
	public void testNegate() {
		assertTrue( POS_INFINITY.negate().compareTo(zero) < 0 );
		assertTrue( NEG_INFINITY.negate().compareTo(zero) > 0 );
		assertTrue( Fraction.isNaN(NaN.negate()) );
	}
	
	/** toString for extended values. */
	@Test(timeout=100)
	public void testToStringExtendedValues() {
		assertEquals("Infinity", POS_INFINITY.toString());
		assertEquals("-Infinity", NEG_INFINITY.toString());
		assertEquals("NaN", NaN.toString());
		// sanity check
		assertEquals("0", zero.toString());
	}
}
