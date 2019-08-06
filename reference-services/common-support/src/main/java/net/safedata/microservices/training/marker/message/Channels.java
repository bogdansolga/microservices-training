package net.safedata.microservices.training.marker.message;

public final class Channels {

    public static final class Inbound {
        // commands
        public static final String CREATE_ORDER = "create_order";

        // events
        public static final String CUSTOMER_CREATED = "customer_created";
        public static final String CUSTOMER_UPDATED = "customer_updated";
        public static final String ORDER_CHARGED = "order_charged";
        public static final String ORDER_CREATED = "order_created";
        public static final String ORDER_NOT_CHARGED = "order_not_charged";
        public static final String ORDER_SHIPPED = "order_shipped";
    }

    public static final class Outbound {
        // commands
        public static final String BILL_CUSTOMER = "bill_customer";
        public static final String CHARGE_ORDER = "charge_order";
        public static final String SHIP_ORDER = "ship_order";

        // queries
        public static final String FIND_ORDER = "find_order";
    }

    // deny instantiation
    private Channels() {}
}
