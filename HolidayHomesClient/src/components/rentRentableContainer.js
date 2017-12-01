import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import auth from '../authorization/auth';
const URL = require("../../package.json").serverURL;

export default class RentRentable extends Component {
    constructor(props) {
        super(props);
        this.state = { clientOrigin: window.location.origin };
    }

    rent = () => {
        let dropdown = document.getElementById(this.props.rName);
        let weekNumber = dropdown.options[dropdown.selectedIndex].value;
        let username = this.props.uName;
        const json = {
            weekNumber: weekNumber,
            rentable: {
                rentableName: this.props.rName
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
        alert("Booking created");
        window.location.href = this.state.clientOrigin+"/#/myDashboard/myBookings";
    }

    render() {
        return (
            <div className="col-md-4 rentablePrice">
                <h4 className="pricePrice">Price("{this.props.rPrice}")</h4>
                {
                    auth.isloggedIn &&
                    (
                        <Link to="/myDashboard/myBookings" className="btn btn-danger btn-lg rentButton" onClick={this.rent}>RENT</Link>
                        /*<button type="button" className="btn btn-danger btn-lg rentButton" onClick={this.rent}>RENT</button>*/
                    )
                }
                <br />
                <p>Availability:</p>
                <select id={this.props.rName}>
                    {this.props.weeks.map(week => {
                        return <option key={week} value={week}>{week}</option>
                    })
                    }
                </select>
            </div>
        );
    }
}