package com.framehawkkata.model;
import java.util.*;
/**
 * A real ProductDAO would undoubtedly have many methods, but I only need the top ten list here.
 * A non-mock implementation would do the real data access.
 * @author tdunham
 *
 */
public interface ProductDAO {
	public List<Product> getTopProducts(int num) throws Exception;
}
