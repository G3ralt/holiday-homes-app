import React from 'react';

const PlaceName = (props) => {
    return (
        <div className="col-md-4 placeName">
            <h3>{props.pName}</h3>
        </div>
    );
}

export default PlaceName;