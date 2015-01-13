/**
 * This file is part of MullionWatcher.
 *
 * MullionWatcher is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * MullionWatcher is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with MullionWatcher.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.watsonplace.mullion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.Vector;

import net.watsonplace.MullionWatcher;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Sender extends Thread {
	private static final Logger logger = Logger.getLogger(Sender.class.getName());

	private String receiver;
	private int port;
	
	public Sender(String host, int port) throws Exception {
		if (host == null || host.length() == 0) {
			throw new Exception("Invalid hostname");
		}
		if (port < 1000) {
			throw new Exception("Invalid port");
		}
		this.receiver = host;
		this.port = port;
	}

	/* Send the String "s" as text via UDP to host "host" on port "port" */
	private void send(String s, String host, int port) throws Exception {
	      DatagramSocket clientSocket = new DatagramSocket();
	      InetAddress IPAddress = InetAddress.getByName(host);
	      byte[] sendData = new byte[64];
	      sendData = s.getBytes();
	      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
	      clientSocket.send(sendPacket);
	      clientSocket.close();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Thread#run()
	 * 
	 * This thread listens to stdin for lines piped from the command: "hcidump -R -l 62"
	 * 
	 * Note: The command "hcitool lescan" must also be running to produce packet flow
	 */
	public void run() {
		String line;
		Vector<String> v = new Vector<String>();
		
		// Listen to stdin
		BufferedReader bufferedInput = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			// Get a line from stdin
			line = null;
			try {
				line = bufferedInput.readLine();
			} catch (IOException e) {
				logger.error("Fatal error reading from input", e);
				MullionWatcher.shutdown();
				return;
			}
			System.out.println(line);

			// If nothing to do, then wait
			if (line == null) {
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {}
				continue;
			}
			
			// Identify the starting line
			if (line.substring(0, 2).startsWith("> ")) {
				v.clear();
			}
			
			// Strip first two chars
			line = line.substring(2);

			// Tokenize the line of hex pairs
			StringTokenizer st = new StringTokenizer(line, " ");
			while (st.hasMoreTokens()) {
				v.add(st.nextToken());
			}

			// If we have enough tokens, evaluate it
			if (v.size() > 33) {
				// Extract ID (Hex, Tokens 7,8,11,12)
				String id = new StringBuilder(v.elementAt(7)).reverse().toString()
					+ new StringBuilder(v.elementAt(8)).reverse().toString()
					+ new StringBuilder(v.elementAt(11)).reverse().toString()
					+ new StringBuilder(v.elementAt(12)).reverse().toString();
				
				// Extract name (ASCII, Bytes 19-26)
				StringBuffer sb = new StringBuffer();
				for (int i=19; i<=26; i++) {
					sb.append((char)Integer.parseInt(v.elementAt(i),16));
				}
				String name = sb.toString();
				
				// If ID not same as name, then this isn't a myBlueT device
				if (!id.equals(name)) continue;
				
				// Select the three temperature bytes and convert to F degrees
				int value = Integer.parseInt(v.elementAt(32)+v.elementAt(31),16);
				if (v.elementAt(33).equals("FF")) value -= 0x10000;
                float temperatureF = 32 + 9*(value/100f)/5;
                temperatureF = Math.round(10*temperatureF)/10f;
                
                // Create TemperatureSample and convert it to json
                TemperatureSample ts = new TemperatureSample(name, temperatureF, System.currentTimeMillis());
                Gson gson = new GsonBuilder().create();
                String jsonMessage = gson.toJson(ts);
                
                // Send it
                logger.debug("Sending temperature sample");
				try {
					send(jsonMessage, receiver, port);
				} catch (Exception e) {
					System.err.println("Couldn't send UDP packet");
				}
                
                // Clear the token buffer
				v.clear();
			}
		}
	}
	
}