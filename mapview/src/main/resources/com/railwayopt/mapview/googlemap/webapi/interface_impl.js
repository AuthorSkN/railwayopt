
var map;
var pointsController;
var linesController;
var markerClusters = [];

function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 55.76, lng: 37.64},
        zoom: 8
    });
    pointsController = new PointsController(map);
    linesController = new LinesController(map);
    window.controller.loadedMap();
}

function addLine(line){
    linesController.setLineByID(line);
}

function changeLine(line){
    addLine(line);
}

function deleteLine(lineId){
    linesController.deleteLineById(lineId);
}

function addMarkerGroup(grIdxMarkers, st){
    var grMarkers = [];
    var picture, type;
    switch (st.getShape()){
        case 0: picture = google.maps.SymbolPath.CIRCLE; break;
        case 1: picture = 'M -1,1 1,1 1,-1 -1,-1 z'; break;
        case 2: picture = 'M -1,1 1,1, 0,-1 z';break;
    }
    for(var i = 0; i < grIdxMarkers.size(); i++){
        var marker = pointList[grIdxMarkers.get(i)];
        marker.icon.path = picture;
        marker.icon.fillColor =st.getColorFill();
        marker.icon.strokeColor = st.getColorContour();
        marker.icon.strokeWeight = st.getWeightContour();
        grMarkers.push(marker);
    }
    var markerCluster = new MarkerClusterer(map, grMarkers,{
        gridSize: 30,
        styles: [{
            fillColor: st.getColorFill(),
            strokeColor: st.getColorContour(),
            strokeWeight: st.getWeightContour(),
            shape: st.getShape()
        }]
    });
    markerClusters.push(markerCluster);
}


function addPoint(point){
    pointsController.setPointByID(point);
}

function changePoint(point){
    addPoint(point);
}

function deletePoint(pointId){
    pointsController.deletePointByID(pointId);
}


function showObject(id, type){
    switch(type){
        case 0: pointsController.showPoint(id); break;
        case 1: linesController.showLine(id); break;
    }
}

function hideObject(id, type){
    switch(type){
        case 0: pointsController.hidePoint(id); break;
        case 1: linesController.hideLine(id); break;
    }
}

function clearPoints(){
    for(var i = 0; i < markerClusters.length; i++)
        markerClusters[0].clearMarkers();
    for(var i = 0; i < pointList.length; i++)
        pointList[0].setMap(null);
    markerClusters = [];
    pointList = [];
}

function clearLines(){
    for(var i = 0; i < lineList.length; i++)
        lineList[i].setMap(null);
    lineList = [];
}

function centering(point){
    map.panTo(new google.maps.LatLng(point.getLatitude(), point.getLongitude()));
}


