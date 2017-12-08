import React, { Component } from "react";
import { Image, RentableName, BookedWeek } from '../components/importContainers';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;

export default class UserBookings extends Component {
    constructor() {
        super();
        this.state = { bookingInfo: [], userItself: { username: "unauthorized" } };;
    }

    componentWillMount() {
        this.getAllBookings();
    }

    getAllBookings = (cb) => {
        let userItself = this.state.userItself;
        /*console.log("Is the user logged in? : ", auth.isloggedIn);*/
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        }
        let options = fetchHelper.makeOptions("GET", true)
        fetch(URL + "api/booking/allForUser/" + this.state.userItself.username, options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                let rInfo = data.map(booking => {
                    return (
                        <div key={booking.weekNumber + booking.rentable.rentableName} className="row nicePlace">
                            <hr />
                            <Image img={booking.rentable.imgURL} />
                            <RentableName rName={booking.rentable.rentableName} />
                            <BookedWeek bWeek={booking.weekNumber} />
                        </div>
                    )
                });
                this.setState({ bookingInfo: rInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }

    render() {
        return (
            <div>
                <button className="btn btn-primary" onClick={() => { window.history.back() }}>Go Back</button>
                <h2 style={{ fontFamily: 'Changa' }}>All My Bookings</h2>
                <div className="container-fluid userBookings">
                    {this.state.bookingInfo}
                    {(this.state.bookingInfo.length === 0) && (<p>(You haven't booked anything yet.)</p>)}
                </div>
            </div>
        );
    }
}

