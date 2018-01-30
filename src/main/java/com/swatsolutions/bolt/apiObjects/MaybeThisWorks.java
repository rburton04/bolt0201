package com.swatsolutions.bolt.apiObjects;

public class MaybeThisWorks {
	private DemoTwoObject address;

	public DemoTwoObject getAddress() {
		return address;
	}

	public void setAddress(DemoTwoObject address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "MaybeThisWorks{" +
				"address=" + address +
				'}';
	}
}
