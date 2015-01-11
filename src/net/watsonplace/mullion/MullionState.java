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
	public synchronized TemperatureSample getLowestTemperature() {
		Iterator<TemperatureSample> i = sensorState.values().iterator();
		TemperatureSample lowest = null;
		while (i.hasNext()) {
			TemperatureSample ts = i.next();
			// Missing sensor?
			if (ts.getTimestamp() < System.currentTimeMillis()-5*ONE_MINUTE) {
				logger.info("Lost sensor "+ts.getSensorName());
				sensorState.remove(ts.getSensorName());
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
