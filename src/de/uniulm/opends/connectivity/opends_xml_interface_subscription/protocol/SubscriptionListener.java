package de.uniulm.opends.connectivity.opends_xml_interface_subscription.protocol;

public interface SubscriptionListener {

	public void eventReceived(OpenDSValue<?> value);
}
