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

import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;


/**
 * MullionState - holds the last temperature sample for the mullion
 * @author tom
 *
 * TODO: Notice failure to update (dead battery)
 */
public class MullionState {
	private static final Logger logger = Logger.getLogger(MullionState.class.getName());
	private static final long ONE_MINUTE = 60000;
	
	private static MullionState singleton = null;
	private HashMap<String, TemperatureSample> sensorState;
	
	public static MullionState getInstance() {
		if (singleton == null) {
			singleton = new MullionState();
		}
		return singleton;
	}
	
	private MullionState() {
		sensorState = new HashMap<String, TemperatureSample>();
	}
	
	// Update state with new sample
	public synchronized void update(TemperatureSample temperatureSample) {
		String key = temperatureSample.getSensorName();
		// New sensor?
		if (!sensorState.containsKey(key)) {
			logger.info("Discovered sensor "+key);
		}
		sensorState.put(key, temperatureSample);
	}

	// Return sample with the lowest temperature
	public synchronized TemperatureSample getTemperature() {
		Iterator<TemperatureSample> i = sensorState.values().iterator();
		TemperatureSample lowest = null;
		while (i.hasNext()) {
			TemperatureSample ts = i.next();
			// Missing sensor?
			if (ts.getTimestamp() < System.currentTimeMillis()-5*ONE_MINUTE) {
				logger.info("Lost sensor "+ts.getSensorName());
				i.remove();
				continue;
			}
			lowest = (lowest == null || ts.getTemperature() < lowest.getTemperature()) ? ts : lowest;
		}
		return lowest;
	}
	
	// Return number of sensors
	public synchronized int getSensorCount() {
		return sensorState.size();
	}

}
