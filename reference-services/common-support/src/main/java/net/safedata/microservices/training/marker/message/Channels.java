package net.safedata.microservices.training.marker.message;

public final class Channels {

    // commands
    public static class Commands {
        public static final String CHARGE_ORDER = "charge_order";
        public static final String CREATE_ORDER = "create_order";
        public static final String SHIP_ORDER = "ship_order";
        public static final String PROCESS_ORDER = "process_order";
        public static final String DELIVER_ORDER = "deliver_order";
    }

    // events
    public static class Events {
        public static final String CUSTOMER_CREATED = "customer_created";

        public static final String ORDER_UPDATED = "order_updated";
        public static final String CUSTOMER_UPDATED = "customer_updated";
        public static final String ORDER_CHARGED = "order_charged";
        public static final String ORDER_CREATED = "order_created";
        public static final String ORDER_NOT_CHARGED = "order_not_charged";
        public static final String ORDER_SHIPPED = "order_shipped";
        public static final String ORDER_PROCESSED = "order_processed";
        public static final String ORDER_DELIVERED = "order_delivered";
    }

    // queries
    public static class Queries {
        public static final String FIND_ORDER = "find_order";
    }

    // deny instantiation
    private Channels() {}
}
