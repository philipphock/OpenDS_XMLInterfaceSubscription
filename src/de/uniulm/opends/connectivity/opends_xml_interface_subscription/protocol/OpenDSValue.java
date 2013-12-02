package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

public class OpenDSValue<V> {
	private final V value;
	private final Subscription type;
	public OpenDSValue(Subscription type, V value) {
		this.value = value;
		this.type = type;
	}
	
	public V getValue() {
		return value;
	}
	
	public Subscription getType() {
		return type;
	}
	
	@Override
	public String toString() {
	
		return type+": "+value;
	}
	
	

}
