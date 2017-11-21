import React from 'react';

const Image = (props) => {
    return (
        <div className="col-md-8">
            <img src={props.pIMG} className="img-thumbnail img-responsive" alt=""/>
        </div>
    );
}

export default Image;