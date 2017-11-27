import React from 'react';
import { Address, Description, GPSinfo, Image, PlaceName, RatingAvg, RatingUser, CreatedByUser } from '../components/importContainers';
import placesData from "../facades/placesFacade";
import auth from '../authorization/auth';
const URL = require("../../package.json").serverURL;


export default class Places extends React.Component{        
  constructor(props){
      super(props);
	  this.state = { placeInfo: [], userItself: { username: "unauthorized" }, createdByUser: "Not active user!" };;
    }
    
  componentWillMount() {
      this.getAllPlaces();
	}  

	getAllPlaces = (cb) => {
        let userItself = this.state.userItself;
        console.log("Is the user logged in? : ", auth.isloggedIn);
        if (auth.isloggedIn) {
            userItself.username = auth.userName;
            this.setState({ userItself: userItself });
        }
        console.log("USER NAME from auth: ", auth.userName);
        console.log("User Name from State: ", this.state.userItself.username);

        const options = {
            method: "POST",
            body: JSON.stringify(this.state.userItself),
            headers: { "Content-Type": "application/json" }
        }

        fetch(URL + "api/places/all", options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                let pInfo = data.map(place => {
                    if (place.hasOwnProperty("user")) {
                        this.setState({createdByUser: place.user.username});
                    }
                    return (
                        <div key={place.placeName} className="row nicePlace">
                            <Image img={place.imgURL} />
							<PlaceName pName={place.placeName} />
                            <RatingAvg avgRating={place.rating} />
                            { auth.isloggedIn && auth.isUser && (<RatingUser userRating={place.userRating} />) }
                            <CreatedByUser uName={this.state.createdByUser} />
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
		<h2>All Nice Places</h2>
		<div className="container-fluid nicePlaces">
			{this.state.placeInfo}
		</div>
		{this.state.data1 && (
			<div className="alert alert-danger errmsg-left" role="alert">
				{this.state.data1}
			</div>
		)}
	</div>
      );
    }
}

