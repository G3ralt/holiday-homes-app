import React from 'react';

const BookedWeek = (props) => {
    return (
        <div className="col-md-4">
            <h3>Booked for Week ("{props.bWeek}")</h3>
        </div>
    );
}

export default BookedWeek;