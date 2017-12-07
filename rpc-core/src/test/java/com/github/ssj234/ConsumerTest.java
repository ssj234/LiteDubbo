package com.github.ssj234;

import java.net.URLEncoder;

import org.apache.zookeeper.ZooKeeper;

import com.github.ssj234.consumer.Consumer;
import com.github.ssj234.protocol.Protocol;
import com.github.ssj234.protocol.DubboProtocol;
import com.github.ssj234.provider.Provider;
import com.github.ssj234.registry.Registry;
import com.github.ssj234.registry.ZookeeperRegistry;
import com.github.ssj234.service.ISearchPrice;
import com.github.ssj234.service.SearchPriceFromInet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ConsumerTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ConsumerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ConsumerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
//        Provider provider = new Provider();
        
        Registry registry = new ZookeeperRegistry("127.0.0.1", 2181);
//        provider.addRegistry(registry);
//        
//        Protocol expProtocol = new DubboProtocol("127.0.0.1",3309);
//        provider.addProtocol(expProtocol);
//        
//        provider.export(ISearchPrice.class,new SearchPriceFromInet());
        
        
        Consumer consumer = new Consumer();
        consumer.addRegistry(registry);
        ISearchPrice searcher = (ISearchPrice) consumer.refer(ISearchPrice.class);
        String rs = searcher.getPrice("Python First Head");
        System.out.println("rs is " + rs);
    }
    
    public static void main(String[] args) {
    	new ConsumerTest("name").testApp();
    }
}
