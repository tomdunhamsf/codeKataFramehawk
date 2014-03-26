package com.framehawkkata.model;

import java.util.List;
/**
 * Used to cover all paths without hitting the database.
 * @author tdunham
 *
 */
public class MockProductDAO implements ProductDAO {
	/**
	 * 
	 * @param values A List of Product Lists to be returned with each successive call to getTopProducts
	 * A list with a single product named Exception will throw an exception.
	 */
	public MockProductDAO(List<List<Product>> values){
		results=values;
	}
	private List<List<Product>> results;
	@Override
	public List<Product> getTopProducts(int num) throws Exception {
		List<Product> result = results.remove(0);
		if(result.size()==1){
			Product prod=result.get(0);
			if(prod.getName().equals("Exception")){
				throw new Exception();
			}
		}
		return result;
	}

}
