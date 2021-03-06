import React from 'react';
import { Description, PlacesMapWithRentablesAround, Image, PlaceName, RatingAvg, CreatedByUser, Zvezdichka } from '../components/importContainers';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;


export default class Places extends React.Component {
    constructor(props) {
        super(props);
        this.state = { placeInfo: [], userItself: { username: "unauthorized" }, createdByUser: "Not active user!", allRentables: [] };
    }

    componentWillMount() {
        this.getAllRentables();
    }

    getAllPlaces = (cb) => {
        let userItself = this.state.userItself;
        /*console.log("Is the user logged in? : ", auth.isloggedIn);*/
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        }
        console.log('Places/ vednaga sled seta :', this.state.userItself);
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
                            <hr />
                            <Image img={place.imgURL} />
                            <PlaceName pName={place.placeName} />
                            <CreatedByUser uName={this.state.createdByUser} />
                            <RatingAvg avgRating={place.rating} pName={place.placeName} />
                            {auth.isloggedIn && auth.isUser && (<Zvezdichka userRating={place.userRating} pName={place.placeName} currentUser={this.state.userItself} />)}
                            <PlacesMapWithRentablesAround pGPSlat={place.gpsLat} pGPSlong={place.gpsLong} pName={place.placeName} allRentables={this.state.allRentables} />
                            <Description desc={place.description} />
                        </div>
                    )
                });
                this.setState({ placeInfo: pInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }

    getAllRentables = async (cb) => {
        let userItself = this.state.userItself;
        /*console.log("Is the user logged in? : ", auth.isloggedIn);*/
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        };
        /*
        console.log("USER NAME from auth: ", auth.username);
        console.log("User Name from State: ", this.state.userItself.username);
        */

        const options = fetchHelper.makeOptions("POST", true, userItself);
        await fetch(URL + "api/rentables/all", options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                this.setState({ allRentables: data });
            }).catch(err => {
                console.log(JSON.stringify(err));
            });
        this.getAllPlaces();
    }


    render() {
        // console.log('####  RENDER');
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

