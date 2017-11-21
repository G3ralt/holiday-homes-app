import React from 'react';

const CreatedByUser = (props) => {
    return (
        <div className="col-md-4 createdBy">
            <h3>Created By ("{props.createdByProp}")</h3>
        </div>
    );
};

export default CreatedByUser;