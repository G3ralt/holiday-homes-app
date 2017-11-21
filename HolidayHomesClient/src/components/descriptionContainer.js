import React from 'react';

const PlaceDescription = (props) => {
    return (
        <div className="col-md-12 placeDesc">
            <h3>PlaceDescription("{props.pDesc}")</h3>
        </div>
    );
}

export default PlaceDescription;