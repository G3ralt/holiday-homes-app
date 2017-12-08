import React, { Component } from "react";
import { RentablesMapWithPlacesAround, Description, Image, RentableName, RatingAvg, RentRentable, CreatedByUser, Zvezdichka } from '../components/importContainers';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;

export default class Rentable extends Component {

    constructor(props) {
        super(props);
        this.state = { rentableInfo: [], userItself: { username: "unauthorized" }, allPlaces: [] };
        
    }

    componentWillMount() {
        this.getAllPlaces();
    }

    getAllPlaces = async (cb) => {
        let userItself = this.state.userItself;
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        }
        const options = fetchHelper.makeOptions("POST", true, userItself);

        await fetch(URL + "api/places/all", options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                this.setState({ allPlaces: data });
            }).catch(err => {
                console.log(JSON.stringify(err));
            });
        this.getRentable();
    }

    getRentable = (cb) => {
        let name = this.props.match.url;
        name = name.substring(name.lastIndexOf("/")+1, name.length);
        let json = {
            username: this.state.userItself.username,
            rentableName: name
        }
        
        const options = fetchHelper.makeOptions("POST", false, json);
        fetch(URL + "api/rentables/rentable", options)
            .then((res) => {
                return res.json();
            }).then((rentable) => {
                let rInfo = 
                     (
                        <div key={rentable.rentableName} className="row nicePlace">
                            <hr />
                            <Image img={rentable.imgURL} />
                            <RentableName rName={rentable.rentableName} />
                            {auth.isloggedIn && auth.isAdmin && (<CreatedByUser uName={rentable.admin.username} />)}
                            <RatingAvg avgRating={rentable.rating} />
                            {auth.isloggedIn && auth.isUser && (<Zvezdichka userRating={rentable.userRating} rName={rentable.rentableName} currentUser={this.state.userItself} />)}
                            <RentablesMapWithPlacesAround rGPSlat={rentable.gpsLat} rGPSlong={rentable.gpsLong} rName={rentable.rentableName} allPlaces={this.state.allPlaces} />
                            <RentRentable rPrice={rentable.price} weeks={rentable.availableWeeks} rName={rentable.rentableName} uName={auth.username} />
                            <Description desc={rentable.description} />
                        </div>
                    )
                
                this.setState({ rentableInfo: rInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }

    


    render() {
        return (
            <div>
                <h2 style={{ fontFamily: 'Changa' }}></h2>
                <div className="container-fluid nicePlaces">
                    {this.state.rentableInfo}
                </div>
            </div>
        );
    }
}

