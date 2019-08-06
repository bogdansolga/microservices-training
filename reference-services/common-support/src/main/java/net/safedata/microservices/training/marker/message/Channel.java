package net.safedata.microservices.training.marker.message;

// generated from the APIBuilder JSON files, used throughout the entire system
public enum Channel {

    // Customer channels
    BILL_CUSTOMER(Channels.Outbound.BILL_CUSTOMER),
    CUSTOMER_CREATED(Channels.Inbound.CUSTOMER_CREATED),
    CUSTOMER_UPDATED(Channels.Inbound.CUSTOMER_UPDATED),

    // Order channels
    CHARGE_ORDER(Channels.Outbound.CHARGE_ORDER),
    CREATE_ORDER(Channels.Inbound.CREATE_ORDER),
    FIND_ORDER(Channels.Outbound.FIND_ORDER),
    ORDER_CHARGED(Channels.Inbound.ORDER_CHARGED),
    ORDER_SHIPPED(Channels.Inbound.ORDER_SHIPPED),
    ORDER_NOT_CHARGED(Channels.Inbound.ORDER_NOT_CHARGED),
    SHIP_ORDER(Channels.Outbound.SHIP_ORDER),
    ;

    private final String channelName;

    Channel(final String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
