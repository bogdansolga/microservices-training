package net.safedata.microservices.training.marker.message;

// generated from the APIBuilder JSON files, used throughout the entire system
public enum Channel {

    // Order channels
    CREATE_ORDER(Channels.CREATE_ORDER);

    private final String channelName;

    Channel(final String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }
}
