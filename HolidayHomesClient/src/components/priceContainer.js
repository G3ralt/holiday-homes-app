import React from 'react';
import auth from '../authorization/auth';
const URL = require("../../package.json").serverURL;

const Price = (props) => {
    
    function rent() {
        let dropdown = document.getElementById(props.rName);
        let weekNumber = dropdown.options[dropdown.selectedIndex].value;
        let username = props.uName;
        const json = {
            weekNumber: weekNumber,
            rentableName: {
                rentableName: props.rName
            },
            user: {
                username: username
                
            }
        }
        console.log("JSON:", json);
        const options = {
            method: "POST",
            body: JSON.stringify(json),
            headers: { "Content-Type": "application/json" }
        }
        fetch(URL + "api/booking/create", options).catch(err => {
            console.log(err);
        })
    }
    
    return (
        <div className="col-md-4 rentablePrice">
            <h4 className="pricePrice">Price("{props.rPrice}")</h4>
            {
                auth.isloggedIn &&
            (<button type="button" className="btn btn-danger btn-lg rentButton" onClick={rent}>RENT</button>)
            }
            <br/>
            <p>Availability:</p>
            <select id={props.rName}>
            {props.weeks.map(week => {
                
                return <option key={week} value={week}>{week}</option>
            })
            
        }
          </select>
        </div>
    );
}

export default Price;