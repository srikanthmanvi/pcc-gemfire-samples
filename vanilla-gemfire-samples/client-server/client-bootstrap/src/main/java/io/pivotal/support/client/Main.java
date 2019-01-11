package io.pivotal.support.client;

import io.pivotal.support.domain.Customer;
import io.pivotal.support.domain.Trade;
import org.apache.commons.collections.map.HashedMap;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.RegionAttributes;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.ClientCacheFactory;
import org.apache.geode.cache.execute.Function;
import org.apache.geode.cache.execute.FunctionService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class Main {

		Properties p = new Properties();
		Region<Integer, Object> customerRegion;

		ClientCache clientCache
				= new ClientCacheFactory().set("cache-xml-file",
				"/Users/smanvi/Workspace/gemfire-samples/vanilla-gemfire-samples/client-server/client-bootstrap/src/main/resources/client-cache.xml")
				//            .set("name", "CqClient")
				//            .set("log-level", "fine")
				//            .set("log-file", "cqclient.log")
				//            .set("statistic-archive-file", "myClientStats.gfs")
				//            .set("statistic-sampling-enabled", "true")


				//            .set("log-level","config")
				//            .set("archive-file-size-limit","1")
				//            .set("statistic-archive-file","ClientStats.gfs")
				//            .set("statistic-sampling-enabled","true")
				//            .set("log-file","client_log.log")
				//            .set("log-file-size-limit","10")
				//            .set("log-disk-space-limit","100")
				.create();

		static final int ONE_MB = 1024*1024;
		static final int ONE_KB = 1024;
		int LOAD_SIZE = 4;

		public static void main(String args[]) {

			//        System.setProperty("gemfire.CLIENT_FUNCTION_TIMEOUT","5000");
			Main client = new Main();
			client.loadLotOfEntries();
//			client.loadBytes();
//			        client.loadCustomers();
//							client.simple();
			//        client.loadTrades();
			//        client.startCQ();
			//        client.loadBytes();

//			          client.loadSimpleData();
			//        client.executeFunction();
			//        client.getFunction();

			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			client.clean();

		}

	private void loadLotOfEntries() {

		Region<Integer, String> customerRegion = clientCache.getRegion("Customer");
		int SIZE=1000000;
		int BATCH_SIZE=10000;
		Map tmp = new HashMap(BATCH_SIZE);

		for (int i = 1; i < SIZE; i++) {
			tmp.put(i,String.valueOf(i));
			if(i%BATCH_SIZE==0){
				customerRegion.putAll(tmp);
				tmp.clear();
			}
		}
		customerRegion.putAll(tmp);

		System.out.println("Done LOADING $$$$$");
	}

	//		private void startCQ() {
//			CqAttributesFactory cqf = new CqAttributesFactory();
//			cqf.addCqListener(new MyCqStatusListener());
//
//			CqAttributes cqAttributes = cqf.create();
//
//			String cq = "SELECT * FROM /Trade WHERE price > 5";
//
//			for (int i = 0; i < 10; i++) {
//				try {
//					CqQuery priceTracker = clientCache.getQueryService().newCq("PriceTracker", cq,cqAttributes);
//					priceTracker.execute();
//					Thread.sleep(2000);
//					priceTracker.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}

//		private void createIndex() {
//			customerRegion = clientCache.getRegion("Customer");
//			QueryService queryService = customerRegion.getRegionService().getQueryService();
//			try {
//				queryService.createIndex("parentIdIndex", "ParentId", customerRegion.getFullPath());
//				queryService.createIndex("strategyParentIdIndex", "StrategyParentId", customerRegion.getFullPath());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

		//    private void load1() {
		//        Region<Integer, Object> customerRegion = clientCache.getRegion("Customer");
		//        Region<Integer, Object> tradeRegion = clientCache.getRegion("Trade");
		//
		//        Customer customer = new Customer("John2", "Doe2", "NC");
		////        Trade trade = new Trade(200, "APPL2");
		//
		////        customerRegion.put(1, new Customer("a", "b", "NJ"));
		////        customerRegion.put(2, new Customer("aq", "basasd", "NY"));
		////        customerRegion.put(3, new Customer("a", "b", "NJ"));
		//
		//        tradeRegion.put(1, new Trade(5,"CS"));
		//        tradeRegion.put(2, new Trade(5,"DEL"));
		//        tradeRegion.put(3, new Trade(5,"DEL"));
		//    }

//		private void getFunction() {
//			FunctionService.onServer(clientCache.getDefaultPool()).execute("no-result-function");
//			//        FunctionService.
//			Function myfunction = FunctionService.getFunction("no-result-function");
//			System.out.println(myfunction.toString());
//		}

		private void loadBytes() {

			Region<Integer, Object> customerRegion = clientCache.getRegion("Customer");
			customerRegion.clear();
			customerRegion.putAll(createData(LOAD_SIZE));
			System.out.println("$$$$$$$ DONE CUSTOMER");
		}

		private Map<Integer, Object> createData(int size){
			Map<Integer, Object> map = new HashMap<Integer, Object>(size);
			for (int i = 0; i <= size; i++) {
				map.put(i,new byte[ONE_MB]);
			}
			return  map;
		}

		private void clean() {
			if (clientCache != null) clientCache.close();
		}

		private void createRegions() {
			Region<Object, Object> clientRegion1 = clientCache.createClientRegionFactory("LOCAL").create("CLIENT_REGION_1");
			clientRegion1.put("1", "abc");
			clientRegion1.put("2", "bcd");
			printClientRegionData();
		}

		private void printClientRegionData() {
			Region<Integer, String> client_region_1 = clientCache.getRegion("CLIENT_REGION_1");
			System.out.println("$$$$$$ CLIENT DATA SIZE: " + client_region_1.size());
			Set<Integer> keys = client_region_1.keySet();
			Iterator<Integer> iterator = keys.iterator();
			while (iterator.hasNext()) {
				System.out.println("########### " + client_region_1.get(iterator.next()));
			}

			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void getLockAndLoad() {
			Region<Integer, Object> customerRegion = clientCache.getRegion("Customer");
			Lock regionDistributedLock = customerRegion.getRegionDistributedLock();
			regionDistributedLock.lock();
			customerRegion.put(1, "CLIENT1");
			regionDistributedLock.unlock();

			regionDistributedLock.lock();
			customerRegion.put(2, "CLIENT2");
			System.out.println("$$$$$$ CLIENT2 HOLDING LOCK");
			regionDistributedLock.unlock();
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("$$$$$$ CLIENT2 RELEASED LOCK");
		}

		private void executeFunction() {

			System.out.println("#######");
			long start=System.currentTimeMillis();
			FunctionService.onServer(clientCache.getDefaultPool()).execute("DemoFunction");
			//        ResultCollector<?, ?> resultCollector = FunctionService.onServer(clientCache.getDefaultPool()).execute("DemoFunction");
			//        System.out.println("RESULT COLLECTOR: "+resultCollector);

			//        ResultCollector<?, ?> resultCollector = FunctionService.onRegion(clientCache.getRegion("Customer")).execute("CustomFunction");
			System.out.println("$$$$$$$$$$$ TIMEDOUT in : "+ (System.currentTimeMillis()-start));
			//        System.out.println(resultCollector);
			//    FunctionService.
			//        ResultCollector<?, ?> resultCollector = execution1.execute("asas");
			//        resultCollector.
			//        FunctionService.onServer(clientCache.)
			//        Region r = clientCache.getRegion("AA");

			//        Execution execution = FunctionService.onRegion(r);
			//        execution.
			//        execution.e
			//        long start = System.currentTimeMillis();
			//        ResultCollector<?, ?> resultCollector = FunctionService.onRegion(customerRegion).execute("remove-function");
			//        Object result = resultCollector.getResult();
			//        System.out.println("$$$$$$$ Executed Remove in "+(System.currentTimeMillis()-start)+" ms.");

		}

		//    private void runQuery() {
		//        Region<Integer, String> customerRegion = clientCache.getRegion("Customer");
		//        String queryStr = "<trace>Select * from /Customer c where c.dob > $1";
		//        QueryService queryService = PoolManager.find("myPool").getQueryService();
		//        Object[] params = new Object[1];
		//        params[0] = LocalDateTime.of(1984, 05, 01, 00, 00);
		//        Query query = queryService.newQuery(queryStr);
		//        try {
		//            Object results = query.execute(params);
		//            System.out.println(results);
		//        } catch (Exception e) {
		//            e.printStackTrace();
		//        }
		//    }

		private void simple() {
			Region<Integer, String> customerRegion = clientCache.getRegion("Customer");
			Object o = customerRegion.get(1);
			System.out.println(o);
//			RegionAttributes<Integer, String> attributes = customerRegion.getAttributes();
//			System.out.println(attributes);
//			//        System.out.println("$$$$$$$$$ Get "+customerRegion.get(Long.valueOf(111)));
//			System.out.println("$$$$$$$$$ Get "+customerRegion.get(Long.valueOf(1)));
			//        Set<Map.Entry<Integer, String>> entrySet = customerRegion.entrySet();
			//        System.out.println(entrySet);

			//customerRegion.
		}

//		private void loadSimpleData(){
//			Region<Integer, Object> customerRegion = clientCache.getRegion("Customer");
//			Map<Integer, String> map = new HashMap<Integer, String>(100);
//			for (int i = 1; i < 100; i++) {
//				map.put(i, String.valueOf(i));
//			}
//			customerRegion.putAll(map);
//		}


		private void loadCustomers() {

			Region<Integer, Customer> customerRegion = clientCache.getRegion("Customer");
			Map<Integer, Customer> map = new HashMap<Integer, Customer>(100);
			for (int i = 0; i < 100; i++) {
				Customer c = new Customer("fname" + i);
				map.put(i, c);
			}
			customerRegion.putAll(map);

			Region<Integer, Trade> tradeRegion = clientCache.getRegion("Trade");
			tradeRegion.put(1, new Trade("APPL"));

		}



}
