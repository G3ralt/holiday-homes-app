import React, {Component} from "react";
import { Address, PlaceDescription, GPSinfo, Image, PlaceName, Rating, CreatedBy } from '../components/importContainers';
import placesData from "../facades/placesFacade";
import auth from '../authorization/auth';
class Rentables extends Component{
        
  constructor(){
      super();
	  this.state = { rentableInfo: [], whatToRender: this.props.match.params.whatToRender, userItself: { username: "unauthorized" } };;
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

        fetch(URL + "api/rentable/all", options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                let rInfo = data.map(rentable => {
                    return (
                        <div key={rentable.locationName} className="row nicePlace">
                            <Image pIMG={rentable.imgURL} />
							<PlaceName pName={rentable.locationName} />
							<CreatedBy uName={rentable.username} />
                            <Rating pRating={rentable.rating} />
                            <GPSinfo pGPSlat={rentable.gpsLat} pGPSlong={rentable.gpsLong} />
                            <PlaceDescription pDesc={rentable.description} />
                        </div>
                    )
                });
                this.setState({ rentableInfo: rInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }


  render() {
		var rows = this.mapData(this.state.data);
      return (
        <div>
		<h2>All Rentables</h2>
		<div className="container-fluid nicePlaces">
			{this.state.rentableInfo}
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
export default Rentables;
