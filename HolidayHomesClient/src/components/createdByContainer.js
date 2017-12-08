import React from 'react';

const CreatedByUser = (props) => {
    return (
        <div className="col-md-4 createdBy">
            <p>(Created By: {props.uName})</p>
        </div>
    );
};

export default CreatedByUser;