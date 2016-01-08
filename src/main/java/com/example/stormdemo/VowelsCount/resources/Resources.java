package com.example.stormdemo.VowelsCount.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Resources {

	public static void close(Object obj) throws IOException {

		if (obj instanceof PrintWriter) {
			((PrintWriter) obj).close();
		}
		if (obj instanceof InputStream) {
			((InputStream) obj).close();
		}

		if (obj instanceof Socket) {
			((Socket) obj).close();
		}

	}
}
