import React from 'react';

const GPSinfo = (props) => {
    return (
        <div className="col-md-4 placeGPS">
            <h3>GPSinfo:</h3>
            <p>Latitude:   {props.pGPSlat}</p>
            <p>Longtitude: {props.pGPSlong}</p>
        </div>
    );
}

export default GPSinfo;