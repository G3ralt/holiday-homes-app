import React from 'react';

const RatingAvg = (props) => {
    return (
        <div key={props.pName} className="col-md-4 placeRating">
            <h4>Rating (average)("{props.avgRating}")</h4>
            <p>no stars, just number</p>
        </div>
    );
}

export default RatingAvg;