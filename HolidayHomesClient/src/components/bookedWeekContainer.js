import React from 'react';

const BookedWeek = (props) => {
    return (
        <div className="col-md-4">
            <h3 style={{ fontFamily: 'Changa' }}>Booked for Week:</h3>
            <h3 style={{ fontFamily: 'Changa' }}>{props.bWeek}</h3>
        </div>
    );
}

export default BookedWeek;