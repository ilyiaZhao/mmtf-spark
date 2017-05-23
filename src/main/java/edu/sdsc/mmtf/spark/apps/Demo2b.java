package edu.sdsc.mmtf.spark.apps;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import edu.sdsc.mmtf.spark.filters.ContainsLProteinChain;
import edu.sdsc.mmtf.spark.io.MmtfSequenceFileReader;

/**
 * This example demonstrates how to filter the PDB by polymer chain type. It filters
 * 
 * Simple example of reading an MMTF Hadoop Sequence file, filtering the entries by resolution,
 * and counting the number of entries. This example shows how methods can be chained for a more
 * concise syntax.
 * 
 * @author Peter Rose
 *
 */
public class Demo2b {

	public static void main(String[] args) {

	    if (args.length != 1) {
	        System.err.println("Usage: " + Demo2b.class.getSimpleName() + " <hadoop sequence file>");
	        System.exit(1);
	    }
	    
	    SparkConf conf = new SparkConf().setMaster("local[*]").setAppName(Demo2b.class.getSimpleName());
	    JavaSparkContext sc = new JavaSparkContext(conf);
	    
		 
	    long count = MmtfSequenceFileReader
	    		.read(args[0], sc) // read MMTF hadoop sequence file
	    		// retain pdb entries that exclusively (flag set to true) contain L-peptide chains
	    		.filter(new ContainsLProteinChain(true)) 
	    		.count();
	    
	    System.out.println("# L-proteins: " + count);
	    sc.close();
	}

}