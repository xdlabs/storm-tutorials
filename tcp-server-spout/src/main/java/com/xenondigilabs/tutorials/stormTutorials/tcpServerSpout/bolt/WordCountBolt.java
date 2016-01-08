package com.xenondigilabs.tutorials.stormTutorials.tcpServerSpout.bolt;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import com.xenondigilabs.tutorials.stormTutorials.tcpServerSpout.resources.Resources;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordCountBolt extends BaseBasicBolt {

	int noOfVowels = 0;

	public void execute(Tuple tuple, BasicOutputCollector collector) {
		String word = tuple.getString(0);
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			// either comparing with 10 chracters ,we can go for 5 chracters by
			// using .toLowerCase() etc bt that will create two strings on heap
			// results in using more memory
			if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'A' || ch == 'E' || ch == 'I'
					|| ch == 'O' || ch == 'U') {
				noOfVowels++;
			}
		}
		System.out.println("NoOfViowless  " + noOfVowels);
		// use this if u want to pass it to other bolts for performing other
		// tasks.
		// collector.emit(new Values(noOfVowels));
		try {
			writeResponse(tuple);
		} catch (Exception e) {
			System.out.println("WordCountBolt");
		}

	}

	private void writeResponse(Tuple tuple) throws IOException {
		Socket s = (Socket) tuple.getValue(2);
		PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
		pw.println(noOfVowels);
		Resources.close(pw);
		Resources.close((InputStream) tuple.getValue(1));
		Resources.close(s);
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		arg0.declare(new Fields("count"));
	}

}
