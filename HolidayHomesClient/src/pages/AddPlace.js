import React, { Component } from "react";
import auth from "../authorization/auth";

class AddPlace extends Component{

	constructor() {
		super();
		this.state = { err: "", place: {gpsLat:"", gpsLong:"",  description:"", rating:"", imgUri:""}}
	  }

          handleSubmit = (event) => {
		event.preventDefault()
		const gpsLat = this.state.place.gpsLat;
		const gpsLong = this.state.place.gpsLong;
		const description = this.state.place.description;
		const rating = this.state.place.rating;
		const imgUri = this.state.place.imgUri;
                
                
		auth.addPlace(gpsLat, gpsLong, description, rating, imgUri, (err, loggedIn) => {
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
          
          <label htmlFor="inputGpsLat" className="sr-only">GPS Latitude</label>
          <input type="text" value={this.state.place.gpsLat} onChange={this.onChange} className="form-control" id="gpsLat" placeholder="Gps Latitude" />
          <label htmlFor="inputGpsLong" className="sr-only">GPS Longitude</label>
          <input type="text" value={this.state.place.gpsLong} onChange={this.onChange} className="form-control" id="gpsLong" placeholder="Gps Longitude" />
          
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