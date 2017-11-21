import React, { Component } from "react";
import auth from "../authorization/auth";

class AddPlace extends Component{

	constructor() {
		super();
		this.state = { err: "", place: {city:"",zip:"", street:"", gpsLocation:"", description:"", rating:"", imgUri:""}}
	  }

          handleSubmit = (event) => {
		event.preventDefault()
		const city = this.state.place.city;
		const zip = this.state.place.zip;
		const street = this.state.place.street;
		const gpsLocation = this.state.place.gpsLocation;
                const description = this.state.place.description;
		const rating = this.state.place.rating;
		const imgUri = this.state.place.imgUri;
                
                
		auth.addPlace(city, zip, street, gpsLocation, description, rating, imgUri, (err, loggedIn) => {
		  if (err) {
			return this.setState({ err: err.errorMessage });
		  }
		  this.setState({ err: "" });
		  this.props.history.push("/");
		});
		window.location.href = "/#/addplace";
	  }
          
          onChange = (e) => {
		const propertyName = e.target.id;
		const value = e.target.value;
		let place = this.state.place;
		place[propertyName] = value;
		this.setState({place});
	  }
          
         render() {
		return (
		<div className="container">
        <form className="form-signin" onSubmit={this.handleSubmit}>
          <h2 className="form-signin-heading">Add a new place!</h2>
          <label htmlFor="inputCity" className="sr-only">City</label>
          <input type="text" value={this.state.place.city} onChange={this.onChange} className="form-control" id="city" placeholder="City" required autoFocus />
          
        <label htmlFor="inputZip" className="sr-only">ZIP code</label>
        <input type="number" value={this.state.place.zip} onChange={this.onChange} className="form-control" id="zip" placeholder="Zip" required />
          
          <label htmlFor="inputStreet" className="sr-only">Street</label>
          <input type="text" value={this.state.place.street} onChange={this.onChange} className="form-control" id="street" placeholder="Street" required />
          
          <label htmlFor="inputGpsLocation" className="sr-only">GPS Location</label>
          <input type="text" value={this.state.place.gpsLocation} onChange={this.onChange} className="form-control" id="gpsLocation" placeholder="Gps Location" />
          
          <label htmlFor="inputDescription" className="sr-only">Description</label>
          <input type="text" value={this.state.place.description} onChange={this.onChange} className="form-control" id="description" placeholder="Description" required />
          
          <label htmlFor="inputRating" className="sr-only">Rating</label>
          <input type="number" value={this.state.place.rating} onChange={this.onChange} className="form-control" id="rating" placeholder="Rating" required />
          
          <label htmlFor="inputImgUri" className="sr-only">Image Uri</label>
          <input type="text" value={this.state.place.imgUri} onChange={this.onChange} className="form-control" id="imgUri" placeholder="Img Uri" required />
          
          <button className="btn btn-lg btn-primary btn-block" type="submit">Add Place</button>
          
          <br />
        </form>
      </div>//
	)
  }

}

export default AddPlace	;