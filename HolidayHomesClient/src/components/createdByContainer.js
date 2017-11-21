import React from 'react';

const CreatedBy = (props) => {
    return (
        <div className="col-md-4 createdBy">
            <h3>Created By {props.uName}</h3>
        </div>
    );
};

export default CreatedBy;