import React from 'react';

const Price = (props) => {
    return (
        <div className="col-md-4 rentablePrice">
            <h4 className="pricePrice">Price("{props.rPrice}")</h4>
            <button type="button" className="btn btn-danger btn-lg rentButton">RENT</button>
            <br/>
            <p>Availability:</p>
            <select>
            <option value="volvo">Week 47</option>
            <option value="saab">Week 48</option>
            <option value="opel">Week 49</option>
            <option value="audi">Week 50</option>
          </select>
        </div>
    );
}

export default Price;