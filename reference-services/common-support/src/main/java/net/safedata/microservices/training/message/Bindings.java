package net.safedata.microservices.training.message;

import net.safedata.microservices.training.marker.message.Channels;

public enum Bindings {
    ORDER_CREATED("orderCreatedProducer-out-0", Channels.Events.ORDER_CREATED),
    ORDER_CHARGED("orderChargedProducer-out-0", Channels.Events.ORDER_CHARGED),
    CHARGE_ORDER("chargeOrderProducer-out-0", Channels.Commands.CHARGE_ORDER),
    CUSTOMER_CREATED("customerCreatedProducer-out-0", Channels.Events.CUSTOMER_CREATED),
    CUSTOMER_UPDATED("customerUpdatedProducer-out-0", Channels.Events.CUSTOMER_UPDATED),
    ORDER_SHIPPED("orderShippedProducer-out-0", Channels.Events.ORDER_SHIPPED);

    private final String bindingName;
    private final String channelName;

    Bindings(String bindingName, String channelName) {
        this.bindingName = bindingName;
        this.channelName = channelName;
    }

    public String getBindingName() {
        return bindingName;
    }

    public String getChannelName() {
        return channelName;
    }
}
