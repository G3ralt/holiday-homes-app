import React from 'react';
import { Description, GPSinfo, Image, PlaceName, RatingAvg, Zvezdichka } from '../components/importContainers';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;

export default class UserPlaces extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            placeInfo: [],
            userItself: { username: "unauthorized" },
            createdByUser: "Not active user!"
        };
    }

    componentWillMount() {
        this.getAllPlaces();
    }

    getAllPlaces = (cb) => {
        let userItself = this.state.userItself;
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        }
        let options = fetchHelper.makeOptions("GET", true);
        fetch(URL + "api/places/all/" + this.state.userItself.username, options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                let pInfo = data.map(place => {
                    if (place.hasOwnProperty("user")) {
                        this.setState({ createdByUser: place.user.username });
                    }
                    return (
                        <div key={place.placeName} className="row nicePlace">
                            <Image img={place.imgURL} />
                            <PlaceName pName={place.placeName} />
                            <RatingAvg avgRating={place.rating} pName={place.placeName} />
                            {auth.isloggedIn && auth.isUser && (<Zvezdichka userRating={place.userRating} pName={place.placeName} currentUser={this.state.userItself} />)}
                            <GPSinfo pGPSlat={place.gpsLat} pGPSlong={place.gpsLong} />
                            <Description desc={place.description} />
                        </div>
                    )
                });
                this.setState({ placeInfo: pInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }

    render() {
        return (
            <div>
                <button onClick={() => { window.history.back() }}>Go Back</button>
                <h2>All Places created by Me</h2>
                <div className="container-fluid noPlacesAndBookingsFound">
                    {this.state.placeInfo}
                    {(this.state.placeInfo.length === 0) && (<p>(You haven't created a Place yet.)</p>)}
                </div>
            </div>
        );
    }
}

