package net.watsonplace.ecobee.api;

/**
 * Represents an Ecobee PIN.
 * @author tom
 * 
 */
public class PIN {
    private String ecobeePin, code, scope;
    private long expires_in, interval;

    public PIN() {}

    @Override
    public String toString() {
        return "PIN{" +
                "ecobeePin='" + ecobeePin + '\'' +
                ", code='" + code + '\'' +
                ", scope='" + scope + '\'' +
                ", expires_in=" + expires_in +
                ", interval=" + interval +
                '}';
    }

	public String getEcobeePin() {
		return ecobeePin;
	}

	public String getCode() {
		return code;
	}

	public String getScope() {
		return scope;
	}

	public long getExpires_in() {
		return expires_in;
	}

	public long getInterval() {
		return interval;
	}
    
}
