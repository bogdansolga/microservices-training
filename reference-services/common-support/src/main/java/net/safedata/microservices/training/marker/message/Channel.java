package net.safedata.microservices.training.marker.message;

// generated from the APIBuilder JSON files, used throughout the entire system
public enum Channel {

    // Customer channels
    CUSTOMER_CREATED(Channels.Events.CUSTOMER_CREATED),
    CUSTOMER_UPDATED(Channels.Events.CUSTOMER_UPDATED),

    // Order channels
    CHARGE_ORDER(Channels.Commands.CHARGE_ORDER),
    CREATE_ORDER(Channels.Commands.CREATE_ORDER),
    FIND_ORDER(Channels.Queries.FIND_ORDER),
    ORDER_CHARGED(Channels.Events.ORDER_CHARGED),
    ORDER_CREATED(Channels.Events.ORDER_CREATED),

    ORDER_PROCESSED(Channels.Events.ORDER_PROCESSED),
    ORDER_SHIPPED(Channels.Events.ORDER_SHIPPED),

    ORDER_DELIVERED(Channels.Events.ORDER_DELIVERED),
    ORDER_NOT_CHARGED(Channels.Events.ORDER_NOT_CHARGED),
    SHIP_ORDER(Channels.Commands.SHIP_ORDER),
    PROCESS_ORDER(Channels.Commands.PROCESS_ORDER),
    DELIVER_ORDER(Channels.Commands.DELIVER_ORDER);

    private final String channelName;

    Channel(final String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
