package com.example.stormdemo.VowelsCount.spout;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import com.example.stormdemo.VowelsCount.resources.MyServerSocket;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import ch.qos.logback.core.db.dialect.MsSQLDialect;

public class SetenceSpout extends BaseRichSpout {

	SpoutOutputCollector _collector;
	String data;
	public ServerSocket ss;
	Socket s;
	int port = 1500;
	private static int spoutCounter;
	private int mySpoutId;

	public SetenceSpout() {
		// TODO Auto-generated constructor stub
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@--CONSTTT");
		spoutCounter = 1;
	}

	public void open(Map map, TopologyContext ctx, SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this._collector = collector;
		this.mySpoutId = spoutCounter;
		spoutCounter++;
		try {
			ss = MyServerSocket.INSTANCE.getServerSocket(port);
		} catch (Exception e) {
			System.out.println("##############error " + e);
		}
	}

	public void nextTuple() {
		// TODO Auto-generated method stub
		StringBuilder string = new StringBuilder();
		try {
			System.out.println("############SPOUT WAITING...ID " + mySpoutId);
			s = ss.accept();
			InputStream is = s.getInputStream();

			BufferedReader read = new BufferedReader(new InputStreamReader(is));
			String temp = null;
			temp = read.readLine();
			System.out.println("##########INCOMING String " + temp.toString()+" Handled by SPOUTID "+mySpoutId);
			Utils.sleep(100);
			// doing unanchoring
			// not reliable as we r not passing any tuple
			// so storm will not track this tuple
			_collector.emit(new Values(temp.toString(), is, s));
			// for reliablity do this
			// now if tuple processed then ack will called and if failed then
			// fail method get called
			// _collector.emit(new Values(temp.toString(), is, s),"some message
			// id");
		} catch (Exception e) {
			System.out.println("############next " + e.toString());
		}
	}

	@Override
	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		// Log.error("Sentence Ack");
	}

	@Override
	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		// Log.error("Sentence Fail");
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		// TODO Auto-generated method stub
		arg0.declare(new Fields("word", "inputStream", "socket"));
	}
}
