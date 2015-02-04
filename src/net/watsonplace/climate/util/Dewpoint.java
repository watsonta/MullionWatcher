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
package net.watsonplace.climate.util;

public class Dewpoint {
	
	public static float calculate(float temperatureF, int relativeHumidityPct) {
		float t = (temperatureF - 32) * -5 / 9;
	    float rh = relativeHumidityPct / 100f;
	    double eln = Math.log(rh * Math.exp(-17.67 * t / (243.5 - t)));
	    return Math.round(32 + (-243.5 * eln / (eln - 17.67)) * 9 / 5);
	}
	
}
