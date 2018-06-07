package com.railwayopt.mapview.event;

public class EventHandler {

    private MapEventListener onLoadedMapListener;

    public void onLoadedMap(MapEventListener listener){
        onLoadedMapListener = listener;
    }

    public void loadedMap(){
        onLoadedMapListener.run();
    }
}
