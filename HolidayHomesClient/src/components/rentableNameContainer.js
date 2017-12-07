import React from 'react';

const RentableName = (props) => {
    return (
        <div className="col-md-4 placeName">
            <h3>{props.rName}</h3>
        </div>
    );
}

export default RentableName;