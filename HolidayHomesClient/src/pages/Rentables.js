import React, {Component} from "react";
import { Address, Description, Image, RentableName, RatingAvg, Price, CreatedByUser, Zvezdichka } from '../components/importContainers';
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
        /*console.log("Is the user logged in? : ", auth.isloggedIn);*/
        if (auth.isloggedIn) {
            userItself.username = auth.username;
            this.setState({ userItself: userItself });
        }
        /*
        console.log("USER NAME from auth: ", auth.username);
        console.log("User Name from State: ", this.state.userItself.username);
        */

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
							<CreatedByUser uName={rentable.admin.username} />
                            <RatingAvg avgRating={rentable.rating} />
                            {auth.isloggedIn && auth.isUser && (<Zvezdichka userRating={rentable.userRating} rName={rentable.rentableName} currentUser={this.state.userItself} />) }
                            <Address street={rentable.street} city={rentable.city}  zipCode={rentable.zipcode} country={rentable.country}/> {/* Passed like address object */}
                            <Price rPrice={rentable.price} weeks={rentable.availableWeeks} rName={rentable.rentableName} uName={auth.username} />
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

