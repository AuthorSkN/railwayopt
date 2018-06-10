'use strict';

class PointsController {

    constructor(map) {
        this.map = map;
        this.pointList = {};
    }

    getShapePath(shapeStyle){
        let path = '';
        switch (shapeStyle){
            case 0: path = google.maps.SymbolPath.CIRCLE; break;
            case 1: path = 'M -1,1 1,1 1,-1 -1,-1 z'; break;
            case 2: path = 'M -1,1 1,1, 0,-1 z';break;
        }
        return path;
    }

    getPicture(style, weight){
        return {
            fillColor: style.getColorFill(),
            fillOpacity: 1.0,
            scale: weight,
            strokeColor: style.getColorContour(),
            strokeWeight: style.getWeightContour(),
            path: this.getShapePath(style.getShape())
        };
    }


    setPointByID(point){
        this.deletePointByID(point.getId());
        let picture = this.getPicture(point.getStyle(), point.getWeight());
        let latitude = point.getCoordinates().getLatitude();
        let longitude = point.getCoordinates().getLongitude();
        let newMarker = new google.maps.Marker({
            position: {lat: latitude, lng: longitude},
            title: point.getTitle(),
            icon: picture
        });

        if( point.isVisible() )
            newMarker.setMap(this.map);
        this.pointList[point.getId()] = newMarker;
    }

    deletePointByID(id){
        if(id in this.pointList){
            this.pointList[id].setMap(null);
            delete this.pointList[id];
        }
    }


    showPoint(id){
        if (id in this.pointList) {
            this.pointList[id].setMap(map);
        }
    }

    hidePoint(id){
        if(id in this.pointList){
            this.pointList[id].setMap(null);
        }
    }



}
