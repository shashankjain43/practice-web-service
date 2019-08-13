/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 22-Jan-2013
 *  @author ajinkya
 */
package com.snapdeal.test.standalone.activemq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snapdeal.ipms.base.queue.update.SUPCSellerUpdateEventSRO;
import com.snapdeal.ipms.base.queue.update.SUPCSellerUpdateEventsSRO;

//import com.snapdeal.ipms.base.common.ActiveMQConstants;

public class ProducerExecutor {

    private static Logger LOG              = LoggerFactory.getLogger(ProducerExecutor.class);

    public static int     mapCounter       = 0;

    private static int    threadRunCounter = 0;

    public static void main(String[] args) throws InterruptedException {

        int threadPoolSize = 70;
        int threshold = 1;

        //register publisher
        
        String queueName1 = "rankQueue";
        String queueName2 = "inventoryQueue";
        String url = "failover:(tcp://localhost:61616)";
        String userName = "user";
        String password = "user";
        
 /*       ArrayList<String> supcList = new ArrayList<String>();
		String[] supcArray = args[0].split(",");
		supcList.addAll(Arrays.asList(supcArray));*/

        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
        Connection connection = null;
        Session session1 = null;
        Session session2 = null;
        Destination queue1 = null;
        Destination queue2 = null;
        MessageProducer messProducer1 = null;
        MessageProducer messProducer2 = null;
        try {
            connection = factory.createConnection(userName, password);
            connection.start();
            
            //for rank update queue
            session1 = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue1 = session1.createQueue(queueName1);
            messProducer1 = session1.createProducer(queue1);
            messProducer1.setDeliveryMode(DeliveryMode.PERSISTENT);
            
            //for inventory status queue
            session2 = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            queue2 = session2.createQueue(queueName2);
            messProducer2 = session2.createProducer(queue2);
            messProducer2.setDeliveryMode(DeliveryMode.PERSISTENT);
        } catch (JMSException e) {
            LOG.error("Could not create connection : {}", e);
        }

        ProducerRankQThread.setSession(session1);
        ProducerRankQThread.setMessageProducer(messProducer1);
        
        ProducerInventoryStatusQThread.setSession(session2);
        ProducerInventoryStatusQThread.setMessageProducer(messProducer2);

        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
        
        // Create list of sample SUPCs for dev env
        List<SUPCSellerUpdateEventSRO> supcSellerUpdateEventSROList = new ArrayList<SUPCSellerUpdateEventSRO>();

        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100026", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100052", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100052", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100134", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("10014",  null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100140", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100173", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100177", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("10018",  null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100222", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1002397",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1002397",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1002397",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003101",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003145",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003265",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003431",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003580",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003634",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003652",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003789",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1003986",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1004206",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1004354",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1004733",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100533", null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("10057",  null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("10058"  ,null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006018",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006026",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006034",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006039",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006041",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006346",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006544",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006564",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006573",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006575",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006582",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006599",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006745",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006785",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006787",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006791",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006793",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006801",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006820",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006826",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1006857",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007059",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007104",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007112",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007114",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007116",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007152",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007156",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007192",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007202",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007207",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007213",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007217",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007222",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007226",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007228",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007233",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007244",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007387",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007393",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007399",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007412",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007443",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007446",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007454",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007459",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007463",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007464",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007468",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007472",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007485",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007489",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007491",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007493",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007495",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007496",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007520",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007525",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007600",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007602",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007605",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007606",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007608",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007609",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007610",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007633",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007635",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100764" ,null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007657",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007658",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007659",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007660",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007661",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007662",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007663",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007665",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007668",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007669",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007670",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007671",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007672",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007673",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007674",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007675",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007676",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007677",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007678",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007679",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007680",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007681",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007682",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007683",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007684",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007685",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007686",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007689",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007690",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007691",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007693",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007694",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007696",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007698",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("100771" ,null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007748",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007750",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007754",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007756",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007759",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007761",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007763",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007764",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007768",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007769",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007773",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007776",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007778",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007780",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007782",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007785",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007786",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007789",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007791",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007837",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007838",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007839",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007840",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007841",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007842",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007843",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007844",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007845",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007846",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007847",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007848",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007849",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007850",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007851",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007854",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007855",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007856",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007859",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007860",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007863",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007881",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007885",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007934",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007940",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007941",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007943",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007955",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007956",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007960",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007962",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007963",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007964",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007966",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007968",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007969",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1007995",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008019",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008024",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008025",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008153",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008154",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008155",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008156",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008157",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008158",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008159",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008160",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008161",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008162",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008163",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008164",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008165",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008166",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008167",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008168",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008169",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008170",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008171",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008172",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008173",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008174",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008175",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008228",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008232",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008238",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008281",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("10083"  ,null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008342",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008354",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008357",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008359",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008374",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008385",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008386",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008388",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008389",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008390",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008391",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008392",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008394",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008407",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008411",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008413",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008419",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008420",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008421",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008422",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008423",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008424",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008425",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008426",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008427",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008428",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008429",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008430",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008432",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008433",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008434",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008435",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008436",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008437",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008438",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008439",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008440",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008441",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008442",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008443",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008444",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008445",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008446",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008447",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008448",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008449",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008450",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008451",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008452",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008453",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008454",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008455",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008456",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008457",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008458",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008459",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008460",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008461",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008462",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008463",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008464",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008465",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008466",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008467",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008468",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008469",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008470",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008471",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008472",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008473",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008474",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008475",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008476",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008477",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008478",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008479",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008480",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008640",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008674",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008683",null, new Date()));
        supcSellerUpdateEventSROList.add(new SUPCSellerUpdateEventSRO("1008684",null, new Date()));
        
        int listSize= supcSellerUpdateEventSROList.size();
        int numberOfBatches = listSize / 10;
        if(listSize % 10 !=0 ){
            numberOfBatches++;
        }
        for(int i=0;i<numberOfBatches;i++){
            List<SUPCSellerUpdateEventSRO> subSUPCSellerUpdateEventSROList = new ArrayList<SUPCSellerUpdateEventSRO>(supcSellerUpdateEventSROList.subList(i*10, Math.min((i+1)*10, listSize)));
            SUPCSellerUpdateEventsSRO supcSellerUpdateEventsSRO = new SUPCSellerUpdateEventsSRO(subSUPCSellerUpdateEventSROList);
            executor.execute(new ProducerRankQThread(supcSellerUpdateEventsSRO));
            executor.execute(new ProducerInventoryStatusQThread(supcSellerUpdateEventsSRO));
        }
        
        /*for (int i = 0; i < threshold; i++) {
            executor.execute(new ProducerRankQThread());
        }*/
        
        executor.shutdown();

        //You may need to change this time to termination if running a long task
        executor.awaitTermination(1, TimeUnit.DAYS);

        if (executor.isTerminated()) {
            try {
                connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

   /* public static void populateData(String filename, Map<Integer, MinPricingSRO> cachedMap) {
        try {
            FileReader fr = new FileReader(new File(filename));
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] sroArray = null;
            while ((line = br.readLine()) != null) {
                sroArray = line.split(",");
                // Require fields in the following order : supc, vendorCode, vendorSku, price, sellingPrice, vendorPrice
                try {
                    cachedMap.put(mapCounter, new MinPricingSRO(sroArray[0], sroArray[1], sroArray[2], sroArray[3], sroArray[4], sroArray[5]));
                    mapCounter++;
                } catch (Exception e) {
                    continue;
                }
            }
            br.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }*/

    public synchronized static int getAndUpdateThreadRunCounter() {

        threadRunCounter++;

        return threadRunCounter - 1;
    }

}
