package com.xenondigilabs.tutorials.stormTutorials.tcpServerSpout.resources;

import java.net.ServerSocket;

public enum MyServerSocket {

	INSTANCE;
	public static ServerSocket ss;

	private synchronized void start(int portNo) {
		try {
			ss = new ServerSocket(portNo);
		} catch (Exception e) {
			System.out.println("### in start " + e);
		}
	}

	public ServerSocket getServerSocket(int portNo) {
		try {
			if (ss == null) {
				start(portNo);
			}
		} catch (Exception e) {
			System.out.println("#### getServerSocket " + e);
		}
		return ss;
	}
}
