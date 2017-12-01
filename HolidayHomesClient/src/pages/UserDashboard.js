import React, { Component } from 'react';
import { Link, NavLink } from 'react-router-dom';

export default class UserDashboard extends Component {
    constructor() {
        super();
        this.state = {  };
    }

    render() {
        return (
            /*Here using Link and NavLink in order to learn about the exact difference between them*/
            <div>
                <h2>My Dashboard</h2>
                <Link to="/myDashboard/myBookings">See My Bookings</Link>
                <p>(Everything you booked so far.)</p>
                <br />
                <NavLink to="/myDashboard/myPlaces">See My Places</NavLink>
                <p>(All the places you have created so far.)</p>
            </div>
        );
    }
}