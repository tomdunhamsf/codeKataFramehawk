package com.framehawkkata.web;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.*;
import com.framehawkkata.model.*;
public class BestSellerListTest {
	private BestSellerList testee;
	@Before
	public void setUp() throws Exception {
		List<Product> old = new ArrayList<Product>();
		old.add(new Product("old1"));
		old.add(new Product("old2"));
		old.add(new Product("old3"));
		old.add(new Product("old4"));
		old.add(new Product("old5"));
		old.add(new Product("old6"));
		old.add(new Product("old7"));
		old.add(new Product("old8"));
		old.add(new Product("old9"));
		old.add(new Product("old10"));
		List<Product> none = new ArrayList<Product>();
		List<Product> exception = new ArrayList<Product>();
		exception.add(new Product("Exception"));
		List<Product> newer = new ArrayList<Product>();
		newer.add(new Product("newer1"));
		newer.add(new Product("newer2"));
		newer.add(new Product("newer3"));
		newer.add(new Product("newer4"));
		newer.add(new Product("newer5"));
		newer.add(new Product("newer6"));
		newer.add(new Product("newer7"));
		newer.add(new Product("newer8"));
		newer.add(new Product("newer9"));
		newer.add(new Product("newer10"));		
		List<List<Product>> results = new ArrayList<List<Product>>();
		results.add(old);
		results.add(none);
		results.add(exception);
		results.add(newer);
		testee=new BestSellerList(new MockProductDAO(results));
	}

	@Test
	public void test() {
		

		synchronized(this){
			try {
				this.wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//testee.setNewBestSellers();First update done by scheduler
		List<Product> firstList=testee.getBestSellers();
		assertTrue(firstList.size()==10);
		assertTrue(firstList.get(0).getName().equals("old1"));
		testee.setNewBestSellers();
		List<Product> secondList=testee.getBestSellers();
		assertEquals(firstList,secondList);
		assertTrue(testee.isStale());
		testee.setNewBestSellers();
		List<Product> thirdList = testee.getBestSellers();
		assertEquals(firstList,thirdList);
		List<Product> fourthList = testee.getBestSellers();
		assertEquals(firstList,secondList);
	}

}
