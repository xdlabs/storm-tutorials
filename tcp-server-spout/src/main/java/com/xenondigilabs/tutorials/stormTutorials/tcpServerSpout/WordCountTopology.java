package com.xenondigilabs.tutorials.stormTutorials.tcpServerSpout;


import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import com.xenondigilabs.tutorials.stormTutorials.tcpServerSpout.bolt.WordCountBolt;
import com.xenondigilabs.tutorials.stormTutorials.tcpServerSpout.spout.SetenceSpout;

public class WordCountTopology {
	private static final int NO_OF_SPOUTS = 3;
	private static final int NO_OF_BOLTS = 5;
	private static final int PARALLELISM = 3;
	private static final String SPOUT_WORD = "word";
	private static final String BOLT_COUNT_VOWELS = "vowels";

	public static void main(String[] args) {

		LocalCluster cluster = null;
		try {
			TopologyBuilder builder = new TopologyBuilder();
			builder.setSpout(SPOUT_WORD, new SetenceSpout(), NO_OF_SPOUTS);
			builder.setBolt(BOLT_COUNT_VOWELS, new WordCountBolt(), NO_OF_BOLTS).shuffleGrouping(SPOUT_WORD);
			Config conf = new Config();
			conf.setDebug(true);
			conf.setMaxTaskParallelism(PARALLELISM);
			cluster = new LocalCluster();
			cluster.submitTopology("word-count", conf, builder.createTopology());
			Thread.sleep(1000);
			// you have to manually stop the cluster because spout is dependent
			// on this...
			// cluster.shutdown();
		} catch (Exception e) {
			System.out.println("#######error " + e);
			cluster.shutdown();
		}
	}
}
