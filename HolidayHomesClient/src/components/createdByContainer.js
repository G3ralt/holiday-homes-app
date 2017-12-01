import React from 'react';

const CreatedByUser = (props) => {
    return (
        <div className="col-md-4">
            <h3>Created By ("{props.uName}")</h3>
        </div>
    );
};

export default CreatedByUser;