import React from 'react';

const RatingAvg = (props) => {
    return (
        <div className="col-md-4 placeRating">
            <h3>Rating (average)("{props.avgRating}")</h3>
            <p>no stars, just number</p>
        </div>
    );
}

export default RatingAvg;