import React, {Component} from "react";
import { Address, Description, Image, RentableName, RatingAvg, RentRentable, CreatedByUser, Zvezdichka } from '../components/importContainers';
import auth from '../authorization/auth';
import fetchHelper from "../facades/fetchHelpers";
const URL = require("../../package.json").serverURL;

export default class Rentables extends Component{
        
  constructor(){
      super();
	  this.state = { rentableInfo: [], userItself: { username: "unauthorized" }, allPlaces: [] };;
    }
    
  async componentWillMount() {
      await this.getAllPlaces();
      this.getAllRentables();
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
                let rInfo = data.map(rentable => {
                    return (
                        <div key={rentable.rentableName} className="row nicePlace">
                            <Image img={rentable.imgURL} />
							<RentableName rName={rentable.rentableName} />
							{auth.isloggedIn && auth.isAdmin && (<CreatedByUser uName={rentable.admin.username} />)}
                            <RatingAvg avgRating={rentable.rating} />
                            {auth.isloggedIn && auth.isUser && (<Zvezdichka userRating={rentable.userRating} rName={rentable.rentableName} currentUser={this.state.userItself} />) }
                            <Address street={rentable.street} city={rentable.city}  zipCode={rentable.zipCode} country={rentable.country}/> {/* Passed like address object */}
                            <RentRentable rPrice={rentable.price} weeks={rentable.availableWeeks} rName={rentable.rentableName} uName={auth.username} />
                            <Description desc={rentable.description} />
                        </div>
                    )
                });
                this.setState({ rentableInfo: rInfo });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
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
                this.setState({ allPlaces: data });
            }).catch(err => {
                console.log(JSON.stringify(err));
            })
    }


  render() {
      return (
        <div>
		<h2>All you can Rent</h2>
		<div className="container-fluid nicePlaces">
			{this.state.rentableInfo}
		</div>
	</div>
      );
    }
}

