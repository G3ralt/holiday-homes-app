import React, {Component} from "react";
import { Address, PlaceDescription, GPSinfo, Image, PlaceName, Rating } from '../components/importContainers';
import placesData from "../facades/placesFacade";
import auth from '../authorization/auth';
class Places extends Component{
        
  constructor(){
      super();
	  this.state = { placeInfo: [], whatToRender: this.props.match.params.whatToRender, userItself: { username: "unauthorized" } };;
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

        fetch(URL + "api/niceplace/all", options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                let pInfo = data.map(place => {
                    return (
                        <div key={place.locationName} className="row nicePlace">
                            <Image pIMG={place.imgURL} />
                            <PlaceName pName={place.locationName} />
                            <Rating pRating={place.rating} />
                            <Address pStreet={place.street} pCity={place.city}  pZIP={place.zipCode} pCountry={place.country}/> {/* Passed like address object */}
                            <GPSinfo pGPSlat={place.gpsLat} pGPSlong={place.gpsLong} />
                            <PlaceDescription pDesc={place.description} />
                        </div>
                    )
                });
                this.setState({ placeInfo: pInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }


  render() {
		var rows = this.mapData(this.state.data);
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
export default Places;

