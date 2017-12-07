package com.github.ssj234;

import org.apache.zookeeper.ZooKeeper;

import com.github.ssj234.consumer.Consumer;
import com.github.ssj234.invoker.Invocation;
import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.invoker.ProviderInvoker;
import com.github.ssj234.invoker.Result;
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
public class InvokerTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public InvokerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( InvokerTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	ISearchPrice sp = new SearchPriceFromInet();
    	Invoker invoker = new ProviderInvoker(ISearchPrice.class,sp);
    	Result result= invoker.doInvoker(new Invocation(ISearchPrice.class,"getPrice",new Class[] {String.class},new Object[] {"Python"}));
    	System.out.println(result.getValue());
    }
    
    public static void main(String[] args) {
		new InvokerTest("dd").testApp();
	}
}
