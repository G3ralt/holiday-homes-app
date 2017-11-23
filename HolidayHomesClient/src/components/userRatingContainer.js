import React from 'react';

const RatingUser = (props) => {
    return (
        <div className="col-md-4 placeRating">
            <h3>Rating (from current user)("{props.userRating}")</h3>
            <i class="fa fa-star fa-2x" aria-hidden="true"></i>
            <i class="fa fa-star fa-2x" aria-hidden="true"></i>
            <i class="fa fa-star fa-2x" aria-hidden="true"></i>
            <i class="fa fa-star-o fa-2x" aria-hidden="true"></i>
            <i class="fa fa-star-o fa-2x" aria-hidden="true"></i>
        </div>
    );
}

export default RatingUser;