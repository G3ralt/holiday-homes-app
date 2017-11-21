import React, { Component } from "react";
import auth from "../authorization/auth";

class AddRentable extends Component{

	constructor() {
		super();
		this.state = { err: "", rentable: {city:"",zip:"", street:"", description:"", rating:"", imgUri:""}}
	  }

          handleSubmit = (event) => {
		event.preventDefault()
		const city = this.state.rentable.city;
		const zip = this.state.rentable.zip;
		const street = this.state.rentable.street;
        const description = this.state.rentable.description;
		const rating = this.state.rentable.rating;
		const imgUri = this.state.rentable.imgUri;
                
                
		auth.addrentable(city, zip, street, description, rating, imgUri, (err, loggedIn) => {
		  if (err) {
			return this.setState({ err: err.errorMessage });
		  }
		  this.setState({ err: "" });
		  this.props.history.push("/");
		});
		window.location.href = "/#/addrentable";
	  }
          
          onChange = (e) => {
		const propertyName = e.target.id;
		const value = e.target.value;
		let rentable = this.state.rentable;
		rentable[propertyName] = value;
		this.setState({rentable});
	  }
          
         render() {
		return (
		<div className="container">
        <form className="form-signin" onSubmit={this.handleSubmit}>
          <h2 className="form-signin-heading">Add a new rentable!</h2>
          <label htmlFor="inputCity" className="sr-only">City</label>
          <input type="text" value={this.state.rentable.city} onChange={this.onChange} className="form-control" id="city" placeholder="City" required autoFocus />
          
        <label htmlFor="inputZip" className="sr-only">ZIP code</label>
        <input type="number" value={this.state.rentable.zip} onChange={this.onChange} className="form-control" id="zip" placeholder="Zip" required />
          
          <label htmlFor="inputStreet" className="sr-only">Street</label>
          <input type="text" value={this.state.rentable.street} onChange={this.onChange} className="form-control" id="street" placeholder="Street" required />
          
          <label htmlFor="inputDescription" className="sr-only">Description</label>
          <input type="text" value={this.state.rentable.description} onChange={this.onChange} className="form-control" id="description" placeholder="Description" required />
          
          <label htmlFor="inputRating" className="sr-only">Rating</label>
          <input type="number" value={this.state.rentable.rating} onChange={this.onChange} className="form-control" id="rating" placeholder="Rating" required />
          
          <label htmlFor="inputImgUri" className="sr-only">Image Uri</label>
          <input type="text" value={this.state.rentable.imgUri} onChange={this.onChange} className="form-control" id="imgUri" placeholder="Img Uri" required />
          
          <button className="btn btn-lg btn-primary btn-block" type="submit">Add rentable</button>
          
          <br />
        </form>
      </div>//
	)
  }

}

export default AddRentable	;