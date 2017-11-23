import React from 'react';

const Price = (props) => {
    return (
        <div className="col-md-4 placeName">
            <h3>Price("{props.rPrice}")</h3>
        </div>
    );
}

export default Price;