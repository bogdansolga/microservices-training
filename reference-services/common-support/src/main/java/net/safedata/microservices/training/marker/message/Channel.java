package net.safedata.microservices.training.marker.message;

// generated from the APIBuilder JSON files, used throughout the entire system
public enum Channel {

    // Customer channels
    CUSTOMER_CREATED(Channels.CUSTOMER_CREATED),
    CUSTOMER_UPDATED(Channels.CUSTOMER_UPDATED),

    // Order channels
    CHARGE_ORDER(Channels.CHARGE_ORDER),
    CREATE_ORDER(Channels.CREATE_ORDER),
    FIND_ORDER(Channels.FIND_ORDER),
    ORDER_CHARGED(Channels.ORDER_CHARGED),
    ORDER_CREATED(Channels.ORDER_CREATED),
    ORDER_SHIPPED(Channels.ORDER_SHIPPED),
    ORDER_NOT_CHARGED(Channels.ORDER_NOT_CHARGED),
    SHIP_ORDER(Channels.SHIP_ORDER)
    ;

    private final String channelName;

    Channel(final String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
