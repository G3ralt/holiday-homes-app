import React from 'react';

const Address = (props) => {
    return (
        <div className="col-md-4">
            <h3>Address:</h3>
            <p>Street:   {props.street}</p>
            <p>City:     {props.city}</p>
            <p>ZIP Code: {props.zipCode}</p>
            <p>Country:  {props.country}</p>
        </div>
    );
}

export default Address;