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

import java.io.IOException;
import java.io.StringReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

import net.watsonplace.MullionWatcher;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class Receiver extends Thread {
	private static final Logger logger = Logger.getLogger(Receiver.class.getName());
	
	private byte[] receiveData;
	DatagramSocket serverSocket;
	MullionState mullionState;

	public Receiver(int port) throws SocketException {
		receiveData = new byte[128];
		serverSocket = new DatagramSocket(port);
		mullionState = MullionState.getInstance();
	}

	public void run() {

		while (true) {
			// Get data packet payload
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				logger.error("Receive error", e);
				serverSocket.close();
				MullionWatcher.shutdown();
				return;
			}
			String payload = new String(receivePacket.getData());
			logger.debug("Received packet");

			// Decode data packet as json (class net.watsonplace.myBlueT.TemperatureSample)
            Gson gson = new GsonBuilder().create();
            JsonReader reader = new JsonReader(new StringReader(payload));
            reader.setLenient(true);
            TemperatureSample ts = gson.fromJson(reader, TemperatureSample.class);
            mullionState.update(ts);
            logger.debug("Updated mullion state");
		}
	}

}
