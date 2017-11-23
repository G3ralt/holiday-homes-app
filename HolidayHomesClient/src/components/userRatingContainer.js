import React from 'react';

const RatingUser = (props) => {
    return (
        <div className="col-md-4 placeRating">
            <h3>Rating (from user)("{props.userRating}")</h3>
            <p>stars here</p>
        </div>
    );
}

export default RatingUser;