'use strict'

class LinesController {

    constructor(map){
        this.map = map;
        this.lineList = {};
    }

    setLineByID(line){
        this.deleteLineById(line.getId());
        let points = line.getPoints();
        let coordinates = [];
        for(let i = 0; i < points.size(); i++) {
            coordinates.push(
                { lat: points.get(i).getLatitude(), lng: points.get(i).getLongitude()}
            );
        }
        let newLine = new google.maps.Polyline({
            path: coordinates,
            geodesic:true,
            strokeColor: line.getColor(),
            strokeOpacity: 1.0,
            strokeWeight: line.getWeight()
        });
        if( line.isVisible() )
            newLine.setMap(map);
        this.lineList[line.getId()] = newLine;
    }

    deleteLineById(lineId){
        if(lineId in this.lineList){
            this.lineList[lineId].setMap(null);
            this.lineList[lineId] = null;
        }
    }

    showLine(id){
        if (id in this.lineList) {
            this.lineList[id].setMap(map);
        }
    }

    hideLine(id){
        if(id in this.lineList){
            this.lineList[id].setMap(null);
        }
    }

}
