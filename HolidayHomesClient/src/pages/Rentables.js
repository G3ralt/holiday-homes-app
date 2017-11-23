import React, {Component} from "react";
import { Address, Description, GPSinfo, Image, RentableName, RatingAvg, RatingUser, Price, CreatedByUser } from '../components/importContainers';
import placesData from "../facades/placesFacade";
import auth from '../authorization/auth';
const URL = require("../../package.json").serverURL;

class Rentables extends Component{
        
  constructor(){
      super();
	  this.state = { rentableInfo: [], userItself: { username: "unauthorized" } };;
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

        fetch(URL + "api/rentables/all", options)
            .then((res) => {
                return res.json();
            }).then((data) => {
                let rInfo = data.map(rentable => {
                    return (
                        <div key={rentable.rentableName} className="row nicePlace">
                            <Image img={rentable.imgURL} />
							<RentableName rName={rentable.rentableName} />
							<CreatedByUser uName={rentable.username} />
                            <RatingAvg avgRating={rentable.rating} />
                            <RatingUser userRating={rentable.userRating} />
                            <Address street={rentable.street} city={rentable.city}  zipCode={rentable.zipcode} country={rentable.country}/> {/* Passed like address object */}
                            <Price rPrice={rentable.price} />
                            <Description desc={rentable.description} />
                        </div>
                    )
                });
                this.setState({ rentableInfo: rInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }


  render() {
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

