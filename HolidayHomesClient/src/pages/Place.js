import React from 'react';
import { Description, PlacesMapWithRentablesAround, Image, PlaceName, RatingAvg, CreatedByUser, Zvezdichka } from '../components/importContainers';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;


export default class Place extends React.Component {
    constructor(props) {
        super(props);
        this.state = { placeInfo: [], userItself: { username: "unauthorized" }, createdByUser: "Not active user!", allRentables: [] };
    }

    componentWillMount() {
        this.getAllRentables();
    }

    getAllRentables = async (cb) => {
        let userItself = this.state.userItself;
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        }

        const options = fetchHelper.makeOptions("POST", true, userItself);
        await fetch(URL + "api/rentables/all", options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                this.setState({ allRentables: data });
            }).catch(err => {
                console.log(JSON.stringify(err));
            });
            this.getPlace();
    }

    getPlace = (cb) => {
        let name = this.props.match.url;
        name = name.substring(name.lastIndexOf("/")+1, name.length);
        let json = {
            username: this.state.userItself.username,
            placeName: name
        }
        const options = fetchHelper.makeOptions("POST", false, json);

        fetch(URL + "api/places/place", options)
            .then((res) => {
                return res.json();
            }).then((place) => {
                if (place.hasOwnProperty("user")) {
                    this.setState({ createdByUser: place.user.username });
                }
                let pInfo = (
                        <div key={place.placeName} className="row nicePlace">
                            <Image img={place.imgURL} />
                            <PlaceName pName={place.placeName} />
                            <RatingAvg avgRating={place.rating} pName={place.placeName} />
                            {auth.isloggedIn && auth.isUser && (<Zvezdichka userRating={place.userRating} pName={place.placeName} currentUser={this.state.userItself} />)}
                            <CreatedByUser uName={this.state.createdByUser} />
                            <PlacesMapWithRentablesAround pGPSlat={place.gpsLat} pGPSlong={place.gpsLong} pName={place.placeName} allRentables={this.state.allRentables} />
                            <Description desc={place.description} />
                        </div>
                    )
               
                this.setState({ placeInfo: pInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }

    


    render() {
        // console.log('####  RENDER');
        return (
            <div>
                <h2></h2>
                <div className="container-fluid nicePlaces">
                    {this.state.placeInfo}
                </div>
            </div>
        );
    }
}

