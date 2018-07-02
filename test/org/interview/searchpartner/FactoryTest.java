package org.interview.searchpartner;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class FactoryTest {
	
	private static AtomicInteger totalProducts = new AtomicInteger(0);

	@Test
	public void testMain() {
		Factory.main(new String []{"3", "6", "5"});
		assertEquals(3, Factory.getTotalProducts().get());
		Factory.main(new String []{"20", "60"});
		//Addition of the earlier products and below created products.
		assertEquals(6, Factory.getTotalProducts().get());
	}

	@Test
	public void testGetTotalProducts() {
		Factory.setTotalProducts(totalProducts);
		assertEquals(0, Factory.getTotalProducts().get());
	}

	@Test
	public void testSetTotalProducts() {
		Factory.setTotalProducts(totalProducts);
		assertEquals(0, Factory.getTotalProducts().get());
	}

}
