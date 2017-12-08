import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import { Link } from "react-router-dom";

const PlaceMarkers = ({ text }) => {
  let path = "/places/" + text;
  return (
    <div>
      <Link to={path} className='placePin bounce'></Link>
      <div className='textLabel'>{text}</div>
      <div className='placePulse'></div>
    </div>
  )
};

const RentableMarker = ({ text }) => {
  return (
    <div key={text}>
      <div className='rentablePin bounce'></div>
      <div className='textLabel'>{text}</div>
      <div className='rentablePulse'></div>
    </div>
  )
};

let allPlaceMarkers;

export default class Map extends Component {
  //   static defaultProps = {
  //     center: { lat: 40.7446790, lng: -73.9485420 },
  //     zoom: 11
  //   }
  // pGPSlat={place.gpsLat} pGPSlong={place.gpsLong}

  constructor(props) {
    super(props);
    this.state = {
      center: { lat: this.props.rGPSlat, lng: this.props.rGPSlong },
      zoom: 15
    }
    allPlaceMarkers = this.props.allPlaces.map(place => {
      return (
          <PlaceMarkers key={place.placeName} lat={place.gpsLat} lng={place.gpsLong} text={place.placeName} />
      )
    });
  }
  render() {
    return (
      <div className='col-md-4 nopadding google-map'>
        <GoogleMapReact
          options={{ fullscreenControl: false }}
          bootstrapURLKeys={{ key: 'AIzaSyCBkhStVuUZ51OOLMY5YM_npWc_Lxr70Ro' }}
          defaultCenter={this.state.center}
          defaultZoom={this.state.zoom}>
          <RentableMarker lat={this.state.center.lat} lng={this.state.center.lng} text={this.props.pName} />
          {allPlaceMarkers}
        </GoogleMapReact>
      </div>
    )
  }
}