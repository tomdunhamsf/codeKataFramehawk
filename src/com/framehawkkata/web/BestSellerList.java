package com.framehawkkata.web;
import java.util.*;
import java.util.concurrent.*;
import com.framehawkkata.model.*;
public class BestSellerList {
	/**
	 * Maintains two copies of a best seller list.  A volatile boolean determines which list is the most current.
	 * This must be volatile to provide a memory barrier. There should only be one mutator, but we must guarantee that accessors
	 * can only see a copy once it is fully constructed. Updates from the database on a schedule
	 * Should errors occur on update, it will mark itself as stale, and continue to use the older copy.
	 */
	private volatile boolean readListA = false;
	private boolean stale = false;
	private List<Product> listA;
	private List<Product> listB;
	private ProductDAO dao;
	private Updater updater;
	private ScheduledThreadPoolExecutor scheduler;
	/**
	 * Gives the means of getting updates from the database. Can come from Hibernate, or a Mock for testing
	 * @param prodDao
	 */
	public BestSellerList(ProductDAO prodDao){
		dao=prodDao;
		scheduler=new ScheduledThreadPoolExecutor(1);
		updater=new Updater();
		scheduler.scheduleAtFixedRate(updater, 0,60,TimeUnit.MINUTES);
	}
	/**
	 * By reading the volatile before returning a copy, there is an ordering guarantee.  The mutator does set the same volatile 
	 * after mutation, therefore cache should be flushed following the mutation, and main memory should be read prior to access.
	 * This class will not mutate the list once a client has it, rather changes are ,made to a new copy that gets handed out to 
	 * new clients. 
	 * @return A list of products. The class assumes that this list will not be modified by clients.  
	 */
	public List<Product> getBestSellers(){
		return readListA ? listA : listB;
	}
	/**
	 * Creates a new top ten list, and marks the list as current using a volatile boolean.  The idea is to behave similar to a 
	 * CopyOnWriteArrayList, but have only this batch modification.  Clients still using the old list are unaffected by the change.
	 * Also, handles errors by marking the data structure as stale and scheduling a follow-up modification.
	 */
	public void setNewBestSellers(){
		try{
			List<Product> newList=dao.getTopProducts(10);
			if( newList.size()!=10){
				makeStale();
			}else{
				if(readListA){
					listB=newList;
				}else{
					listA=newList;
				}
				readListA = !readListA;
			}
		}catch(Exception e){
			makeStale();
			e.printStackTrace();
		}
	}
	protected void makeStale(){
		stale=true;
		scheduler.schedule(updater,30,TimeUnit.SECONDS);
	}
	protected void makeFresh(){
		stale=false;
	}
	public boolean isStale(){
		return stale;
	}
	class Updater implements Runnable
	{
		public Updater(){
		}
		public void run(){
			setNewBestSellers();
		}
	}
}
