import React from 'react';
import { Description, MyMap, Image, PlaceName, RatingAvg, CreatedByUser, Zvezdichka } from '../components/importContainers';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;


export default class Places extends React.Component {
    constructor(props) {
        super(props);
        this.state = { placeInfo: [], userItself: { username: "unauthorized" }, createdByUser: "Not active user!", allRentables: {} };
    }

    async componentWillMount() {
        await this.getAllRentables();
        this.getAllPlaces();
    }

    getAllPlaces = (cb) => {
        let userItself = this.state.userItself;
        /*console.log("Is the user logged in? : ", auth.isloggedIn);*/
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        }
        /*
        console.log("Username from auth: ", auth.username);
        console.log("Username from State: ", this.state.userItself.username);
        */

        const options = fetchHelper.makeOptions("POST", false, userItself);

        fetch(URL + "api/places/all", options)
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
                            <CreatedByUser uName={this.state.createdByUser} />
                            <MyMap pGPSlat={place.gpsLat} pGPSlong={place.gpsLong} pName={place.placeName} allRentables={this.state.allRentables} />
                            <Description desc={place.description} />
                        </div>
                    )
                });
                this.setState({ placeInfo: pInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }

    getAllRentables = (cb) => {
        let userItself = this.state.userItself;
        /*console.log("Is the user logged in? : ", auth.isloggedIn);*/
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        }
        /*
        console.log("USER NAME from auth: ", auth.username);
        console.log("User Name from State: ", this.state.userItself.username);
        */

        const options = fetchHelper.makeOptions("POST", true, userItself);
        fetch(URL + "api/rentables/all", options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                this.setState({ allRentables: data });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }


    render() {
        return (
            <div>
                <h2>All Nice Places</h2>
                <div className="container-fluid nicePlaces">
                    {this.state.placeInfo}
                </div>
            </div>
        );
    }
}

