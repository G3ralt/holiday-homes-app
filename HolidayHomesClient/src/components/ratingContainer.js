import React from 'react';

const Rating = (props) => {
    return (
        <div className="col-md-4 placeRating">
            <h3>Rating("{props.pRating}")</h3>
            <p>stars here</p>
        </div>
    );
}

export default Rating;