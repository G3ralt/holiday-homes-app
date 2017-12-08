import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
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
        // console.log("JSON:", json);
        const options = fetchHelper.makeOptions("POST", true, json);
        fetch(URL + "api/booking/create", options)
        .then(
            window.location.href = this.state.clientOrigin + "/#/myDashboard/myBookings"
        )
        .catch(err => {
            console.log(err);
        })
        
    }

    render() {
        return (
            <div className="row col-md-12" style={{ margin: '10px 0 0 0' }}>
                <div className="col-xs-3">
                    <div className="price-tag">Price: <b>{this.props.rPrice}</b> dkk/week</div>
                </div>
                <div className="col-xs-2">
                    <h4 style={{ fontFamily: 'Changa', textAlign: 'right' }}>Availability:</h4>
                </div>
                <div className="col-xs-2">
                    <div className="select">
                        <select name="slct" id={this.props.rName}>
                            {this.props.weeks.map(week => {
                                return <option key={week} value={week}>{week}</option>
                            })
                            }
                        </select>
                    </div>
                </div>
                <div className="col-xs-2">
                    {
                        auth.isloggedIn &&
                        (
                            <Link to="/myDashboard/myBookings" className="btn btn-danger btn-lg rentButton" onClick={this.rent}>RENT</Link>
                            /*<button type="button" className="btn btn-danger btn-lg rentButton" onClick={this.rent}>RENT</button>*/
                        )
                    }
                </div>
            </div>
        );
    }
}