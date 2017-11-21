import React from 'react';

const Address = (props) => {
    return (
        <div className="col-md-4 placeAddress">
            <h3>Address:</h3>
            <p>Street:   {props.pStreet}</p>
            <p>City:     {props.pCity}</p>
            <p>ZIP Code: {props.pZIP}</p>
            <p>Country:  {props.pCountry}</p>
        </div>
    );
}

export default Address;