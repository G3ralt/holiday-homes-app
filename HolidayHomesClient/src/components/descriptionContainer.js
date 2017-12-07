import React from 'react';

const Description = (props) => {
    return (
        <div className="col-md-12 placeDesc">
            <h3>Description:</h3>
            <p>{props.desc}</p>
        </div>
    );
}

export default Description;