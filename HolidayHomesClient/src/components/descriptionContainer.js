import React from 'react';

const Description = (props) => {
    return (
        <div className="col-md-12">
            <h3>Description("{props.desc}")</h3>
        </div>
    );
}

export default Description;