import React from 'react';

const RatingAvg = (props) => {
    return (
        <div key={props.pName} className="col-md-4 placeRating">
            <h4>Rating : {props.avgRating} <i className="fa fa-star"></i> (average)</h4>
        </div>
    );
}

export default RatingAvg;